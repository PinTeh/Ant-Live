package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.Menu;
import cn.imhtb.antlive.entity.database.RoleMenu;
import cn.imhtb.antlive.mappers.MenuMapper;
import cn.imhtb.antlive.mappers.RoleMenuMapper;
import cn.imhtb.antlive.service.IMenuService;
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
    public ApiResponse listMenusByRole(Integer roleId,Integer pid) {
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", pid).eq("hidden",0).orderByAsc("sort"));
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return ApiResponse.ofSuccess(listMenusTree(menus,menuIds,0));
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

    private Object listMenusTree(List<Menu> menus,List<Integer> menuIds,Integer hidden){
        List<Map<String,Object>> list = new LinkedList<>();
        menus.forEach(v -> {
            if (v!=null){
                List<Menu> childList = menuMapper.selectList(new QueryWrapper<Menu>().eq("pid", v.getId()).eq("hidden",hidden).orderByAsc("sort"));
                // 可能出错
                if (menuIds == null){
                    Map<String,Object> map = new HashMap<>(16);
                    map.put("id",v.getId());
                    map.put("label",v.getTitle());
                    map.put("index",v.getMenuIndex());
                    map.put("icon",v.getIcon());
                    map.put("path",v.getPath());
                    map.put("hidden",v.getHidden());
                    map.put("sort",v.getSort());
                    map.put("pid",v.getPid());
                    if(childList!=null && childList.size()!=0){
                        map.put("children",listMenusTree(childList,menuIds,hidden));
                    }
                    list.add(map);
                }else if (menuIds.contains(v.getId())){
                    Map<String,Object> map = new HashMap<>(16);
                    map.put("id",v.getId());
                    map.put("label",v.getTitle());
                    map.put("index",v.getMenuIndex());
                    map.put("icon",v.getIcon());
                    map.put("path",v.getPath());
                    map.put("hidden",v.getHidden());
                    map.put("sort",v.getSort());
                    map.put("pid",v.getPid());
                    if(childList!=null && childList.size()!=0){
                        map.put("children",listMenusTree(childList,menuIds,hidden));
                    }
                    list.add(map);
                }
            }
        });
        return list;
    }

}
