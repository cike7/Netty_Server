package com.zhu.sql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class SqlDataSource implements DataSource {

    /**
     * 数据库连接数量
     */
    private static final int NUM = 20;

    /**
     * 保存数据库链接，当作循环队列使用
     */
    private LinkedList<Connection> dataSourceList = new LinkedList<Connection>();

    /**
     * 一次性创建 NUM 个数据库链接,放入队列中
     */
    private SqlDataSource() {
        for (int i = 0; i < NUM; i++) {
            Connection connection=null;
            try {
                connection = SqlConnect.getConnection();
                dataSourceList.add(connection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 内部类，初始化单例
     */
    private static class Holder {
        private static final SqlDataSource DATA_SOURCE = new SqlDataSource();
    }

    /**
     * 返回内部类实例化的单列
     *
     * @return 数据库连接池
     */
    public static SqlDataSource getInstance() {
        return Holder.DATA_SOURCE;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {

    }

    public void setLoginTimeout(int arg0) throws SQLException {

    }

    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

    /**
     * 1、取出第一个链接，利用代理将close（）改写 2、返回改写后的链接
     * 为什么改写：避免外界使用该链接时依旧使用原来的关闭方法，导致连接池的链接越来越少
     */
    public Connection getConnection() throws SQLException {
        System.out.println(dataSourceList.size()+"-->获取数据库连接");
        // 取出连接池中一个连接
        final Connection conn = dataSourceList.removeFirst(); // 删除第一个连接返回

        // 将目标Connection对象进行增强，更改close（）方法
        Connection connProxy = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(),
                conn.getClass().getInterfaces(), new InvocationHandler() {
                    // 执行代理对象任何方法 都将执行 invoke
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("close")) {
                            // 需要加强的方法
                            // 不将连接真正关闭，将连接放回连接池
                            releaseConnection(conn);
                            System.out.println("需要加强的方法");
                            return null;
                        } else {
                            // 不需要加强的方法
                            System.out.println("不需要加强的方法" + method.getName());
                            return method.invoke(conn, args); // 调用真实对象方法
                        }
                    }
                });
        // 返回代理后的链接对象
        return connProxy;
    }

    /**
     *  将连接放回连接池
     * @param conn
     */
    public void releaseConnection(Connection conn) {
        dataSourceList.add(conn);
    }

    public Connection getConnection(String arg0, String arg1) throws SQLException {
        return null;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
