package cn.imhtb.antlive.entity.tencent;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamBanRequest {

    /**
     * ForbidLiveStream
     */

    private String action;

    /**
     * 固定取值 2018-08-01
     */
    private String version;

    private String app_name;

    private String domainName;

    private String streamName;

    /**
     * UTC
     */
    private String resumeTime;

    private String reason;
}
