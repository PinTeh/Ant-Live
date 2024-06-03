package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/4/9
 */
@Data
public class Withdrawal {

    private Integer id;

    private String identity;

    private String identityName;

    private String mark;

    private String type;

    private Integer userId;

    private Integer status;

    private BigDecimal virtualAmount;

    private BigDecimal realAmount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
