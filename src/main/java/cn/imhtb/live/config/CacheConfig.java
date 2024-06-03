package cn.imhtb.live.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author pinteh
 * @date 2023/2/18
 */
@Configuration
public class CacheConfig {

    @Bean("caffeineCache")
    public Cache<String, String> caffeineCache(){
        return Caffeine.newBuilder()
                .initialCapacity(30)
                .maximumSize(50)
                .expireAfterAccess(2, TimeUnit.MINUTES)
                .build();
    }

}
