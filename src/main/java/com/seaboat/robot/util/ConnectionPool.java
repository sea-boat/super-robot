package com.seaboat.robot.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

/**
 * 
 * <pre><b>database pool for h2. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class ConnectionPool {

	static String PATH = "./resources/bot.db";
	private static JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:" + PATH, "sa", "");
	static {
		pool.setLoginTimeout(10000);
		pool.setMaxConnections(10);
	}

	public static Connection getConnection() throws SQLException {
		return pool.getConnection();
	}
}
