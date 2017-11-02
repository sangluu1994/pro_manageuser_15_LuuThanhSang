/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * BaseDaoImpl.java, 2017-10-25 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import common.Constant;
import dao.BaseDao;
import properties.DatabaseProperties;

/**
 * Class cài đặt các phương thức thao tác cơ bản với cơ sở dữ liệu
 * 
 * @author luuthanhsang
 */
public class BaseDaoImpl implements BaseDao {

	/* (non-Javadoc)
	 * @see dao.BaseDao#getConnection()
	 */
	@SuppressWarnings("finally")
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		// khai báo, khởi tạo kết nối
		Connection connection = null;
		// khai báo các thông tin kết nối đến db
		String DB_URL = DatabaseProperties.getValue(Constant.URL);
		String USER_NAME = DatabaseProperties.getValue(Constant.USERNAME);
		String PASS_WORD = DatabaseProperties.getValue(Constant.PASSWORD);
		String DRIVE = DatabaseProperties.getValue(Constant.DRIVER);
		try {
			// kết nối đến db
			Class.forName(DRIVE);
			connection = DriverManager.getConnection(DB_URL, USER_NAME, PASS_WORD);
		} catch(ClassNotFoundException e) {
			throw new ClassNotFoundException();
		} catch(SQLException sqlException) {
			throw new SQLException();
		} finally {
			// trả về kết nối
			return connection;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#close(java.sql.Connection)
	 */
	@Override
	public void close(Connection connection) throws SQLException {
		// kiểm tra connection
		if (connection != null) { // nếu khác null
			try {
				connection.close();
			} catch(SQLException e) {
				throw new SQLException();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#commit(java.sql.Connection)
	 */
	@Override
	public void commit(Connection connection) throws SQLException {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// commit query
				connection.commit();
			} catch(SQLException e) {
				throw new SQLException();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#rollback(java.sql.Connection)
	 */
	@Override
	public void rollback(Connection connection) throws SQLException {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// rollback query
				connection.rollback();
			} catch(SQLException e) {
				throw new SQLException();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#disableAutoCommit(java.sql.Connection)
	 */
	@Override
	public void disableAutoCommit(Connection connection) throws SQLException {
		// kiểm tra connection
		if (connection != null) { // nếu connection khác null
			try {
				// set autocommit = false
				connection.setAutoCommit(false);
			} catch(SQLException e) {
				throw new SQLException();
			}
		}
	}

}
