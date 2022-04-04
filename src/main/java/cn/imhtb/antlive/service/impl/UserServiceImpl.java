package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.mappers.AuthMapper;
import cn.imhtb.antlive.mappers.BillMapper;
import cn.imhtb.antlive.mappers.RoomMapper;
import cn.imhtb.antlive.mappers.UserMapper;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.utils.RedisUtils;
import cn.imhtb.antlive.vo.request.RegisterRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    private final RoomMapper roomMapper;

    private final ModelMapper modelMapper;

    private final RedisUtils redisUtils;

    private final BillMapper billMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserMapper userMapper, AuthMapper authMapper, ModelMapper modelMapper, RedisUtils redisUtils, RoomMapper roomMapper, BillMapper billMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
        this.modelMapper = modelMapper;
        this.redisUtils = redisUtils;
        this.roomMapper = roomMapper;
        this.billMapper = billMapper;
    }

    @Override
    public User login(String account, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (account.contains("@")){
            wrapper.eq("email",account);
        }else{
            wrapper.eq("mobile",account);
        }
        User user = userMapper.selectOne(wrapper);
        if (encoder.matches(password,user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean[] getSecurityInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        boolean[] checked = new boolean[3];
        checked[0] = !StringUtils.isEmpty(user.getEmail());
        checked[1] = !StringUtils.isEmpty(user.getMobile());
        long count = authMapper.selectCount(new QueryWrapper<AuthInfo>().eq("user_id", userId).eq("status", 1));
        checked[2] = (count !=0 );
        return checked;
    }

    @Override
    public void updateStatusByIds(Integer[] ids,Integer status) {
        //TODO 优化
        User update = new User();
        for (Integer id : ids) {
            update.setId(id);
            update.setDisabled(status);
            userMapper.updateById(update);
        }

    }

    @Override
    public ApiResponse register(RegisterRequest request) {
        String account = request.getAccount();
        String verifyCode = request.getCode();
        String redisCode;
        User u = modelMapper.map(request,User.class);
        u.setPassword(encoder.encode(u.getPassword()));
        if (account.contains("@")){
            redisCode = redisUtils.get("email:" + account).toString();
            User exist = userMapper.selectOne(new QueryWrapper<User>().eq("email", account));
            if (exist != null){
                return ApiResponse.ofError("该邮箱已被注册");
            }
            u.setEmail(account);
        }else {
            redisCode = redisUtils.get("mobile:" + account).toString();
            User exist = userMapper.selectOne(new QueryWrapper<User>().eq("mobile", account));
            if (exist != null){
                return ApiResponse.ofError("该手机号已被注册");
            }
            u.setMobile(account);
        }
        if (!redisCode.isEmpty() && redisCode.equals(verifyCode)){
            userMapper.insert(u);
            log.info("注册返回id" + u.getId());
            roomMapper.insert(Room.builder().userId(u.getId()).build());
            billMapper.insert(Bill.builder().userId(u.getId()).balance(new BigDecimal(0)).billChange(new BigDecimal(0)).type(0).mark("初始化账单").build());
        }else {
            return ApiResponse.ofError("验证码错误，请重新尝试");
        }
        return ApiResponse.ofSuccess("注册成功");
    }

    @Override
    public Integer countToday() {
        return userMapper.countToday();
    }

}
