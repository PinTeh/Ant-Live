package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Data
public class SystemRoleQuery {

    @QueryFiled(type = QueryType.LIKE)
    private String name;

}
