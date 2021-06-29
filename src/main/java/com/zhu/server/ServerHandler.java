package com.zhu.server;

import com.google.gson.Gson;
import com.zhu.Application;
import com.zhu.Info.DataPackage;
import com.zhu.service.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    //转发数据包
    public DataPackage feedback = new DataPackage();

    //邮箱验证码
    public Integer verifyCode = 0;

    public Channel channel = null;

    //相关业务
    public List<StreamData> relatedService = new ArrayList<>();

    public ServerHandler(){

        System.out.println("创建成功");

        //添加基础相关业务
        //其他服务需要登入成功才能操作
        relatedService.add(new TypeUserLogin());//登入
        relatedService.add(new TypeUserRegister());//注册
        relatedService.add(new TypeUserVerify());//验证
        relatedService.add(new TypeGameRoom());//创建房间
        relatedService.add(new TypeGameBattle());//游戏同步
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg){
        channel = ctx.channel();
        //裁剪空缺是为了json解析不易出错
        String str = msg.toString().trim();
        System.out.println("收到消息 [客户端] ：" + str);
        //json
        Gson gson = new Gson();
        //接收数据包
        DataPackage dataPackage = gson.fromJson(str, DataPackage.class);

        //传递消息给相关服务进行处理
        for (int i = 0; i < relatedService.size(); i++) {
            relatedService.get(i).receiveData(this, dataPackage);
        }
    }


    /**
     * 读取通道
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(" [客户端] 收到消息：" + msg);
    }


    /**
     * 客户端加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }


    /**
     * 客户端离开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端]-" + ctx.channel().remoteAddress() + "离开\n");
        synchronized (Application.class) {
            //移除客户端
            if (Application.getInstance.clients.containsKey(ctx.channel())) {
                Application.getInstance.clients.remove(ctx.channel());
            }
        }
    }


    /**
     * 活跃状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[客户端]-" + channel.remoteAddress() + "上线了\n");
    }


    /**
     * 不活跃状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端]-" + ctx.channel().remoteAddress() + "下线了\n");
        synchronized (Application.class) {
            //移除客户端
            if (Application.getInstance.clients.containsKey(ctx.channel())) {
                Application.getInstance.clients.remove(ctx.channel());
                System.out.println("剩余在线人数 >> : " + Application.getInstance.clients.size());
            }
        }

    }


    /**
     * 客户端退出
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println(ctx.channel() + ":" + "退出");
        ctx.close();
    }


    /**
     * 根据value值获取到对应的一个key值
     * @param map
     * @param value
     * @return
     */
    public Channel getKey(HashMap<Channel,Integer> map,Integer value){
        Channel key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for(Channel getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                key = getKey;
            }
        }
        return key;
    }

}
