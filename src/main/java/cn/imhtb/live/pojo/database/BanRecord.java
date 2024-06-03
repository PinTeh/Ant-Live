package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/5/23
 */
@Data
public class BanRecord {

    private Integer id;

    private Integer roomId;

    private LocalDateTime resumeTime;

    private Integer status;

    private String mark;

    private LocalDateTime startTime;

    private String reason;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
