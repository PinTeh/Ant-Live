package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.pojo.vo.RoomExtraInfo;
import cn.imhtb.live.pojo.vo.request.RoomInfoSaveRequest;
import cn.imhtb.live.pojo.vo.response.RoomResponse;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.IWatchService;
import cn.imhtb.live.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author PinTeh
 */
@Api(tags = "room", value = "直播间接口")
@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoomController {

    private final IRoomService roomService;
    private final IWatchService watchService;

    @ApiOperation("获取直播间信息")
    @GetMapping
    public ApiResponse<RoomResponse> getRoomInfo(Integer rid) {
        return ApiResponse.ofSuccess(roomService.getRoomInfo(rid));
    }

    @ApiOperation("更新保存直播间信息")
    @PostMapping("/info/save")
    public ApiResponse<?> saveInfo(@RequestBody RoomInfoSaveRequest request) {
        return ApiResponse.ofSuccess(roomService.saveInfo(request));
    }

    @ApiOperation("获取直播间关联额外信息")
    @GetMapping("/extra/info")
    public ApiResponse<RoomExtraInfo> extraInfo(Integer rid) {
        return ApiResponse.ofSuccess(roomService.getExtraInfo(rid));
    }

    @ApiOperation("获取直播间配置信息")
    @GetMapping("/setting/info")
    public ApiResponse<?> settingInfo(HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request.getHeader(JwtUtil.getHeaderKey()));
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id", uid));
        return ApiResponse.ofSuccess(room);
    }

    @ApiOperation("获取正在直播的房间")
    @GetMapping("/living")
    public ApiResponse<?> livingRoom(@RequestParam(required = false) Integer cid) {
        return ApiResponse.ofSuccess(roomService.getLivingRooms(cid));
    }

}
