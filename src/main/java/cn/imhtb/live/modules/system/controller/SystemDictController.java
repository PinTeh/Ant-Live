package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemDictDetail;
import cn.imhtb.live.modules.system.model.SystemDictQuery;
import cn.imhtb.live.modules.system.model.SystemDictUpdate;
import cn.imhtb.live.modules.system.service.ISystemDictService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Api(tags = "系统_字典接口")
@RequestMapping("/api/v1/system/dict")
@RestController
public class SystemDictController extends AbstractBaseController<ISystemDictService, SystemDictDetail, SystemDictQuery, SystemDictDetail, SystemDictUpdate> {
}
