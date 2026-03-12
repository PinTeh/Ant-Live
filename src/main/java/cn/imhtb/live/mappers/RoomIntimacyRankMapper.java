package cn.imhtb.live.mappers;

import cn.imhtb.live.modules.live.vo.RoomIntimacyRankRespVo;
import cn.imhtb.live.pojo.database.RoomIntimacyRank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface RoomIntimacyRankMapper extends BaseMapper<RoomIntimacyRank> {

    @Insert("insert into room_intimacy_rank (room_id, user_id, intimacy_value, create_time, update_time) " +
            "values (#{roomId}, #{userId}, #{delta}, now(), now()) " +
            "on duplicate key update intimacy_value = intimacy_value + #{delta}, update_time = now()")
    int increase(@Param("roomId") Integer roomId, @Param("userId") Integer userId, @Param("delta") BigDecimal delta);

    @Select("select rir.user_id as userId, u.nickname as nickName, u.avatar as avatar, rir.intimacy_value as intimacyValue " +
            "from room_intimacy_rank rir " +
            "left join `user` u on u.id = rir.user_id " +
            "where rir.room_id = #{roomId} " +
            "order by rir.intimacy_value desc, rir.update_time asc " +
            "limit #{limit}")
    List<RoomIntimacyRankRespVo> selectTopByRoomId(@Param("roomId") Integer roomId, @Param("limit") Integer limit);

    @Delete("delete from room_intimacy_rank")
    int clearAll();
}
