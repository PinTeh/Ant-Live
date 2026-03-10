package cn.imhtb.live.modules.infra.config;

/**
 * @author pinteh
 * @date 2025/4/19
 */
public class RedisKey {

    public static final String BASE = "AntLive:";

    public static final String VERIFY_CODE = "verifyCode:%s:%d";

    /**
     * 获取缓存键
     *
     * @param keyType 缓存键类型
     * @param objects 替换值
     * @return 键
     */
    public static String getKey(String keyType, Object... objects){
        return String.format(BASE + keyType, objects);
    }

}
