/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserDao.java, 2017-10-25 luuthanhsang
 */
package dao;

import java.sql.SQLException;
import java.util.List;

import entity.TblUser;
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
	 * @throws ClassNotFoundException
	 */
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException;
	
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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy ra user có id cho trước
	 * 
	 * @param id - ID của user cần lấy thông tin
	 * @return đối tượng getUsersById | null nếu không tìm thấy
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public UserInfor getUsersById(int id) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy thông tin của user theo loginName
	 * 
	 * @param userId
	 * @param loginName
	 * @return đối tượng TblUser
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUser getUserByLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Lấy thông tin của user theo email
	 * 
	 * @param userId
	 * @param email
	 * @return đối tượng TblUser
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUser getUserByEmail(Integer userId, String email) throws ClassNotFoundException, SQLException;
	
}
