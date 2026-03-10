package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pinteh
 * @date 2024/9/20
 */
@Data
public class RewardReqVo {

    @NotNull
    @ApiModelProperty("礼物编号")
    private Integer presentId;

    @NotNull
    @ApiModelProperty("礼物数量")
    private Integer number;

    @NotNull
    @ApiModelProperty("房间号")
    private Integer roomId;

    private Integer vid;

}
