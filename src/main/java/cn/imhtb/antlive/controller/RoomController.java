package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.Category;
import cn.imhtb.antlive.service.ICategoryService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.RoomInfoSaveRequest;
import cn.imhtb.antlive.vo.response.RoomResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.BiIntFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author PinTeh
 */
@Slf4j
@RestController
@RequestMapping("/room")
public class RoomController {

    private final IUserService userService;

    private final IRoomService roomService;

    private final ICategoryService categoryService;

    public RoomController(IRoomService roomService, IUserService userService, ICategoryService categoryService) {
        this.roomService = roomService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/info")
    public ApiResponse info(Integer rid){
        Room room = roomService.getById(rid);
        if (room!=null){
            return ApiResponse.ofSuccess(room);
        }
        return ApiResponse.ofError();
    }

    @PostMapping("/info/save")
    public ApiResponse saveInfo(@RequestBody RoomInfoSaveRequest request,HttpServletRequest r){
        Integer uid = JwtUtils.getId(r);
        Room one = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid).last("limit 0,1"));
        if (one == null){
            return ApiResponse.ofError();
        }
        Room room = new Room();
        room.setId(one.getId());
        room.setTitle(request.getTitle());
        room.setCover(request.getCover());
        room.setCategoryId(request.getCid());
        room.setNotice(request.getNotice());
        roomService.updateById(room);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("")
    public ApiResponse roomInfo(Integer rid){
        Room room = roomService.getById(rid);
        return ApiResponse.ofSuccess(packageRoomResponse(room));
    }

    @GetMapping("/setting/info")
    public ApiResponse settingInfo(HttpServletRequest request){
        Integer uid = JwtUtils.getId(request.getHeader(JwtUtils.getHeaderKey()));
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid));
        return ApiResponse.ofSuccess(room);
    }

    /**
     * Get living rooms list
     */
    @GetMapping("/living")
    public ApiResponse roomList(@RequestParam(required = false)Integer cid){
        List<Room> list = roomService.list(new QueryWrapper<Room>()
                .eq(cid!=null,"category_id",cid)
                .eq("status", Constants.LiveStatus.LIVING.getCode())
                .eq("disabled", Constants.DisabledStatus.YES.getCode()));
        List<RoomResponse> res = list.stream()
                .map(this::packageRoomResponse)
                .collect(Collectors.toList());
        return ApiResponse.ofSuccess(res);
    }

    private RoomResponse packageRoomResponse(Room room){
        //TODO
        User user = userService.getById(room.getUserId());
        Category category = categoryService.getById(room.getCategoryId());
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setTitle(room.getTitle());
        response.setLiveUrl(room.getRtmpUrl());
        response.setCover(room.getCover());
        if (user != null) {
            response.setUserInfo(new RoomResponse.UserInfo(user.getId(), user.getNickName(), user.getAvatar()));
        }
        if (category != null) {
            response.setCategoryInfo(new RoomResponse.CategoryInfo(category.getId(), category.getName()));
        }
        response.setStatus(room.getStatus());
        return response;
    }
}
