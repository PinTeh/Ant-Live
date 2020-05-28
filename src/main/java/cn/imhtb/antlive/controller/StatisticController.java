package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.service.IStatisticSpeakService;
import cn.imhtb.antlive.service.IStatisticViewService;
import cn.imhtb.antlive.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final IRoomService roomService;

    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;

    public StatisticController(IStatisticSpeakService statisticSpeakService, IStatisticViewService statisticViewService, IRoomService roomService) {
        this.statisticSpeakService = statisticSpeakService;
        this.statisticViewService = statisticViewService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public ApiResponse list(){
        return ApiResponse.ofSuccess();
    }


    @GetMapping("/stat/view/list")
    public ApiResponse statViewList(@RequestParam(required = false) Integer days, @RequestParam(required = false)Integer rid, HttpServletRequest request){
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
