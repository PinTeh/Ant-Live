package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.JwtUser;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.Role;
import cn.imhtb.antlive.entity.database.UserRole;
import cn.imhtb.antlive.mappers.RoleMapper;
import cn.imhtb.antlive.mappers.UserMapper;
import cn.imhtb.antlive.mappers.UserRoleMapper;
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
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;

    private final UserRoleMapper userRoleMapper;

    private final RoleMapper roleMapper;

    public UserDetailsServiceImpl(UserMapper userMapper, ModelMapper modelMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
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

        // 获取用户角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<UserRole> userRoles = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        jwtUser.setRoleIds(roleIds);
        userRoles.forEach(v -> {
            Role role = roleMapper.selectById(v.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getPermission()));
        });
        jwtUser.setAuthorities(authorities);
        return jwtUser;
    }
}
