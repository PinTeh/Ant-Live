package cn.imhtb.live.modules.server.netty.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@Data
@ApiModel("聊条消息")
public class ChatMsgRespDTO {

    @ApiModelProperty(value = "用户id")
    private Integer fromUserId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "聊天内容")
    private String text;

}
