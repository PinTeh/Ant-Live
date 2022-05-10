package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.Category;
import cn.imhtb.antlive.mappers.CategoryMapper;
import cn.imhtb.antlive.service.ICategoryService;
import cn.imhtb.antlive.vo.PageWrapperResp;
import cn.imhtb.antlive.vo.response.CategoryResp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PinTeh
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public PageWrapperResp<CategoryResp> queryCategoryPage(Integer page, Integer limit) {
        Page<Category> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().orderByDesc(Category::getSort);
        Page<Category> categoryPage = page(pageParam, wrapper);

        List<CategoryResp> categoryRespList = Lists.newArrayList();
        for (Category record : categoryPage.getRecords()) {
            CategoryResp categoryResp = new CategoryResp();
            categoryResp.setId(record.getId());
            categoryResp.setName(record.getName());
            categoryResp.setParentId(record.getParentId());
            categoryResp.setSort(record.getSort());
            categoryRespList.add(categoryResp);
        }

        PageWrapperResp<CategoryResp> pageWrapperResp = new PageWrapperResp<>();
        pageWrapperResp.setTotal(categoryPage.getTotal());
        pageWrapperResp.setList(categoryRespList);
        return pageWrapperResp;
    }

}
