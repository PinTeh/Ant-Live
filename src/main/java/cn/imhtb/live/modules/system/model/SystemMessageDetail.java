package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息表
 */
@Data
public class SystemMessageDetail {
    /**
     * id
     */
    private Integer id;

    /**
     * 会话表id
     */
    private Integer roomId;

    private String roomUserNickname;

    private String fromUserNickname;

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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}