package cn.imhtb.live.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2023/5/31
 */
@Data
public class RoomExtraInfoResp {

    @ApiModelProperty("关注房间状态")
    private Boolean follow;

}
