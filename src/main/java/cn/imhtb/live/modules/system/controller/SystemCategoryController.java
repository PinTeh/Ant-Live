package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemCategoryDetail;
import cn.imhtb.live.modules.system.model.SystemCategoryQuery;
import cn.imhtb.live.modules.system.model.SystemCategoryUpdate;
import cn.imhtb.live.modules.system.service.ISystemCategoryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Api(tags = "系统_目录接口")
@RequestMapping("/api/v1/system/category")
@RestController
public class SystemCategoryController extends AbstractBaseController<ISystemCategoryService, SystemCategoryDetail, SystemCategoryQuery, SystemCategoryDetail, SystemCategoryUpdate> {
}
