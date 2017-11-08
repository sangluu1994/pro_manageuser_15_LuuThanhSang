/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * BaseDaoImpl.java, 2017-10-25 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	// Khai báo các đối tượng khi làm việc với transaction
	protected static Connection connection = null; 
	protected static PreparedStatement preparedStatement = null;
	protected static ResultSet resultSet = null;

	/* (non-Javadoc)
	 * @see dao.BaseDao#getConnection()
	 */
	@SuppressWarnings("finally")
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		// khai báo, khởi tạo kết nối
		Connection con = null;
		// khai báo các thông tin kết nối đến db
		String DB_URL = DatabaseProperties.getValue(Constant.URL);
		String USER_NAME = DatabaseProperties.getValue(Constant.USERNAME);
		String PASS_WORD = DatabaseProperties.getValue(Constant.PASSWORD);
		String DRIVE = DatabaseProperties.getValue(Constant.DRIVER);
		try {
			// kết nối đến db
			Class.forName(DRIVE);
			con = DriverManager.getConnection(DB_URL, USER_NAME, PASS_WORD);
		} catch(ClassNotFoundException e) {
			throw new ClassNotFoundException();
		} catch(SQLException sqlException) {
			throw new SQLException();
		} finally {
			// trả về kết nối
			return con;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#close(java.sql.Connection)
	 */
	@Override
	public void close(Connection con) throws SQLException {
		// kiểm tra connection
		if (con != null) { // nếu khác null
			try {
				con.close();
			} catch(SQLException e) {
				throw new SQLException();
			}
		}
	}

	

	/* (non-Javadoc)
	 * @see dao.BaseDao#connectDB()
	 */
	@Override
	public void connectDB() throws SQLException, ClassNotFoundException {
		connection = getConnection();
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#disableAutoCommit()
	 */
	@Override
	public void disableAutoCommit() throws SQLException {
		connection.setAutoCommit(false);
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#commit()
	 */
	@Override
	public void commit() throws SQLException {
		connection.commit();
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		connection.rollback();
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#closeDB()
	 */
	@Override
	public void closeDB() throws SQLException {
		close(connection);
	}

}
