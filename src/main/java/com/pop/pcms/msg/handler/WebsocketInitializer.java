package com.pop.pcms.msg.handler;

import com.pop.pcms.msg.map.ChannelMap;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * websocket handler Initializer
 * Created by zhangjinye on 2017/3/8.
 */
@Component
public class WebsocketInitializer extends ChannelInitializer {

    private static final Logger log = LoggerFactory.getLogger(WebsocketInitializer.class);

    @Autowired
    private WebSocketServerHandler webSocketServerHandler;

    /**
     * 配置websocket转码相关参数
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        //构建ChannelPipeline并设置支持websocket的配置参数
        ChannelPipeline pipeline = ch.pipeline();
        addChannelMap(ch);
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("handler", webSocketServerHandler);
        log.info("WebsocketInitializer配置参数配置结束!");

        //添加关闭监听
        ch.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //当通道关闭后 移除channelmap ch.id()和ch
                ChannelMap channelMap = ChannelMap.getInstance();
                channelMap.removeChannelMap(ch.id());
            }
        });
    }

    /**
     * 将ch.id()和ch放入 放入channel map
     *
     * @param ch
     */
    private void addChannelMap(Channel ch) {
        ChannelMap channelMap = ChannelMap.getInstance();
        channelMap.setChannelMap(ch.id(), ch);
    }
}
