package cn.imhtb.antlive.entity.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Data
public class Video {

    private Integer id;

    private String fileId;

    private String videoUrl;

    private String coverUrl;

    private String type;

    private Integer userId;

    private Integer videoCategoryId;

    private String title;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
