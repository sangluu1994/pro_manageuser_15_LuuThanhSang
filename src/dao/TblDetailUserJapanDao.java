/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblDetailUserJapan.java, 2017-11-02 luuthanhsang
 */
package dao;

import java.sql.SQLException;

import entity.TblDetailUserJapan;

/**
 * Interface xử lí thao tác với bảng tbl_detail_user_japan
 *
 * @author luuthanhsang
 */
public interface TblDetailUserJapanDao extends BaseDao {

	/**
	 * Inser thông tin chi tiết của user vào bảng tbl_detail_user_japan
	 * 
	 * @param tblDetailUserJapan - đối tượng cần lưu
	 * @return boolean - true: ghi thành công | false: ghi không thành công
	 * @throws SQLException 
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;

}
