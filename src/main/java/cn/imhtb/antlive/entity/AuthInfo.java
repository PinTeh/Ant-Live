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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth")
public class AuthInfo {

    private int id;

    private int userId;

    private String realName;

    private String positiveUrl;

    private String reverseUrl;

    private String handUrl;

    private String rejectReason;

    private String cardNo;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private int status;

    private int operator;

}
