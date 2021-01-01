package com.zhu.Info;

public class UserInfo {

    private int user_id;
    private String user_account;
    private String user_password;
    private String user_name;
    private String user_phone;
    private int user_age;
    private int user_sex;
    private String user_address;
    private String user_sign;

    /**
     * ID
     * @return
     */
    public int getUser_id(){
        return user_id;
    }
    public void setUser_id(int id){
        this.user_id = id;
    }
    /**
     * 账号
     * @return
     */
    public String getUser_account(){
        return user_account;
    }
    public void setUser_account(String account){
        this.user_account = account;
    }
    /**
     * 密码
     * @return
     */
    public String getUser_password(){
        return user_password;
    }
    public void setUser_password(String password){
        this.user_password = password;
    }

    /**
     * 名字
     * @return
     */
    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String getUser_name){
        this.user_name = getUser_name;
    }


    /**
     * 手机
     * @return
     */
    public String getUser_Phone(){
        return user_phone;
    }
    public void setUser_phone(String phone){
        this.user_phone = phone;
    }
    /**
     * 性别
     * @return
     */
    public int  getUser_sex(){
        return user_sex;
    }
    public void setUser_sex(int  sex){
        this.user_sex = sex;
    }
    /**
     * 年龄
     * @return
     */
    public int getUser_age(){
        return user_age;
    }
    public void setUser_age(int age){
        this.user_age = age;
    }

    /**
     * 地区
     * @return
     */
    public String getUser_address(){
        return user_address;
    }
    public void setUser_address(String address){
        this.user_address = address;
    }

    /**
     * 签名
     * @return
     */
    public String getUser_sign(){
        return user_sign;
    }
    public void setUser_sign(String sign){
        this.user_sign = sign;
    }
}
