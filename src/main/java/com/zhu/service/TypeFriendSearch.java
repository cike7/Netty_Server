package com.zhu.service;

import com.zhu.Info.DataPackage;
import com.zhu.Info.FriendInfo;
import com.zhu.Info.RequestType;
import com.zhu.server.ServerFeedbackClientInfo;
import com.zhu.server.ServerHandler;
import com.zhu.sql.SqlSelectUser;

public class TypeFriendSearch implements StreamData {
    @Override
    public void receiveData(ServerHandler serverHandler, DataPackage dataPackage) {
        if(dataPackage.requestType == RequestType.friend_search) {
            System.out.println("查找好友id" + dataPackage.friendId);

            FriendInfo friendId = SqlSelectUser.getSearchFriendInfo(dataPackage.friendId);

            String feedbackSearchData = "";
            if (friendId.getFriend_id() != 0) {
                serverHandler.feedback.status = 1;
                feedbackSearchData = ServerFeedbackClientInfo.GetInstance().setSearchDataInfo(friendId, serverHandler.feedback, RequestType.friend_search);

            } else {
                serverHandler.feedback.status = 0;
                feedbackSearchData = ServerFeedbackClientInfo.GetInstance().setSearchDataInfo(new FriendInfo(), serverHandler.feedback, RequestType.friend_search);
            }

            System.out.println("反馈信息：" + feedbackSearchData);
            serverHandler.channel.writeAndFlush(feedbackSearchData);

        }
    }
}
