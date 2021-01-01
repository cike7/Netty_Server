package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.FriendInfo;
import com.zhu.Info.RequestType;
import com.zhu.Info.UserInfo;
import com.zhu.server.Application;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

import java.util.List;

public class TypeFriendAttest implements StreamData {
    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {
        if(dataPackage.requestType == RequestType.friend_attest) {

            if (SqlSelectUser.getFriendAuthentication(dataPackage.userInfo.getUser_id(), dataPackage.friendId, dataPackage.status)) {

                serverHandler.feedback.friendList.clear();
                serverHandler.feedback.friendList = SqlSelectUser.getQueryFriendsList(dataPackage.userInfo);
                String hostAttestData = ServerFeedbackClientInfo.GetInstance().setDataInfo(dataPackage.userInfo, serverHandler.feedback, RequestType.friend_attest);
                System.out.println("好友认证结果，返回好友列表信息：" + hostAttestData);
                //反馈回自己
                serverHandler.channel.writeAndFlush(hostAttestData);

                //-------

                synchronized (Application.class) {
                    //好友如果在线直接发添加提醒
                    if (Application.getInstance.clients.containsValue(dataPackage.friendId)) {
                        UserInfo friendInfo = new UserInfo();
                        friendInfo.setUser_id(dataPackage.friendId);
                        List<FriendInfo> friendsList = SqlSelectUser.getQueryFriendsList(friendInfo);
                        String friendAttestData = ServerFeedbackClientInfo.GetInstance().setDataInfo(friendInfo, friendsList, RequestType.friend_attest);
                        //反馈回好友
                        serverHandler.getKey(Application.getInstance.clients, dataPackage.friendId).writeAndFlush(friendAttestData);
                    }
                }

            } else {
                //
                System.out.println("数据库写入发生意外");
            }

        }
    }
}
