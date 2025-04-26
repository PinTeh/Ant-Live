package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Present {

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
