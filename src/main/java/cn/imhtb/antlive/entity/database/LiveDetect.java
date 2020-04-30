package cn.imhtb.antlive.entity.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
@Data
public class LiveDetect {

    private Integer id;

    private String type;

    private Integer roomId;

    private Integer confidence;

    private Integer normalScore;

    private Integer hotScore;

    private Integer pornScore;

    private Integer screenshotTime;

    private Integer level;

    private String img;

    private Integer illegalScore;

    private Integer polityScore;

    private Integer terrorScore;

    private Integer handleStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime resumeTime;

}
