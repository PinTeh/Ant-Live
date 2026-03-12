package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 直播间亲密榜返回
 */
@Data
@ApiModel("直播间亲密榜")
public class RoomIntimacyRankRespVo {

    @ApiModelProperty("排名")
    private Integer rankNo;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("亲密值")
    private BigDecimal intimacyValue;
}
