package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.mappers.UserRoleMapper;
import cn.imhtb.live.modules.system.service.ISystemService;
import cn.imhtb.live.pojo.database.UserRole;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;
import cn.imhtb.live.service.IMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pinteh
 * @date 2025/4/26
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SystemCommonServiceImpl implements ISystemService {

    private final IMenuService menuService;
    private final UserRoleMapper userRoleMapper;

    @Override
    public List<FrontMenuItemResp> getMenus() {
        Integer userId = UserHolder.getUserId();
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        return menuService.listMenusByRoleIds(roleIds, 0);
    }

}
