package cn.imhtb.antlive.service;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.Menu;
import cn.imhtb.antlive.vo.FrontMenuItem;
import cn.imhtb.antlive.vo.request.RoleMenuUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    ApiResponse listMenus(Integer pid,Integer hidden);

    List<FrontMenuItem> listMenusByRole(Integer roleId, Integer pid);

    ApiResponse listMenusByRoleIds(List<Integer> roleIds, Integer pid);

    ApiResponse updateRoleMenu(RoleMenuUpdateRequest request);
}
