package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.AuthInfo;
import cn.imhtb.live.common.enums.AuthStatusEnum;
import cn.imhtb.live.modules.user.service.IAuthService;
import cn.imhtb.live.pojo.vo.request.IdsRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@Api(tags = "认证管理接口")
@RestController
@RequestMapping("/admin/auth")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_AUTH','ROLE_LIVE')")
public class AdminAuthController {

    private final IAuthService authService;

    public AdminAuthController(IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/list")
    public ApiResponse authList(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit, @RequestParam(required = false) Integer status) {
        IPage<AuthInfo> iPage = authService.page(new Page<>(page, limit), new QueryWrapper<AuthInfo>().eq(status != null, "status", status).orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @PostMapping("/pass/{type}")
    public ApiResponse pass(@RequestBody IdsRequest request, @PathVariable("type") String type) {
        if ("pass".equals(type)) {
            authService.updateStatusByIds(request.getIds(), AuthStatusEnum.PASS.getCode());
        } else if ("reset".equals(type)) {
            authService.updateStatusByIds(request.getIds(), AuthStatusEnum.NO.getCode());
        } else if ("reject".equals(type)) {
            authService.updateStatusByIds(request.getIds(), AuthStatusEnum.REJECT.getCode());
        }
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/del")
    public ApiResponse pass(@RequestBody IdsRequest request) {
        authService.removeByIds(Arrays.asList(request.getIds()));
        return ApiResponse.ofSuccess();
    }
}
