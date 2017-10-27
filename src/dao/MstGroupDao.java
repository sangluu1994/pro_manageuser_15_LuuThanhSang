/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupDao.java, 2017-10-25 luuthanhsang
 */
package dao;

import java.util.List;

import entity.MstGroup;

/**
 * Interface chứa các thao tác với bảng mst_group
 * 
 * @author luuthanhsang
 */
public interface MstGroupDao extends BaseDao {
	/**
	 * Lấy tất cả các nhóm
	 * 
	 * @return mảng các nhóm
	 */
	public List<MstGroup> getAllMstGroup();

	/**
	 * Lấy ra nhóm có id cho trước
	 * 
	 * @param id - mã id của nhóm cần tìm
	 * @return nhóm trong bảng mst_group | null nếu không tìm thấy
	 */
	public MstGroup getGroup(int id);
}
