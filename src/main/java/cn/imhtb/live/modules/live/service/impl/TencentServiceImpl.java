package cn.imhtb.live.modules.live.service.impl;

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

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author pinteh
 * @date 2024/6/4
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TencentServiceImpl implements ILiveService {

    private final IRoomService roomService;
    private final ITokenService tokenService;
    private final ILiveInfoService liveInfoService;

    @Override
    public String getName() {
        return "tencent";
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
        liveInfo.setStatus(LiveInfoStatusEnum.LIVING.getCode());
        liveInfo.setStartTime(LocalDateTime.now());
        liveInfoService.save(liveInfo);

        StartOpenLiveVo startOpenLiveVo = new StartOpenLiveVo();
        startOpenLiveVo.setPushUrl("");
        startOpenLiveVo.setSecret(room.getId() + "");
        return startOpenLiveVo;
    }

    @Override
    public void stopLive() {

    }

    @Override
    public LiveStatusVo getLiveStatus() {
        return null;
    }

}
