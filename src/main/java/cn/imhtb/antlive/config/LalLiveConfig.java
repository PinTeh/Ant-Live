package cn.imhtb.antlive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "lal")
public class LalLiveConfig {

    private String rtmpPushStream;

    private String flvPullStream;

}
