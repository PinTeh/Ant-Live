package cn.imhtb.live.modules.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author pinteh
 * @date 2025/4/14
 */
@Data
public class PresentDetail {

    private Integer id;

    @ApiModelProperty(value = "礼物名称")
    private String name;

    @ApiModelProperty(value = "礼物图标")
    private String icon;

    @ApiModelProperty(value = "礼物描述")
    private String description;

    @ApiModelProperty(value = "礼物价格")
    private BigDecimal price;

    @ApiModelProperty(value = "礼物排序")
    private Integer sort;

    @ApiModelProperty(value = "是否禁用")
    private Integer disabled;

}
