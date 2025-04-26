package cn.imhtb.live.modules.infra.utils;

import cn.imhtb.live.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * @author pinteh
 * @date 2025/4/19
 */
@Slf4j
public class RedisUtils {

    private static final StringRedisTemplate redisTemplate;

    static {
        redisTemplate = SpringContextUtil.getBean(StringRedisTemplate.class);
    }

    public static void set(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, String value, Duration duration){
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public static String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public static Boolean contains(String key){
        return redisTemplate.hasKey(key);
    }
}
