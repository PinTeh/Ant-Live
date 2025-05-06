package cn.imhtb.live.modules.server.netty.service.impl;

import cn.imhtb.live.modules.server.netty.AttrUtil;
import cn.imhtb.live.modules.server.netty.assembly.WsMsgAssembly;
import cn.imhtb.live.modules.server.netty.domain.WsChannelExtraInfoDTO;
import cn.imhtb.live.modules.server.netty.domain.req.ChatMsgReq;
import cn.imhtb.live.modules.server.netty.domain.req.WsEnterReqDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.ChatMsgRespDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.GiftMsgRespDTO;
import cn.imhtb.live.modules.server.netty.domain.resp.WsMsgRespDTO;
import cn.imhtb.live.modules.server.netty.service.IRoomChatService;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.pojo.database.User;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoomChatServiceImpl implements IRoomChatService {

    private final IUserService userService;

    /**
     * 所有的会话信息维护
     */
    private static final ConcurrentHashMap<Channel, WsChannelExtraInfoDTO> ONLINE_ALL = new ConcurrentHashMap<>();
    /**
     * 房间会话维护
     */
    private static final ConcurrentHashMap<Integer, CopyOnWriteArraySet<Channel>> ONLINE_ROOM = new ConcurrentHashMap<>();
    /**
     * 用户会话维护
     */
    private static final ConcurrentHashMap<Integer, CopyOnWriteArraySet<Channel>> ONLINE_USER = new ConcurrentHashMap<>();


    @Override
    public long getRoomUserSize(Integer roomId) {
        CopyOnWriteArraySet<Channel> channels = ONLINE_ROOM.get(roomId);
        if (channels == null){
            return 0L;
        }
        return channels.size();
    }

    @Override
    public void enter(Channel channel, String data) {
//        log.info("chanel = {} , 进入房间:{}", channel.id(), data);
        WsEnterReqDTO wsEnterReqDTO = JSON.parseObject(data, WsEnterReqDTO.class);
        Integer roomId = wsEnterReqDTO.getRoomId();
        if (Objects.isNull(roomId)) {
            return;
        }

        // 游客登录处理
        WsChannelExtraInfoDTO channelExtraInfoDto = getChannelExtraInfo(channel).addRoomId(roomId);
        ONLINE_ROOM.putIfAbsent(roomId, new CopyOnWriteArraySet<>());
        ONLINE_ROOM.get(roomId).add(channel);

        // 会员登录处理
        AttributeKey<Integer> attributeKey = AttributeKey.valueOf("userId");
        Attribute<Integer> attr = channel.attr(attributeKey);
        Integer userId = AttrUtil.getAttr(channel, AttrUtil.USER_ID);
        if (Objects.nonNull(userId)) {
            ONLINE_USER.putIfAbsent(userId, new CopyOnWriteArraySet<>());
            ONLINE_USER.get(userId).add(channel);
            channelExtraInfoDto.setUserId(userId);
//            AttrUtil.setAttr(channel, AttrUtil.USER_ID, userId);
        }

//        this.sendMessage(channel, WsMsgAssembly.buildWelcome("欢迎进入直播间~"));
        ChatMsgRespDTO chatMsgRespDTO = new ChatMsgRespDTO();
        chatMsgRespDTO.setNickname("系统消息");
        chatMsgRespDTO.setText("欢迎进入直播间~");
        this.sendMessage(channel, WsMsgAssembly.buildChat(chatMsgRespDTO));
    }

    private WsChannelExtraInfoDTO getChannelExtraInfo(Channel channel) {
        WsChannelExtraInfoDTO extraInfo = ONLINE_ALL.getOrDefault(channel, WsChannelExtraInfoDTO.init());
        WsChannelExtraInfoDTO old = ONLINE_ALL.putIfAbsent(channel, extraInfo);
        return Objects.isNull(old) ? extraInfo : old;
    }

    @Override
    public void exit(Channel channel) {
//        log.info("chanel = {}，关闭", channel.id());

        channel.close();
        // 获取当前通道的附加信息
        WsChannelExtraInfoDTO channelExtraInfoDto = ONLINE_ALL.get(channel);
        if (Objects.nonNull(channelExtraInfoDto)) {
            Set<Integer> roomIds = channelExtraInfoDto.getRoomIds();
            // 移除房间会话
            for (Integer roomId : roomIds) {
                CopyOnWriteArraySet<Channel> channels = ONLINE_ROOM.get(roomId);
                channels.remove(channel);
            }
            // 移除用户会话
            Integer userId = channelExtraInfoDto.getUserId();
            if (Objects.nonNull(userId)){
                CopyOnWriteArraySet<Channel> channels = ONLINE_USER.get(userId);
                channels.remove(channel);
            }
            // 移除会话
            ONLINE_ALL.remove(channel);
        }
    }

    @Override
    public void sendMessage(String msg, Integer roomId, Integer userId) {
        CopyOnWriteArraySet<Channel> channels = ONLINE_ROOM.get(roomId);
        if (Objects.nonNull(channels)) {
            for (Channel channel : channels) {
                WsChannelExtraInfoDTO extra = ONLINE_ALL.get(channel);
                if (Objects.nonNull(extra) && userId.equals(extra.getUserId())) {
                    continue;
                }
                sendMessage(channel, WsMsgAssembly.buildWelcome(msg));
            }
        }
    }

    @Override
    public void sendChatMsg(ChatMsgReq chatMsgReq) {
        // todo
        User userInfo = userService.getUserInfo();
        Integer userId = userInfo.getId();
        Integer roomId = chatMsgReq.getRoomId();
        CopyOnWriteArraySet<Channel> channels = ONLINE_ROOM.get(roomId);
        if (Objects.nonNull(channels)){
            for (Channel channel : channels) {
                ChatMsgRespDTO chatMsgRespDTO = new ChatMsgRespDTO();
                chatMsgRespDTO.setFromUserId(userId);
                chatMsgRespDTO.setNickname(userInfo.getNickname());
                chatMsgRespDTO.setText(chatMsgReq.getText());
                sendMessage(channel, WsMsgAssembly.buildChat(chatMsgRespDTO));
            }
        }
    }

    @Override
    public void sendGiftMsg(String msg, Integer roomId, Integer userId, Integer giftId) {
        CopyOnWriteArraySet<Channel> channels = ONLINE_ROOM.get(roomId);
        if (Objects.nonNull(channels)) {
            for (Channel channel : channels) {
                ChatMsgRespDTO chatMsgRespDTO = new ChatMsgRespDTO();
//                chatMsgRespDTO.setFromUserId(userId);
                chatMsgRespDTO.setNickname("系统消息");
                chatMsgRespDTO.setText(msg);
                sendMessage(channel, WsMsgAssembly.buildChat(chatMsgRespDTO));
                // 不发送给当前赠送的用户
//                WsChannelExtraInfoDTO extra = ALL_ONLINE.get(channel);
//                if (Objects.nonNull(extra) && userId.equals(extra.getUserId())) {
//                    continue;
//                }
                sendMessage(channel, WsMsgAssembly.buildGift(GiftMsgRespDTO.builder()
                                .giftName("")
                                .giftId(giftId)
                                .text(msg)
                                .build()));
            }
        }
    }

    private void sendMessage(Channel channel, WsMsgRespDTO<?> wsMsgRespDTO) {
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(wsMsgRespDTO)));
    }

}
