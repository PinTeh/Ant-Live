package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.AuthInfo;
import cn.imhtb.live.service.IAuthService;
import cn.imhtb.live.utils.JwtUtil;
import cn.imhtb.live.pojo.vo.request.AuthRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public ApiResponse<?> save(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request);
        authRequest.setUserId(uid);
        AuthInfo authInfo = new AuthInfo();
        BeanUtils.copyProperties(authRequest, authInfo);
        return authService.saveAndCheck(authInfo);
    }

    @GetMapping("/")
    public ApiResponse list() {
        return ApiResponse.ofSuccess(authService.list());
    }

    @GetMapping()
    public ApiResponse getOne(Integer id) {
        return ApiResponse.ofSuccess(authService.getById(id));
    }

    @GetMapping("/info")
    public ApiResponse getOneByUserId(HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request);
        return ApiResponse.ofSuccess(authService.getOne(new QueryWrapper<AuthInfo>().eq("user_id", uid).orderByDesc("id").last("limit 0,1")));
    }
}
