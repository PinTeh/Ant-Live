package cn.imhtb.antlive.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 */
@Data
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String username;

    @TableField(exist = false)
    private String account;

    @JsonIgnore
    private String password;

    private String email;

    private String mobile;

    private String avatar;

    private String nickName;

    private String sex;

    private String signature;

    private Integer roleId;

    private int disabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
