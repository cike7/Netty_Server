package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.RequestType;
import com.zhu.Application;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

import java.io.UnsupportedEncodingException;

public class TypeUserChat implements StreamData {
    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {
        if(dataPackage.requestType == RequestType.chat) {

            synchronized (Application.class) {
                if (Application.getInstance.clients.containsValue(dataPackage.friendId)) {//好友在线直接发送
                    //设置聊天数据
                    String chatData = ServerFeedbackClientInfo.GetInstance().setDataInfo(dataPackage);
                    //通过value得到key
                    serverHandler.getKey(Application.getInstance.clients, dataPackage.friendId).writeAndFlush(chatData);

                } else {//好友离线存数据库
                    //给好友插入数据
                    try {
                        SqlSelectUser.getWriteFriendChatMessage(dataPackage.friendId, dataPackage.userInfo.getUser_id(), new String(dataPackage.data, "Utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        //e.printStackTrace();
                        System.err.println("字节转字符串异常" + e.getMessage());
                    }
                    System.out.println("好友id：" + dataPackage.friendId);

                }
            }

        }
    }
}
