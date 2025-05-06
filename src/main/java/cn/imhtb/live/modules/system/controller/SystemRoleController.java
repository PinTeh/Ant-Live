package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemRoleDetail;
import cn.imhtb.live.modules.system.model.SystemRoleQuery;
import cn.imhtb.live.modules.system.model.SystemRoleUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Api(tags = "系统_角色接口")
@RequestMapping("/api/v1/system/role")
@RestController
public class SystemRoleController extends AbstractBaseController<ISystemRoleService, SystemRoleDetail, SystemRoleQuery, SystemRoleDetail, SystemRoleUpdate> {
}
