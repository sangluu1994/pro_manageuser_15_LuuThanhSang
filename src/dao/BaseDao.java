/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * BaseDao.java, 2017-10-25 luuthanhsang
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface chứa các phương thức thao tác cơ bản với cơ sở dữ liệu
 * 
 * @author luuthanhsang
 */
public interface BaseDao {
	/**
	 * Tạo kết nối đến CSDL
	 * 
	 * @return kết nối
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException;

	/**
	 * Đóng kết nối CSDL
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void close(Connection connection) throws SQLException;
	
	/**
	 * Khởi tạo kết nối đến CSDL khi làm việc có transaction
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void connectDB() throws SQLException, ClassNotFoundException;
	
	/**
	 * Disable autocommit
	 * 
	 * @throws SQLException
	 */
	public void disableAutoCommit() throws SQLException;

	/**
	 * Phương thức commit một Connection.
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException;

	/**
	 * Phương thức rollback bất kì thay đổi nào được thực hiện bởi Connection.
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException;

	/**
	 * Đóng kết nối đến CSDL khi làm việc có transaction
	 *
	 * @throws SQLException
	 */
	public void closeDB() throws SQLException;
	
}
