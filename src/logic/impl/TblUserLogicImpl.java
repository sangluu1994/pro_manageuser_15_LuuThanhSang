/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import common.Common;
import common.Constant;
import dao.BaseDao;
import dao.TblDetailUserJapanDao;
import dao.TblUserDao;
import dao.impl.BaseDaoImpl;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entity.TblDetailUserJapan;
import entity.TblUser;
import entity.UserInfor;
import logic.TblUserLogic;
import properties.AdminProperties;

/**
 * Class thao tác với bảng tbl_user
 *
 * @author luuthanhsang
 */
public class TblUserLogicImpl implements TblUserLogic {
	private TblUserDao tblUserDao;
	private BaseDao baseDao;
	private TblDetailUserJapanDao tblDetailUserJapanDao;
	/**
	 * Constructor
	 */
	public TblUserLogicImpl() {
		tblUserDao = new TblUserDaoImpl();
		baseDao = new BaseDaoImpl();
		tblDetailUserJapanDao = new TblDetailUserJapanDaoImpl();
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
		return tblUserDao.getTotalUsers(groupId, fullName);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException {
		return tblUserDao.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#checkExistedLoginName(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException {
		if (AdminProperties.getValue(Constant.ADMIN_USER).equals(loginName)) {
			return true;
		} else {
			TblUser tblUser = tblUserDao.getUserByLoginName(userId, loginName);
			return (tblUser != null);
		}
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#checkExistedEmail(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean checkExistedEmail(Integer userId, String email) throws ClassNotFoundException, SQLException {
		TblUser tblUser = tblUserDao.getUserByEmail(userId, email);
		return (tblUser != null);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#createUser(entity.UserInfor)
	 */
	@Override
	public boolean createUser(UserInfor userInfor) throws SQLException, ClassNotFoundException {
		boolean result = false;
		// lấy current timestamp làm salt cho userInfor
		Long timeStampMillis = Instant.now().toEpochMilli();
		String salt = timeStampMillis.toString();
		String password = Common.encodeMD5(userInfor.getPass() + salt);
		// tạo đối tượng TblUser để add vào bảng tbl_user
		TblUser tblUser = new TblUser();
		tblUser.setGroupId(userInfor.getGroupId());
		tblUser.setLoginName(userInfor.getLoginName());
		tblUser.setPassword(password);
		tblUser.setFullName(userInfor.getFullName());
		tblUser.setFullNameKana(userInfor.getFullNameKana());
		tblUser.setEmail(userInfor.getEmail());
		tblUser.setTel(userInfor.getTel());
		tblUser.setBirthday(userInfor.getBirthday());
		tblUser.setSalt(salt);
		try {
			// làm việc với transaction
			baseDao.connectDB();
			baseDao.disableAutoCommit();
			Integer userId = tblUserDao.insertUser(tblUser);
			// nếu insert không thành công vào bảng tbl_user, return false
			if (userId == null) {
				return false;
			} else if (userInfor.getCodeLevel() != null && !Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
				// nếu có các trường trình độ tiếng Nhật, insert vào bảng tbl_detail_user_japan
				TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
				tblDetailUserJapan.setUserId(userId);
				tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
				tblDetailUserJapan.setStartDate(userInfor.getStartDate());
				tblDetailUserJapan.setEndDate(userInfor.getEndDate());
				tblDetailUserJapan.setTotal(userInfor.getTotal());
				result = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
			} else { // nếu không có vùng trình độ tiếng Nhật
				result = true;
			}
			if (result) {
				// commit nếu insert thành công
				baseDao.commit();
			} else {
				// rollback nếu insert không thành công
				baseDao.rollback();
			}
			return result;
		} catch (SQLException e) {
			// rollback nếu xảy ra lỗi, xóa rác trong bộ nhớ
			baseDao.rollback();
			return false;
		} finally {
			// đóng kết nối
			baseDao.closeDB();
		}
		
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#isExistedUser(java.lang.String)
	 */
	@Override
	public boolean isExistedUser(int userId) throws ClassNotFoundException, SQLException {
		TblUser tblUser = tblUserDao.getTblUserById(userId);
		return (tblUser != null);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getUserInforById(int)
	 */
	@Override
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		return tblUserDao.getUserInforById(userId);
	}
	
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
//		UserInfor userInfor = new UserInfor();
//		userInfor.setGroupId(1);
//		userInfor.setLoginName("sangluu1994");
//		userInfor.setPass("abcdkdkdkdkdkdkdkdkdkdkd");
//		userInfor.setFullName("Lưu Thanh Sang 001");
//		userInfor.setFullNameKana("");
//		userInfor.setEmail("sangluu1994@netflix.com");
//		userInfor.setTel("0987654321");
//		userInfor.setBirthday(new Date());
//		userInfor.setCodeLevel("I2");
//		userInfor.setStartDate(new Date());
//		userInfor.setEndDate(new Date());
//		userInfor.setTotal(125);
//		System.out.println(tblUserLogic.createUser(userInfor));
//	}
	
}
