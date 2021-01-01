package com.zhu.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //通道管道
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(
            //基于分隔符的帧解码器
            new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()),
            //字符串解码器
            new StringDecoder(CharsetUtil.UTF_8),
            //字符串编码器
            new StringEncoder(CharsetUtil.UTF_8),
            //自定义消息处理器
            new ServerHandler()
        );

    }
}
