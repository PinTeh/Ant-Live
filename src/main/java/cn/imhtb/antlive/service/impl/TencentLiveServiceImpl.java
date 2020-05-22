package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.service.ITencentLiveService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    private final LiveClient client;

    public TencentLiveServiceImpl(LiveClient liveClient) {
        this.client = liveClient;
    }

    private static final String KEY = "9e57e2310202990ba3c470e250385784";

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /*
     * KEY+ streamName + txTime
     */

    private void foo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2020-04-08 23:59:59",dateTimeFormatter);
        long txTime = parse.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(txTime);
        //1586361599000
        //1715A850418
        System.out.println(Long.toHexString(txTime).toUpperCase());
    }

    public static String getSafeUrl(String key, String streamName, long txTime) {
        if (StringUtils.isEmpty(key)){
            key = KEY;
        }
        String input = key +
                streamName +
                Long.toHexString(txTime).toUpperCase();
        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
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
    public boolean ban(Integer rid,String resumeTime){
        log.info("调用腾讯云封禁接口: rid = " + rid);
        ForbidLiveStreamRequest forbidLiveStreamRequest = new ForbidLiveStreamRequest();
        forbidLiveStreamRequest.setAppName("live");
        forbidLiveStreamRequest.setDomainName("live.imhtb.cn");
        forbidLiveStreamRequest.setStreamName(String.valueOf(rid));
        forbidLiveStreamRequest.setReason("reason");

        if (!resumeTime.isEmpty()){
            forbidLiveStreamRequest.setResumeTime(resumeTime);
        }
        ForbidLiveStreamResponse resp = null;
        try {
            resp = client.ForbidLiveStream(forbidLiveStreamRequest);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }
        log.info(ForbidLiveStreamRequest.toJsonString(resp));
        log.info("调用腾讯云封禁接口：执行成功");
        return true;
    }

    @Override
    public boolean resume(Integer rid) {
        ResumeLiveStreamRequest resumeLiveStreamRequest = new ResumeLiveStreamRequest();
        resumeLiveStreamRequest.setAppName("live");
        resumeLiveStreamRequest.setDomainName("live.imhtb.cn");
        resumeLiveStreamRequest.setStreamName(String.valueOf(rid));
        ResumeLiveStreamResponse response = null;
        try {
            response = client.ResumeLiveStream(resumeLiveStreamRequest);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return false;
        }
        log.info(ResumeLiveStreamRequest.toJsonString(response));
        return true;
    }

    @Override
    public DescribeLiveForbidStreamListResponse banList(Integer page,Integer limit) {
        DescribeLiveForbidStreamListRequest request = new DescribeLiveForbidStreamListRequest();
        request.setPageNum(Long.valueOf(page));
        request.setPageSize(Long.valueOf(limit));
        try {
             return client.DescribeLiveForbidStreamList(request);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DescribeLiveSnapshotTemplatesResponse snapshotTemplatesList() {
        String params = "{}";
        DescribeLiveSnapshotTemplatesRequest req = DescribeLiveSnapshotTemplatesRequest.fromJsonString(params, DescribeLiveSnapshotTemplatesRequest.class);
        try {
            DescribeLiveSnapshotTemplatesResponse response = client.DescribeLiveSnapshotTemplates(req);
            System.out.println(DescribeLiveSnapshotTemplatesRequest.toJsonString(response));
            return response;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void snapshotTemplatesUpdate(ModifyLiveSnapshotTemplateRequest request) {
        try {
            ModifyLiveSnapshotTemplateResponse response = client.ModifyLiveSnapshotTemplate(request);
            log.info("修改截图模板:{}",ModifyLiveSnapshotTemplateResponse.toJsonString(response));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
