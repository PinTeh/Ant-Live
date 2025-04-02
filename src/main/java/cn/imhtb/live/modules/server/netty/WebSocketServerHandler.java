package cn.imhtb.live.modules.server.netty;

import cn.imhtb.live.modules.server.netty.domain.req.WsMsgReqDTO;
import cn.imhtb.live.modules.server.netty.enums.WsReqTypeEnum;
import cn.imhtb.live.modules.server.netty.service.IRoomChatService;
import cn.imhtb.live.utils.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        getBean().exit(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        getBean().exit(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        getBean().exit(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 服务端在乎的是客户端有没有发过来心跳，所以这里关注读空闲
            if(event.state() == IdleState.READER_IDLE) {
                log.warn("服务端30秒内没有收到客户端心跳，主动关闭连接");
                getBean().exit(ctx.channel());
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){

        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        WsMsgReqDTO wsMsgReqDTO = JSON.parseObject(text, WsMsgReqDTO.class);
        WsReqTypeEnum wsReqTypeEnum = WsReqTypeEnum.of(wsMsgReqDTO.getMsgType());
        if (Objects.isNull(wsReqTypeEnum)){
            return;
        }
        switch (wsReqTypeEnum) {
            case ENTER:
                getBean().enter(channelHandlerContext.channel(), wsMsgReqDTO.getData());
                break;
            case EXIT:
                getBean().exit(channelHandlerContext.channel());
                break;
            case HEARTBEAT:
                break;
            default:
                log.warn("未知消息类型, {}", wsMsgReqDTO.getMsgType());
        }
    }

    private IRoomChatService getBean() {
        return SpringContextUtil.getBean(IRoomChatService.class);
    }

}
