package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.PresentMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemPresentDetail;
import cn.imhtb.live.modules.system.model.SystemPresentQuery;
import cn.imhtb.live.modules.system.model.SystemPresentUpdate;
import cn.imhtb.live.modules.system.service.ISystemPresentService;
import cn.imhtb.live.pojo.database.Present;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Service
public class SystemPresentServiceImpl extends BaseServiceImpl<PresentMapper, Present, SystemPresentDetail, SystemPresentQuery, SystemPresentDetail, SystemPresentUpdate> implements ISystemPresentService {
}
