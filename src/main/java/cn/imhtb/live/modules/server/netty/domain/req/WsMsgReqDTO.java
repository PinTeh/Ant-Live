package cn.imhtb.live.modules.server.netty.domain.req;

import lombok.Data;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Data
public class WsMsgReqDTO {

    /**
     * 消息类型 - 连接消息
     */
    private Integer msgType;

    /**
     * 消息体
     */
    private String data;

}
