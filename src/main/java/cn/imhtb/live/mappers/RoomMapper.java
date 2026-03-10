package cn.imhtb.live.mappers;

import cn.imhtb.live.modules.system.model.SystemRoomDetail;
import cn.imhtb.live.pojo.database.Room;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 分页查询直播间详情（包含主播信息）
     */
    Page<SystemRoomDetail> pageDetail(Page<?> page, @Param(Constants.WRAPPER) QueryWrapper<Room> queryWrapper);
}
