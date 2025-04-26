package cn.imhtb.live.modules.base.service.impl;

import cn.imhtb.live.mappers.PresentMapper;
import cn.imhtb.live.modules.base.service.IPresentTestService;
import cn.imhtb.live.modules.base.model.PresentDetail;
import cn.imhtb.live.modules.base.model.PresentQuery;
import cn.imhtb.live.modules.base.model.PresentUpdate;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.pojo.database.Present;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/14
 */
@Service
public class PresentTestService extends BaseServiceImpl<PresentMapper, Present, Present, PresentQuery, PresentDetail, PresentUpdate> implements IPresentTestService {
}
