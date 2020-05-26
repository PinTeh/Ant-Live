package cn.imhtb.antlive.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiveInfo {

    private Integer id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Integer status;

    private Integer roomId;

    private Integer userId;

    private String clickCount;

    private String danMuCount;

    private String presentCount;

}
