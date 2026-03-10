package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/12/07
 */
@Data
public class SystemRoomQuery {

    @QueryFiled(type = QueryType.EQ)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @QueryFiled(type = QueryType.EQ)
    private Integer userId;

    @ApiModelProperty(value = "房间标题")
    @QueryFiled(type = QueryType.LIKE)
    private String title;

    @ApiModelProperty(value = "房间状态")
    @QueryFiled(type = QueryType.EQ)
    private Integer status;

    @ApiModelProperty(value = "分类ID")
    @QueryFiled(type = QueryType.EQ)
    private Integer categoryId;

    @ApiModelProperty(value = "是否禁用")
    @QueryFiled(type = QueryType.EQ)
    private Integer disabled;

}
