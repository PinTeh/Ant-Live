package cn.imhtb.live.modules.base.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.modules.base.model.CategoryResp;
import cn.imhtb.live.modules.base.model.VerifyCodeReq;
import cn.imhtb.live.modules.base.service.ICommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/12
 */
@Api(tags = "公共接口")
@RestController
@RequestMapping("/api/v1/common")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonController {

    private final ICommonService commonService;

    @IgnoreToken
    @ApiOperation("获取直播分类列表")
    @GetMapping("/getCategories")
    public ApiResponse<List<CategoryResp>> getCategories() {
        return ApiResponse.ofSuccess(commonService.getCategories());
    }

    @IgnoreToken
    @ApiOperation("发送验证码")
    @PostMapping("/sendVerifyCode")
    public ApiResponse<Boolean> sendVerifyCode(@RequestBody @Valid VerifyCodeReq req) {
        return ApiResponse.ofSuccess(commonService.sendVerifyCode(req));
    }

}
