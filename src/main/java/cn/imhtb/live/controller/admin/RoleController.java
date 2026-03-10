package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.User;
import cn.imhtb.live.pojo.database.Menu;
import cn.imhtb.live.pojo.database.Role;
import cn.imhtb.live.pojo.database.UserRole;
import cn.imhtb.live.service.IMenuService;
import cn.imhtb.live.service.IRoleService;
import cn.imhtb.live.modules.user.service.IUserRoleService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.vo.request.IdsRequest;
import cn.imhtb.live.pojo.vo.request.MenuRequest;
import cn.imhtb.live.pojo.vo.request.RoleMenuUpdateRequest;
import cn.imhtb.live.pojo.vo.request.UserRoleUpdateRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/29
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class RoleController {

    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IUserService userService;

    @GetMapping("/menu/list")
    public ApiResponse list(@RequestParam(value = "pid", defaultValue = "0") Integer pid, @RequestParam(value = "hidden", defaultValue = "0") Integer hidden) {
        return ApiResponse.ofSuccess(menuService.listMenus(pid, null));
    }

    @GetMapping("/menu/listByRole")
    public ApiResponse listByRole(@RequestParam(value = "rid", defaultValue = "1") Integer roleId, @RequestParam(value = "pid", defaultValue = "0") Integer pid) {
        return ApiResponse.ofSuccess(menuService.listMenusByRole(roleId, pid));
    }

    @PostMapping("/menu/listByRoleIds")
    public ApiResponse listByRoleIds(@RequestBody IdsRequest request, @RequestParam(value = "pid", defaultValue = "0") Integer pid) {
        return ApiResponse.ofSuccess(menuService.listMenusByRoleIds(Arrays.asList(request.getIds()), pid));
    }

    @PostMapping("/menu/update")
    public ApiResponse updateMenu(@RequestBody MenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        menu.setTitle(request.getLabel());
        menu.setMenuIndex(request.getIndex());
        menuService.updateById(menu);
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/menu/save")
    public ApiResponse saveMenu(@RequestBody MenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        menu.setTitle(request.getLabel());
        menu.setMenuIndex(request.getIndex());
        menuService.save(menu);
        return ApiResponse.ofSuccess();
    }


    @PostMapping("/role/menu/update")
    public ApiResponse updateRoleMenu(@RequestBody RoleMenuUpdateRequest request) {
        menuService.updateRoleMenu(request);
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/user/role/update")
    public ApiResponse updateUserRole(@RequestBody UserRoleUpdateRequest request) {
        userRoleService.updateUserRole(request);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/role/list")
    public ApiResponse roleList() {
        return ApiResponse.ofSuccess(roleService.list(new QueryWrapper<Role>().orderByAsc("level")));
    }

    @PostMapping("/role/save")
    public ApiResponse roleSave(@RequestBody Role role) {
        return ApiResponse.ofSuccess(roleService.save(role));
    }

    /**
     * 获取存在角色的用户
     */
    @GetMapping("/user/hasRole/list")
    public ApiResponse listHasRoleUsers() {
        List<Integer> userIds = userRoleService.listHasRoleUserIds();
        List<User> users = new ArrayList<>();
        userIds.forEach(v -> {
            users.add(userService.getById(v));
        });
        return ApiResponse.ofSuccess(users);
    }

    @GetMapping("/role/listByUserId")
    public ApiResponse listRolesByUserId(Integer uid) {
        List<UserRole> userRoles = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", uid));
        List<Role> roles = new ArrayList<>();
        userRoles.forEach(v -> {
            roles.add(roleService.getById(v.getRoleId()));
        });
        return ApiResponse.ofSuccess(roles);
    }
}
