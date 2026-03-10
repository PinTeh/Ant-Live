package cn.imhtb.live.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/3/5
 */
public class LocalDateTimeUtil {

    public static long subMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }

    public static long subSeconds(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }

    public static long subHours(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours();
    }

}
