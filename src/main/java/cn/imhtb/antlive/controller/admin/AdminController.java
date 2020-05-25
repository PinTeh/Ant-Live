package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.*;
import cn.imhtb.antlive.entity.database.LiveDetect;
import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.service.*;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.utils.LocalDateTimeUtils;
import cn.imhtb.antlive.vo.request.IdsRequest;
import cn.imhtb.antlive.vo.response.LiveBanResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveForbidStreamListResponse;
import com.tencentcloudapi.live.v20180801.models.ForbidStreamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PinTeh
 * @date 2020/3/26
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ROOT')")
public class AdminController {

    private final IRoomService roomService;

    private final IBillService billService;

    private final ILiveInfoService liveInfoService;

    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;

    public AdminController(IStatisticSpeakService statisticSpeakService, IRoomService roomService, IBillService billService, ILiveInfoService liveInfoService,  IStatisticViewService statisticViewService ) {
        this.roomService = roomService;
        this.billService = billService;
        this.liveInfoService = liveInfoService;
        this.statisticViewService = statisticViewService;
        this.statisticSpeakService = statisticSpeakService;
    }


    @GetMapping("/room/list")
    public ApiResponse roomList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        IPage<Room> iPage = roomService.page(new Page<>(page,limit), new QueryWrapper<Room>().orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/liveInfo/list")
    public ApiResponse liveInfoList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,
                                    @RequestParam(required = false) Integer rid,@RequestParam(required = false) String dateRange){
        String maxTime = "",minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        IPage<LiveInfo> iPage = liveInfoService.page(new Page<>(page,limit), new QueryWrapper<LiveInfo>()
                .eq(rid!=null,"room_id",rid)
                .le(condition,"create_time",maxTime)
                .ge(condition,"create_time",minTime)
                .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/bill/list")
    public ApiResponse billList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,
                                @RequestParam(required = false) Integer type,@RequestParam(required = false) String dateRange){
        String maxTime = "",minTime = "";
        boolean condition = !StringUtils.isEmpty(dateRange) && !"null".equals(dateRange);
        if (condition) {
            maxTime = dateRange.split(",")[1];
            minTime = dateRange.split(",")[0];
        }
        IPage<Bill> iPage = billService.page(new Page<>(page,limit), new QueryWrapper<Bill>()
                .eq(type!=null,"type",type)
                .le(condition,"create_time",maxTime)
                .ge(condition,"create_time",minTime)
                 .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/stat/view/list")
    public ApiResponse statViewList(@RequestParam(required = false) Integer days,@RequestParam(required = false)Integer rid,HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid));
        List<StatisticView> statisticViews = statisticViewService.listInDateRange(days,rid==null?room.getId():rid);
        Map<String,StatisticView> map = new HashMap<>(statisticViews.size());
        statisticViews.forEach(v-> map.put(v.getDate().toString(),v));
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);
        List<String> betweenDate = getBetweenDate(start, end);
        List<StatisticView> ret = new ArrayList<>();
        betweenDate.forEach(v -> {
            ret.add(map.getOrDefault(v,StatisticView.builder().memberNumber(0).totalNumber(0).visitorNumber(0).date(LocalDate.parse(v)).build()));
        });
        return ApiResponse.ofSuccess(ret);
    }

    @GetMapping("/stat/speak/list")
    public ApiResponse statSpeakList(@RequestParam(required = false) Integer days,@RequestParam(required = false) Integer rid,HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        Room room = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid));
        List<StatisticSpeak> statisticSpeaks = statisticSpeakService.listInDateRange(days,rid==null?room.getId():rid);
        Map<String,StatisticSpeak> map = new HashMap<>(statisticSpeaks.size());
        statisticSpeaks.forEach(v-> map.put(v.getDate().toString(),v));
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);
        List<String> betweenDate = getBetweenDate(start, end);
        List<StatisticSpeak> ret = new ArrayList<>();
        betweenDate.forEach(v -> {
            ret.add(map.getOrDefault(v,StatisticSpeak.builder().number(0).date(LocalDate.parse(v)).build()));
        });
        return ApiResponse.ofSuccess(ret);
    }

    private static List<String> getBetweenDate(LocalDate start, LocalDate end) {
        long between = ChronoUnit.DAYS.between(start, end);
        if (between < 1) {
            return Stream.of(start.toString(), end.toString()).collect(Collectors.toList());
        }

        return Stream.iterate(start, e -> e.plusDays(1))
                .limit(between + 1)
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }

}
