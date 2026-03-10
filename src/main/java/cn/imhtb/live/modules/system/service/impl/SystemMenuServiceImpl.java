package cn.imhtb.live.modules.system.service.impl;

import cn.imhtb.live.mappers.MenuMapper;
import cn.imhtb.live.modules.infra.service.impl.BaseServiceImpl;
import cn.imhtb.live.modules.system.model.SystemMenuDetail;
import cn.imhtb.live.modules.system.model.SystemMenuQuery;
import cn.imhtb.live.modules.system.model.SystemMenuUpdate;
import cn.imhtb.live.modules.system.service.ISystemMenuService;
import cn.imhtb.live.pojo.database.Menu;
import org.springframework.stereotype.Service;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu, SystemMenuDetail, SystemMenuQuery, SystemMenuDetail, SystemMenuUpdate> implements ISystemMenuService {
}
