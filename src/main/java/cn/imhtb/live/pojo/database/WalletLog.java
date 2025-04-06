package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/4/2
 */
@Data
@TableName("tb_wallet_log")
public class WalletLog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer walletId;

    private BigDecimal fee;

    private Integer actionType;

    private String sourceUuid;

    private String sourceType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
