package com.zhu.service;

import com.google.gson.Gson;
import com.zhu.Info.DataPackage;
import com.zhu.Info.GameInfo;
import com.zhu.Info.GameType;
import com.zhu.Info.RequestType;
import com.zhu.server.Application;
import com.zhu.server.ServerHandler;
import io.netty.channel.Channel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class TypeGameRoom implements StreamData {

    Gson json = new Gson();

    Room room = new Room();

    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {

        if(dataPackage.requestType == RequestType.game){

            //裁剪空缺是为了json解析不易出错
            String str = null;

            try {
                str = new String(dataPackage.data,"UTF-8").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            System.out.println(str);

            GameInfo info = json.fromJson(str,GameInfo.class);

            if(info.requestType == GameType.game_create){//创建
                //获取客户端套接字
                Channel channel = serverHandler.getKey(Application.getInstance.clients, dataPackage.userInfo.getUser_id());

                //创建加入房间
                room.roomId = info.roomNumber;
                room.players = new HashMap<>();
                room.players.put(info.roomNumber,channel);

                Application.getInstance.rooms.add(room);

            }
            else if(info.requestType == GameType.game_join){//加入
                //获取客户端套接字
                Channel joinChannel = serverHandler.getKey(Application.getInstance.clients, dataPackage.userInfo.getUser_id());

                //加入房间
                for (Room i:Application.getInstance.rooms) {
                    if(i.roomId == info.roomNumber){
                        if(i.players.containsKey(dataPackage.userInfo.getUser_id())){
                            i.players.put(dataPackage.userInfo.getUser_id(),joinChannel);

                            //返回在房间的人
                            //joinChannel.writeAndFlush();
                        }
                    }
                }
            }
            else if(info.requestType == GameType.game_quit){//离开
                //加入房间
                //获取客户端套接字
                Channel quitChannel = serverHandler.getKey(Application.getInstance.clients, dataPackage.userInfo.getUser_id());

                //加入房间
                for (Room i:Application.getInstance.rooms) {
                    if(i.roomId == info.roomNumber){
                        if(i.players.size() < 2){
                            Application.getInstance.rooms.remove(i);
                        }else {
                            i.players.remove(dataPackage.userInfo.getUser_id(),quitChannel);
                        }
                    }
                }
            }
        }

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
