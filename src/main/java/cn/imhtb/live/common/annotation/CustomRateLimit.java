package cn.imhtb.live.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pinteh
 * @date 2025/4/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRateLimit {

    /**
     * 限流key
     */
    String key();

    /**
     * 速率
     */
    long rate();

    /**
     * 速率时间间隔
     */
    long rateSecondInterval();

}
