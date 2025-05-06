package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.UserMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemUserDetail;
import cn.imhtb.live.modules.system.model.SystemUserQuery;
import cn.imhtb.live.modules.system.model.SystemUserUpdate;
import cn.imhtb.live.modules.system.service.ISystemUserService;
import cn.imhtb.live.pojo.database.User;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Service
public class SystemUserServiceImpl extends BaseServiceImpl<UserMapper, User, SystemUserDetail, SystemUserQuery, SystemUserDetail, SystemUserUpdate> implements ISystemUserService{
}
