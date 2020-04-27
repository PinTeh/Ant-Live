package cn.imhtb.antlive.service;

import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.database.RoomPresent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
public interface IRoomPresentService extends IService<RoomPresent> {

    boolean create(Integer fromId, Present present, Room room,Integer number);
}
