package cn.imhtb.live.modules.server.netty.domain.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/4/9
 */
@Data
@Builder
public class GiftMsgRespDTO {

    private String text;

    private Integer giftId;

    private String giftName;

}
