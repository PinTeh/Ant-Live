package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.MessageMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import cn.imhtb.live.modules.system.model.SystemMessageQuery;
import cn.imhtb.live.modules.system.model.SystemMessageUpdate;
import cn.imhtb.live.modules.system.service.ISystemMessageService;
import cn.imhtb.live.pojo.database.Message;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Service
public class SystemMessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, SystemMessageDetail, SystemMessageQuery, SystemMessageDetail, SystemMessageUpdate> implements ISystemMessageService {
}
