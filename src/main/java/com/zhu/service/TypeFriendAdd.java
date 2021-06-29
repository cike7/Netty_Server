package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.RequestType;
import com.zhu.Application;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

import java.io.UnsupportedEncodingException;

public class TypeFriendAdd implements StreamData {

    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {
        if(dataPackage.requestType == RequestType.friend_add){

            try {
                if (SqlSelectUser.getAddFriend(dataPackage.userInfo.getUser_id(), dataPackage.friendId, new String(dataPackage.data, "UTF-8"))) {
                    String addFriendData = ServerFeedbackClientInfo.GetInstance().setDataInfo(dataPackage.userInfo, dataPackage, RequestType.friend_add);

                    synchronized (Application.class) {
                        //好友如果在线直接发添加提醒
                        if (Application.getInstance.clients.containsValue(dataPackage.friendId)) {
                            //通过value得到key
                            serverHandler.getKey(Application.getInstance.clients, dataPackage.friendId).writeAndFlush(addFriendData);
                        }
                    }

                    System.out.println("反馈信息：" + addFriendData);

                } else {
                    System.out.println("用户请求添加好友失败");
                }
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
                System.err.println("字节转字符串异常" + e.getMessage());
            }

        }
    }
}
