/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * BaseDaoImpl.java, 2017-10-25 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.BaseDao;
import properties.DatabaseProperties;

/**
 * Class cài đặt các phương thức thao tác cơ bản với cơ sở dữ liệu
 * 
 * @author luuthanhsang
 */
public class BaseDaoImpl implements BaseDao{

	/* (non-Javadoc)
	 * @see dao.BaseDao#getConnection()
	 */
	@SuppressWarnings("finally")
	@Override
	public Connection getConnection() {
		// khai báo, khởi tạo kết nối
		Connection conn = null;
		// khai báo các thông tin kết nối đến db
		String DB_URL = DatabaseProperties.getString("url");
		String USER_NAME = DatabaseProperties.getString("user");
		String PASS_WORD = DatabaseProperties.getString("password");
		String DRIVE = DatabaseProperties.getString("driver");
		try {
			// kết nối đến db
			Class.forName(DRIVE);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASS_WORD);
		} catch (SQLException e) {
			// show console log ngoại lệ
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// show console log ngoại lệ
			e.printStackTrace();
		} finally {
			// trả về kết nối
			return conn;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#close(java.sql.Connection)
	 */
	@Override
	public void close(Connection connection) {
		// kiểm tra connection
		if (connection != null) { // nếu khác null
			try {
				// đóng kết nối
				connection.close();
			} catch (SQLException e) {
				// show console log ngoại lệ
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#commit(java.sql.Connection)
	 */
	@Override
	public void commit(Connection connection) {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// commit query
				connection.commit();
			} catch (SQLException e) {
				// show console log ngoại lệ
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#rollback(java.sql.Connection)
	 */
	@Override
	public void rollback(Connection connection) {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// rollback query
				connection.rollback();
			} catch (SQLException e) {
				// show console log ngoại lệ
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#disableAutoCommit(java.sql.Connection)
	 */
	@Override
	public void disableAutoCommit(Connection connection) {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// set autocommit = false
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// show console log ngoại lệ
				e.printStackTrace();
			}
		}
	}

}
