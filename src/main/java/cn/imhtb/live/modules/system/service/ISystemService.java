package cn.imhtb.live.modules.system.service;

import cn.imhtb.live.pojo.vo.FrontMenuItemResp;

import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/26
 */
public interface ISystemService {

    /**
     * 获取菜单项列表
     *
     * @return 菜单项列表
     */
    List<FrontMenuItemResp> getMenus();

}
