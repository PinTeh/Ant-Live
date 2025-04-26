package cn.imhtb.live.mappers;

import cn.imhtb.live.pojo.database.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) from user where date(create_time) = curdate()")
    Integer countToday();

}
