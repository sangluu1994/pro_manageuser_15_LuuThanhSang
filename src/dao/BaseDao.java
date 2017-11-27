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
	 * Khởi tạo kết nối đến CSDL khi làm việc có thao tác với transaction
	 *
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void startTransaction() throws SQLException, ClassNotFoundException;
	
	/**
	 * Lưu các thay đổi vào CSDL
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException;

	/**
	 * Quay lui, xóa bỏ các thay đổi chưa commit
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException;

	/**
	 * Đóng kết nối đến CSDL khi làm việc có thao tác với transaction
	 *
	 * @throws SQLException
	 */
	public void endTransaction() throws SQLException;
	
}
