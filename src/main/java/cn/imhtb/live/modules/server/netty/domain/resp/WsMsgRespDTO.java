package cn.imhtb.live.modules.server.netty.domain.resp;

import lombok.Data;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Data
public class WsMsgRespDTO<T> {

    private String method;

    private T data;

}
