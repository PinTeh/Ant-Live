package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/5/8
 */
@Data
public class SysPush {

    private Integer id;

    private String email;

    private String mobile;

    private Integer open;

    private Integer userId;

    private String listenerItems;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
