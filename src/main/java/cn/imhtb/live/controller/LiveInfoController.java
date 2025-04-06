package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.LiveInfo;
import cn.imhtb.live.pojo.Room;
import cn.imhtb.live.service.ILiveInfoService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.common.utils.JwtUtil;
import cn.imhtb.live.common.utils.LocalDateTimeUtil;
import cn.imhtb.live.pojo.vo.response.LiveStatResponse;
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
                            HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.getHeaderKey());
        Integer uid = JwtUtil.getId(token);
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
                .le(condition, "start_time", maxTime)
                .ge(condition, "start_time", minTime)
                .orderByDesc("id");
        Page<LiveInfo> liveInfoPage = liveInfoService.page(new Page<>(page, limit), liveInfoQueryWrapper);
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
            liveStat.setDanMuCount(v.getDanMuCount());
            liveStat.setPresentCount(v.getPresentCount());
            liveStat.setClickCount(v.getClickCount());
            long sub = LocalDateTimeUtil.subSeconds(v.getStartTime(), v.getEndTime());
            totalTime += sub;
            liveStat.setTime(sub);
            liveStats.add(liveStat);
        }
        response.setLiveStats(liveStats);
        response.setTotalTime(totalTime);
        return response;
    }

}
