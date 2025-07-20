package cn.imhtb.live.modules.system.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/7/12
 */
@Data
public class SystemDictUpdate {

    private Integer id;

    private String type;

    private String typeName;

    @ApiModelProperty("label")
    private String label;

    @ApiModelProperty("value")
    private String value;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
