package cn.imhtb.live.modules.user.controller;

import cn.imhtb.live.annotation.NeedToken;
import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.vo.request.BindExtraInfoRequestVO;
import cn.imhtb.live.pojo.vo.request.RegisterRequest;
import cn.imhtb.live.pojo.vo.request.UserInfoUpdateRequest;
import cn.imhtb.live.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author pinteh
 */
@Api(tags = "user", value = "用户接口")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("获取绑定关系")
    @GetMapping("/getBindingInfo")
    public ApiResponse<?> getBindingInfo() {
        // TODO
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("注册用户")
    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.ofSuccess(userService.register(request));
    }

    @ApiOperation("获取验证码")
    @PostMapping("/generateVerifyCode")
    public ApiResponse<Boolean> generateVerifyCode(String account){
        return ApiResponse.ofSuccess(userService.generateVerifyCode(account));
    }

    @ApiOperation("绑定邮箱或者手机号码")
    @PostMapping("/bind")
    public ApiResponse<Boolean> bindExtraInfo(@RequestBody @Valid BindExtraInfoRequestVO bindExtraInfoRequestVO) {
        return ApiResponse.ofSuccess(userService.bindExtraInfo(bindExtraInfoRequestVO));
    }

    @ApiOperation("获取关联安全认证信息")
    @NeedToken
    @GetMapping("/getSecurityInfo")
    public ApiResponse<boolean[]> getSecurityInfo() {
        return ApiResponse.ofSuccess(userService.getSecurityInfo());
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/info/update")
    public ApiResponse<Boolean> updateUserInfo(@RequestBody @Valid UserInfoUpdateRequest request) {
        return ApiResponse.ofSuccess(userService.updateUserInfo(request));
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/info")
    public ApiResponse<User> getUserInfo(){
        return ApiResponse.ofSuccess(userService.getUserInfo());
    }

    @ApiOperation("验证token有效性")
    @GetMapping("/verify/token")
    public ApiResponse<Boolean> verifyToken(String token){
        Claims claims = JwtUtil.verifyJwt(token);
        if (claims == null){
            return ApiResponse.ofSuccess(false);
        }
        return ApiResponse.ofSuccess(true);
    }

}
