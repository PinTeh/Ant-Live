package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.*;
import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.service.*;
import cn.imhtb.antlive.utils.LocalDateTimeUtils;
import cn.imhtb.antlive.vo.request.IdsRequest;
import cn.imhtb.antlive.vo.response.LiveBanResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveForbidStreamListResponse;
import com.tencentcloudapi.live.v20180801.models.ForbidStreamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
public class AdminController {

    private final IUserService userService;

    private final IRoomService roomService;

    private final IAuthService authService;

    private final IBillService billService;

    private final ILiveInfoService liveInfoService;

    private final IPresentService presentService;

    private final ITencentLiveService tencentLiveService;

    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;


    public AdminController(IStatisticSpeakService statisticSpeakService, IUserService userService, IRoomService roomService, IAuthService authService, IBillService billService, ILiveInfoService liveInfoService, IPresentService presentService, IStatisticViewService statisticViewService, ITencentLiveService tencentLiveService) {
        this.userService = userService;
        this.roomService = roomService;
        this.authService = authService;
        this.billService = billService;
        this.liveInfoService = liveInfoService;
        this.presentService = presentService;
        this.statisticViewService = statisticViewService;
        this.statisticSpeakService = statisticSpeakService;
        this.tencentLiveService = tencentLiveService;
    }

    @GetMapping("/user/list")
    public ApiResponse userList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,
                                @RequestParam(required = false)Integer uid,@RequestParam(required = false)Integer disabled){
        IPage<User> iPage = userService.page(new Page<>(page,limit), new QueryWrapper<User>()
                .eq(uid!=null,"id",uid)
                .eq(disabled!=null,"disabled",disabled)
                .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/room/list")
    public ApiResponse roomList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        IPage<Room> iPage = roomService.page(new Page<>(page,limit), new QueryWrapper<Room>().orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/auth/list")
    public ApiResponse authList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit,@RequestParam(required = false) Integer status){
        IPage<AuthInfo> iPage = authService.page(new Page<>(page,limit), new QueryWrapper<AuthInfo>().eq(status!=null,"status",status).orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    @GetMapping("/present/list")
    public ApiResponse presentList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        IPage<Present> iPage = presentService.page(new Page<>(page,limit), new QueryWrapper<Present>().orderByDesc("id"));
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
    public ApiResponse statViewList(@RequestParam(required = false) Integer days){
        List<StatisticView> statisticViews = statisticViewService.listInDateRange(days);
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
    public ApiResponse statSpeakList(@RequestParam(required = false) Integer days){
        List<StatisticSpeak> statisticSpeaks = statisticSpeakService.listInDateRange(days);
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

    /**
        {
          "Response": {
            "RequestId": "1369339a-d886-47b8-bfe0-5c0fd56269e4",
            "TotalNum": 1,
            "TotalPage": 1,
            "PageNum": 1,
            "PageSize": 10,
            "ForbidStreamList": [
              {
                "StreamName": "1",
                "ExpireTime": "2020-04-21 17:55:57",
                "CreateTime": "2020-04-14 17:55:57"
              }
            ]
          }
        }
     */
    @GetMapping("/live/ban/list")
    public ApiResponse banList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        DescribeLiveForbidStreamListResponse response = tencentLiveService.banList(page,limit);
        ForbidStreamInfo[] forbidStreamList = response.getForbidStreamList();
        List<LiveBanResponse> responses = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (ForbidStreamInfo forbidStreamInfo : forbidStreamList) {
            String streamName = forbidStreamInfo.getStreamName();
            Room r = roomService.getById(Integer.valueOf(streamName));
            LiveBanResponse liveBanResponse = LiveBanResponse.builder()
                    .roomId(r.getId())
                    .userId(r.getUserId())
                    .reason("放小视频")
                    .resumeTime(LocalDateTime.parse(forbidStreamInfo.getExpireTime(),df))
                    .createTime(LocalDateTime.parse(forbidStreamInfo.getCreateTime(),df))
                    .status(r.getStatus())
                    .build();
            responses.add(liveBanResponse);
        }

        Map<String,Object> map = new HashMap<>(2);
        map.put("records",responses);
        map.put("total",response.getTotalNum());
        return ApiResponse.ofSuccess(map);
    }

    /**
     *  封禁用户
     */
    @PostMapping("/user/block/{type}")
    public ApiResponse userBlock(@RequestBody IdsRequest request,@PathVariable(name = "type") String type){
        Integer[] ids = request.getIds();
        if ("block".equals(type)){
            userService.updateStatusByIds(ids, Constants.DisabledStatus.NO.getCode());
        }else if ("unblock".equals(type)){
            userService.updateStatusByIds(ids, Constants.DisabledStatus.YES.getCode());
        }
        return ApiResponse.ofSuccess();
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
