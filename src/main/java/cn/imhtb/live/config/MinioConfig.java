package cn.imhtb.live.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author pinteh
 * @date 2023/5/13
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "minio")
@Configuration
public class MinioConfig {

    @ApiModelProperty(value = "包含完整的协议ip和端口",example = "http://localhost:9090")
    private String endpoint;

    private String ip;

    private Integer port;

    private String accessKey;

    private String secretKey;

    @ApiModelProperty("桶名称")
    private String bucketName;

    @ApiModelProperty(value = "是否返回相对路径",notes = "默认false")
    private boolean relative;

}