package cn.imhtb.live.modules.system.service;

import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.modules.system.model.SystemRoleDetail;
import cn.imhtb.live.modules.system.model.SystemRoleQuery;
import cn.imhtb.live.modules.system.model.SystemRoleUpdate;
import cn.imhtb.live.pojo.vo.FrontMenuItemResp;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/5/6
 */
public interface ISystemRoleService extends IBaseService<SystemRoleDetail, SystemRoleQuery, SystemRoleDetail, SystemRoleUpdate> {

    List<FrontMenuItemResp> listMenusByRole(Integer roleId, Integer pid);

    List<FrontMenuItemResp> listMenus(Integer pid, Integer hidden);

}
