package cn.imhtb.antlive.entity.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/4/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticSpeak {

    private Integer id;

    private Integer roomId;

    private Integer userId;

    private Integer number;

    private LocalDate date;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
