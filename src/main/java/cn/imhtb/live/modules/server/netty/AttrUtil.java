package cn.imhtb.live.modules.server.netty;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author pinteh
 * @date 2023/6/4
 */
public class AttrUtil {

    public static AttributeKey<String> IP = AttributeKey.valueOf("ip");
    public static AttributeKey<Integer> USER_ID = AttributeKey.valueOf("userId");

    public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T data) {
        Attribute<T> attr = channel.attr(attributeKey);
        attr.set(data);
    }

    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        return channel.attr(attributeKey).get();
    }

}
