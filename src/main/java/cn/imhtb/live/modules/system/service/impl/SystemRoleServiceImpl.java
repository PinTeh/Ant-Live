package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.RoleMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemRoleDetail;
import cn.imhtb.live.modules.system.model.SystemRoleQuery;
import cn.imhtb.live.modules.system.model.SystemRoleUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoleService;
import cn.imhtb.live.pojo.database.Role;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Service
public class SystemRoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, SystemRoleDetail, SystemRoleQuery, SystemRoleDetail, SystemRoleUpdate> implements ISystemRoleService {
}
