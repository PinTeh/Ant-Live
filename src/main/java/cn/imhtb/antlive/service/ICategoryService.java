package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.database.Category;
import cn.imhtb.antlive.vo.PageWrapperResp;
import cn.imhtb.antlive.vo.response.CategoryResp;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ICategoryService extends IService<Category> {

    /**
     * 分页获取分类数据
     * @param page 页码
     * @param limit 页面大小
     * @return 分页数据
     */
    PageWrapperResp<CategoryResp> queryCategoryPage(Integer page, Integer limit);

}
