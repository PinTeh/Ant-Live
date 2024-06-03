package cn.imhtb.live.modules.server.netty.handler;

import cn.imhtb.live.modules.server.netty.AttrUtil;
import cn.imhtb.live.utils.JwtUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author pinteh
 * @date 2023/6/4
 */
@Slf4j
public class HttpHeaderHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> parameters = decoder.parameters();
            List<String> paramValues = parameters.get("token");
            if (!CollectionUtils.isEmpty(paramValues)){
                String token = paramValues.get(0);
                try {
                    Integer userId = JwtUtil.getId(token);
                    log.info("connect userId = {}, token: {}", userId, token);
                    AttrUtil.setAttr(ctx.channel(), AttrUtil.USER_ID, userId);
                } catch (Exception e) {
                    // ignore
                }
            }
            // 因为有可能携带了参数，导致客户端一直无法返回握手包，因此在校验通过后，重置请求路径
            request.setUri("/");
        }

        ctx.fireChannelRead(msg);
    }

}
