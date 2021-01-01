package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.RequestType;
import com.zhu.server.Application;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

public class TypeUserLogin implements StreamData {

    //转发数据包
    DataPackage feedback = new DataPackage();

    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {

        if(dataPackage.requestType == RequestType.account_login) {
            System.out.println("客户端请求登录");
            int userID = SqlSelectUser.getQueryUserID(dataPackage.userInfo);

            if (userID != 0) {//用户存在
                dataPackage.userInfo.setUser_id(userID);
                feedback.status = 1;
                //完善用户信息
                SqlSelectUser.getQueryUserInfo(dataPackage.userInfo);
                //得到好友名单
                feedback.friendList = SqlSelectUser.getQueryFriendsList(dataPackage.userInfo);

                synchronized (Application.class) {
                    Application.getInstance.clients.put(serverHandler.channel, userID);
                }

                //需要登入才能开启相关服务
                loginSuccessfulStartedService(serverHandler);

            } else {//用户不存在
                feedback.status = 0;
            }

            String feedbackLoginData = ServerFeedbackClientInfo.GetInstance().setDataInfo(dataPackage.userInfo, feedback, RequestType.account_login);
            System.out.println("反馈信息：" + feedbackLoginData);
            serverHandler.channel.writeAndFlush(feedbackLoginData);
        }

    }

    /**
     * 需要登入才能开启相关服务
     * @param serverHandler
     */
    private void loginSuccessfulStartedService(ServerHandler serverHandler){

        serverHandler.relatedService.add(new TypeUserAlter());
        serverHandler.relatedService.add(new TypeUserChat());


        serverHandler.relatedService.add(new TypeFriendAdd());
        serverHandler.relatedService.add(new TypeFriendAttest());
        serverHandler.relatedService.add(new TypeFriendSearch());

    }
}
