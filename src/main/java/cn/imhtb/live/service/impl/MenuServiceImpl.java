package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.mappers.MenuMapper;
import cn.imhtb.live.mappers.RoleMenuMapper;
import cn.imhtb.live.pojo.database.Menu;
import cn.imhtb.live.pojo.database.RoleMenu;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;
import cn.imhtb.live.pojo.vo.request.RoleMenuUpdateRequest;
import cn.imhtb.live.service.IMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Override
    public List<FrontMenuItemResp> listMenus(Integer pid, Integer hidden) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>()
                .eq(pid != null, Menu::getPid, pid)
                .eq(hidden != null, Menu::getHidden, hidden)
                .orderByAsc(Menu::getSort);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return listMenusTree(menus, null, hidden);
    }

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
    public List<FrontMenuItemResp> listMenusByRoleIds(List<Integer> roleIds, Integer pid) {
        List<FrontMenuItemResp> list = new ArrayList<>();
        for (Integer roleId : roleIds) {
            list.addAll(listMenusByRole(roleId, pid));
        }
        List<FrontMenuItemResp> ret = new ArrayList<>(new LinkedHashSet<>(list));
        return ret.stream().sorted(Comparator.comparing(FrontMenuItemResp::getSort)).collect(Collectors.toList());
    }

    @Override
    public ApiResponse updateRoleMenu(RoleMenuUpdateRequest request) {
        Integer roleId = request.getRoleId();
        List<Integer> currentMenuIds = request.getMenuIds();
        // 获取当前角色的菜单
        // [1,2,3,4,5,6,7]  //2 -
        // [1,3,4,5,6,7,8]  //8 +

        // [1,2,3] // 123 -
        // [4,5] //4,5 +
        List<Integer> menuIds = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId)).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Integer> sub = new ArrayList<>(menuIds);
        List<Integer> plus = new ArrayList<>(currentMenuIds);
        sub.removeAll(currentMenuIds);
        plus.removeAll(menuIds);

        for (Integer mid : sub) {
            roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("menu_id", mid).eq("role_id", roleId));
        }
        for (Integer mid : plus) {
            roleMenuMapper.insert(RoleMenu.builder().roleId(roleId).menuId(mid).build());
        }
        return ApiResponse.ofSuccess();
    }

    private List<FrontMenuItemResp> listMenusTree(List<Menu> menus, List<Integer> menuIds, Integer hidden) {
        List<FrontMenuItemResp> list = new LinkedList<>();
        for (Menu menu : menus) {
            List<Menu> childList = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", menu.getId()).eq(hidden != null, "hidden", hidden).orderByAsc("sort"));
            if (menuIds == null || menuIds.contains(menu.getId())) {
                FrontMenuItemResp menuItem = new FrontMenuItemResp();
                menuItem.setId(menu.getId());
                menuItem.setLabel(menu.getTitle());
                menuItem.setIndex(menu.getMenuIndex());
                menuItem.setIcon(menu.getIcon());
                menuItem.setPath(menu.getPath());
                menuItem.setHidden(menu.getHidden());
                menuItem.setSort(menu.getSort());
                menuItem.setPid(menu.getPid());
                if (childList != null && !childList.isEmpty()) {
                    menuItem.setChildren(listMenusTree(childList, menuIds, hidden));
                }
                list.add(menuItem);
            }
        }
        return list;
    }

}
