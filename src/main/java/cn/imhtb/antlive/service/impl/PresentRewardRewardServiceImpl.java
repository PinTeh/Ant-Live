package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.PresentReward;
import cn.imhtb.antlive.entity.database.Video;
import cn.imhtb.antlive.mappers.*;
import cn.imhtb.antlive.server.RedisPrefix;
import cn.imhtb.antlive.server.RoomChatServer;
import cn.imhtb.antlive.server.WebMessage;
import cn.imhtb.antlive.service.IPresentRewardService;
import cn.imhtb.antlive.utils.CommonUtils;
import cn.imhtb.antlive.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PresentRewardRewardServiceImpl extends ServiceImpl<PresentRewardMapper, PresentReward> implements IPresentRewardService {

    private final UserMapper userMapper;

    private final BillMapper billMapper;

    private final PresentRewardMapper presentRewardMapper;

    private final RoomChatServer roomChatServer;

    private final VideoMapper videoMapper;

    private final PresentMapper presentMapper;

    private final RoomMapper roomMapper;

    private final RedisUtils redisUtils;

    public PresentRewardRewardServiceImpl(PresentRewardMapper presentRewardMapper, BillMapper billMapper, UserMapper userMapper, RoomChatServer roomChatServer, VideoMapper videoMapper, PresentMapper presentMapper, RoomMapper roomMapper, RedisUtils redisUtils) {
        this.presentRewardMapper = presentRewardMapper;
        this.billMapper = billMapper;
        this.userMapper = userMapper;
        this.roomChatServer = roomChatServer;
        this.videoMapper = videoMapper;
        this.presentMapper = presentMapper;
        this.roomMapper = roomMapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean createReward(Integer fromId, Integer presentId, Integer rvId, Integer number,Integer type) {

        String mark = null;
        Integer toId = null;
        Room room;

        Present present = presentMapper.selectById(presentId);
        BigDecimal totalPrice = present.getPrice().multiply(new BigDecimal(number));
        Bill fromLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", fromId).orderByDesc("id").last("limit 0,1"));
        BigDecimal ret = fromLast.getBalance().subtract(totalPrice);
        //余额不足
        if (ret.compareTo(BigDecimal.ZERO) < 0){
            log.info("余额不足");
            return false;
        }

        PresentReward presentReward = new PresentReward();

        if (type.equals(Constants.PresentRewardType.LIVE.getCode())){
            room = roomMapper.selectById(rvId);
            toId = room.getUserId();
            presentReward.setRoomId(room.getId());
            presentReward.setType(Constants.PresentRewardType.LIVE.getCode());
            mark = Constants.BillMark.LIVE_REWARD.getDesc();

            // 直播间显示赠送礼物信息
            try {

                // TODO 逻辑有点问题
                String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, room.getId());
                if(redisUtils.exists(key)){
                    // 舍去小数点
                    redisUtils.hIncrBy(key,RedisPrefix.LIVE_PRESENT_COUNT,totalPrice.longValue());
                }

                User fromUser = userMapper.selectById(fromId);
                WebMessage webMessage = WebMessage.createPresent(
                        fromUser,
                        new WebMessage.P(present.getId(), present.getName(), present.getIcon(), number));
                roomChatServer.sendSystemPresentMessage(webMessage.toJson(),room.getId());
            }catch (Exception e){
                log.error("RoomChatServer err:{}",e.getMessage());
            }

        }else if (type.equals(Constants.PresentRewardType.VIDEO.getCode())){
            Video video = videoMapper.selectById(rvId);
            toId = video.getUserId();
            presentReward.setVideoId(video.getId());
            presentReward.setType(Constants.PresentRewardType.VIDEO.getCode());
            mark = Constants.BillMark.VIDEO_REWARD.getDesc();
        }

        presentReward.setNumber(number);
        presentReward.setUnitPrice(present.getPrice());
        presentReward.setFromId(fromId);
        presentReward.setToId(toId);
        presentReward.setPresentId(present.getId());
        presentReward.setTotalPrice(totalPrice);

        Bill toLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", toId).orderByDesc("id").last("limit 0,1"));

        Bill fromBill = new Bill();
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setBalance(fromLast.getBalance().subtract(totalPrice));
        fromBill.setMark(mark);
        fromBill.setType(Constants.BillType.OUTLAY.getCode());
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setUserId(fromId);
        fromBill.setOrderNo(CommonUtils.getOrderNo());

        Bill toBill = new Bill();
        toBill.setBillChange(totalPrice);
        toBill.setBalance(toLast.getBalance().add(totalPrice));
        toBill.setMark(mark);
        toBill.setType(Constants.BillType.INCOME.getCode());
        toBill.setBillChange(totalPrice);
        toBill.setUserId(toId);
        toBill.setOrderNo(CommonUtils.getOrderNo());

        int rf = presentRewardMapper.insert(presentReward);
        int rs = billMapper.insert(fromBill);
        int rt = billMapper.insert(toBill);

        return rf == 1 && rs == 1 && rt ==1;
    }
}
