package cn.imhtb.live.modules.system.service;

import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import cn.imhtb.live.modules.system.model.SystemMessageQuery;
import cn.imhtb.live.modules.system.model.SystemMessageUpdate;

/**
 * @author pinteh
 * @date 2025/4/30
 */
public interface ISystemMessageService extends IBaseService<SystemMessageDetail, SystemMessageQuery, SystemMessageDetail, SystemMessageUpdate> {
}
