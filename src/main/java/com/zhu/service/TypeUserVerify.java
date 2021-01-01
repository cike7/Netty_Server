package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.RequestType;
import com.zhu.Info.UserInfo;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

import java.io.UnsupportedEncodingException;

public class TypeUserVerify implements StreamData {
    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {
        if(dataPackage.requestType == RequestType.email_verify) {
            System.out.println("客户端邮箱验证提交" + new String(dataPackage.data));
            //判断验证码是否正确
            try {
                if (serverHandler.verifyCode.equals(Integer.valueOf(new String(dataPackage.data, "UTF-8")))) {
                    if (SqlSelectUser.getEnrollUser(dataPackage)) {
                        serverHandler.feedback.status = 1;
                    } else {
                        serverHandler.feedback.status = 0;
                    }

                    serverHandler.verifyCode = 0;
                } else {
                    serverHandler.feedback.status = 2;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String feedbackVerifyData = ServerFeedbackClientInfo.GetInstance().setDataInfo(new UserInfo(), serverHandler.feedback, RequestType.email_verify);
            System.out.println("反馈信息：" + feedbackVerifyData);
            serverHandler.channel.writeAndFlush(feedbackVerifyData);
        }
    }
}
