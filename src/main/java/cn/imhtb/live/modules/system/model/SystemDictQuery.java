package cn.imhtb.live.modules.system.model;

import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/7/12
 */
@Data
public class SystemDictQuery {

    @QueryFiled(type = QueryType.LIKE)
    private String type;

    @QueryFiled(type = QueryType.LIKE)
    private String typeName;

    @QueryFiled(type = QueryType.LIKE)
    private String label;

    @QueryFiled(type = QueryType.LIKE)
    private String value;


}
