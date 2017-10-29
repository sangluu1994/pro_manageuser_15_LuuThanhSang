/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserDao.java, 2017-10-25 luuthanhsang
 */
package dao;

import java.sql.SQLException;
import java.util.List;

import entity.UserInfor;

/**
 * Interface chứa các thao tác với bảng tbl_user
 * 
 * @author luuthanhsang
 */
public interface TblUserDao extends BaseDao {
	/**
	 * Lấy ra tổng số user thỏa mãn groupId và tên cho trước
	 * 
	 * @param groupId - ID của nhóm
	 * @param fullName - họ tên user
	 * @return tổng số user
	 * @throws SQLException 
	 */
	public int getTotalUsers(int groupId, String fullName) throws SQLException;
	
	/**
	 * Lấy ra các user thỏa mãn điều kiện tìm kiếm
	 * 
	 * @param offset - vị trí lấy bản ghi
	 * @param limit - giới hạn số bản ghi trên 1 trang
	 * @param groupId - ID của nhóm
	 * @param fullName - họ tên đầy đủ
	 * @param sortType - kiểu sort
	 * @param sortByFullName - giá trị sort theo tên
	 * @param sortByCodeLevel - giá trị sort theo codelevel
	 * @param sortByEndDate - giá trị sort theo enddate
	 * @return mảng các đối tượng UserInfo
	 */
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate);
	
	/**
	 * Lấy ra user có id cho trước
	 * 
	 * @param id - ID của user cần lấy thông tin
	 * @return đối tượng getUsersById | null nếu không tìm thấy
	 */
	public UserInfor getUsersById(int id);
	
}
