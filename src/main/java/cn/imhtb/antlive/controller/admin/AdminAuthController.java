package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.AuthInfo;
import cn.imhtb.antlive.service.IAuthService;
import cn.imhtb.antlive.vo.request.IdsRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@RestController
@RequestMapping("/admin/auth")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_AUTH')")
public class AdminAuthController {


    private final IAuthService authService;

    public AdminAuthController(IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/list")
    public ApiResponse authList(@RequestParam(defaultValue = "1",required = false) Integer page, @RequestParam(defaultValue = "10",required = false) Integer limit, @RequestParam(required = false) Integer status){
        IPage<AuthInfo> iPage = authService.page(new Page<>(page,limit), new QueryWrapper<AuthInfo>().eq(status!=null,"status",status).orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @PostMapping("/pass/{type}")
    public ApiResponse pass(@RequestBody IdsRequest request, @PathVariable("type")String type){
        if ("pass".equals(type)){
            authService.updateStatusByIds(request.getIds(), Constants.AuthStatus.YES.getCode());
        }else if ("reset".equals(type)){
            authService.updateStatusByIds(request.getIds(),Constants.AuthStatus.NO.getCode());
        }else if("reject".equals(type)){
            authService.updateStatusByIds(request.getIds(),Constants.AuthStatus.REJECT.getCode());
        }
        return ApiResponse.ofSuccess();
    }

    @PostMapping("/del")
    public ApiResponse pass(@RequestBody IdsRequest request){
        authService.removeByIds(Arrays.asList(request.getIds()));
        return ApiResponse.ofSuccess();
    }
}
