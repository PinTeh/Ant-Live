package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2024/9/20
 */
@Data
public class RewardRespVo {

    @ApiModelProperty("礼物名称")
    private String presentName;

    @ApiModelProperty("礼物图标")
    private String presentIcon;

    @ApiModelProperty("老板")
    private String fromUserNickname;

    @ApiModelProperty("老板头像")
    private String fromUserAvatar;

    @ApiModelProperty("礼物数量")
    private Integer number;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("时间")
    private LocalDateTime createTime;

}
