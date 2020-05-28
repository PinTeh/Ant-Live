package cn.imhtb.antlive.schedule;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.database.StatisticSpeak;
import cn.imhtb.antlive.entity.database.StatisticView;
import cn.imhtb.antlive.entity.database.SysPush;
import cn.imhtb.antlive.entity.database.SysPushLog;
import cn.imhtb.antlive.server.RedisPrefix;
import cn.imhtb.antlive.service.IStatisticSpeakService;
import cn.imhtb.antlive.service.IStatisticViewService;
import cn.imhtb.antlive.service.ISysPushLogService;
import cn.imhtb.antlive.service.ISysPushService;
import cn.imhtb.antlive.utils.MailUtils;
import cn.imhtb.antlive.utils.RedisUtils;
import cn.imhtb.antlive.utils.SmsUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Value("${aliyun.iii}")
    private String accessKeyId;

    @Value("${aliyun.kkk}")
    private String accessSecret;

    @Value("${aliyun.regionId}")
    private String regionId;

    @Value("${aliyun.instanceId}")
    private String instanceId;

    private final SmsUtils smsUtils;

    private final MailUtils mailUtils;

    private final RedisUtils redisUtils;

    private final IStatisticViewService statisticViewService;

    private final IStatisticSpeakService statisticSpeakService;

    private final ISysPushService sysPushService;

    private final ISysPushLogService sysPushLogService;

    public StatisticsSchedule(RedisUtils redisUtils, IStatisticViewService statisticViewService, IStatisticSpeakService statisticSpeakService, SmsUtils smsUtils, ISysPushService sysPushService, ISysPushLogService sysPushLogService, MailUtils mailUtils) {
        this.redisUtils = redisUtils;
        this.statisticViewService = statisticViewService;
        this.statisticSpeakService = statisticSpeakService;
        this.smsUtils = smsUtils;
        this.sysPushService = sysPushService;
        this.sysPushLogService = sysPushLogService;
        this.mailUtils = mailUtils;
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void aliyunMonitor(){
        log.info("定时任务 - 服务器CPU监控");
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 获取实例信息
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        request.setRegionId(regionId);
        // 获取实例监控信息
        DescribeInstanceMonitorDataRequest describeInstanceMonitorDataRequest = new DescribeInstanceMonitorDataRequest();
        describeInstanceMonitorDataRequest.setInstanceId(instanceId);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String startTime = LocalDateTime.now().minusHours(9L).format(df);
        String endTime = LocalDateTime.now().minusHours(8L).format(df);
        log.info("监控开始时间(实际+8): " + startTime);
        log.info("监控结束时间(实际+8): " + endTime);
        // 只能获取8小时前的数据，并且endTime最多是startTime + 1小时
        describeInstanceMonitorDataRequest.setStartTime(startTime);
        describeInstanceMonitorDataRequest.setEndTime(endTime);
        try {
            // DescribeInstancesResponse response = client.getAcsResponse(request);
            DescribeInstanceMonitorDataResponse acsResponse = client.getAcsResponse(describeInstanceMonitorDataRequest);
            List<DescribeInstanceMonitorDataResponse.InstanceMonitorData> monitorData = acsResponse.getMonitorData();
            int count = (int) monitorData.stream().filter(v -> v.getCPU() >= 5).count();
            if(count > (monitorData.size() / 2)){
                log.info("定时任务 - CPU发生异常");
                List<SysPush> sysPushes = sysPushService.list(new QueryWrapper<SysPush>().like("listener_items", "system-exception-notice").eq("open",1));
                sysPushes.forEach(v -> {
                    ArrayList<String> params = new ArrayList<>();
                    params.add("服务监控异常");
                    params.add("CPU使用率异常");
                    if(!StringUtils.isEmpty(v.getEmail())){
                        mailUtils.sendSimpleMessage(v.getEmail(),"服务器监控异常","CPU异常");
                    }else if(!StringUtils.isEmpty(v.getMobile())){
                        smsUtils.txSmsSend(v.getMobile(),params,"server");
                    }
                    SysPushLog sysPushLog = new SysPushLog();
                    sysPushLog.setSysPushId(v.getId());
                    sysPushLog.setContent("服务器CPU监控异常");
                    sysPushLogService.save(sysPushLog);
                });
            }

        } catch (ClientException e) {
            log.error(String.format("Fail. Business error. ErrorCode: %s, RequestId: %s",
                    e.getErrCode(), e.getRequestId()));
        }
    }


    @Scheduled(cron = "5 0 0 * * ?")
    public void statistics(){
        /*
            MEMBER_SPEAK:   1 -> 80
                            2 -> 33
         */
        LocalDate now = LocalDate.now().minusDays(1L);
        log.info("定时任务当前时间:" + now);
        Map<Object, Object> memberSpeak = redisUtils.hGetAll(RedisPrefix.MEMBER_SPEAK);
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
        Map<Object, Object> memberView = redisUtils.hGetAll(RedisPrefix.MEMBER_VIEW);
        Map<Object, Object> totalView = redisUtils.hGetAll(RedisPrefix.TOTAL_VIEW);
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
