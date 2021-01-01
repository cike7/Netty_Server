package com.zhu.Info;

import java.util.List;

public class DataPackage {
    /**
     * 用户账号
     */
    public UserInfo userInfo;
    /**
     * 服务端反馈状态
     */
    public int status;
    /**
     * 请求类型
     */
    public byte requestType;
    /**
     * 好友列表
     */
    public List<FriendInfo> friendList;
    /**
     * 请求好友信息
     */
    public int friendId;
    /**
     * 发送数据
     */
    public byte[] data;

}
