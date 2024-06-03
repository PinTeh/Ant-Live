package cn.imhtb.live.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author pinteh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    private Long id;

    private Integer userId;

    private String orderNo;

    private BigDecimal billChange;

    private Integer type;

    private BigDecimal balance;

    private String ip;

    private String mark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
