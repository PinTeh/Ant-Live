package cn.imhtb.live.modules.wallet.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/5/23
 */
@Data
public class WalletLogResp {

    private Integer id;

    @ApiModelProperty("钱包id")
    private Integer walletId;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("操作金额")
    private BigDecimal fee;

    @ApiModelProperty("操作类型")
    private Integer actionType;

    @ApiModelProperty("操作类型名称")
    private String actionTypeName;

    @ApiModelProperty("源id")
    private String sourceUuid;

    @ApiModelProperty("源类型")
    private String sourceType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
