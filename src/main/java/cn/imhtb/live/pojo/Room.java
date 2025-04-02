package cn.imhtb.live.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "id", type = IdType.AUTO)
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

    private String pushUrl;

    private String pullUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
