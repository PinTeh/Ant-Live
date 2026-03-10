package cn.imhtb.live.modules.user.service;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.UserRole;
import cn.imhtb.live.pojo.vo.request.UserRoleUpdateRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
public interface IUserRoleService extends IService<UserRole> {

    ApiResponse updateUserRole(UserRoleUpdateRequest request);

    List<Integer> listHasRoleUserIds();

}
