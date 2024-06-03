package cn.imhtb.live.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author pinteh
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "lal")
public class LalLiveConfig {

    private String rtmpPushStream;

    private String flvPullStream;

}
