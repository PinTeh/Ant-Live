package cn.imhtb.live.modules.server.netty.domain.resp;

import lombok.Data;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@Data
public class ChatMsgRespDTO {

    private Integer fromUserId;

    private String nickname;

    private String text;

}
