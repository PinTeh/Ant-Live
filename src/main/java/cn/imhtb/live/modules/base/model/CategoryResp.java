package cn.imhtb.live.modules.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/4/12
 */
@Data
@ApiModel("目录分类")
public class CategoryResp {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

}
