package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.service.IAuthService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.AuthRequest;
import cn.imhtb.antlive.vo.request.IdsRequest;
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
    public ApiResponse auth(@RequestBody AuthRequest authRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        authRequest.setUserId(uid);
        AuthInfo authInfo = modelMapper.map(authRequest, AuthInfo.class);
        authService.save(authInfo);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/")
    public ApiResponse list(){
        return ApiResponse.ofSuccess(authService.list());
    }

    @GetMapping()
    public ApiResponse getOne(Integer id){
        return ApiResponse.ofSuccess(authService.getById(id));
    }

    @PostMapping("/pass/{type}")
    public ApiResponse pass(@RequestBody IdsRequest request,@PathVariable("type")String type){
        if ("pass".equals(type)){
            authService.updateStatusByIds(request.getIds(),Constants.AuthStatus.YES.getCode());
        }else if ("reset".equals(type)){
            authService.updateStatusByIds(request.getIds(),Constants.AuthStatus.NO.getCode());
        }
        return ApiResponse.ofSuccess();
    }
}
