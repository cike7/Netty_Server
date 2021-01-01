package com.zhu.server;

import com.zhu.service.Room;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application {

    public static Application getInstance;

    /**
     * 客户端连接池实例
     */
    public volatile HashMap<Channel,Integer> clients = new HashMap<>();

    public volatile List<Room> rooms = new ArrayList<>();

    /**
     * 客户端连接池实例
     */
    //public static volatile ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Application() throws Exception{

        getInstance = this;

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(20530).sync();
            System.out.println("服务器启动成功!!! ");

            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{

        new Application();

    }
}
