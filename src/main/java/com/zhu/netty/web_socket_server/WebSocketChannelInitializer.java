package com.zhu.netty.web_socket_server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //Http服务器编解码器
        pipeline.addLast(
            new HttpServerCodec(),
            //分块编写处理程序
            new ChunkedWriteHandler(),
            //将分段请求集合到一起
            new HttpObjectAggregator(8192),
            //Web Socket服务器协议处理程序
            new WebSocketServerProtocolHandler("/ws"),
            //自定义网络通信程序
            new TextWebSocketFrameHandler()
        );
    }
}
