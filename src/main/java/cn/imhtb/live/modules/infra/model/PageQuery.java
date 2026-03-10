package cn.imhtb.live.modules.infra.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author pinteh
 * @date 2025/4/15
 */
@Data
@ApiModel("分页查询参数")
public class PageQuery {

    @ApiModelProperty("页码")
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNo = 1;

    @ApiModelProperty("分页大小")
    @Max(value = 1000, message = "分页大小不能超过1000")
    private Integer pageSize = 10;

}
