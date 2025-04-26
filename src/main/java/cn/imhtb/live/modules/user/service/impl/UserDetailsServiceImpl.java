package cn.imhtb.live.modules.user.service.impl;

import cn.imhtb.live.modules.user.service.IUserRoleService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.AntLiveUserBo;
import cn.imhtb.live.pojo.database.User;
import cn.imhtb.live.pojo.database.Role;
import cn.imhtb.live.pojo.database.UserRole;
import cn.imhtb.live.service.IBillService;
import cn.imhtb.live.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/7
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;
    private final IBillService billService;
    private final IUserRoleService userRoleService;
    private final IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username), false);
        if (Objects.isNull(user)){
            return null;
        }
        AntLiveUserBo antLiveUserBo = new AntLiveUserBo();
        BeanUtils.copyProperties(user, antLiveUserBo);

        BigDecimal balance = billService.getBalance(user.getId());
        antLiveUserBo.setBalance(balance);
        // 获取用户角色
        List<GrantedAuthority> authorities = Lists.newArrayList();
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        antLiveUserBo.setRoleIds(roleIds);
        for (UserRole userRole : userRoles) {
            Role role = roleService.getById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getPermission()));
        }
        antLiveUserBo.setAuthorities(authorities);
        return antLiveUserBo;
    }

}
