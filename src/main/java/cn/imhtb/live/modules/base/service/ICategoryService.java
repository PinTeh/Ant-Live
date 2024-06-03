package cn.imhtb.live.modules.base.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.pojo.database.Category;
import cn.imhtb.live.pojo.vo.response.CategoryResp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pinteh
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 分页获取分类数据
     *
     * @param page  页码
     * @param limit 页面大小
     * @return 分页数据
     */
    PageData<CategoryResp> queryCategoryPage(Integer page, Integer limit);

}
