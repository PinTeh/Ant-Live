package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.service.IAuthService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.AuthRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ModelMapper modelMapper;

    private final IAuthService authService;

    public AuthController(ModelMapper modelMapper, IAuthService authService ) {
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    @PostMapping()
    public ApiResponse save(@RequestBody AuthRequest authRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        authRequest.setUserId(uid);
        AuthInfo authInfo = modelMapper.map(authRequest, AuthInfo.class);
        return authService.saveAndCheck(authInfo);
    }

    @GetMapping("/")
    public ApiResponse list(){
        return ApiResponse.ofSuccess(authService.list());
    }

    @GetMapping()
    public ApiResponse getOne(Integer id){
        return ApiResponse.ofSuccess(authService.getById(id));
    }

    @GetMapping("/info")
    public ApiResponse getOneByUserId(HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        return ApiResponse.ofSuccess(authService.getOne(new QueryWrapper<AuthInfo>().eq("user_id",uid)));
    }
}
