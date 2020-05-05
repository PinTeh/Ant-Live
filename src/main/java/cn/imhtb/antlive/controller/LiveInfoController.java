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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/list")
    public ApiResponse list(Integer rid,
                            @RequestParam(required = false) String dateRange,
                            @RequestParam(required = false, defaultValue = "10") Integer limit,
                            @RequestParam(required = false, defaultValue = "1") Integer page,
                            HttpServletRequest request){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id", uid));
        String maxTime = "", minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        QueryWrapper<LiveInfo> liveInfoQueryWrapper = new QueryWrapper<LiveInfo>()
                .eq("room_id", room.getId())
                .eq("status", 1)
                .le(condition,"start_time",maxTime)
                .ge(condition,"start_time",minTime)
                .orderByDesc("id");
        Page<LiveInfo> liveInfoPage = liveInfoService.page(new Page<LiveInfo>(page,limit),liveInfoQueryWrapper);
        LiveStatResponse response = packageResponse(liveInfoPage.getRecords());
        response.setTotal(liveInfoPage.getTotal());
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
