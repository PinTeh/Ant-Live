package cn.imhtb.live.modules.server.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Getter
@AllArgsConstructor
public enum WsReqTypeEnum {

    /**
     * WebSocket消息类型
     */
    ENTER(0, "进入"),
    EXIT(1, "退出"),
    HEARTBEAT(2, "心跳")
    ;

    private final Integer code;
    private final String desc;

    public static WsReqTypeEnum of(Integer type){
        for (WsReqTypeEnum value : WsReqTypeEnum.values()) {
            if (value.getCode().equals(type)){
                return value;
            }
        }
        return null;
    }

}
