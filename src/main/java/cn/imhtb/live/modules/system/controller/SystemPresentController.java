package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.system.model.SystemPresentDetail;
import cn.imhtb.live.modules.system.model.SystemPresentQuery;
import cn.imhtb.live.modules.system.model.SystemPresentUpdate;
import cn.imhtb.live.modules.system.service.ISystemPresentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Api(tags = "系统_礼物接口")
@RequestMapping("/api/v1/system/gift")
@RestController
public class SystemPresentController extends AbstractBaseController<ISystemPresentService, SystemPresentDetail, SystemPresentQuery, SystemPresentDetail, SystemPresentUpdate> {
}
