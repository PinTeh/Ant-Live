package cn.imhtb.live.modules.base.service.impl;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.mappers.CategoryMapper;
import cn.imhtb.live.modules.base.service.ICategoryService;
import cn.imhtb.live.pojo.database.Category;
import cn.imhtb.live.pojo.vo.response.CategoryResp;
import cn.imhtb.live.utils.CovertBeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PinTeh
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public PageData<CategoryResp> queryCategoryPage(Integer page, Integer limit) {
        Page<Category> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>().orderByDesc(Category::getSort);
        Page<Category> categoryPage = page(pageParam, wrapper);
        List<CategoryResp> categoryRespList = CovertBeanUtil.covertList(categoryPage.getRecords(), CategoryResp.class);
        PageData<CategoryResp> pageData = new PageData<>();
        pageData.setTotal(categoryPage.getTotal());
        pageData.setList(categoryRespList);
        return pageData;
    }

}
