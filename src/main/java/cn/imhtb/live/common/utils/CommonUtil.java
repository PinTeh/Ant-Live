package cn.imhtb.live.common.utils;

import java.util.UUID;

/**
 * @author pinteh
 */
public class CommonUtil {

    public static int getRandomCode() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

    public static String getRandomString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(16);
    }

    public static String getOrderNo() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(10);
    }

}
