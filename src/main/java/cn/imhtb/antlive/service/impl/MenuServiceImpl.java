package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.Menu;
import cn.imhtb.antlive.entity.database.RoleMenu;
import cn.imhtb.antlive.mappers.MenuMapper;
import cn.imhtb.antlive.mappers.RoleMenuMapper;
import cn.imhtb.antlive.service.IMenuService;
import cn.imhtb.antlive.vo.FrontMenuItem;
import cn.imhtb.antlive.vo.request.RoleMenuUpdateRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse listMenus(Integer pid,Integer hidden) {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", pid).eq("hidden",hidden).orderByAsc("sort"));
        return ApiResponse.ofSuccess(listMenusTree(menus,null,hidden));
    }

    @Override
    public List<FrontMenuItem> listMenusByRole(Integer roleId,Integer pid) {
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", pid).eq("hidden",0).orderByAsc("sort"));
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return listMenusTree(menus,menuIds,0);
    }

    @Override
    public ApiResponse listMenusByRoleIds(List<Integer> roleIds, Integer pid) {
        List<FrontMenuItem> list = new ArrayList<>();
        roleIds.forEach(roleId -> {
            list.addAll(listMenusByRole(roleId, pid));
        });
        List<FrontMenuItem> ret = new ArrayList<>(new LinkedHashSet<>(list));
        List<FrontMenuItem> retSorted = ret.stream().sorted(Comparator.comparing(FrontMenuItem::getSort)).collect(Collectors.toList());
        return ApiResponse.ofSuccess(retSorted);
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
        List<Integer> menuIds = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",roleId)).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Integer> sub = new ArrayList<>(menuIds);
        List<Integer> plus = new ArrayList<>(currentMenuIds);
        sub.removeAll(currentMenuIds);
        plus.removeAll(menuIds);

        for (Integer mid : sub) {
            roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("menu_id",mid).eq("role_id",roleId));
        }
        for (Integer mid : plus) {
            roleMenuMapper.insert(RoleMenu.builder().roleId(roleId).menuId(mid).build());
        }
        return ApiResponse.ofSuccess();
    }

    private List<FrontMenuItem> listMenusTree(List<Menu> menus,List<Integer> menuIds,Integer hidden){
        List<FrontMenuItem> list = new LinkedList<>();
        menus.forEach(v -> {
            if (v!=null){
                List<Menu> childList = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", v.getId()).eq("hidden",hidden).orderByAsc("sort"));

                if (menuIds == null || menuIds.contains(v.getId())){
                    FrontMenuItem menuItem = new FrontMenuItem();
                    menuItem.setId(v.getId());
                    menuItem.setLabel(v.getTitle());
                    menuItem.setIndex(v.getMenuIndex());
                    menuItem.setIcon(v.getIcon());
                    menuItem.setPath(v.getPath());
                    menuItem.setHidden(v.getHidden());
                    menuItem.setSort(v.getSort());
                    menuItem.setPid(v.getPid());
                    if(childList!=null && childList.size()!=0){
                        menuItem.setChildren(listMenusTree(childList,menuIds,hidden));
                    }
                    list.add(menuItem);
                }
            }
        });
        return list;
    }

}
