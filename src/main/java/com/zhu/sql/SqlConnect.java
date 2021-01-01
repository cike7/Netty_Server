package com.zhu.sql;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 连接数据库
 * @author chen
 *
 */
public class SqlConnect {

	// 数据库驱动
	private static final String DRIVELERS;
	// 数据库链接url
	private static final String URL;
	// 用户名
	private static final String USER;
	// 密码
	private static final String PWD;
	// 从配置文件初始化参数
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		DRIVELERS = bundle.getString("DRIVERCLASS");
		URL = bundle.getString("URL");
		USER = bundle.getString("USER");
		PWD = bundle.getString("PWD");
	}

	/**
	 * 建立并返回链接
	 *
	 * @return 数据库链接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 加载驱动
		loadDriver();
		// 返回数据库链接
		return DriverManager.getConnection(URL, USER, PWD);
	}

	// 装载驱动
	private static void loadDriver() throws ClassNotFoundException {
		// 利用反射，避免二次加载驱动
		Class.forName(DRIVELERS);
	}

	/**
	 *
	 * 释放资源
	 *
	 * @param rs
	 *            需要关闭的ResultSet
	 * @param stmt
	 *            需要关闭的Statement
	 * @param conn
	 *            需要关闭的Connection
	 */
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}

		release(stmt, conn);
	}

	/**
	 * 释放资源
	 *
	 * @param stmt
	 *            需要关闭的Statement
	 * @param conn
	 *            需要关闭的Connection
	 */
	public static void release(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
