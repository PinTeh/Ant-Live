package cn.imhtb.live.pojo.vo.lal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pinteh
 */
@NoArgsConstructor
@Data
public class PubVO {

    @JsonProperty("server_id")
    private String serverId;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("url")
    private String url;

    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("stream_name")
    private String streamName;

    @JsonProperty("url_param")
    private String urlParam;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("remote_addr")
    private String remoteAddr;

    @JsonProperty("has_in_session")
    private Boolean hasInSession;

    @JsonProperty("has_out_session")
    private Boolean hasOutSession;
}
