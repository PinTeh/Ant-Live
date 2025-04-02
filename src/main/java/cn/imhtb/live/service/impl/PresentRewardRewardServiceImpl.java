package cn.imhtb.live.service.impl;

import cn.imhtb.live.enums.BillMarkEnum;
import cn.imhtb.live.enums.BillTypeEnum;
import cn.imhtb.live.enums.PresentRewardTypeEnum;
import cn.imhtb.live.mappers.BillMapper;
import cn.imhtb.live.mappers.PresentMapper;
import cn.imhtb.live.mappers.PresentRewardMapper;
import cn.imhtb.live.mappers.VideoMapper;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.modules.server.RoomChatServer;
import cn.imhtb.live.modules.server.netty.service.IRoomChatService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.Bill;
import cn.imhtb.live.pojo.Present;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.database.PresentReward;
import cn.imhtb.live.pojo.database.Video;
import cn.imhtb.live.service.IPresentRewardService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import cn.imhtb.live.utils.CommonUtil;
import cn.imhtb.live.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PresentRewardRewardServiceImpl extends ServiceImpl<PresentRewardMapper, PresentReward> implements IPresentRewardService {

    private final IUserService userService;
    private final BillMapper billMapper;
    private final PresentRewardMapper presentRewardMapper;
    private final RoomChatServer roomChatServer;
    private final VideoMapper videoMapper;
    private final PresentMapper presentMapper;
    private final IRoomService roomService;
    private final RedisUtil redisUtil;
    private final ITokenService tokenService;
    private final IRoomChatService roomChatService;


    @Override
    public String createReward(Integer presentId, Integer rvId, Integer number, Integer type) {
        Integer userId = tokenService.getUserId();
        String mark = null;
        Integer toId = null;
        Room room;

        Present present = presentMapper.selectById(presentId);
        if (present == null){
            return "礼物不存在";
        }
        BigDecimal totalPrice = present.getPrice().multiply(new BigDecimal(number));
        Bill fromLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", userId).orderByDesc("id").last("limit 0,1"));
        BigDecimal ret = fromLast.getBalance().subtract(totalPrice);
        //余额不足
        if (ret.compareTo(BigDecimal.ZERO) < 0) {
            return "余额不足";
        }

        PresentReward presentReward = new PresentReward();

        if (type.equals(PresentRewardTypeEnum.LIVE.getCode())) {
            room = roomService.getById(rvId);
            toId = room.getUserId();
            if (Objects.equals(userId, toId)){
                return "不允许给自己送礼物哦～";
            }
            presentReward.setRoomId(room.getId());
            presentReward.setType(PresentRewardTypeEnum.LIVE.getCode());
            mark = BillMarkEnum.LIVE_REWARD.getDesc();
            // 直播间显示赠送礼物信息
            try {
                // TODO 逻辑有点问题
//                String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, room.getId());
//                if (redisUtil.exists(key)) {
//                    // 舍去小数点
//                    redisUtil.hIncrBy(key, RedisPrefix.LIVE_PRESENT_COUNT, totalPrice.longValue());
//                }
                User fromUser = userService.getById(userId);
//                WebMessage webMessage = WebMessage.createPresent(
//                        fromUser,
//                        new WebMessage.P(present.getId(), present.getName(), present.getIcon(), number));
//                roomChatServer.sendSystemPresentMessage(webMessage.toJson(), room.getId());
                String msgFormat = "%s赠送了%s * %d";
                roomChatService.sendGiftMsg(String.format(msgFormat, fromUser.getNickname(), present.getName(), number), room.getId(), fromUser.getId());
            } catch (Exception e) {
                log.error("RoomChatServer error", e);
            }

        } else if (type.equals(PresentRewardTypeEnum.VIDEO.getCode())) {
            Video video = videoMapper.selectById(rvId);
            toId = video.getUserId();
            presentReward.setVideoId(video.getId());
            presentReward.setType(PresentRewardTypeEnum.VIDEO.getCode());
            mark = BillMarkEnum.VIDEO_REWARD.getDesc();
        }

        presentReward.setNumber(number);
        presentReward.setUnitPrice(present.getPrice());
        presentReward.setFromId(userId);
        presentReward.setToId(toId);
        presentReward.setPresentId(present.getId());
        presentReward.setTotalPrice(totalPrice);

        Bill toLast = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", toId).orderByDesc("id").last("limit 0,1"));

        Bill fromBill = new Bill();
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setBalance(fromLast.getBalance().subtract(totalPrice));
        fromBill.setMark(mark);
        fromBill.setType(BillTypeEnum.OUTLAY.getCode());
        fromBill.setBillChange(totalPrice.negate());
        fromBill.setUserId(userId);
        fromBill.setOrderNo(CommonUtil.getOrderNo());

        Bill toBill = new Bill();
        toBill.setBillChange(totalPrice);
        toBill.setBalance(toLast.getBalance().add(totalPrice));
        toBill.setMark(mark);
        toBill.setType(BillTypeEnum.INCOME.getCode());
        toBill.setBillChange(totalPrice);
        toBill.setUserId(toId);
        toBill.setOrderNo(CommonUtil.getOrderNo());

        int rf = presentRewardMapper.insert(presentReward);
        int rs = billMapper.insert(fromBill);
        int rt = billMapper.insert(toBill);

        if (rf == 1 && rs == 1 && rt == 1){
            return null;
        }
        return "未知错误";
    }

    @Override
    public void createReward(RewardReqVo rewardReqVo) {


    }

}
