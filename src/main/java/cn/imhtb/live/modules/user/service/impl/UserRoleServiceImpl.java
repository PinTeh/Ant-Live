package cn.imhtb.live.modules.user.service.impl;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.UserRole;
import cn.imhtb.live.mappers.UserRoleMapper;
import cn.imhtb.live.modules.user.service.IUserRoleService;
import cn.imhtb.live.pojo.vo.request.UserRoleUpdateRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public ApiResponse updateUserRole(UserRoleUpdateRequest request) {
        Integer userId = request.getUserId();
        List<Integer> currentRoleIds = request.getRoleIds();
        // 获取当前用户的角色
        // [1,2,3,4,5,6,7]  //2 -
        // [1,3,4,5,6,7,8]  //8 +

        // [1,2,3] // 123 -
        // [4,5] //4,5 +
        List<Integer> roleIds = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", userId)).stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Integer> sub = new ArrayList<>(roleIds);
        List<Integer> plus = new ArrayList<>(currentRoleIds);
        sub.removeAll(currentRoleIds);
        plus.removeAll(roleIds);

        for (Integer rid : sub) {
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("role_id", rid).eq("user_id", userId));
        }
        for (Integer mid : plus) {
            userRoleMapper.insert(UserRole.builder().userId(userId).roleId(mid).build());
        }
        return ApiResponse.ofSuccess();
    }

    @Override
    public List<Integer> listHasRoleUserIds() {
        return userRoleMapper.listHasRoleUserIds();
    }
}
