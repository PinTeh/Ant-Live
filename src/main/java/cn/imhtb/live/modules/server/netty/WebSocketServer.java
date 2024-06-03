package cn.imhtb.live.modules.server.netty;

import cn.imhtb.live.modules.server.netty.handler.HttpHeaderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * netty实现直播间聊天
 * @author pinteh
 * @date 2023/6/4
 */
@Slf4j
@Component
public class WebSocketServer {

    public static final int INET_PORT = 10022;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(8);

    @PostConstruct
    public void run(){
        try {
            startServer();
            log.info("websocket server start success, port : {}", INET_PORT);
        } catch (InterruptedException e) {
            log.error("websocket server start error", e);
        }
    }

    private void startServer() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(30, 0,0));
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
                        // 自定义头部处理器
                        pipeline.addLast(new HttpHeaderHandler());
                        // webSocket处理器
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        pipeline.addLast(new WebSocketServerHandler());
                    }
                });
        // 启动服务
        bootstrap.bind(INET_PORT).sync();
    }

}
