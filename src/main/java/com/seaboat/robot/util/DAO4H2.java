package com.seaboat.robot.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * <pre><b>DAO for h2. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class DAO4H2 {

	public static void crateTable(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		} finally {
			releaseConnection(conn, stmt, null);
		}
	}

	public static void insertQA(QA qa) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement("INSERT INTO qa (pattern,template) VALUES(?,?)");
			stmt.setString(1, qa.getOriPattern());
			stmt.setString(2, qa.getTemplate());
			stmt.execute();
		} finally {
			releaseConnection(conn, stmt, null);
		}
	}

	public static void clearQA() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement("delete  from qa");
			stmt.execute();
		} finally {
			releaseConnection(conn, stmt, null);
		}
	}

	public static void deleteQA(long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.prepareStatement("delete  from qa where id = " + id);
			stmt.execute();
		} finally {
			releaseConnection(conn, stmt, null);
		}
	}

	public static List<QA> getAllQA() throws SQLException {
		List<QA> list = new LinkedList<QA>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM QA ");
			while (rs.next()) {
				QA qa = new QA(rs.getString("pattern"), rs.getString("template"));
				qa.setId(rs.getLong("id"));
				list.add(qa);
			}
		} finally {
			releaseConnection(conn, stmt, null);
		}
		return list;
	}

	private static void releaseConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public static void main(String[] args) {
		String sql = "create table qa (id int(11) NOT NULL auto_increment,pattern varchar(50),template varchar(100),PRIMARY KEY (`id`))";
		try {
			DAO4H2.crateTable(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
