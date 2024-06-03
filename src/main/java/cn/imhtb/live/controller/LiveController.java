package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.LiveInfo;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.database.LiveDetect;
import cn.imhtb.live.enums.LiveRoomStatusEnum;
import cn.imhtb.live.service.ILiveDetectService;
import cn.imhtb.live.service.ILiveInfoService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITencentLiveService;
import cn.imhtb.live.utils.CommonUtil;
import cn.imhtb.live.utils.JwtUtil;
import cn.imhtb.live.pojo.vo.request.RoomRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * nginx-rtmp-module
 *
 * @author PinTeh
 */
@RestController
@RequestMapping("/live")
public class LiveController {

    private final IRoomService roomService;

    private final ILiveInfoService liveInfoService;

    private final ITencentLiveService tencentLiveService;

    private final ILiveDetectService liveDetectService;

    public LiveController(IRoomService roomService, ILiveInfoService liveInfoService, ITencentLiveService tencentLiveService,
                          ILiveDetectService liveDetectService) {
        this.roomService = roomService;
        this.liveInfoService = liveInfoService;
        this.tencentLiveService = tencentLiveService;
        this.liveDetectService = liveDetectService;
    }

    /**
     * nginx授权认证
     */
    @RequestMapping("/on_publish")
    public ApiResponse publish(String name, String key, HttpServletResponse response) {
        Integer roomId = Integer.valueOf(name);
        Room room = roomService.getById(roomId);
        if (room != null) {
            if (key.equals(room.getSecret())) {
                return ApiResponse.ofSuccess();
            }
        }
        response.setStatus(400);
        return ApiResponse.ofError();
    }

    @RequestMapping("/ban")
    public void ban() throws TencentCloudSDKException {
        tencentLiveService.ban(1, "", "");
    }

    @RequestMapping("/on_done")
    public ApiResponse done(String name) {
        Integer roomId = Integer.valueOf(name);
        Room room = new Room();
        room.setId(roomId);
        room.setStatus(LiveRoomStatusEnum.STOP.getCode());
        roomService.updateById(room);

        //更新直播信息
        LiveInfo liveInfo = liveInfoService.getOne(new QueryWrapper<LiveInfo>().eq("room_id", roomId).orderByDesc("id").last("limit 0,1"));
        if (liveInfo.getEndTime() != null) {
            //TODO
            return ApiResponse.ofSuccess();
        }
        liveInfo.setEndTime(LocalDateTime.now());
        liveInfo.setStatus(1);
        liveInfoService.updateById(liveInfo);

        return ApiResponse.ofSuccess();
    }

    @PostMapping("/open")
    public ApiResponse open(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.getHeaderKey());
        Integer uid = JwtUtil.getId(token);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id", uid));
        if (room == null || room.getStatus() != LiveRoomStatusEnum.STOP.getCode()) {
            return ApiResponse.ofError("开播失败");
        }
        room.setSecret(CommonUtil.getRandomString());
        room.setStatus(LiveRoomStatusEnum.LIVING.getCode());
        roomService.updateById(room);

        //记录直播时长
        LocalDateTime dateTime = LocalDateTime.now();
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setUserId(uid);
        liveInfo.setRoomId(room.getId());
        liveInfo.setStatus(0);
        liveInfo.setStartTime(dateTime);
        liveInfoService.save(liveInfo);

        return ApiResponse.ofSuccess(liveInfo);
    }

    /**
     * 更新直播间信息
     */
    @PostMapping("/config")
    public ApiResponse config(@RequestBody RoomRequest request) {
        Room room = new Room();
        BeanUtils.copyProperties(request, room);
        roomService.updateById(room);
        return ApiResponse.ofSuccess(room);
    }

    /**
     * 获取用户封禁记录
     */
    @GetMapping("/ban/list")
    public ApiResponse banList(HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request);
        List<LiveDetect> list = liveDetectService.list(new QueryWrapper<LiveDetect>().eq("user_id", uid).eq("handle_status", 1).orderByDesc("id"));
        return ApiResponse.ofSuccess(list);
    }
}
