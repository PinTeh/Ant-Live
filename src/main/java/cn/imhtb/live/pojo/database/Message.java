package cn.imhtb.live.pojo.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息表
 */
@TableName(value ="message")
@Data
public class Message {
    /**
     * id
     */
    @TableId
    private Integer id;

    /**
     * 会话表id
     */
    private Integer roomId;

    /**
     * 消息发送者uid
     */
    private Integer fromUid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 回复的消息内容
     */
    private Integer replyMsgId;

    /**
     * 消息状态 0正常 -1删除
     */
    private Integer status;

    /**
     * 消息类型 1正常文本 2.撤回消息
     */
    private Integer type;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}