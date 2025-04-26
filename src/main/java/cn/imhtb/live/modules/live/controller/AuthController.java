package cn.imhtb.live.modules.live.controller;

/**
 * @author pinteh
 * @date 2024/9/13
 */

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.live.vo.AuthReqVo;
import cn.imhtb.live.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "认证接口")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

    private final IAuthService authService;

    @ApiOperation("提交身份认证")
    @PostMapping("/submit")
    public ApiResponse<?> save(@RequestBody AuthReqVo authReqVo) {
        return ApiResponse.ofSuccess(authService.submit(authReqVo));
    }

    @ApiOperation("获取身份认证状态")
    @PostMapping("/getStatus")
    public ApiResponse<?> getStatus() {
        return ApiResponse.ofSuccess(authService.getStatus());
    }


}
