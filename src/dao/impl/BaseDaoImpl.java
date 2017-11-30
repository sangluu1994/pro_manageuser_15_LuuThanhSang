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
import common.LogFile;
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
			// trả về kết nối
			return con;
		} catch (ClassNotFoundException e) {
			LogFile.warning("Error in dao.impl.BaseDaoImpl#getConnection: " + e.getMessage());
			throw e;
		} catch (SQLException sqlException) {
			LogFile.warning("Error in dao.impl.BaseDaoImpl#getConnection: " + sqlException.getMessage());
			throw sqlException;
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
				LogFile.warning("[DAO] Error in dao.impl.BaseDaoImpl#close: " + e.getMessage());
				throw e;
			}
		}
	}

	

	/* (non-Javadoc)
	 * @see dao.BaseDao#connectDB()
	 */
	@Override
	public void startTransaction() throws SQLException, ClassNotFoundException {
		connection = getConnection();
		connection.setAutoCommit(false);
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#commit()
	 */
	@Override
	public void commit() throws SQLException {
		try {
			connection.commit();
		} catch (SQLException e) {
			LogFile.warning("Error in dao.impl.BaseDaoImpl#commit: " + e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			LogFile.warning("Error in dao.impl.BaseDaoImpl#rollback: " + e.getMessage());
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#closeDB()
	 */
	@Override
	public void endTransaction() throws SQLException {
		close(connection);
		connection = null;
	}

}
