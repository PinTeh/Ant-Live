package cn.imhtb.live.modules.base.controller;

import cn.imhtb.live.modules.base.model.PresentDetail;
import cn.imhtb.live.modules.base.model.PresentQuery;
import cn.imhtb.live.modules.base.model.PresentUpdate;
import cn.imhtb.live.modules.base.service.IPresentTestService;
import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.pojo.database.Present;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/14
 */
@RestController
@RequestMapping("/api/v2/present")
public class PresentTestController extends AbstractBaseController<IPresentTestService, Present, PresentQuery, PresentDetail, PresentUpdate> {
}
