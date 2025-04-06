package cn.imhtb.live.modules.server;

import cn.imhtb.live.pojo.User;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.common.utils.JwtUtil;
import cn.imhtb.live.common.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PinTeh
 */
@Slf4j
@Component
@ServerEndpoint(value = "/live/chat/{rid}/{token}")
public class RoomChatServer {

    private static IUserService userService;

    private static RedisUtil redisUtil;

    @Autowired
    public void setComponent(IUserService userService, RedisUtil redisUtil) {
        RoomChatServer.userService = userService;
        RoomChatServer.redisUtil = redisUtil;
    }

    private static int onlineCount = 0;

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        RoomChatServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        RoomChatServer.onlineCount--;
    }

    private static final ConcurrentHashMap<String, Set<Session>> CHAT_ROOMS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("rid") String rid, @PathParam("token") String token, Session session) {
        if (!CHAT_ROOMS.containsKey(rid)) {
            Set<Session> room = new HashSet<>();
            room.add(session);
            CHAT_ROOMS.put(rid, room);
        } else {
            CHAT_ROOMS.get(rid).add(session);
        }
        User user = userService.getById(JwtUtil.getIdNoError(token));
        if (user != null) {
            Long ret = (Long) redisUtil.hIncr(RedisPrefix.MEMBER_VIEW, rid);
            log.info("插入Redis" + ret + " Rid:" + rid);
            sendMessage(
                    WebMessage.createChat(user, user.getNickname() + "进入直播间~").toJson(),
                    CHAT_ROOMS.get(rid), session);
            log.info("[会员连接消息] rid:" + rid + " session:" + session.getId() + "当前房间人数:" + CHAT_ROOMS.get(rid).size());
        } else {
            log.info("[游客连接消息] rid:" + rid + " session:" + session.getId() + "当前房间人数:" + CHAT_ROOMS.get(rid).size());
        }
        addOnlineCount();

        redisUtil.hIncr(RedisPrefix.TOTAL_VIEW, rid);

        String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, rid);
        if (redisUtil.exists(key)) {
            redisUtil.hIncr(key, RedisPrefix.LIVE_CLICK_COUNT);
        }
    }

    @OnMessage
    public void onMessage(@PathParam("rid") String rid, @PathParam("token") String token, String message, Session session) {

        User user = userService.getById(JwtUtil.getIdNoError(token));
        if (user != null) {
            WebMessage webMessage = JSON.parseObject(message, WebMessage.class);
            // TODO 验证消息
            webMessage.setU(new WebMessage.U(user.getId(), user.getNickname()));

            // Statistic
            redisUtil.hIncr(RedisPrefix.MEMBER_SPEAK, rid);
            String key = String.format(RedisPrefix.LIVE_KEY_PREFIX, rid);
            if (redisUtil.exists(key)) {
                redisUtil.hIncr(key, RedisPrefix.LIVE_DAN_MU_COUNT);
            }
            sendMessage(webMessage.toJson(), CHAT_ROOMS.get(rid), session);
        }
        log.info("[收到消息] rid:" + rid + ", message:" + message);
    }

    @OnClose
    public void onClose(@PathParam("rid") String rid, Session session) {
        log.info("[退出消息] 有人退出直播间 rid:" + rid);
        Set set = CHAT_ROOMS.get(rid);
        if (set == null) {
            return;
        }
        set.remove(session);
        subOnlineCount();
    }

    @OnError
    public void onError(@PathParam("rid") String rid, Session session, Throwable error) {
        subOnlineCount();
        error.printStackTrace();
    }

    private void sendMessage(String message, Set<Session> room, Session session) {
        log.info("[系统发送消息] 聊天室人数:" + room.size());
        for (Session user : room) {
            if (!user.equals(session)) {
                try {
                    user.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendSystemPresentMessage(String message, Integer rid) {
        log.info("[sendMessage] :" + rid);
        Set<Session> sessions = CHAT_ROOMS.get(String.valueOf(rid));
        for (Session user : sessions) {
            try {
                user.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
