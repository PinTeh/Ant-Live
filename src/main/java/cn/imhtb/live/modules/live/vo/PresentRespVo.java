package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author pinteh
 * @date 2025/4/8
 */
@Data
@ApiModel("礼物")
public class PresentRespVo {

    @ApiModelProperty(value = "礼物id")
    private Integer id;

    @ApiModelProperty(value = "礼物名称")
    private String name;

    @ApiModelProperty(value = "礼物图标")
    private String icon;

    @ApiModelProperty(value = "礼物描述")
    private String description;

    @ApiModelProperty(value = "礼物价格")
    private BigDecimal price;

}
