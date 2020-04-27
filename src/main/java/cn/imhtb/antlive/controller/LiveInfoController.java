package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.service.ILiveInfoService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.utils.LocalDateTimeUtils;
import cn.imhtb.antlive.vo.response.LiveStatResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
@RestController
@RequestMapping("/live/info")
public class LiveInfoController {

    private final ILiveInfoService liveInfoService;

    private final IRoomService roomService;

    public LiveInfoController(ILiveInfoService liveInfoService, IRoomService roomService) {
        this.liveInfoService = liveInfoService;
        this.roomService = roomService;
    }

    @GetMapping("")
    public ApiResponse list(Integer rid, HttpServletRequest request){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id", uid));

        List<LiveInfo> list = liveInfoService.list(new QueryWrapper<LiveInfo>().eq("room_id", room.getId()).eq("status",1).orderByDesc("id"));
        LiveStatResponse response = packageResponse(list);
        return ApiResponse.ofSuccess(response);
    }

    private LiveStatResponse packageResponse(List<LiveInfo> list) {
        LiveStatResponse response = new LiveStatResponse();
        List<LiveStatResponse.LiveStat> liveStats = new ArrayList<>();
        long totalTime = 0L;
        for (LiveInfo v : list) {
            LiveStatResponse.LiveStat liveStat = new LiveStatResponse.LiveStat();
            liveStat.setEndTime(v.getEndTime());
            liveStat.setStartTime(v.getStartTime());
            liveStat.setId(v.getId());
            long sub = LocalDateTimeUtils.subMinutes(v.getStartTime(), v.getEndTime());
            totalTime += sub;
            liveStat.setTime(sub);
            liveStats.add(liveStat);
        }

        response.setLiveStats(liveStats);
        response.setTotalTime(totalTime);
        return response;
    }

}
