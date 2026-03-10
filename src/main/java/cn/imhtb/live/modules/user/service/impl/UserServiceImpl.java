package cn.imhtb.live.modules.user.service.impl;

import cn.imhtb.live.common.constants.AntLiveConstant;
import cn.imhtb.live.common.exception.BusinessException;
import cn.imhtb.live.common.exception.base.UserErrorCode;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.mappers.UserMapper;
import cn.imhtb.live.modules.user.model.req.UserExtraReq;
import cn.imhtb.live.modules.user.model.req.UserInfoUpdateReq;
import cn.imhtb.live.modules.user.model.req.UserRegisterReq;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.database.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pinteh
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Cache<String, String> cache;

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
    public boolean register(UserRegisterReq request) {
        // 校验用户信息
        if (checkExistUsername(request.getUsername())) {
            throw new BusinessException(UserErrorCode.ERR_USERNAME_REPEAT);
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(encoder.encode(user.getPassword()));

        // 保存用户信息
        save(user);
        log.info("user register info, userId = {}", user.getId());

        // 初始化部分数据
        // TODO 后续推荐这部分数据用懒加载的方式进行初始化
        initializeUserRegisterData(user.getId());
        return true;
    }

    /**
     * 初始化用户注册数据
     *
     * @param userId 用户标识
     */
    private void initializeUserRegisterData(Integer userId) {
//        roomService.save(Room.builder().userId(userId).build());
//        billService.save(Bill.builder()
//                .userId(userId)
//                .orderNo(CommonUtil.getOrderNo())
//                .balance(BigDecimal.ZERO)
//                .billChange(BigDecimal.ZERO)
//                .type(BillTypeEnum.INCOME.getCode())
//                .mark("初始化账单")
//                .build());
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
    public boolean bindUserExtra(UserExtraReq userExtraReq) {
        // todo
        User user = getById(UserHolder.getUserId());
        String account = user.getAccount();
        String verifyCode = cache.getIfPresent(getCacheKey(userExtraReq.getType(), account));
        if (StringUtils.equalsIgnoreCase(verifyCode, userExtraReq.getVerifyCode())) {
            return false;
        }
        switch (userExtraReq.getType()) {
            case AntLiveConstant.EMAIL:
                user.setEmail(userExtraReq.getVal());
                break;
            case AntLiveConstant.MOBILE:
                user.setMobile(userExtraReq.getVal());
                break;
            default:
        }
        return updateById(user);
    }

    @Override
    public boolean updateUserInfo(UserInfoUpdateReq request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setId(UserHolder.getUserId());
        if (StringUtils.isEmpty(user.getSignature())){
            user.setSignature("");
        }
        return updateById(user);
    }

    @Override
    public User getUserInfo() {
        return getById(UserHolder.getUserId());
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        User user = new User();
        user.setId(UserHolder.getUserId());
        user.setAvatar(avatarUrl);
        updateById(user);
    }

    private String getCacheKey(String type, String account) {
        return type + ":" + account;
    }

}
