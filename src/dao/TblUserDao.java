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
	 * Lấy ra các đối tượng UserInfor thỏa mãn điều kiện tìm kiếm
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
	 * Lấy ra đối tượng UserInfor có id cho trước
	 * 
	 * @param id - ID của user cần lấy thông tin
	 * @return đối tượng UserInfor
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public UserInfor getUserInforById(int id) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy ra đối tượng TblUser theo loginName
	 * 
	 * @param userId - userId của đối tượng cần lấy
	 * @param loginName - loginName của đối tượng cần lấy
	 * @return đối tượng TblUser
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUser getUserByLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Lấy đối tượng TblUser theo email
	 * 
	 * @param userId - userId của đối tượng cần lấy
	 * @param email - email của đối tượng cần lấy
	 * @return đối tượng TblUser
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUser getUserByEmail(Integer userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Insert 1 đối tượng TblUser vào cơ sở dữ liệu
	 *
	 * @param tblUser - đối tượng TblUser cần insert
	 * @return userId - userId của đối tượng vừa được insert
	 * @throws SQLException 
	 */
	public Integer insertUser(TblUser tblUser) throws SQLException;

	/**
	 * Lấy ra đối tượng TblUser theo id
	 *
	 * @param userId - userId của đối tượng cần lấy
	 * @return đối tượng TblUser
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public TblUser getTblUserById(int userId) throws SQLException, ClassNotFoundException;
	
	/**
	 * Update đối tượng TblUser vào database
	 * 
	 * @param tblUser - object tbl_user
	 * @return boolean - true nếu update thành công | false nếu ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Integer updateUser(TblUser tblUser) throws ClassNotFoundException, SQLException;
	
	/**
	 * Xóa đối tượng TblUser trong database
	 * 
	 * @param userId - user_id của object cần xóa
	 * @return boolean - true nếu thành công | false nếu ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean deleteUser(int userId) throws ClassNotFoundException, SQLException;
	
}
