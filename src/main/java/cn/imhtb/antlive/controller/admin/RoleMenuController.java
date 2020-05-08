package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.Menu;
import cn.imhtb.antlive.service.IMenuService;
import cn.imhtb.antlive.service.IRoleService;
import cn.imhtb.antlive.vo.request.MenuRequest;
import cn.imhtb.antlive.vo.request.RoleMenuUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 * @date 2020/4/29
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class RoleMenuController {

    private final IMenuService menuService;

    private final IRoleService roleService;

    private final ModelMapper modelMapper;

    public RoleMenuController(IMenuService menuService, IRoleService roleService, ModelMapper modelMapper) {
        this.menuService = menuService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/menu/list")
    public ApiResponse list(@RequestParam(value = "pid",defaultValue = "0") Integer pid,@RequestParam(value = "hidden",defaultValue = "0")Integer hidden){
        return menuService.listMenus(pid,hidden);
    }

    @GetMapping("/menu/listByRole")
    public ApiResponse listByRole(@RequestParam(value = "rid",defaultValue = "1") Integer roleId,@RequestParam(value = "pid",defaultValue = "0") Integer pid){
        return menuService.listMenusByRole(roleId,pid);
    }

    @PostMapping("/menu/update")
    public ApiResponse updateMenu(@RequestBody MenuRequest request){
        Menu menu = modelMapper.map(request, Menu.class);
        menu.setTitle(request.getLabel());
        menu.setMenuIndex(request.getIndex());
        menuService.updateById(menu);
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/menu/save")
    public ApiResponse saveMenu(@RequestBody MenuRequest request){
        Menu menu = modelMapper.map(request, Menu.class);
        menu.setTitle(request.getLabel());
        menu.setMenuIndex(request.getIndex());
        menuService.save(menu);
        return ApiResponse.ofSuccess();
    }


    @PostMapping("/role/menu/update")
    public ApiResponse updateRoleMenu(@RequestBody RoleMenuUpdateRequest request){
        menuService.updateRoleMenu(request);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/role/list")
    public ApiResponse roleList(){
        return ApiResponse.ofSuccess(roleService.list());
    }
}
