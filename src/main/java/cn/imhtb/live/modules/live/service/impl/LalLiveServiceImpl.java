package cn.imhtb.live.modules.live.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.imhtb.live.common.config.LalLiveConfig;
import cn.imhtb.live.common.enums.LiveInfoStatusEnum;
import cn.imhtb.live.common.enums.LiveRoomStatusEnum;
import cn.imhtb.live.mappers.MessageMapper;
import cn.imhtb.live.mappers.PresentRewardMapper;
import cn.imhtb.live.modules.live.service.ILiveInfoService;
import cn.imhtb.live.modules.live.service.ILiveService;
import cn.imhtb.live.modules.live.vo.StopLiveStatsVo;
import cn.imhtb.live.modules.server.RedisPrefix;
import cn.imhtb.live.pojo.LiveStatusVo;
import cn.imhtb.live.pojo.StartOpenLiveVo;
import cn.imhtb.live.pojo.database.LiveInfo;
import cn.imhtb.live.pojo.database.Message;
import cn.imhtb.live.pojo.database.PresentReward;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.common.enums.PresentRewardTypeEnum;
import cn.imhtb.live.common.utils.RedisUtil;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * lal直播服务实现
 *
 * @author pinteh
 * @since 2022/06/13
 */
@Slf4j
@Service("LalLiveService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LalLiveServiceImpl implements ILiveService {

    private final IRoomService roomService;
    private final ITokenService tokenService;
    private final LalLiveConfig lalLiveConfig;
    private final ILiveInfoService liveInfoService;
    private final MessageMapper messageMapper;
    private final PresentRewardMapper presentRewardMapper;
    private final RedisUtil redisUtil;

    @Override
    public String getName() {
        return "lal";
    }

    @Override
    public StartOpenLiveVo applySecret() {
        Integer userId = tokenService.getUserId();
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
        if (Objects.isNull(room)) {
            throw new RuntimeException("开播失败");
        }

        // 设置密钥
        String digestStr = lalLiveConfig.getSecret() + room.getId();
        String digestStrHex = MD5.create().digestHex(digestStr);
        String pushSecret = String.format("%d?lal_secret=%s", room.getId(), digestStrHex);
        // 更新直播间信息
        room.setSecret(pushSecret);
        room.setPushUrl(lalLiveConfig.getRtmpPushStream());
        room.setPullUrl(lalLiveConfig.getFlvPullStream() + room.getId() + ".flv");
        room.setStatus(LiveRoomStatusEnum.LIVING.getCode());
        roomService.updateById(room);
        redisUtil.remove(getLiveViewerKey(room.getId()));

        // 添加直播记录
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setUserId(userId);
        liveInfo.setRoomId(room.getId());
        liveInfo.setStatus(LiveInfoStatusEnum.LIVING.getCode());
        liveInfo.setStartTime(LocalDateTime.now());
        liveInfoService.save(liveInfo);

        StartOpenLiveVo startOpenLiveVo = new StartOpenLiveVo();
        startOpenLiveVo.setPushUrl(lalLiveConfig.getRtmpPushStream());
        startOpenLiveVo.setSecret(pushSecret);
        return startOpenLiveVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StopLiveStatsVo stopLive() {
        Integer userId = tokenService.getUserId();
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
        if (Objects.isNull(room) || room.getStatus() != LiveRoomStatusEnum.LIVING.getCode()) {
            throw new RuntimeException("关闭直播失败");
        }

        LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
        if (Objects.isNull(historyLiveInfo)) {
            throw new RuntimeException("直播记录不存在");
        }
        historyLiveInfo.setStatus(LiveInfoStatusEnum.FINISHED.getCode());
        historyLiveInfo.setEndTime(LocalDateTime.now());

        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<Message>().eq(Message::getRoomId, room.getId())
                .ge(Message::getCreateTime, historyLiveInfo.getStartTime())
                .le(Message::getCreateTime, historyLiveInfo.getEndTime());
        Long messageCount = messageMapper.selectCount(wrapper);
        historyLiveInfo.setMessageCount(messageCount);

        BigDecimal presentAmount = getPresentAmount(room, historyLiveInfo);
        historyLiveInfo.setPresentCount(presentAmount.longValue());

        Long totalViewCount = getLiveViewerCount(room.getId());
        historyLiveInfo.setClickCount(totalViewCount);
        liveInfoService.updateById(historyLiveInfo);

        room.setStatus(LiveRoomStatusEnum.STOP.getCode());
        roomService.updateById(room);
        redisUtil.remove(getLiveViewerKey(room.getId()));

        log.info("initiative stop live, roomId = {}, userId = {}", room.getId(), userId);
        return StopLiveStatsVo.builder()
                .presentAmount(presentAmount)
                .danMuCount(messageCount)
                .totalViewCount(totalViewCount)
                .liveDurationSeconds(getLiveDurationSeconds(historyLiveInfo))
                .build();
    }

    @Override
    public LiveStatusVo getLiveStatus() {
        Integer userId = tokenService.getUserId();
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
        if (Objects.isNull(room)) {
            throw new RuntimeException("获取直播间信息失败");
        }

        LiveStatusVo build = LiveStatusVo.builder()
                .liveStatus(room.getStatus())
                .livePushUrl(lalLiveConfig.getRtmpPushStream())
                .build();

        if (room.getStatus() == LiveRoomStatusEnum.LIVING.getCode()) {
            LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
            build.setLivePushSecret(room.getSecret());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            build.setLiveStartTime(formatter.format(historyLiveInfo.getStartTime()));
        }
        return build;
    }

    /**
     * 获取历史直播信息
     *
     * @param room 直播间
     * @return {@link LiveInfo}
     */
    private LiveInfo getHistoryLiveInfo(Room room) {
        return liveInfoService.getOne(new LambdaQueryWrapper<LiveInfo>().eq(LiveInfo::getRoomId, room.getId())
                        .eq(LiveInfo::getStatus, LiveInfoStatusEnum.LIVING.getCode())
                        .orderByDesc(LiveInfo::getCreateTime)
                        .last("limit 1"),
                false);
    }

    private BigDecimal getPresentAmount(Room room, LiveInfo liveInfo) {
        List<PresentReward> rewards = presentRewardMapper.selectList(new LambdaQueryWrapper<PresentReward>()
                .eq(PresentReward::getRoomId, room.getId())
                .eq(PresentReward::getToId, room.getUserId())
                .and(wrapper -> wrapper.eq(PresentReward::getType, PresentRewardTypeEnum.LIVE.getCode())
                        .or()
                        .isNull(PresentReward::getType))
                .ge(PresentReward::getCreateTime, liveInfo.getStartTime())
                .le(PresentReward::getCreateTime, liveInfo.getEndTime()));
        return rewards.stream()
                .map(PresentReward::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long getLiveViewerCount(Integer roomId) {
        Set<Object> viewers = redisUtil.setMembers(getLiveViewerKey(roomId));
        return Objects.isNull(viewers) ? 0L : (long) viewers.size();
    }

    private Long getLiveDurationSeconds(LiveInfo liveInfo) {
        if (Objects.isNull(liveInfo.getStartTime()) || Objects.isNull(liveInfo.getEndTime())) {
            return 0L;
        }
        return Math.max(Duration.between(liveInfo.getStartTime(), liveInfo.getEndTime()).getSeconds(), 0L);
    }

    private String getLiveViewerKey(Integer roomId) {
        return String.format(RedisPrefix.LIVE_VIEWER_SET_KEY, roomId);
    }

}
