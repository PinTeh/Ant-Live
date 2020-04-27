package cn.imhtb.antlive.schedule;

import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.server.RedisPrefix;
import cn.imhtb.antlive.service.IStatisticSpeakService;
import cn.imhtb.antlive.service.IStatisticViewService;
import cn.imhtb.antlive.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@Slf4j
@Component
@EnableScheduling
@Transactional
public class StatisticsSchedule {

    private final RedisUtils redisUtils;

    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;

    public StatisticsSchedule(RedisUtils redisUtils,IStatisticViewService statisticViewService,IStatisticSpeakService statisticSpeakService) {
        this.redisUtils = redisUtils;
        this.statisticViewService = statisticViewService;
        this.statisticSpeakService = statisticSpeakService;
    }

    @Scheduled(cron = "5 0 0 * * ?")
    public void statistics(){
        /*
            MEMBER_SPEAK:   1 -> 80
                            2 -> 33
         */
        LocalDate now = LocalDate.now().minusDays(1L);
        log.info("定时任务当前时间:" + now);
        Map<Object, Object> memberSpeak = redisUtils.hmGetAll(RedisPrefix.MEMBER_SPEAK);
        List<StatisticSpeak> statisticSpeaks = new ArrayList<>();
        memberSpeak.forEach((k,v)->{
            // rid date number
            log.info("memberSpeak:" + k + "," + v);
            StatisticSpeak statisticSpeak = new StatisticSpeak();
            statisticSpeak.setRoomId(Integer.valueOf(k.toString()));
            statisticSpeak.setNumber(Integer.valueOf(v.toString()));
            statisticSpeak.setDate(now);
            statisticSpeaks.add(statisticSpeak);
        });

        statisticSpeakService.saveBatch(statisticSpeaks);

        /*
            MEMBER_VIEW:    1 -> 10
                            2 -> 22

         */
        List<StatisticView> statisticViews = new ArrayList<>();
        Map<Object, Object> memberView = redisUtils.hmGetAll(RedisPrefix.MEMBER_VIEW);
        Map<Object, Object> totalView = redisUtils.hmGetAll(RedisPrefix.TOTAL_VIEW);
        totalView.forEach((k,v)->{
            log.info("totalView:" + k + "," + v);
            StatisticView statisticView = new StatisticView();
            statisticView.setRoomId(Integer.valueOf(k.toString()));
            int memberNumber = 0;
            if (memberView != null){
                memberNumber = (Integer)memberView.getOrDefault(Integer.valueOf(k.toString()),0);
            }
            statisticView.setMemberNumber(memberNumber);
            statisticView.setVisitorNumber((Integer.parseInt(v.toString())) - memberNumber);
            statisticView.setTotalNumber(Integer.valueOf(v.toString()));
            statisticView.setDate(now);
            statisticViews.add(statisticView);
        });

        statisticViewService.saveBatch(statisticViews);
        redisUtils.remove(RedisPrefix.MEMBER_SPEAK,RedisPrefix.MEMBER_VIEW,RedisPrefix.TOTAL_VIEW,RedisPrefix.VISITOR_VIEW);
        log.info("定时任务执行完成");
    }
}
