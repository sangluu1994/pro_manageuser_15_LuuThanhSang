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
	 * @return danh sách đối tượng MstJapan lấy từ bảng mst_japan
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException;
	
	/**
	 * Phương thức lấy trình độ tiếng Nhật theo codeLevel truyền vào
	 * 
	 * @param codeLevel - code_level của đối tượng mst_japan cần lấy
	 * @return đối tượng MstJapan (trình độ tiếng Nhật) cần lấy
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MstJapan getJpById(String codeLevel) throws SQLException, ClassNotFoundException;
	
}
