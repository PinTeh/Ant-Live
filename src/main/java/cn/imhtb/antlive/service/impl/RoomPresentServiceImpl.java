package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.RoomPresent;
import cn.imhtb.antlive.mappers.BillMapper;
import cn.imhtb.antlive.mappers.RoomPresentMapper;
import cn.imhtb.antlive.mappers.UserMapper;
import cn.imhtb.antlive.server.RoomChatServer;
import cn.imhtb.antlive.server.WebMessage;
import cn.imhtb.antlive.service.IRoomPresentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
@Slf4j
@Service
@Transactional
public class RoomPresentServiceImpl extends ServiceImpl<RoomPresentMapper, RoomPresent> implements IRoomPresentService {

    private final UserMapper userMapper;

    private final BillMapper billMapper;

    private final RoomPresentMapper roomPresentMapper;

    private final RoomChatServer roomChatServer;

    public RoomPresentServiceImpl(RoomPresentMapper roomPresentMapper, BillMapper billMapper, UserMapper userMapper, RoomChatServer roomChatServer) {
        this.roomPresentMapper = roomPresentMapper;
        this.billMapper = billMapper;
        this.userMapper = userMapper;
        this.roomChatServer = roomChatServer;
    }

    @Override
    public boolean create(Integer fromId, Present present, Room room,Integer number) {

        Integer toId = room.getUserId();

        RoomPresent roomPresent = new RoomPresent();
        roomPresent.setNumber(number);
        roomPresent.setRoomId(room.getId());
        roomPresent.setUnitPrice(present.getPrice());
        roomPresent.setFromId(fromId);
        roomPresent.setToId(toId);
        roomPresent.setPresentId(present.getId());
        BigDecimal totalPrice = present.getPrice().multiply(new BigDecimal(number));
        roomPresent.setTotalPrice(totalPrice);

        Bill fromLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", fromId).orderByDesc("id").last("limit 0,1"));
        Bill toLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", toId).orderByDesc("id").last("limit 0,1"));

        BigDecimal ret = fromLast.getBalance().subtract(totalPrice);
        //余额不足
        if (ret.compareTo(BigDecimal.ZERO) < 0){
            log.info("余额不足");
            return false;
        }

        Bill fromBill = new Bill();
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setBalance(fromLast.getBalance().subtract(totalPrice));
        fromBill.setMark("赠送礼物");
        fromBill.setType(1);
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setUserId(fromId);

        Bill toBill = new Bill();
        toBill.setBillChange(totalPrice);
        toBill.setBalance(toLast.getBalance().add(totalPrice));
        toBill.setMark("收获礼物");
        toBill.setType(0);
        toBill.setBillChange(totalPrice);
        toBill.setUserId(toId);

        int rf = roomPresentMapper.insert(roomPresent);
        int rs = billMapper.insert(fromBill);
        int rt = billMapper.insert(toBill);

        User fromUser = userMapper.selectById(fromId);
        WebMessage webMessage = WebMessage.createPresent(
                fromUser,
                new WebMessage.P(present.getId(), present.getName(), present.getIcon(), number));
        roomChatServer.sendSystemPresentMessage(webMessage.toJson(),room.getId());

        return rf == 1 && rs == 1 && rt ==1;
    }
}
