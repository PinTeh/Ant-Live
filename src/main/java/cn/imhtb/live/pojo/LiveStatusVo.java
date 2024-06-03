package cn.imhtb.live.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pinteh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiveStatusVo {

    @ApiModelProperty("直播开始时间")
    private String liveStartTime;

    @ApiModelProperty("直播状态")
    private Integer liveStatus;

    @ApiModelProperty("直播推流地址")
    private String livePushUrl;

    @ApiModelProperty("直播密钥")
    private String livePushSecret;

}
