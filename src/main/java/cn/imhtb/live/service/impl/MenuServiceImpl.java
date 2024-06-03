package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Menu;
import cn.imhtb.live.pojo.database.RoleMenu;
import cn.imhtb.live.mappers.MenuMapper;
import cn.imhtb.live.mappers.RoleMenuMapper;
import cn.imhtb.live.service.IMenuService;
import cn.imhtb.live.pojo.vo.FrontMenuItem;
import cn.imhtb.live.pojo.vo.request.RoleMenuUpdateRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuMapper menuMapper;

    private final RoleMenuMapper roleMenuMapper;

    public MenuServiceImpl(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    //@Cacheable("menus")
    public List<FrontMenuItem> listMenus(Integer pid, Integer hidden) {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", pid).eq(hidden != null, "hidden", hidden).orderByAsc("sort"));
        return listMenusTree(menus, null, hidden);
    }

    @Override
    //@Cacheable(cacheNames = "menu",key = "#roleId")
    public List<FrontMenuItem> listMenusByRole(Integer roleId, Integer pid) {
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", pid).eq("hidden", 0).orderByAsc("sort"));
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return listMenusTree(menus, menuIds, 0);
    }

    @Override
    //@Cacheable(cacheNames = "menu",key = "#roleIds.toArray()")
    public List<FrontMenuItem> listMenusByRoleIds(List<Integer> roleIds, Integer pid) {
        List<FrontMenuItem> list = new ArrayList<>();
        roleIds.forEach(roleId -> {
            list.addAll(listMenusByRole(roleId, pid));
        });
        List<FrontMenuItem> ret = new ArrayList<>(new LinkedHashSet<>(list));
        return ret.stream().sorted(Comparator.comparing(FrontMenuItem::getSort)).collect(Collectors.toList());
    }

    @Override
    //@CacheEvict(cacheNames = "menu")
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

    private List<FrontMenuItem> listMenusTree(List<Menu> menus, List<Integer> menuIds, Integer hidden) {
        List<FrontMenuItem> list = new LinkedList<>();
        menus.forEach(v -> {
            if (v != null) {
                List<Menu> childList = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", v.getId()).eq(hidden != null, "hidden", hidden).orderByAsc("sort"));

                if (menuIds == null || menuIds.contains(v.getId())) {
                    FrontMenuItem menuItem = new FrontMenuItem();
                    menuItem.setId(v.getId());
                    menuItem.setLabel(v.getTitle());
                    menuItem.setIndex(v.getMenuIndex());
                    menuItem.setIcon(v.getIcon());
                    menuItem.setPath(v.getPath());
                    menuItem.setHidden(v.getHidden());
                    menuItem.setSort(v.getSort());
                    menuItem.setPid(v.getPid());
                    if (childList != null && childList.size() != 0) {
                        menuItem.setChildren(listMenusTree(childList, menuIds, hidden));
                    }
                    list.add(menuItem);
                }
            }
        });
        return list;
    }

}
