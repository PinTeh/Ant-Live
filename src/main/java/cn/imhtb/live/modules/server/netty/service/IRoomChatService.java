package cn.imhtb.live.modules.server.netty.service;

import cn.imhtb.live.modules.server.netty.domain.req.ChatMsgReq;
import io.netty.channel.Channel;

/**
 * @author pinteh
 * @date 2023/6/4
 */
public interface IRoomChatService {

    /**
     * 获取房间在线人数
     *
     * @param roomId 房间id
     * @return long
     */
    long getRoomUserSize(Integer roomId);

    /**
     * 进入
     *
     * @param channel 通道
     * @param data    数据
     */
    void enter(Channel channel, String data);

    /**
     * 退出
     *
     * @param channel 通道
     */
    void exit(Channel channel);

    /**
     * 发送消息
     *
     * @param msg    消息
     * @param roomId 房间id
     * @param userId 用户id
     */
    void sendMessage(String msg, Integer roomId, Integer userId);


    /**
     * 发送消息
     *
     * @param chatMsgReq 发送消息
     */
    void sendChatMsg(ChatMsgReq chatMsgReq);

    /**
     * 送礼物
     *
     * @param msg    味精
     * @param roomId 房间id
     * @param userId 当前用户id
     */
    void sendGiftMsg(String msg, Integer roomId, Integer userId);

}
