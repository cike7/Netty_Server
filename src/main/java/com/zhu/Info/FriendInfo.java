package com.zhu.Info;

import java.util.ArrayList;
import java.util.List;

public class FriendInfo {
    private int friend_id;
    private String friend_name;
    private int friend_status;
    private int friend_attest;
    private String friend_remarks;
    private String friend_address;
    private String friend_sign;
    private int friend_sex;
    public List<Content> chat_message = new ArrayList<>();

    /**
     * id
     * @return
     */
    public int getFriend_id(){
        return this.friend_id;
    }
    public void setFriend_id(int id){
        this.friend_id = id;
    }

    /**
     * 在线状态
     * @return
     */
    public int getFriend_status(){
        return this.friend_status;
    }
    public void setFriend_status(int status){
        this.friend_status = status;
    }

    /**
     * 好友验证
     * @return
     */
    public int getFriend_attest(){
        return this.friend_attest;
    }
    public void setFriend_attest(int attest){
        this.friend_attest = attest;
    }

    /**
     * 备注
     * @return
     */
    public String getFriend_remarks(){
        return this.friend_remarks;
    }
    public void setFriend_remarks(String remarks){
        this.friend_remarks = remarks;
    }

    /**
     * 聊天记录
     * @return
     */
    public List<Content> getChat_Message(){
        return this.getChat_Message();
    }
    public void setChat_Message(List<Content> contents){
        this.chat_message = contents;
    }


    /**
     * 好友名字
     */
    public String getFriend_name(){
        return friend_name;
    }
    public void setFriend_name(String name){
        this.friend_name = name;
    }

    /**
     * 地区
     */
    public String getFriend_address(){
        return friend_address;
    }
    public void setFriend_address(String address){
        this.friend_address = address;
    }

    /**
     * 签名
     */
    public String getFriend_sign(){
        return friend_sign;
    }
    public void setFriend_sign(String sign){
        this.friend_sign = sign;
    }

    /**
     * 性别
     */
    public int getFriend_sex(){
        return friend_sex;
    }
    public void setFriend_sex(int sex){
        this.friend_sex = sex;
    }


    public static class Content
    {
        /// 0:自己 1:朋友
        public int type;

        public String msg;
    }

}
