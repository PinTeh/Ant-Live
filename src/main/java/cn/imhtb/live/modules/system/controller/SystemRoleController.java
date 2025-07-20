package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemRoleDetail;
import cn.imhtb.live.modules.system.model.SystemRoleQuery;
import cn.imhtb.live.modules.system.model.SystemRoleUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoleService;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Api(tags = "系统_角色接口")
@RequestMapping("/api/v1/system/role")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SystemRoleController extends AbstractBaseController<ISystemRoleService, SystemRoleDetail, SystemRoleQuery, SystemRoleDetail, SystemRoleUpdate> {

    private final ISystemRoleService roleService;

    @GetMapping("/listMenus")
    public ApiResponse<List<FrontMenuItemResp>> listMenus(@RequestParam(value = "pid", defaultValue = "0") Integer pid, @RequestParam(value = "hidden", defaultValue = "0") Integer hidden) {
        List<FrontMenuItemResp> menus = roleService.listMenus(pid, hidden);
        return ApiResponse.ofSuccess(menus);
    }

    @GetMapping("/listMenusByRole")
    public ApiResponse<List<FrontMenuItemResp>> listMenusByRole(Integer roleId, @RequestParam(value = "pid", defaultValue = "0") Integer pid) {
        List<FrontMenuItemResp> menus = roleService.listMenusByRole(roleId, pid);
        return ApiResponse.ofSuccess(menus);
    }

}
