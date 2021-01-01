package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.RequestType;
import com.zhu.Info.UserInfo;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;
import com.zhu.util.EmailUtils;

public class TypeUserRegister implements StreamData {
    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {

        if(dataPackage.requestType == RequestType.account_register) {
            System.out.println("客户端请求注册");

            int getUserID = SqlSelectUser.getQueryUserID(dataPackage.userInfo.getUser_account());

            if (getUserID != 0) {//用户存在不能进行注册
                System.out.println("用户存在");
                serverHandler.feedback.status = 0;

            } else {//用户不存在
                System.out.println("用户不存在");
                serverHandler.feedback.status = 1;
                serverHandler.verifyCode = EmailUtils.sendEmail(dataPackage.userInfo.getUser_account());
            }

            String feedbackRegisterData = ServerFeedbackClientInfo.GetInstance().setDataInfo(new UserInfo(), serverHandler.feedback, RequestType.account_register);
            System.out.println("反馈信息：" + feedbackRegisterData);
            serverHandler.channel.writeAndFlush(feedbackRegisterData);
        }
    }
}
