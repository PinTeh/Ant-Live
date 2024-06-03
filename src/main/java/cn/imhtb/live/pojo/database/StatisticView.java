package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticView {

    private Integer id;

    private Integer roomId;

    private Integer userId;

    private Integer memberNumber;

    private Integer visitorNumber;

    private Integer totalNumber;

    private LocalDate date;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
