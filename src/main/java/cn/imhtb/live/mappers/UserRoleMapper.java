package cn.imhtb.live.mappers;

import cn.imhtb.live.pojo.database.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("select distinct user_id from user_role")
    List<Integer> listHasRoleUserIds();

}
