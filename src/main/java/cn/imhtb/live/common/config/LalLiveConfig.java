package cn.imhtb.live.common.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author pinteh
 */
@ApiModel("lal推流服务器配置")
@Data
@Configuration
@ConfigurationProperties(prefix = "lal")
public class LalLiveConfig {

    @ApiModelProperty("密钥")
    private String secret;

    @ApiModelProperty("推流地址")
    private String rtmpPushStream;

    @ApiModelProperty("拉流地址")
    private String flvPullStream;

}
