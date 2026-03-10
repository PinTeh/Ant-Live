package cn.imhtb.live.modules.server.netty.assembly;

import cn.imhtb.live.modules.server.netty.domain.resp.ChatMsgRespDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.GiftMsgRespDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.WsMsgRespDTO;
import cn.imhtb.live.modules.server.netty.enums.WsRespMethodEnum;

/**
 * 消息组装
 *
 * @author pinteh
 * @date 2023/6/13
 */
public class WsMsgAssembly {

    /**
     * 构建欢迎信息
     */
    public static WsMsgRespDTO<String> buildWelcome(String message){
        WsMsgRespDTO<String> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.WELCOME.getMethod());
        wsMsgRespDTO.setData(message);
        return wsMsgRespDTO;
    }

    /**
     * 构建聊天信息
     */
    public static WsMsgRespDTO<ChatMsgRespDTO> buildChat(ChatMsgRespDTO chatMsgRespDTO){
        WsMsgRespDTO<ChatMsgRespDTO> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.CHAT.getMethod());
        wsMsgRespDTO.setData(chatMsgRespDTO);
        return wsMsgRespDTO;
    }

    /**
     * 构建礼物信息
     */
    public static WsMsgRespDTO<GiftMsgRespDTO> buildGift(GiftMsgRespDTO message){
        WsMsgRespDTO<GiftMsgRespDTO> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.GIFT.getMethod());
        wsMsgRespDTO.setData(message);
        return wsMsgRespDTO;
    }

}
