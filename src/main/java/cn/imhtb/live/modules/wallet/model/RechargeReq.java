package cn.imhtb.live.modules.wallet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2025/5/23
 */
@Data
@ApiModel("充值参数")
public class RechargeReq {

    @ApiModelProperty("充值金额")
    private String fee;

}
