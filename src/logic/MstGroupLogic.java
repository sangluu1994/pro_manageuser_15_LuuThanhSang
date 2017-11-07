/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupLogic.java, 2017-10-26 luuthanhsang
 */
package logic;

import java.sql.SQLException;
import java.util.List;
import entity.MstGroup;

/**
 * Interface chứa các phương thức thao tác với bảng mst_group
 *
 * @author luuthanhsang
 */
public interface MstGroupLogic {
	/**
	 * Phương thức lấy tất cả các nhóm trong bảng mst_group
	 * 
	 * @return danh sách đối tượng tất cả các nhóm trong bảng mst_group
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<MstGroup> getAllMstGroups() throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức kiểm tra group nhập vào có tồn tại trong bảng MstGroup không
	 * @param groupId - group_id cần kiểm tra
	 * @return boolean - true nếu đã tồn tại | false nếu ngược lại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean isRealGroup(int groupId) throws SQLException, ClassNotFoundException;

	/**
	 * Lấy ra nhóm có id cho trước
	 * 
	 * @param id - mã id của nhóm cần tìm
	 * @return nhóm trong bảng mst_group | null nếu không tìm thấy
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MstGroup getGroupById(int id) throws SQLException, ClassNotFoundException;
	
}
