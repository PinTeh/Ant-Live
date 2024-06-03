package cn.imhtb.live.modules.server.netty.assembly;

import cn.imhtb.live.modules.server.netty.domain.resp.ChatMsgRespDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.WsMsgRespDTO;
import cn.imhtb.live.modules.server.netty.enums.WsRespMethodEnum;

/**
 * @author pinteh
 * @date 2023/6/13
 */
public class WsMsgAssembly {

    public static WsMsgRespDTO<String> buildWelcome(String message){
        WsMsgRespDTO<String> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.WELCOME.getMethod());
        wsMsgRespDTO.setData(message);
        return wsMsgRespDTO;
    }

    public static WsMsgRespDTO<ChatMsgRespDTO> buildChat(ChatMsgRespDTO chatMsgRespDTO){
        WsMsgRespDTO<ChatMsgRespDTO> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.CHAT.getMethod());
        wsMsgRespDTO.setData(chatMsgRespDTO);
        return wsMsgRespDTO;
    }

    public static WsMsgRespDTO<String> buildGift(String message){
        WsMsgRespDTO<String> wsMsgRespDTO = new WsMsgRespDTO<>();
        wsMsgRespDTO.setMethod(WsRespMethodEnum.GIFT.getMethod());
        wsMsgRespDTO.setData(message);
        return wsMsgRespDTO;
    }

}
