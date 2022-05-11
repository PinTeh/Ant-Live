package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.service.ICategoryService;
import cn.imhtb.antlive.vo.PageWrapperResp;
import cn.imhtb.antlive.vo.response.CategoryResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @GetMapping("/query")
    public ApiResponse queryCategoryPage(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                         @RequestParam(required = false, defaultValue = "1") Integer page){
        PageWrapperResp<CategoryResp> pageWrapperResp = categoryService.queryCategoryPage(page, limit);
        return ApiResponse.ofSuccess(pageWrapperResp);
    }

}
