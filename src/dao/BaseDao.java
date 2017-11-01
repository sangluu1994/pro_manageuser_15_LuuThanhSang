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
	public Connection getConnection() throws SQLException;

	/**
	 * Đóng kết nối CSDL
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void close(Connection connection) throws SQLException;

//	/**
//	 * Phương thức commit một Connection.
//	 * 
//	 * @param connection - Connection cần commit và đóng.
//	 * @throws SQLException
//	 */
//	public void commit(Connection connection) throws SQLException;
//
//	/**
//	 * Phương thức rollback bất kỳ thay đổi nào được thực hiện bởi Connection.
//	 * 
//	 * @param connection - Connection muốn rollback.
//	 * @throws SQLException
//	 */
//	public void rollback(Connection connection) throws SQLException;
//
//	/**
//	 * Disable autocommit
//	 * 
//	 * @param connection - Connection cần disable.
//	 * @throws SQLException
//	 */
//	public void disableAutoCommit(Connection connection) throws SQLException;
//	
}
