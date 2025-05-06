package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Data
public class SystemUserQuery {

    private String id;

    @ApiModelProperty(value = "用户名")
    @QueryFiled(type = QueryType.LIKE)
    private String username;

    @ApiModelProperty(value = "昵称")
    @QueryFiled(type = QueryType.LIKE)
    private String nickname;

}
