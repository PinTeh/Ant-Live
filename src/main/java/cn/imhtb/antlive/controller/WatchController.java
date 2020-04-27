package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.Watch;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.service.IWatchService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.WatchRequest;
import cn.imhtb.antlive.vo.response.WatchResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
@RestController
@RequestMapping("/watch")
public class WatchController {

    private final IWatchService watchService;

    private final IRoomService roomService;

    private final IUserService userService;

    public WatchController(IWatchService watchService,IRoomService roomService,IUserService userService) {
        this.watchService = watchService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @PostMapping("")
    public ApiResponse save(@RequestBody WatchRequest watchRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request.getHeader(JwtUtils.getHeaderKey()));
        Watch build = Watch.builder().roomId(watchRequest.getRid()).userId(uid).watchType(watchRequest.getType()).build();
        try {
            watchService.save(build);
        }catch (Exception ignore){

        }
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/list")
    public ApiResponse list(Integer type,HttpServletRequest request
            , @RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page){
        Integer uid = JwtUtils.getId(request.getHeader(JwtUtils.getHeaderKey()));
        Page<Watch> watchPage = watchService.page(new Page<>(page, limit), new QueryWrapper<Watch>().eq("user_id", uid).eq("watch_type", type).orderByDesc("id").last(type == 0, "limit 6"));
        List<Watch> list = watchPage.getRecords();
        Map<String,Object> ret = new HashMap<>(2);
        ret.put("records", packageWatch(list));
        ret.put("total",watchPage.getTotal());
        return ApiResponse.ofSuccess(ret);
    }

    @DeleteMapping("")
    public ApiResponse del(Integer id,HttpServletRequest request){
        Integer uid = JwtUtils.getId(request.getHeader(JwtUtils.getHeaderKey()));
        Watch watch = watchService.getById(id);
        if (watch!=null && watch.getUserId().equals(uid)){
            watchService.removeById(id);
        }
        return ApiResponse.ofSuccess();
    }

    private List<WatchResponse> packageWatch(List<Watch> watches){
        if (watches.size() < 1){
            return null;
        }
        List<WatchResponse> list = new ArrayList<>();
        List<Integer> ids = watches.stream().map(Watch::getRoomId).collect(Collectors.toList());
        List<Integer> uIds = roomService.listByIds(ids).stream().map(Room::getUserId).collect(Collectors.toList());
        List<String> nickNames = userService.listByIds(uIds).stream().map(User::getNickName).collect(Collectors.toList());
        for (int i = 0; i < watches.size(); i++) {
            WatchResponse response = new WatchResponse();
            response.setId(watches.get(i).getId());
            Room room = roomService.getById(watches.get(i).getRoomId());
            response.setCover(room.getCover());
            response.setName(nickNames.get(i));
            response.setTitle(room.getTitle());
            list.add(response);
        }
        return list;
    }

}
