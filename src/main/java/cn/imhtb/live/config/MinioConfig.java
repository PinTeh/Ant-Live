package cn.imhtb.live.config;

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

    private String endpoint;

    private String ip;

    private Integer port;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private Boolean relative;

}