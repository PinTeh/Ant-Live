package cn.imhtb.live.modules.live.service.impl;

import cn.imhtb.live.config.LalLiveConfig;
import cn.imhtb.live.enums.LiveInfoStatusEnum;
import cn.imhtb.live.enums.LiveRoomStatusEnum;
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
@Service
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
        // TODO 设置密钥
        room.setSecret("todo");
        room.setStatus(LiveRoomStatusEnum.LIVING.getCode());
        roomService.updateById(room);

        // 添加直播记录
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setUserId(userId);
        liveInfo.setRoomId(room.getId());
        liveInfo.setStatus(LiveInfoStatusEnum.NO.getCode());
        liveInfo.setStartTime(LocalDateTime.now());
        liveInfoService.save(liveInfo);

        StartOpenLiveVo startOpenLiveVo = new StartOpenLiveVo();
        startOpenLiveVo.setPushUrl(lalLiveConfig.getRtmpPushStream());
        startOpenLiveVo.setSecret(room.getId() + "");
        return startOpenLiveVo;
    }

    @Override
    public void stopLive() {
        Integer userId = tokenService.getUserId();
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, userId));
        if (Objects.isNull(room) || room.getStatus() != LiveRoomStatusEnum.LIVING.getCode()) {
            throw new RuntimeException("关闭直播失败");
        }

        LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
        historyLiveInfo.setStatus(LiveInfoStatusEnum.YES.getCode());
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
                // TODO
                .livePushSecret("--")
                .liveStartTime("2022-01-01 12:12:00")
                .build();

        if (room.getStatus() == LiveRoomStatusEnum.LIVING.getCode()) {
            LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
            build.setLivePushSecret(room.getId() + "");
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
                        .eq(LiveInfo::getStatus, LiveInfoStatusEnum.NO.getCode())
                        .orderByDesc(LiveInfo::getCreateTime)
                        .last("limit 1"),
                false);
    }

}