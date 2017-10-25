/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserDao.java, 2017-10-25 luuthanhsang
 *
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entity.TblUser;
import entity.UserInfor;

/**
 * Interface chứa các thao tác với bảng tbl_user
 * 
 * @author luuthanhsang
 *
 */
public interface TblUserDao extends BaseDao {
	/**
	 * Lấy salt trong db
	 * 
	 * @param loginName - tài khoản cần lấy salt
	 * @return Chuỗi salt
	 */
	public String getSalt(String loginName);
	/**
	 * Lấy ra tổng số user thỏa mãn ID và tên cho trước
	 * 
	 * @param groupId - ID của nhóm
	 * @param fullName - họ tên user
	 * @return tổng số user
	 * @throws SQLException 
	 */
	public int getTotalUsers(int groupId, String fullName, String birthDay) throws SQLException;
	
	/**
	 * Phương thức lấy tất cả user có địa chỉ mail cho trước
	 * 
	 * @param mail - địa chỉ được tìm kiếm
	 * @return Đối tượng tblUser | null nếu không timg thấy
	 */
	TblUser getMail(int userId, String mail);
	
	/**
	 * Insert một bản ghi vào bảng tbl_user
	 * 
	 * @param tblUser - đối tượng tblUser cần insert
	 * @param connection - kết nối đến database
	 * @return boolean - true nếu insert thành công | false nếu insert thất bại
	 * @throws SQLException
	 */
	public boolean insertUser(TblUser tblUser, Connection connection) throws SQLException;
	
	/**
	 * Phương thức update một đối tượng trong bảng tbl_user
	 * 
	 * @param tblUser - đối tượng cần update
	 * @param connection - kết nối đến database
	 * @return boolean - true nếu update thành công | false nếu update không thành công
	 * @throws SQLException
	 */
	public boolean editUser(TblUser tblUser, Connection connection) throws SQLException;
	
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
	
	/**
	 * Phương thức xóa tblUser trong bảng tbl_user
	 * 
	 * @param tblUser - đối tượng cần xóa
	 * @param connection - kết nối đến database
	 * @return true nếu xóa thành công | flase nếu xóa không thành công
	 * @throws SQLException
	 */
	public boolean deleteUser(TblUser tblUser, Connection connection) throws SQLException;
}
