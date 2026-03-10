package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.CategoryMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemCategoryDetail;
import cn.imhtb.live.modules.system.model.SystemCategoryQuery;
import cn.imhtb.live.modules.system.model.SystemCategoryUpdate;
import cn.imhtb.live.modules.system.service.ISystemCategoryService;
import cn.imhtb.live.pojo.database.Category;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Service
public class SystemCategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category, SystemCategoryDetail, SystemCategoryQuery, SystemCategoryDetail, SystemCategoryUpdate> implements ISystemCategoryService {
}
