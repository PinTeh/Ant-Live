package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/4/2
 */
@Data
@TableName("tb_wallet")
public class Wallet {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("关联用户id")
    private Integer userId;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("钱包状态")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
