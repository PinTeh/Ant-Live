package cn.imhtb.live.modules.system.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Data
public class SystemPresentDetail {

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
