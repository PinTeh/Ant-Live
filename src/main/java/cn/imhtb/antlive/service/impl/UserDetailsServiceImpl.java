package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.JwtUser;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.Role;
import cn.imhtb.antlive.entity.database.UserRole;
import cn.imhtb.antlive.mappers.RoleMapper;
import cn.imhtb.antlive.mappers.UserMapper;
import cn.imhtb.antlive.mappers.UserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        JwtUser jwtUser = new JwtUser();
        BeanUtils.copyProperties(user, jwtUser);

        // 获取用户角色
        List<GrantedAuthority> authorities = Lists.newArrayList();
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
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
