package com.zhu.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhu.Info.DataPackage;
import com.zhu.Info.FriendInfo;
import com.zhu.Info.RequestType;
import com.zhu.Info.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ServerFeedbackClientInfo {

    private ServerFeedbackClientInfo(){}

    private volatile static ServerFeedbackClientInfo info;

    /**
     * 字符串解析
     */
    private static Gson gson;

    /**
     * 用户信息
     */
    private static UserInfo nonentityUser;

    /**
     *
     * @return 反馈客户信息
     */
    public static ServerFeedbackClientInfo GetInstance(){
        if(info == null){
            synchronized (ServerFeedbackClientInfo.class){
                if (info == null){
                    info = new ServerFeedbackClientInfo();
                }

                if(gson==null){
                    //创建序列化予许为空，不然空的将不进行序列化，unity客户端解析会报错
                    gson = new GsonBuilder().serializeNulls().create();
                }

                if(nonentityUser == null){
                    nonentityUser = new UserInfo();
                }
            }
        }
        return info;
    }


    /**
     * 设置数据消息
     * @param user 用户信息
     * @param dataStream 数据包
     * @param requestType 请求类型
     * @return
     */
    public String setDataInfo(UserInfo user, DataPackage dataStream, byte requestType){
        dataStream.userInfo = user;
        dataStream.requestType = requestType;
        return gson.toJson(dataStream, DataPackage.class);
    }


    /**
     * 设置数据消息
     * @param user 用户信息
     * @param friendsList 好友列表
     * @param requestType 请求类型
     * @return
     */
    public String setDataInfo(UserInfo user, List<FriendInfo> friendsList, byte requestType){
        DataPackage _DataPackage = new DataPackage();
        _DataPackage.friendList = friendsList;
        _DataPackage.userInfo = user;
        _DataPackage.requestType = requestType;
        return gson.toJson(_DataPackage, DataPackage.class);
    }

    /**
     * 设置数据消息
     * @param user 用户信息
     * @param dataStream 数据包
     * @param requestType 请求类型
     * @return
     */
    public String setSearchDataInfo(FriendInfo user, DataPackage dataStream, byte requestType){
        dataStream.friendList = new ArrayList<>();
        dataStream.friendList.add(user);
        dataStream.requestType = requestType;
        return gson.toJson(dataStream, DataPackage.class);
    }


    /**
     * 设置数据消息
     * @param dataStream
     * @return
     */
    public String setDataInfo(DataPackage dataStream){
        nonentityUser.setUser_id(dataStream.friendId);
        DataPackage pack = new DataPackage();
        pack.userInfo = nonentityUser;
        pack.requestType = RequestType.chat;
        //朋友的id为发送者的id
        pack.friendId = dataStream.userInfo.getUser_id();
        pack.data = dataStream.data;
        return gson.toJson(pack, DataPackage.class);
    }
}
