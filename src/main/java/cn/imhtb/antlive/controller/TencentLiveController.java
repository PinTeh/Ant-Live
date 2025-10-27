package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.LiveInfo;
import cn.imhtb.antlive.entity.Room;
import cn.imhtb.antlive.entity.database.LiveDetect;
import cn.imhtb.antlive.entity.database.SysPush;
import cn.imhtb.antlive.entity.database.SysPushLog;
import cn.imhtb.antlive.entity.tencent.ShotRuleResponse;
import cn.imhtb.antlive.entity.tencent.StreamResponse;
import cn.imhtb.antlive.server.RedisPrefix;
import cn.imhtb.antlive.service.*;
import cn.imhtb.antlive.utils.*;
import cn.imhtb.antlive.service.impl.TencentLiveServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PinTeh
 * @date 2020/4/7
 */
@Slf4j
@RestController
@RequestMapping("/tencent/live")
public class TencentLiveController {

    @Value("${tencent.live.domain:}")
    private String domain;

    @Value("${tencent.live.appName:}")
    private String appName;

    private final MailUtils mailUtils;

    private final SmsUtils smsUtils;

    private final ISysPushLogService sysPushLogService;

    private final ISysPushService sysPushService;

    private final IRoomService roomService;

    private final ILiveInfoService liveInfoService;

    private final ILiveDetectService liveDetectService;

    private final ModelMapper modelMapper;

    private final RedisUtils redisUtils;

    private final ITencentLiveService tencentLiveService;

    public TencentLiveController(IRoomService roomService, ILiveInfoService liveInfoService, ModelMapper modelMapper, ILiveDetectService liveDetectService, RedisUtils redisUtils, SmsUtils smsUtils, ISysPushLogService sysPushLogService, ISysPushService sysPushService, MailUtils mailUtils, ITencentLiveService tencentLiveService) {
        this.roomService = roomService;
        this.liveInfoService = liveInfoService;
        this.modelMapper = modelMapper;
        this.liveDetectService = liveDetectService;
        this.redisUtils = redisUtils;
        this.smsUtils = smsUtils;
        this.sysPushLogService = sysPushLogService;
        this.sysPushService = sysPushService;
        this.mailUtils = mailUtils;
        this.tencentLiveService = tencentLiveService;
    }

    /**
     * 获取推流密钥
     * 1. 用户没认证
     * 2. 直播间已被封禁
     */
    @GetMapping("/open")
    public ApiResponse open(HttpServletRequest request){
        // get token
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        // get room by uid
        Room r = roomService.getOne(new QueryWrapper<Room>().eq("user_id",uid).last("limit 0,1"));

        Map<String,String> ret = new HashMap<>(2);
        ret.put("pushUrl","rtmp://" + domain + "/" + appName + "/");
        ret.put("secret","申请失败，请认证后重新尝试");
        if (r.getStatus() == -1){
            return ApiResponse.ofSuccess(ret);
        }
        // generator push url
        long txTime = LocalDateTime.now().plusHours(12L).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String safeUrl = TencentLiveServiceImpl.getSafeUrl("", String.valueOf(r.getId()), txTime/1000);

        ret.put("secret",+ r.getId() + "?" + safeUrl);
        return ApiResponse.ofSuccess(ret);
    }

    /*
    StreamResponse(app=live.imhtb.cn,
                   appid=1253825991,
                   appname=live,
                   channelId=null,
                   errcode=0,
                   errmsg=ok,
                   eventTime=0,
                   eventType=0,
                   node=125.94.63.175,
                   sequence=7001420704626954503,
                   streamId=null,
                   streamParam=null,
                   userIp=null,
                   sign=613bf306b7fa5cf7e295babe77868e2a,
                   t=1586273219)
           */

    /**
     * stream push recall
     */
    @RequestMapping("/start")
    public void start(@RequestBody StreamResponse response){
        log.info("腾讯云直播回调：启动回调 - 开始");
        log.info("response:" + response);
        String sign = DigestUtils.md5DigestAsHex(("AntLive" + response.getT()).getBytes());
        log.info("sign:" + sign + " ret:" + response.getSign().equals(sign));
        if (!response.getSign().equals(sign)){
            log.error("illegal request! stream_id:" + response.getStream_id());
            return;
        }

        Room room = roomService.getById(Integer.valueOf(response.getStream_id()));
        if (room == null){
            log.error("live start fail : because room = null ");
            return;
        }

        room.setSecret(CommonUtils.getRandomString());
        room.setStatus(Constants.LiveStatus.LIVING.getCode());
        roomService.updateById(room);

        // record live duration
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setUserId(room.getUserId());
        liveInfo.setRoomId(room.getId());
        liveInfo.setStatus(Constants.LiveInfoStatus.NO.getCode());
        liveInfo.setStartTime(LocalDateTime.now());
        liveInfoService.save(liveInfo);

        //开始统计直播数据
        /*
            LIVE_STATISTIC:Rid {
                info_id：live_info_id
            }
        */
        String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, String.valueOf(room.getId()));
        Map<Object,Object> map = new HashMap<>(2);
        map.put("live_info_id",liveInfo.getId().toString());
        map.put("room_id",room.getId().toString());
        log.info("直播开始：Redis开始统计直播数据 - key:{}， live_info_id:{},room_id:{}",key,liveInfo.getId(),room.getId());
        redisUtils.hPutAll(key,map);
        log.info("直播开始：Redis开始统计直播数据 - 插入完成");
        log.info("腾讯云直播回调：启动回调 - 结束");
    }

    /**
     * stream end recall
     */
    @RequestMapping("/end")
    public ApiResponse end(@RequestBody StreamResponse response){
        log.info("腾讯云直播回调：结束回调 - 开始");
        String sign = DigestUtils.md5DigestAsHex(("AntLive" + response.getT()).getBytes());
        log.info("sign:" + sign + " ret:" + response.getSign().equals(sign));
        if (!response.getSign().equals(sign)){
            return ApiResponse.ofError("illegal request! stream_id:" + response.getStream_id());
        }

        Integer rid = Integer.valueOf(response.getStream_id());
        // update room status
        Room room = new Room();
        room.setId(rid);
        room.setStatus(Constants.LiveStatus.STOP.getCode());
        roomService.updateById(room);

        // update live info, add end time
        LiveInfo liveInfo = liveInfoService.getOne(new QueryWrapper<LiveInfo>().eq("room_id", rid).orderByDesc("id").last("limit 0,1"));
        if (liveInfo.getEndTime()!=null){
            return ApiResponse.ofSuccess();
        }
        // 不能直接更新liveInfo
        LiveInfo updateInfo = new LiveInfo();
        updateInfo.setId(liveInfo.getId());
        updateInfo.setEndTime(LocalDateTime.now());
        // 0-living 1-finished
        updateInfo.setStatus(Constants.LiveInfoStatus.YES.getCode());

        //结束统计数据
        String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, String.valueOf(room.getId()));
        try {

            Map<Object, Object> map = redisUtils.hGetAll(key);
            log.info("直播结束：Redis获取统计数据 map size:{}，key:{}",map.size(),key);
            String pc = String.valueOf(map.getOrDefault(RedisPrefix.LIVE_PRESENT_COUNT,"0"));
            String cc = String.valueOf(map.getOrDefault(RedisPrefix.LIVE_CLICK_COUNT,"0"));
            String dm = String.valueOf(map.getOrDefault(RedisPrefix.LIVE_DAN_MU_COUNT,"0"));

            updateInfo.setClickCount(cc);
            updateInfo.setPresentCount(pc);
            updateInfo.setDanMuCount(dm);

        }catch (Exception e){
            log.error("直播结束 : Redis取值出现异常 {}",e.getMessage());
            e.printStackTrace();
        }finally {
            redisUtils.remove(key);
        }

        liveInfoService.updateById(updateInfo);
        log.info("腾讯云直播回调：结束回调 - 结束");
        return ApiResponse.ofSuccess();
    }

    /**
     * screenshot recall
     */
    @RequestMapping("/screenshot")
    public void screenshot(){
        log.info("腾讯云直播截图回调");
    }

    /**
     * appraise salacity recall
     * type 图片类型， 0 ：正常图片， 1 ：色情图片， 2 ：性感图片， 3 ：涉政图片， 4 ：违法图片， 5 ：涉恐图片 ，6 - 9 ：其他其它图片
     */
    @RequestMapping("/appraise")
    public void appraise(@RequestBody ShotRuleResponse response) {
        log.info("智能鉴黄回调: " + response.toString());
        int confidence = response.getConfidence();
        Integer rid = Integer.valueOf(response.getStreamId());
        log.info("直播截图异常检测: rid = " + rid + ",confidence:" + confidence);

        //LiveDetect Id  多匹配 StreamId ChannelId
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        LiveDetect liveDetect = modelMapper.map(response, LiveDetect.class);
        liveDetect.setRoomId(rid);
        StringBuilder builder = new StringBuilder();
        for (Integer integer : response.getType()) {
            builder.append(integer).append(",");
        }
        String type = builder.toString();
        liveDetect.setType(type.substring(0,type.length()-1));
        liveDetect.setHandleStatus(0);
        if (confidence >= 80){
            log.info("直播截图异常检测: 置信度>80,系统自动封禁 ");
            liveDetect.setHandleStatus(1);
            LocalDateTime resumeTime = LocalDateTime.now().plusDays(3);
            liveDetect.setResumeTime(resumeTime);

            List<SysPush> sysPushes = sysPushService.list(new QueryWrapper<SysPush>().like("listener_items", "salacity-notice").eq("open",1));
            sysPushes.forEach(v -> {
                ArrayList<String> params = new ArrayList<>();
                params.add(String.valueOf(rid));
                params.add("直播内容涉黄");
                if(!StringUtils.isEmpty(v.getEmail())){
                    mailUtils.sendSimpleMessage(v.getEmail(),"直播内容监控异常","直播内容监控异常 , 房间号:" + rid);
                }
                if(!StringUtils.isEmpty(v.getMobile())){
                    smsUtils.txSmsSend(v.getMobile(),params,"server");
                }
                SysPushLog sysPushLog = new SysPushLog();
                sysPushLog.setSysPushId(v.getId());
                sysPushLog.setContent("直播内容监控异常，房间号:" + rid);
                sysPushLogService.save(sysPushLog);
            });

            // 封号处理
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            tencentLiveService.ban(rid, resumeTime.format(df),"自动封禁:涉黄");
        }
        liveDetectService.save(liveDetect);

    }

}
