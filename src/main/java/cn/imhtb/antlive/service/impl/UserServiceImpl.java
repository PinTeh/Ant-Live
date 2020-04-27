package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.mappers.AuthMapper;
import cn.imhtb.antlive.mappers.UserMapper;
import cn.imhtb.antlive.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserMapper userMapper, AuthMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
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
        Integer count = authMapper.selectCount(new QueryWrapper<AuthInfo>().eq("user_id", userId).eq("status", 1));
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

}
