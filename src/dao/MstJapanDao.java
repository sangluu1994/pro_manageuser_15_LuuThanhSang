/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstJapanDao.java, 2017-10-25 luuthanhsang
 */
package dao;

import java.sql.SQLException;
import java.util.List;
import entity.MstJapan;

/**
 * Interface chứa các thao tác với bảng mst_japan
 * 
 * @author luuthanhsang
 */
public interface MstJapanDao extends BaseDao {
	/**
	 * Phương thức lấy tất cả danh sách trình độ tiếng Nhật của bảng mst_japan
	 * 
	 * @return danh sách tất cả trình độ tiếng Nhật của bảng mst_japan
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException;
}
