package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Data
public class SystemMenuQuery {

    @QueryFiled(type = QueryType.LIKE)
    private String title;

}
