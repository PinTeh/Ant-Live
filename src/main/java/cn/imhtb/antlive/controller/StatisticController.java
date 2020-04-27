package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.service.IStatisticSpeakService;
import cn.imhtb.antlive.service.IStatisticViewService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {


    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;

    public StatisticController(IStatisticSpeakService statisticSpeakService, IStatisticViewService statisticViewService) {
        this.statisticSpeakService = statisticSpeakService;
        this.statisticViewService = statisticViewService;
    }

    @GetMapping("/")
    public ApiResponse list(){
        return ApiResponse.ofSuccess();
    }
}
