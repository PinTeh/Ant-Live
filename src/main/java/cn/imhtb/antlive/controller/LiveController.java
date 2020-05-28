package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.database.LiveDetect;
import cn.imhtb.antlive.service.ILiveDetectService;
import cn.imhtb.antlive.service.ILiveInfoService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.service.ITencentLiveService;
import cn.imhtb.antlive.utils.CommonUtils;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.RoomRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * nginx-rtmp-module
 * @author PinTeh
 */
@RestController
@RequestMapping("/live")
public class LiveController {

    private final IRoomService roomService;

    private final ModelMapper modelMapper;

    private final ILiveInfoService liveInfoService;

    private final ITencentLiveService tencentLiveService;

    private final ILiveDetectService liveDetectService;

    public LiveController(IRoomService roomService, ModelMapper modelMapper, ILiveInfoService liveInfoService, ITencentLiveService tencentLiveService,
                          ILiveDetectService liveDetectService) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
        this.liveInfoService = liveInfoService;
        this.tencentLiveService = tencentLiveService;
        this.liveDetectService = liveDetectService;
    }

    /**
     * nginx授权认证
     */
    @RequestMapping("/on_publish")
    public ApiResponse publish(String name, String key, HttpServletResponse response){
        Integer roomId = Integer.valueOf(name);
        Room room = roomService.getById(roomId);
        if (room!=null){
            if (key.equals(room.getSecret())){
                return ApiResponse.ofSuccess();
            }
        }
        response.setStatus(400);
        return ApiResponse.ofError();
    }

    @RequestMapping("/ban")
    public void ban() throws TencentCloudSDKException {
        tencentLiveService.ban(1,"","");
    }

    @RequestMapping("/on_done")
    public ApiResponse done(String name){
        Integer roomId = Integer.valueOf(name);
        Room room = new Room();
        room.setId(roomId);
        room.setStatus(Constants.LiveStatus.STOP.getCode());
        roomService.updateById(room);

        //更新直播信息
        LiveInfo liveInfo = liveInfoService.getOne(new QueryWrapper<LiveInfo>().eq("room_id", roomId).orderByDesc("id").last("limit 0,1"));
        if (liveInfo.getEndTime()!=null){
            //TODO
            return ApiResponse.ofSuccess();
        }
        liveInfo.setEndTime(LocalDateTime.now());
        liveInfo.setStatus(1);
        liveInfoService.updateById(liveInfo);

        return ApiResponse.ofSuccess();
    }

    @PostMapping("/open")
    public ApiResponse open(HttpServletRequest request){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid));
        if (room == null || room.getStatus() != Constants.LiveStatus.STOP.getCode()){
            return ApiResponse.ofError("开播失败");
        }
        room.setSecret(CommonUtils.getRandomString());
        room.setStatus(Constants.LiveStatus.LIVING.getCode());
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
    public ApiResponse config(@RequestBody RoomRequest request){
        Room room = modelMapper.map(request, Room.class);
        roomService.updateById(room);
        return ApiResponse.ofSuccess(room);
    }

    /**
     * 获取用户封禁记录
     */
    @GetMapping("/ban/list")
    public ApiResponse banList(HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        List<LiveDetect> list = liveDetectService.list(new QueryWrapper<LiveDetect>().eq("user_id", uid).eq("handle_status", 1).orderByDesc("id"));
        return ApiResponse.ofSuccess(list);
    }
}
