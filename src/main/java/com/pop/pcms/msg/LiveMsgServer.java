package com.pop.pcms.msg;

import com.pop.pcms.msg.handler.WebsocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 直播消息--通过TCP端口监听实现
 * Created by zhangjinye on 2017/3/8.
 */
@Service
public class LiveMsgServer {

    private static final Logger log = LoggerFactory.getLogger(LiveMsgServer.class);

    //端口监听地址
    @Value("${nettyServver.liveMsgPort}")
    private int liveMsgPort;

    @Autowired
    private WebsocketInitializer websocketInitializer;

    public void runServer() throws Exception {
        //构建bossGroup workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            log.info("LiveMsgServer is start----->");
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(websocketInitializer);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(liveMsgPort).sync();
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("LiveMsgServer is start error:{}", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
