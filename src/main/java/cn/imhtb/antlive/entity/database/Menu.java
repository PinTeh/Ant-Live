package cn.imhtb.antlive.entity.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/4/29
 */
@Data
public class Menu {

    private Integer id;

    private Integer menuIndex;

    private String icon;

    private String path;

    private String title;

    private Integer pid;

    private Integer sort;

    private Integer hidden;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
