package cn.imhtb.antlive.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String title;

    private String cover;

    private String secret;

    private String introduce;

    private String notice;

    private String rtmpUrl;

    private Integer disabled;

    private Integer status;

    private Integer categoryId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
