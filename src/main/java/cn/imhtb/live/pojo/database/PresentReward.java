package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
@Data
public class PresentReward {
    private Long id;

    private Integer fromId;

    private Integer toId;

    private Integer roomId;

    private Integer presentId;

    private Integer number;

    private Integer videoId;

    private Integer type;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
