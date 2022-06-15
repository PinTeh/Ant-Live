package cn.imhtb.antlive.modules.live.impl;

import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.config.LalLiveConfig;
import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.modules.live.ILiveService;
import cn.imhtb.antlive.pojo.LiveStatusVo;
import cn.imhtb.antlive.pojo.StartOpenLiveVo;
import cn.imhtb.antlive.service.ILiveInfoService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.utils.LocalDateTimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * lal直播服务impl
 *
 * @author pinteh
 * @since  2022/06/13
 */
@Slf4j
@Service
public class LalLiveServiceImpl implements ILiveService {

    @Resource
    private IRoomService roomService;

    @Resource
    private LalLiveConfig lalLiveConfig;

    @Resource
    private ILiveInfoService liveInfoService;

    @Override
    public StartOpenLiveVo applySecret(HttpServletRequest request) {
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, uid));
        if (Objects.isNull(room)){
            throw new RuntimeException("开播失败");
        }
        // TODO 设置密钥
        room.setSecret("todo");
        room.setStatus(Constants.LiveStatus.LIVING.getCode());
        roomService.updateById(room);

        // 添加直播记录
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setUserId(uid);
        liveInfo.setRoomId(room.getId());
        liveInfo.setStatus(Constants.LiveInfoStatus.NO.getCode());
        liveInfo.setStartTime(LocalDateTime.now());
        liveInfoService.save(liveInfo);

        StartOpenLiveVo startOpenLiveVo = new StartOpenLiveVo();
        startOpenLiveVo.setPushUrl(lalLiveConfig.getRtmpPushStream());
        startOpenLiveVo.setSecret(room.getId() + "");
        return startOpenLiveVo;
    }

    @Override
    public void stopLive(HttpServletRequest request) {
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, uid));
        if (Objects.isNull(room) || room.getStatus() != Constants.LiveStatus.LIVING.getCode()){
            throw new RuntimeException("关闭直播失败");
        }

        LiveInfo historyLiveInfo = getHistoryLiveInfo(room);
        historyLiveInfo.setStatus(Constants.LiveInfoStatus.YES.getCode());
        historyLiveInfo.setEndTime(LocalDateTime.now());
        liveInfoService.updateById(historyLiveInfo);

        room.setStatus(Constants.LiveStatus.STOP.getCode());
        roomService.updateById(room);

        log.info("initiative stop live, roomId = {}, userId = {}", room.getId(), uid);
    }

    @Override
    public LiveStatusVo getLiveStatus(HttpServletRequest request) {
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new LambdaQueryWrapper<Room>().eq(Room::getUserId, uid));
        if (Objects.isNull(room)){
            throw new RuntimeException("获取直播间信息失败");
        }

        LiveStatusVo build = LiveStatusVo.builder()
                .liveStatus(room.getStatus())
                .livePushUrl(lalLiveConfig.getRtmpPushStream())
                // TODO
                .livePushSecret("--")
                .liveStartTime("2022-01-01 12:12:00")
                .build();

        if (room.getStatus() == Constants.LiveStatus.LIVING.getCode()){
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
                .eq(LiveInfo::getStatus, Constants.LiveInfoStatus.NO.getCode())
                .orderByDesc(LiveInfo::getCreateTime)
                .last("limit 1"),
                false);
    }

}
