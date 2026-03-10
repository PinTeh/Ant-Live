package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemUserDetail;
import cn.imhtb.live.modules.system.model.SystemUserQuery;
import cn.imhtb.live.modules.system.model.SystemUserUpdate;
import cn.imhtb.live.modules.system.service.ISystemUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Api(tags = "系统_用户接口")
@RequestMapping("/api/v1/system/user")
@RestController
public class SystemUserController extends AbstractBaseController<ISystemUserService, SystemUserDetail, SystemUserQuery, SystemUserDetail, SystemUserUpdate> {
}
