package cn.imhtb.live.modules.server.netty.domain.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@Data
public class ChatMsgReq {

    @NotNull
    private Integer roomId;

    @NotNull
    private String text;

}
