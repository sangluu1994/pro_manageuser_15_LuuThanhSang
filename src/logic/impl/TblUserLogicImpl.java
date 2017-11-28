/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
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
		// escape giá trị wildcard trong fullName
		fullName = Common.escapeWildCard(fullName);
		return tblUserDao.getTotalUsers(groupId, fullName);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException {
		// định dạng lại sortType, chống tấn công SQL Injection vào câu lệnh ORDER BY
		if (Constant.SORT_BY_FULL_NAME.equals(sortType)) {
			sortByFullName = Common.preparedSortType(sortByFullName);
		} else if (Constant.SORT_BY_CODE_LEVEL.equals(sortType)) {
			sortByCodeLevel = Common.preparedSortType(sortByCodeLevel);
		} else {
			sortByEndDate = Common.preparedSortType(sortByEndDate);
		}
		// escape giá trị wildcard trong fullName
		fullName = Common.escapeWildCard(fullName);
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
		String salt = Common.getTimeStampMillis();
		String password = Common.SHA1(userInfor.getPass() + salt);
		// tạo đối tượng TblUser để add vào bảng tbl_user
		userInfor.setSalt(salt);
		TblUser tblUser = getTblUserFromUserInfor(userInfor);
		tblUser.setPassword(password);
		try {
			// làm việc với transaction
			baseDao.startTransaction();
			Integer userId = tblUserDao.insertUser(tblUser);
			// nếu insert không thành công vào bảng tbl_user, return false
			if (userId == null) {
				return false;
			} else if (userInfor.getCodeLevel() != null && !Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
				// nếu có các trường trình độ tiếng Nhật, insert vào bảng tbl_detail_user_japan
				userInfor.setUserId(userId);
				TblDetailUserJapan tblDetailUserJapan = getTblDetailUserJapanFromUserInfor(userInfor);
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
			throw e;
		} finally {
			// kết thúc phiên làm việc có transaction
			baseDao.endTransaction();
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

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#editUser(entity.UserInfor)
	 */
	@Override
	public boolean editUser(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		boolean success = false;
		int userId = userInfor.getUserId();
		// chuẩn bị các đối tượng TblUser, TblDetailUserJapan sẽ update
		TblUser tblUser = getTblUserFromUserInfor(userInfor);
		TblDetailUserJapan detailUserJapan = tblDetailUserJapanDao.getDetailUserJapanByUserId(userId);
		try {
			// transaction
			baseDao.startTransaction();
			success = tblUserDao.updateUser(tblUser);
			// check vùng trình độ tiếng Nhật
			if (!Constant.DEFAULT_CODE_LEVEL.equals(userInfor.getCodeLevel())) {
				TblDetailUserJapan tblDetailUserJapan = getTblDetailUserJapanFromUserInfor(userInfor);
				if (detailUserJapan == null) {
					success = tblDetailUserJapanDao.insertDetailUserJapan(tblDetailUserJapan);
				} else {
					success = tblDetailUserJapanDao.updateDetailUserJapan(tblDetailUserJapan);
				}
			} else {
				if (detailUserJapan != null) {
					success = tblDetailUserJapanDao.deleteDetailUserJapan(userId);
				} 
			}
			if (success) {
				baseDao.commit();
			} else {
				baseDao.rollback();
			}
			return success;
		} catch (SQLException e) {
			baseDao.rollback();
			throw e;
		} finally {
			baseDao.endTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#removeUser(int)
	 */
	@Override
	public boolean removeUser(int userId) throws ClassNotFoundException, SQLException {
		boolean success = false;
		try {
			// transaction
			baseDao.startTransaction();
			tblDetailUserJapanDao.deleteDetailUserJapan(userId);
			success = tblUserDao.deleteUser(userId);
			if (success) {
				baseDao.commit();
			} else {
				baseDao.rollback();
			}
			return success;
		} catch (SQLException e) {
			baseDao.rollback();
			throw e;
		} finally {
			baseDao.endTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#changePassword(int, java.lang.String)
	 */
	@Override
	public boolean changePassword(int userId, String passWord) throws ClassNotFoundException, SQLException {
		TblUser tblUser = tblUserDao.getTblUserById(userId);
		String newPassword = Common.SHA1(passWord, tblUser.getSalt());
		return tblUserDao.updatePassword(userId, newPassword);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getTblDetailUserJapanFromUserInfor(entity.UserInfor)
	 */
	@Override
	public TblDetailUserJapan getTblDetailUserJapanFromUserInfor(UserInfor userInfor) {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		tblDetailUserJapan.setUserId(userInfor.getUserId());
		tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
		tblDetailUserJapan.setStartDate(userInfor.getStartDate());
		tblDetailUserJapan.setEndDate(userInfor.getEndDate());
		tblDetailUserJapan.setTotal(userInfor.getTotal());
		return tblDetailUserJapan;
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getTblUserFromUserInfor(entity.UserInfor)
	 */
	@Override
	public TblUser getTblUserFromUserInfor(UserInfor userInfor) {
		TblUser tblUser = new TblUser();
		tblUser.setUserId(userInfor.getUserId());
		tblUser.setLoginName(userInfor.getLoginName());
		tblUser.setGroupId(userInfor.getGroupId());
		tblUser.setFullName(userInfor.getFullName());
		tblUser.setFullNameKana(userInfor.getFullNameKana());
		tblUser.setEmail(userInfor.getEmail());
		tblUser.setTel(userInfor.getTel());
		tblUser.setBirthday(userInfor.getBirthday());
		tblUser.setSalt(userInfor.getSalt());
		return tblUser;
	}
	
}
