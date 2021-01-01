package com.zhu.sql;
import com.zhu.Info.DataPackage;
import com.zhu.Info.FriendInfo;
import com.zhu.Info.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询
 * @author chen
 *
 */
public class SqlSelectUser {

	private SqlSelectUser() { }

	public static void main(String[] args) {

//		UserInfo info = new UserInfo();
//
//		info.setUser_id(10002);
//		info.setUser_account("2053095395@qq.com");
//		info.setUser_password("123");

		//getQueryUserID(info);
		//getQueryFriendsList(info);
		//getQueryFriendInfo(info.getUser_id());
		//getQueryFriendChatMessage(10000,10001);

		//getWriteFriendChatMessage(10000,10001,"????");

		getFriendAuthentication(10001,10000,1);

	}

	/**
	 * 查询用户返回id
	 * @param user 用户信息
	 * @return
	 */
	public static int getQueryUserID(UserInfo user){
		int userID = 0;
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT id FROM `user` WHERE account=? and password=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setString(1, user.getUser_account());
			stmt.setString(2, user.getUser_password());
			//接收查询结果集
			rs = stmt.executeQuery();

			while (rs.next()) {
				userID = rs.getInt(1);
			}
//			System.out.println("查询结果>>> 用户ID:" + userID);
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return userID;
	}


	/**
	 * 查询用户返回id
	 * @param user 用户ID
	 * @return
	 */
	public static int getQueryUserID(String user){
		int userID = 0;
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT id FROM `user` WHERE account=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setString(1, user);
			//接收查询结果集
			rs = stmt.executeQuery();

			while (rs.next()) {
				userID = rs.getInt(1);
			}

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return userID;
	}

	/**
	 * 查询用户信息
	 * @param user 用户信息
	 * @return
	 */
	public static void getQueryUserInfo(UserInfo user){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT name,phone,age,sex,address,sign FROM `user` WHERE id=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, user.getUser_id());
			//接收查询结果集
			rs = stmt.executeQuery();

			while (rs.next()) {
				user.setUser_name(rs.getString(1));
				user.setUser_phone(rs.getString(2));
				user.setUser_age(rs.getInt(3));
				user.setUser_sex(rs.getInt(4));
				user.setUser_address(rs.getString(5));
				user.setUser_sign(rs.getString(6));
//				System.out.println("查询结果>>> 用户信息" + rs.getString(1) + "\b" +
//					rs.getLong(2)+ "\b" +
//					rs.getInt(3)+ "\b" +
//					rs.getString(4)+ "\b" +
//					rs.getString(5)+ "\b" +
//					rs.getString(6));
			}
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}

	/**
	 * 得到好友列表
	 * @param info 查询ID
	 * @return
	 */
	public static List<FriendInfo> getQueryFriendsList(UserInfo info){
		//通讯录列表ID
		List<Integer> friendIdList = new ArrayList<>();
		//通讯录列表信息
		List<FriendInfo> friendInfoList = new ArrayList<>();
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			//建立数据库连接
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句(查询表同个ID所有好友的ID)
			String sql = "SELECT guestId FROM `relation` WHERE hostId=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, info.getUser_id());
			//接收查询结果集
			rs = stmt.executeQuery();

			//查询所有列
			while (rs.next()) {
				friendIdList.add((int)rs.getLong(1));
			}

			for (int i = 0; i < friendIdList.size(); i++) {
//				System.out.println("查询结果>>>好友"+i+" ID:" + friendIdList.get(i));
				//查询好友信息
				friendInfoList.add(getQueryFriendInfo(info.getUser_id(),friendIdList.get(i)));
			}
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

		return friendInfoList;
	}

	/**
	 * 查询好友信息
	 * @param hostId
	 * @param guestId
	 */
	public static FriendInfo getQueryFriendInfo(int hostId,int guestId){
		FriendInfo friendInfo = new FriendInfo();
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT relation.active, relation.attest, relation.remarks, user.name,user.sex,user.address,user.sign FROM `relation`,`user` WHERE hostId=? AND guestId=? AND relation.guestId=user.id";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, hostId);
			// 设置查询参数
			stmt.setLong(2, guestId);
			//接收查询结果集
			rs = stmt.executeQuery();
			while (rs.next()) {
				friendInfo.setFriend_id(guestId);
				friendInfo.setFriend_status(rs.getInt(1));
				friendInfo.setFriend_attest(rs.getInt(2));
				friendInfo.setFriend_remarks(rs.getString(3));
				friendInfo.setFriend_name(rs.getString(4));
				friendInfo.setFriend_sex(rs.getInt(5));
				friendInfo.setFriend_address(rs.getString(6));
				friendInfo.setFriend_sign(rs.getString(7));
				//获得好友聊天记录
				friendInfo.setChat_Message(getQueryFriendChatMessage(hostId,guestId));
			}
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return friendInfo;
	}


	/**
	 * 查询好友聊天记录
	 * @param hostId
	 * @param guestId
	 */
	public static List<FriendInfo.Content> getQueryFriendChatMessage(int hostId,int guestId){
		List<FriendInfo.Content> messages = new ArrayList<>();
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT type,message FROM `chat_record` WHERE hostId=? AND guestId=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, hostId);
			// 设置查询参数
			stmt.setLong(2, guestId);
			//接收查询结果集
			rs = stmt.executeQuery();
			while (rs.next()) {

				FriendInfo.Content content = new FriendInfo.Content();
				content.type = rs.getInt(1);
				content.msg = rs.getString(2);

				messages.add(content);
			}

			//删除以查询记录
			deleteFriendChatMessage(hostId,guestId);

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return messages;
	}

	/**
	 * 删除聊天记录
	 * @param hostId
	 * @param guestId
	 */
	public static void deleteFriendChatMessage(int hostId,int guestId) {
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;

		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "DELETE FROM chat_record WHERE hostId=? AND guestId=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql);
			//设置查询参数
			stmt.setLong(1, hostId);
			stmt.setLong(2, guestId);
			//执行sql语句
			stmt.executeUpdate();

			//释放连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

	}

	/**
	 * 插入离线聊天记录
	 * @param hostId
	 * @param guestId
	 */
	public static void getWriteFriendChatMessage(int hostId,int guestId,String msg){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "INSERT INTO `chat_record`(hostId,guestId,type,message) VALUES(?,?,1,?)";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, hostId);
			// 设置查询参数
			stmt.setLong(2, guestId);
			// 设置查询参数
			stmt.setString(3, msg);
			//接收查询结果集
			stmt.executeUpdate();
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}

	/**
	 * 用户注册
	 */
	public static boolean getEnrollUser(DataPackage DataPackage){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "INSERT INTO user(ACCOUNT,PASSWORD,NAME) VALUES(?,?,?)";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setString(1, DataPackage.userInfo.getUser_account());
			// 设置查询参数
			stmt.setString(2, DataPackage.userInfo.getUser_password());
			// 设置查询参数
			stmt.setString(3, DataPackage.userInfo.getUser_name());

			//接收查询结果集
			stmt.executeUpdate();

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			//throwable.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 查找好友id
	 */
	public static FriendInfo getSearchFriendInfo(Integer friendId){
		FriendInfo friendInfo = new FriendInfo();
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;
		//接收查询结果集
		ResultSet rs = null;
		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "SELECT id,name,sex,address,sign FROM `user` WHERE id=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置查询参数
			stmt.setLong(1, friendId);
			// 设置查询参数
			//接收查询结果集
			rs = stmt.executeQuery();
			while (rs.next()) {
				friendInfo.setFriend_id(rs.getInt(1));
				friendInfo.setFriend_name(rs.getString(2));
				friendInfo.setFriend_sex(rs.getInt(3));
				friendInfo.setFriend_address(rs.getString(4));
				friendInfo.setFriend_sign(rs.getString(5));

			}
			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return friendInfo;
	}

	/**
	 * 添加好友
	 * @param hostId
	 */
	public static boolean getAddFriend(int hostId,int friendId,String remarks){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;

		try {
			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "INSERT INTO relation(hostId,guestId,attest,remarks) VALUES(?,?,0,?)";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 将自己添加为对方好友等待好友认证
			stmt.setInt(1, friendId);
			// 设置查询参数
			stmt.setInt(2, hostId);

			stmt.setString(3,remarks);

			//接收查询结果集
			stmt.executeUpdate();

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			//throwable.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 好友认证
	 * @param hostId
	 * @param friendId
	 * @param status
	 */
	public static boolean getFriendAuthentication(int hostId,int friendId,int status){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;

		try {
			conn = SqlDataSource.getInstance().getConnection();

			if(status == 1) {
				//sql查询语句
				final String sql = "INSERT INTO relation(hostId,guestId,attest) VALUES(?,?,1)";
				//将sql 发送给数据库进行编译
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				//同步更新好友的认证状态
				getUpdateFriendAttest(1,hostId,friendId);
			}
			else if(status == 0){
				//sql查询语句
				final String sql = "DELETE FROM relation WHERE guestId=? AND hostId=?";
				//将sql 发送给数据库进行编译
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			}

			// 设置查询参数
			stmt.setInt(1, friendId);
			// 设置查询参数
			stmt.setInt(2, hostId);

			//执行
			stmt.executeUpdate();

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
//			throwable.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 更新好友状态
	 * @param hostId
	 * @param friendId
	 */
	public static void getUpdateFriendAttest(int status,int hostId,int friendId){
		//预处理语句
		PreparedStatement stmt = null;
		//建立数据库连接
		Connection conn = null;

		try {

			conn = SqlDataSource.getInstance().getConnection();
			//sql查询语句
			final String sql = "UPDATE relation SET attest=? WHERE hostId=? AND guestId=?";
			//将sql 发送给数据库进行编译
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// 设置查询参数
			stmt.setInt(1, status);
			// 设置查询参数
			stmt.setInt(2, hostId);
			// 设置查询参数
			stmt.setInt(3, friendId);

			//执行
			stmt.executeUpdate();

			//清理数据库连接
			conn.close();
			stmt.close();
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}
}
