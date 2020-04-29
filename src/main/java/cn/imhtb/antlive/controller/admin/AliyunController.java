package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PinTeh
 * @date 2020/4/27
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AliyunController {

    @Value("${aliyun.iii}")
    private String accessKeyId;

    @Value("${aliyun.kkk}")
    private String accessSecret;

    @Value("${aliyun.regionId}")
    private String regionId;

    @Value("${aliyun.instanceId}")
    private String instanceId;

    @GetMapping("/monitor")
    public ApiResponse monitor(){
        /*
            每次最多返回400条监控数据，如果指定的参数(EndTime - StartTime)/Period大于400时，则返回错误。
            单次最多能查询近30天内的监控信息，如果指定的参数StartTime超过30天，则返回错误。
            当返回信息中缺少部分内容时，可能是由于系统没有获取到相应的信息，比如当时实例处于已停止（Stopped）状态。
         */
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
            DescribeInstancesResponse response = client.getAcsResponse(request);
            DescribeInstanceMonitorDataResponse acsResponse = client.getAcsResponse(describeInstanceMonitorDataRequest);
            Map<String,Object> ret = new HashMap<>(2);
            ret.put("describeInstancesResponse",response);
            ret.put("describeInstanceMonitorDataResponse",acsResponse);
            List<Map<String,Object>> list = new ArrayList<>();
            list.add(ret);
            return ApiResponse.ofSuccess(list);
        } catch (ClientException e) {
            log.error(String.format("Fail. Business error. ErrorCode: %s, RequestId: %s",
                    e.getErrCode(), e.getRequestId()));
        }
        return ApiResponse.ofError();
    }

}
