package com.zhu.netty.web_socket_server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static List<Channel> channels = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到客户端消息:"+msg.text());

        System.out.println("size:"+channels.size());

        for (Channel i : channels){
            if(i != ctx.channel()){
                i.writeAndFlush(new TextWebSocketFrame(msg.text()));
            }
        }

        ctx.channel().writeAndFlush(new TextWebSocketFrame("发送成功! 时间:" + LocalDateTime.now()));
    }

    /**
     * 处理程序添加
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("处理程序添加"+ctx.channel().id().asLongText());
        channels.add(ctx.channel());
    }

    /**
     * 处理程序移除
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("处理程序移除"+ctx.channel().id().asLongText());
        channels.remove(ctx.channel());
    }

    /**
     *程序发送异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发送异常");
        ctx.close();
    }
}
