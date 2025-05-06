package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemMenuDetail;
import cn.imhtb.live.modules.system.model.SystemMenuQuery;
import cn.imhtb.live.modules.system.model.SystemMenuUpdate;
import cn.imhtb.live.modules.system.service.ISystemMenuService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Api(tags = "系统_菜单接口")
@RequestMapping("/api/v1/system/menu")
@RestController
public class SystemMenuController extends AbstractBaseController<ISystemMenuService, SystemMenuDetail, SystemMenuQuery, SystemMenuDetail, SystemMenuUpdate> {
}
