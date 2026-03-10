package cn.imhtb.live.modules.system.model;

import lombok.Data;

/**
 * 消息表
 */
@Data
public class SystemMessageQuery {

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

}