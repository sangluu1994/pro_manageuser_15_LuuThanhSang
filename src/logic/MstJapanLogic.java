/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstJapanLogic.java, 2017-11-02 luuthanhsang
 */
package logic;

import java.sql.SQLException;
import java.util.List;
import entity.MstJapan;

/**
 * Interface xử lí logic cho các chức năng liên quan đến mst_japan
 *
 * @author luuthanhsang
 */
public interface MstJapanLogic {
	/**
	 * Phương thức lấy tất cả danh sách trình độ tiếng Nhật của bảng mst_japan
	 * 
	 * @return danh sách tất cả trình độ tiếng Nhật của bảng mst_japan
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức kiểm tra tính tồn tại của trình độ tiếng Nhật nhập vào
	 * 
	 * @param codeLevel - code_level cần kiểm tra
	 * @return boolean - true nếu đã tồn tại | false nếu ngược lại
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean isRealJpLv(String codeLevel) throws ClassNotFoundException, SQLException;
}
