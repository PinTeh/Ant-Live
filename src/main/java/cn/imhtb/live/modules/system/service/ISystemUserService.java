package cn.imhtb.live.modules.system.service;

import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.modules.system.model.SystemUserDetail;
import cn.imhtb.live.modules.system.model.SystemUserQuery;
import cn.imhtb.live.modules.system.model.SystemUserUpdate;

/**
 * @author pinteh
 * @date 2025/4/29
 */
public interface ISystemUserService extends IBaseService<SystemUserDetail, SystemUserQuery, SystemUserDetail, SystemUserUpdate> {
}
