package cn.imhtb.live.modules.live.service;

import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import cn.imhtb.live.modules.system.model.SystemMessageQuery;

/**
 * @author pinteh
 * @date 2025/7/20
 */
public interface IMessageService {

    PageData<SystemMessageDetail> pageDetail(PageQuery pageQuery, SystemMessageQuery query);

}
