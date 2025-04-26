package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.common.utils.JwtUtil;
import cn.imhtb.live.modules.live.vo.RoomRespVo;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.pojo.vo.RoomExtraInfoResp;
import cn.imhtb.live.pojo.vo.request.RoomInfoSaveRequest;
import cn.imhtb.live.service.IRoomService;
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

    @IgnoreToken
    @ApiOperation("获取直播间信息")
    @GetMapping("/detail")
    public ApiResponse<RoomRespVo> getRoomInfo(@RequestParam Integer roomId) {
        return ApiResponse.ofSuccess(roomService.getRoomInfo(roomId));
    }

    @ApiOperation("更新保存直播间信息")
    @PostMapping("/info/save")
    public ApiResponse<Boolean> saveInfo(@RequestBody RoomInfoSaveRequest request) {
        return ApiResponse.ofSuccess(roomService.saveInfo(request));
    }

    @ApiOperation("获取直播间关联额外信息")
    @GetMapping("/extra/info")
    public ApiResponse<RoomExtraInfoResp> extraInfo(Integer roomId) {
        return ApiResponse.ofSuccess(roomService.getExtraInfo(UserHolder.getUserId(), roomId));
    }

    @ApiOperation("获取直播间配置信息")
    @GetMapping("/setting/info")
    public ApiResponse<Room> settingInfo(HttpServletRequest request) {
        Integer uid = JwtUtil.getId(request.getHeader(JwtUtil.getHeaderKey()));
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id", uid));
        return ApiResponse.ofSuccess(room);
    }

    @IgnoreToken
    @ApiOperation("获取正在直播的直播间")
    @GetMapping("/living")
    public ApiResponse<PageData<RoomRespVo>> livingRoom(@RequestParam(required = false) Integer categoryId,
                                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ApiResponse.ofSuccess(roomService.getLivingRooms(categoryId, pageNo, pageSize));
    }

}
