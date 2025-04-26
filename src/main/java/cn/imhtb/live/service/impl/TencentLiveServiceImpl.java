package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.enums.LiveInfoStatusEnum;
import cn.imhtb.live.common.enums.LiveRoomStatusEnum;
import cn.imhtb.live.mappers.BanRecordMapper;
import cn.imhtb.live.mappers.LiveInfoMapper;
import cn.imhtb.live.mappers.RoomMapper;
import cn.imhtb.live.pojo.database.LiveInfo;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.pojo.database.BanRecord;
import cn.imhtb.live.service.ITencentLiveService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentcloudapi.live.v20180801.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/2
 */
@Slf4j
@Service
public class TencentLiveServiceImpl implements ITencentLiveService {

    private final RoomMapper roomMapper;

    private final BanRecordMapper banRecordMapper;

    private final LiveInfoMapper liveInfoMapper;

    public TencentLiveServiceImpl( BanRecordMapper banRecordMapper, RoomMapper roomMapper, LiveInfoMapper liveInfoMapper) {
        this.banRecordMapper = banRecordMapper;
        this.roomMapper = roomMapper;
        this.liveInfoMapper = liveInfoMapper;
    }

    private static final String KEY = "9e57e2310202990ba3c470e250385784";

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /*
     * KEY+ streamName + txTime
     */

    private void foo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2020-04-08 23:59:59", dateTimeFormatter);
        long txTime = parse.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(txTime);
        //1586361599000
        //1715A850418
        System.out.println(Long.toHexString(txTime).toUpperCase());
    }

    public static String getSafeUrl(String key, String streamName, long txTime) {
        if (StringUtils.isEmpty(key)) {
            key = KEY;
        }
        String input = key +
                streamName +
                Long.toHexString(txTime).toUpperCase();
        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret = byteArrayToHexString(
                    messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return txSecret == null ? "" :
                "txSecret=" +
                        txSecret +
                        "&" +
                        "txTime=" +
                        Long.toHexString(txTime).toUpperCase();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    @Override
    public boolean ban(Integer rid, String resumeTime, String reason) {

        List<BanRecord> banRecords = banRecordMapper.selectList(new QueryWrapper<BanRecord>().eq("room_id", rid).eq("status", 0));
        if (banRecords.size() > 0) {
            return false;
        }

        LiveInfo liveInfo = liveInfoMapper.selectOne(new QueryWrapper<LiveInfo>().eq("room_id", rid).orderByDesc("id").last("limit 0,1"));
        if (liveInfo.getEndTime() == null) {
            // 不能直接更新liveInfo
            LiveInfo updateInfo = new LiveInfo();
            updateInfo.setId(liveInfo.getId());
            updateInfo.setEndTime(LocalDateTime.now());
            // 0-living 1-finished
            updateInfo.setStatus(LiveInfoStatusEnum.FINISHED.getCode());
            liveInfoMapper.updateById(updateInfo);
        }

        try {
            roomMapper.updateById(Room.builder().id(rid).status(LiveRoomStatusEnum.BANNING.getCode()).build());
        } catch (Exception e) {
            log.error("更新房间状态异常:{}", e.getMessage());
        }

        log.info("调用腾讯云封禁接口: rid = " + rid);
        ForbidLiveStreamRequest forbidLiveStreamRequest = new ForbidLiveStreamRequest();
        forbidLiveStreamRequest.setAppName("live");
        forbidLiveStreamRequest.setDomainName("live.imhtb.cn");
        forbidLiveStreamRequest.setStreamName(String.valueOf(rid));
        forbidLiveStreamRequest.setReason("reason");

        /*
         * 	恢复流的时间。UTC 格式，例如：2018-11-29T19:00:00Z。
         * 注意：
         * 1. 默认禁播7天，且最长支持禁播90天。
         */
        LocalDateTime resumeLocalDateTime = LocalDateTime.now().plusDays(7);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        if (!StringUtils.isEmpty(resumeTime)) {
            forbidLiveStreamRequest.setResumeTime(resumeTime);
            //TODO 手动添加8小时
            resumeLocalDateTime = LocalDateTime.parse(resumeTime, df).plusHours(8L);
        }
        ForbidLiveStreamResponse resp = null;
        try {
//            resp = client.ForbidLiveStream(forbidLiveStreamRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        BanRecord record = new BanRecord();
        record.setRoomId(rid);
        record.setReason(reason);
        record.setCreateTime(now);
        record.setStartTime(now);
        record.setResumeTime(resumeLocalDateTime);
        record.setStatus(0);
        banRecordMapper.insert(record);

        roomMapper.updateById(Room.builder().id(rid).status(LiveRoomStatusEnum.BANNING.getCode()).build());

        log.info(ForbidLiveStreamRequest.toJsonString(resp));
        log.info("调用腾讯云封禁接口：执行成功");
        return true;
    }

    @Override
    public boolean resume(Integer rid) {
        log.info("调用腾讯云恢复接口：执行开始");
        ResumeLiveStreamRequest resumeLiveStreamRequest = new ResumeLiveStreamRequest();
        resumeLiveStreamRequest.setAppName("live");
        resumeLiveStreamRequest.setDomainName("live.imhtb.cn");
        resumeLiveStreamRequest.setStreamName(String.valueOf(rid));
        ResumeLiveStreamResponse response = null;
        try {
//            response = client.ResumeLiveStream(resumeLiveStreamRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        BanRecord record = banRecordMapper.selectOne(new QueryWrapper<BanRecord>().eq("room_id", rid).eq("status", 0).orderByDesc("id").last("limit 0,1"));
        if (record != null) {
            BanRecord update = new BanRecord();
            update.setId(record.getId());
            update.setMark("手动恢复");
            update.setStatus(1);
            banRecordMapper.updateById(update);
        }

        roomMapper.updateById(Room.builder().id(rid).status(LiveRoomStatusEnum.STOP.getCode()).build());

        log.info("调用腾讯云恢复接口: 返回{}", ResumeLiveStreamRequest.toJsonString(response));
        log.info("调用腾讯云恢复接口：执行结束");
        return true;
    }

    @Override
    public DescribeLiveForbidStreamListResponse banList(Integer page, Integer limit) {
        DescribeLiveForbidStreamListRequest request = new DescribeLiveForbidStreamListRequest();
        request.setPageNum(Long.valueOf(page));
        request.setPageSize(Long.valueOf(limit));
        try {
//            return client.DescribeLiveForbidStreamList(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DescribeLiveSnapshotTemplatesResponse snapshotTemplatesList() {
        String params = "{}";
        DescribeLiveSnapshotTemplatesRequest req = DescribeLiveSnapshotTemplatesRequest.fromJsonString(params, DescribeLiveSnapshotTemplatesRequest.class);
        try {
//            DescribeLiveSnapshotTemplatesResponse response = client.DescribeLiveSnapshotTemplates(req);
            DescribeLiveSnapshotTemplatesResponse response = null;
            System.out.println(DescribeLiveSnapshotTemplatesRequest.toJsonString(response));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void snapshotTemplatesUpdate(ModifyLiveSnapshotTemplateRequest request) {
        try {
//            ModifyLiveSnapshotTemplateResponse response = client.ModifyLiveSnapshotTemplate(request);
            ModifyLiveSnapshotTemplateResponse response = null;
            log.info("修改截图模板:{}", ModifyLiveSnapshotTemplateResponse.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
