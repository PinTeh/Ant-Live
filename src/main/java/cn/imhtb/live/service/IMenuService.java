package cn.imhtb.live.service;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Menu;
import cn.imhtb.live.pojo.vo.FrontMenuItem;
import cn.imhtb.live.pojo.vo.request.RoleMenuUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<FrontMenuItem> listMenus(Integer pid, Integer hidden);

    List<FrontMenuItem> listMenusByRole(Integer roleId, Integer pid);

    List<FrontMenuItem> listMenusByRoleIds(List<Integer> roleIds, Integer pid);

    ApiResponse updateRoleMenu(RoleMenuUpdateRequest request);
}
