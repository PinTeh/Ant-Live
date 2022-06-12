package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.ApiResponse;
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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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

    @Resource
    private IUserService userService;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResponse register(RegisterRequest request) {
        if (checkExistUsername(request.getUsername())){
            return ApiResponse.ofError("当前用户名已存在");
        }

        User user = modelMapper.map(request,User.class);
        user.setPassword(encoder.encode(user.getPassword()));

        // 保存用户信息
        userMapper.insert(user);
        log.info("注册用户信息, userId = {}", user.getId());

        // 初始化部分数据
        initializeUserRegisterData(user);
        return ApiResponse.ofSuccess("注册成功");
    }

    /**
     * 初始化用户注册数据
     *
     * @param user 用户
     */
    private void initializeUserRegisterData(User user) {
        roomMapper.insert(Room.builder().userId(user.getId()).build());
        billMapper.insert(Bill.builder()
                .userId(user.getId())
                .balance(new BigDecimal(0))
                .billChange(new BigDecimal(0))
                .type(0)
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
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return count > 0;
    }

    @Override
    public Integer countToday() {
        return userMapper.countToday();
    }

}
