package cn.imhtb.live.modules.system.service;

import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.modules.system.model.SystemMenuDetail;
import cn.imhtb.live.modules.system.model.SystemMenuQuery;
import cn.imhtb.live.modules.system.model.SystemMenuUpdate;

/**
 * @author pinteh
 * @date 2025/4/30
 */
public interface ISystemMenuService extends IBaseService<SystemMenuDetail, SystemMenuQuery, SystemMenuDetail, SystemMenuUpdate> {
}
