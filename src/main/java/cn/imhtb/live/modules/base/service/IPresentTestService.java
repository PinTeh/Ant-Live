package cn.imhtb.live.modules.base.service;

import cn.imhtb.live.modules.base.model.PresentDetail;
import cn.imhtb.live.modules.base.model.PresentQuery;
import cn.imhtb.live.modules.base.model.PresentUpdate;
import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.pojo.database.Present;

/**
 * @author pinteh
 * @date 2025/4/14
 */
public interface IPresentTestService extends IBaseService<Present, PresentQuery, PresentDetail, PresentUpdate> {
}
