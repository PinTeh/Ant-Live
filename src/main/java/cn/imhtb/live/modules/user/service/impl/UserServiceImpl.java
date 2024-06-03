package cn.imhtb.live.modules.user.service.impl;

import cn.imhtb.live.constants.AntLiveConstant;
import cn.imhtb.live.enums.AuthStatusEnum;
import cn.imhtb.live.enums.BillTypeEnum;
import cn.imhtb.live.exception.BusinessException;
import cn.imhtb.live.exception.base.CommonErrorCode;
import cn.imhtb.live.mappers.UserMapper;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.AuthInfo;
import cn.imhtb.live.pojo.Bill;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.vo.request.BindExtraInfoRequestVO;
import cn.imhtb.live.pojo.vo.request.RegisterRequest;
import cn.imhtb.live.pojo.vo.request.UserInfoUpdateRequest;
import cn.imhtb.live.service.IAuthService;
import cn.imhtb.live.service.IBillService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import cn.imhtb.live.utils.CommonUtil;
import cn.imhtb.live.utils.MailUtil;
import cn.imhtb.live.utils.SmsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author pinteh
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private Cache<String, String> cache;
    @Resource
    private IAuthService authService;
    @Resource
    private IRoomService roomService;
    @Resource
    private IBillService billService;
    @Resource
    private SmsService smsService;
    @Resource
    private MailUtil mailUtils;
    @Resource
    private ITokenService tokenService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User getByAccount(String account) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account));
    }

    @Override
    public User login(String account, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (account.contains(AntLiveConstant.AT)) {
            wrapper.eq("email", account);
        } else {
            wrapper.eq("mobile", account);
        }
        User user = getOne(wrapper);
        if (encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean[] getSecurityInfo() {
        User user = getById(tokenService.getUserId());
        boolean[] checked = new boolean[3];
        checked[0] = StringUtils.isNotEmpty(user.getEmail());
        checked[1] = StringUtils.isNotEmpty(user.getMobile());
        // 获取身份认证状态
        long count = authService.count(new LambdaQueryWrapper<AuthInfo>()
                .eq(AuthInfo::getUserId, user.getId())
                .eq(AuthInfo::getStatus, AuthStatusEnum.PASS.getCode()));
        checked[2] = (count != 0);
        return checked;
    }

    @Override
    public void updateStatusByIds(Integer[] ids, Integer status) {
        //TODO 优化
        User update = new User();
        for (Integer id : ids) {
            update.setId(id);
            update.setDisabled(status);
            updateById(update);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(RegisterRequest request) {
        // 校验用户信息
        if (checkExistUsername(request.getUsername())) {
            throw new BusinessException(CommonErrorCode.SERVICE_ERROR, "当前用户名已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(encoder.encode(user.getPassword()));

        // 保存用户信息
        save(user);
        log.info("user register info, userId = {}", user.getId());

        // 初始化部分数据
        initializeUserRegisterData(user.getId());
        return true;
    }

    /**
     * 初始化用户注册数据
     *
     * @param userId 用户标识
     */
    private void initializeUserRegisterData(Integer userId) {
        roomService.save(Room.builder().userId(userId).build());
        billService.save(Bill.builder()
                .userId(userId)
                .orderNo(CommonUtil.getOrderNo())
                .balance(BigDecimal.ZERO)
                .billChange(BigDecimal.ZERO)
                .type(BillTypeEnum.INCOME.getCode())
                .mark("初始化账单")
                .build());
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return boolean
     */
    private boolean checkExistUsername(String username) {
        return count(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0;
    }

    @Override
    public Integer countToday() {
        return this.baseMapper.countToday();
    }

    @Override
    public boolean generateVerifyCode(String account) {
        int verifyCode = CommonUtil.getRandomCode();
        if (account.contains(AntLiveConstant.AT)) {
            String key = getCacheKey(AntLiveConstant.EMAIL, account);
            cache.put(key, String.valueOf(verifyCode));
            mailUtils.sendSimpleMessage(account, "直播注册验证码", "您的动态验证码为：" + verifyCode + "，有效时间为2分钟，如非本人操作，请忽略本短信！");
        } else if (account.length() == AntLiveConstant.MOBILE_LENGTH) {
            String key = getCacheKey(AntLiveConstant.MOBILE, account);
            cache.put(key, String.valueOf(verifyCode));
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(verifyCode));
            smsService.txSmsSend(account, params, "verifyCode");
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean bindExtraInfo(BindExtraInfoRequestVO bindExtraInfoRequestVO) {
        Integer userId = tokenService.getUserId();
        // todo
        User user = getById(userId);
        String account = user.getAccount();
        String verifyCode = cache.getIfPresent(getCacheKey(bindExtraInfoRequestVO.getType(), account));
        if (StringUtils.equalsIgnoreCase(verifyCode, bindExtraInfoRequestVO.getVerifyCode())) {
            return false;
        }
        switch (bindExtraInfoRequestVO.getType()) {
            case AntLiveConstant.EMAIL:
                user.setEmail(bindExtraInfoRequestVO.getVal());
                break;
            case AntLiveConstant.MOBILE:
                user.setMobile(bindExtraInfoRequestVO.getVal());
                break;
            default:
        }
        return updateById(user);
    }

    @Override
    public boolean updateUserInfo(UserInfoUpdateRequest request) {
        Integer userId = tokenService.getUserId();
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setId(userId);
        return updateById(user);
    }

    @Override
    public User getUserInfo() {
        return getById(tokenService.getUserId());
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Integer userId = tokenService.getUserId();
        User user = new User();
        user.setId(userId);
        user.setAvatar(avatarUrl);
        updateById(user);
    }

    private String getCacheKey(String type, String account) {
        return type + ":" + account;
    }

}
