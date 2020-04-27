package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.database.Category;
import cn.imhtb.antlive.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    final private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ApiResponse list(@RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page){
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>().eq("disabled", Constants.DisabledStatus.YES.getCode()).orderByDesc("sort");
        Page<Category> categoryPage = categoryService.page(new Page<>(page, limit), wrapper);
        return ApiResponse.ofSuccess(categoryPage);
    }
}
