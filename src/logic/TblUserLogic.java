/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserLogic.java, 2017-10-26 luuthanhsang
 */
package logic;

import java.sql.SQLException;
import java.util.List;

import entity.TblDetailUserJapan;
import entity.TblUser;
import entity.UserInfor;

/**
 * Interface chứa các thao tác với bảng tbl_user
 *
 * @author luuthanhsang
 */
public interface TblUserLogic {
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
	 * Phương thức kiểm tra loginName đã tồn tại chưa?
	 * 
	 * @param userId - user_id cần kiểm tra
	 * @param loginName - tên đăng nhập cần kiểm tra
	 * @return boolean - true nếu tồn tại | false nếu ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExistedLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra email đã tồn tại trong bảng tbl_user chưa?
	 *
	 * @param userId - user_id cần kiểm tra
	 * @param email - email cần kiểm tra
	 * @return boolean - true nếu đã tồn tại | false nếu ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExistedEmail(Integer userId, String email) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức insert data  user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor - đối tượng cần insert
	 * @return boolean - true: insert thành công | false: insert không thành công
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean createUser(UserInfor userInfor) throws SQLException, ClassNotFoundException;

	/**
	 * Phương thức kiểm tra user có tồn tại trong cơ sở dữ liệu không
	 *
	 * @param userId - userId của đối tượng user cần kiểm tra
	 * @return boolean - true: có tồn tại | false: không tồn tại
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean isExistedUser(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy đối tượng UserInfor theo userId
	 *
	 * @param userId - userId của đối tượng cần lấy
	 * @return đối tượng UserInfor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức edit user
	 * 
	 * @param userInfor - đối tượng user cần edit
	 * @return boolean - true nếu thành công | false nếu ngược lại
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean editUser(UserInfor userInfor) throws ClassNotFoundException, SQLException;

	/**
	 * Xóa đối tượng userInfor khỏi database
	 *
	 * @param userId - userId của đối tượng cần xóa
	 * @return boolean - true nếu xóa thành công | false nếu ngược lại
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public boolean removeUser(int userId) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức change password
	 *
	 * @param userId - userId của đối tượng cần change password
	 * @param passWord - password cập nhật
	 * @return boolean - true nếu change password thành công | false nếu ngược lại
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean changePassword(int userId, String passWord) throws ClassNotFoundException, SQLException;
	
	/**
	 * Lấy TblDetailUserJapan từ UserInfor
	 *
	 * @param userInfor - object UserInfor
	 * @return tblDetailUserJapan - object TblDetailUserJapan
	 */
	public TblDetailUserJapan getTblDetailUserJapanFromUserInfor(UserInfor userInfor);
	
	/**
	 * Lấy TblUser từ UserInfor
	 *
	 * @param userInfor - object UserInfor
	 * @return tblUser - object TblUser
	 */
	public TblUser getTblUserFromUserInfor(UserInfor userInfor);
}
