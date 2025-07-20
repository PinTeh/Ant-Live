package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.MenuMapper;
import cn.imhtb.live.mappers.RoleMapper;
import cn.imhtb.live.mappers.RoleMenuMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemRoleDetail;
import cn.imhtb.live.modules.system.model.SystemRoleQuery;
import cn.imhtb.live.modules.system.model.SystemRoleUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoleService;
import cn.imhtb.live.pojo.database.Menu;
import cn.imhtb.live.pojo.database.Role;
import cn.imhtb.live.pojo.database.RoleMenu;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SystemRoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, SystemRoleDetail, SystemRoleQuery, SystemRoleDetail, SystemRoleUpdate> implements ISystemRoleService {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<FrontMenuItemResp> listMenusByRole(Integer roleId, Integer pid) {
        LambdaQueryWrapper<RoleMenu> roleWrapper = new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleWrapper);
        LambdaQueryWrapper<Menu> menuWrapper = new LambdaQueryWrapper<Menu>().eq(Menu::getPid, pid).eq(Menu::getHidden, 0).orderByAsc(Menu::getSort);
        List<Menu> menus = menuMapper.selectList(menuWrapper);
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return listMenusTree(menus, menuIds, 0);
    }

    @Override
    public List<FrontMenuItemResp> listMenus(Integer pid, Integer hidden) {
        // 只查询指定父级下的菜单，而不是所有菜单
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>()
                .eq("pid", pid)
                .eq(hidden != null, "hidden", hidden)
                .orderByAsc("sort"));
        return listMenusTree(menus, null, hidden);
    }

    private List<FrontMenuItemResp> listMenusTree(List<Menu> menus, List<Integer> menuIds, Integer hidden) {
        List<FrontMenuItemResp> list = new LinkedList<>();
        for (Menu menu : menus) {
            List<Menu> childList = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", menu.getId()).eq(hidden != null, "hidden", hidden).orderByAsc("sort"));
            if (menuIds == null || menuIds.contains(menu.getId())) {
                FrontMenuItemResp menuItem = new FrontMenuItemResp();
                menuItem.setId(menu.getId());
                menuItem.setLabel(menu.getTitle());
                menuItem.setTitle(menu.getTitle());
                menuItem.setIndex(menu.getMenuIndex());
                menuItem.setIcon(menu.getIcon());
                menuItem.setPath(menu.getPath());
                menuItem.setHidden(menu.getHidden());
                menuItem.setSort(menu.getSort());
                menuItem.setPid(menu.getPid());
                menuItem.setStatus(menu.getStatus());
                menuItem.setCreateTime(menu.getCreateTime());
                if (childList != null && !childList.isEmpty()) {
                    menuItem.setChildren(listMenusTree(childList, menuIds, hidden));
                }
                list.add(menuItem);
            }
        }
        return list;
    }

}
