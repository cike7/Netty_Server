package com.zhu.netty.thirdexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 聊天客户端
 */
public class MyChatClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyChetClientInitializer());
            Channel channel = bootstrap.connect("localhost",20530).sync().channel();

            //获取控制台输入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            for (;;){
                channel.writeAndFlush(br.readLine() + "\r\n");
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
