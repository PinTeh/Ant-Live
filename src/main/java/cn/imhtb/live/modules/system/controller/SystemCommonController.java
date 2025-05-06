package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.system.service.ISystemService;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/26
 */
@Api(tags = "系统接口")
@RestController
@RequestMapping("/api/v1/system")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SystemCommonController {

    private final ISystemService systemService;

    @ApiOperation("获取当前用户的菜单权限")
    @GetMapping("/getMenus")
    public ApiResponse<List<FrontMenuItemResp>> getMenus(){
        List<FrontMenuItemResp> menus = systemService.getMenus();
        return ApiResponse.ofSuccess(menus);
    }

}
