package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/4/30
 */
@Data
public class SystemPresentQuery {

    private Integer id;

    @ApiModelProperty(value = "礼物名称")
    @QueryFiled(type = QueryType.LIKE)
    private String name;

}
