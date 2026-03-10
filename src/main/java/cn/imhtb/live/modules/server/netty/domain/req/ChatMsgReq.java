package cn.imhtb.live.modules.server.netty.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author pinteh
 * @date 2023/6/13
 */
@Data
@ApiModel(value = "聊天消息")
public class ChatMsgReq {

    @NotNull(message = "房间id不能为空")
    @ApiModelProperty(value = "房间id")
    private Integer roomId;

    @NotNull(message = "聊天内容text不能为空")
    @Length(max = 100, message = "文字内容不能超过100")
    @ApiModelProperty(value = "文字内容")
    private String text;

}
