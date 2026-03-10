package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.SysDictMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemDictDetail;
import cn.imhtb.live.modules.system.model.SystemDictQuery;
import cn.imhtb.live.modules.system.model.SystemDictUpdate;
import cn.imhtb.live.modules.system.service.ISystemDictService;
import cn.imhtb.live.pojo.database.SysDict;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Service
public class SystemDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict, SystemDictDetail, SystemDictQuery, SystemDictDetail, SystemDictUpdate> implements ISystemDictService {
}
