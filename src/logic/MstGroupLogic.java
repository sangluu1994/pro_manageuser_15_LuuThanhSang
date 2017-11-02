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
}
