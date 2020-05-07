package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.JwtUser;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.mappers.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;


    public UserDetailsServiceImpl(UserMapper userMapper, ModelMapper modelMapper) {
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user;
        JwtUser jwtUser;
        if (username.contains("@")){
            wrapper.eq("email", username);
            user  = userMapper.selectOne(wrapper);
            jwtUser = modelMapper.map(user, JwtUser.class);
            jwtUser.setUsername(user.getEmail());
        }else{
            wrapper.eq("mobile",username);
            user = userMapper.selectOne(wrapper);
            jwtUser = modelMapper.map(user, JwtUser.class);
            jwtUser.setUsername(user.getMobile());
        }
        //TODO
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        jwtUser.setAuthorities(authorities);
        return jwtUser;
    }
}
