package com.zhu.Info;

/**
 * 请求类型
 * @author chen
 *
 */
public class RequestType {
	
	public static void main(String[] args) {
//        Gson gson = new Gson();
//
//        DataPackage2 dataPackage = new DataPackage2();
//        dataPackage.requestType = RequestType.account_login;
//
//        JsonObject json = new JsonObject();
//        json.addProperty("user","123");
//        json.addProperty("password","abc");
//
//        byte[] str = new byte[1024];
//
//		try {
//	        dataPackage.data = AesUtil.encrypt(json.toString().getBytes("utf-8"));
//			str = gson.toJson(dataPackage,DataPackage2.class).getBytes("utf-8");
//
//			System.out.println("原数据"+json);
//			System.out.println("加密后：" +new String(str,"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Gson gson2 = new Gson();
//
//		try {
//
//			DataPackage2 dataPackage2 = gson2.fromJson(new String(str,"utf-8"), DataPackage2.class);
//
//			JsonParser parser = new JsonParser();
//
//			JsonObject json1 = (JsonObject) parser.parse(new String(AesUtil.decrypt(dataPackage2.data),"utf-8"));
//
//			System.out.println("解密后："+json1);
//	        System.out.println("用户:"+json1.get("user"));
//	        System.out.println("秘密:"+json1.get("password"));
//
//		} catch (JsonSyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 聊天
	 */
	public static final byte chat = 1;
	
	/**
	 * 电话请求
	 */
	public static final byte phone_start = 2;
	
	/**
	 * 挂断电话
	 */
	public static final byte phone_stop = 3;
	
	/**
	 * 添加好友
	 */
	public static final byte friend_add= 4;
	
	/**
	 * 删除好友
	 */
	public static final byte friend_delete = 5;
	
	/**
	 * 登入
	 */
	public static final byte account_login = 6;
	
	/**
	 * 注册
	 */
	public static final byte account_register = 7;
	
	/**
	 * 销户
	 */
	public static final byte account_destroy = 8;
	
	/**
	 * 修改
	 */
	public static final byte account_alter = 9;
	
	/**
	 * 天气
	 */
	public static final byte weather = 10;
	
    /**
     * 邮箱验证
     */
    public static final byte email_verify = 11;

	/**
	 * 查找好友
	 */
	public static final byte friend_search = 12;

	/**
	 * 好友认证
	 */
	public static final byte friend_attest = 13;

	/**
	 * 游戏
	 */
	public static final byte game = 14;

}