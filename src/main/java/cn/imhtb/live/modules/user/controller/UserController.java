package cn.imhtb.live.modules.user.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.modules.user.model.req.UserExtraReq;
import cn.imhtb.live.modules.user.model.req.UserInfoUpdateReq;
import cn.imhtb.live.modules.user.model.req.UserRegisterReq;
import cn.imhtb.live.modules.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pinteh
 */
@Api(tags = "user", value = "用户接口")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final IUserService userService;

    @IgnoreToken
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody @Valid UserRegisterReq req) {
        return ApiResponse.ofSuccess(userService.register(req));
    }

    @ApiOperation("绑定邮箱或者手机号码")
    @PostMapping("/extra/bind")
    public ApiResponse<Boolean> bindUserExtra(@RequestBody @Valid UserExtraReq req) {
        return ApiResponse.ofSuccess(userService.bindUserExtra(req));
    }

    @ApiOperation("更新用户基础信息")
    @PostMapping("/basic/update")
    public ApiResponse<Boolean> updateUserInfo(@RequestBody @Valid UserInfoUpdateReq request) {
        return ApiResponse.ofSuccess(userService.updateUserInfo(request));
    }

}
