package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.mappers.RoomMapper;
import cn.imhtb.antlive.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
}
