package cn.imhtb.live.modules.live.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.imhtb.live.common.config.LalLiveConfig;
import cn.imhtb.live.common.enums.LiveInfoStatusEnum;
import cn.imhtb.live.common.enums.LiveRoomStatusEnum;
import cn.imhtb.live.modules.live.service.ILiveService;
import cn.imhtb.live.pojo.LiveInfo;
import cn.imhtb.live.pojo.LiveStatusVo;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.StartOpenLiveVo;
import cn.imhtb.live.service.ILiveInfoService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITokenService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * lal直播服务impl
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
    public void stopLive() {
        Integer userId = tokenService.getUserId();
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
        if (Objects.isNull(room) || room.getStatus() != LiveRoomStatusEnum.LIVING.getCode()) {
            throw new RuntimeException("关闭直播失败");
        }

        LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
        historyLiveInfo.setStatus(LiveInfoStatusEnum.FINISHED.getCode());
        historyLiveInfo.setEndTime(LocalDateTime.now());
        liveInfoService.updateById(historyLiveInfo);

        room.setStatus(LiveRoomStatusEnum.STOP.getCode());
        roomService.updateById(room);

        log.info("initiative stop live, roomId = {}, userId = {}", room.getId(), userId);
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

}
