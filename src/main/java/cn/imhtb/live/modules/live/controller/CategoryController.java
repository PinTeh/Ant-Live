package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.pojo.vo.response.CategoryResp;
import cn.imhtb.live.modules.live.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 直播分类接口
 *
 * @author PinTeh
 */
@Api(tags = "直播分类接口")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @IgnoreToken
    @ApiOperation("获取直播分类")
    @GetMapping("/query")
    public ApiResponse<PageData<CategoryResp>> queryCategoryPage(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                                             @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ApiResponse.ofSuccess(categoryService.queryCategoryPage(page, limit));
    }

}
