package cn.imhtb.live.modules.server.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@AllArgsConstructor
@Getter
public enum WsRespMethodEnum {
    /**
     * 响应消息类型
     */
    WELCOME("welcomeMessage", "欢迎"),
    LIKE("likeMessage", "点赞"),
    CHAT("chatMessage", "聊天"),
    GIFT("giftMessage", "礼物"),
    STATS("statsMessage", "状态"),
    ;

    private final String method;
    private final String desc;

    public static WsRespMethodEnum of(String name) {
        for (WsRespMethodEnum value : WsRespMethodEnum.values()) {
            if (value.getMethod().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("error type");
    }

}
