/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
import java.util.List;
import common.Constant;
import dao.impl.TblUserDaoImpl;
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
	private TblUserDaoImpl tblUserDaoImpl;
	
	/**
	 * Constructor
	 */
	public TblUserLogicImpl() {
		tblUserDaoImpl = new TblUserDaoImpl();
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
		return tblUserDaoImpl.getTotalUsers(groupId, fullName);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException {
		return tblUserDaoImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#checkExistedLoginName(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException {
		if (AdminProperties.getValue(Constant.ADMIN_USER).equals(loginName)) {
			return true;
		} else {
			TblUser tblUser = tblUserDaoImpl.getUserByLoginName(userId, loginName);
			return (tblUser != null);
		}
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#checkExistedEmail(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean checkExistedEmail(Integer userId, String email) throws ClassNotFoundException, SQLException {
		TblUser tblUser = tblUserDaoImpl.getUserByEmail(userId, email);
		return (tblUser != null);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#createUser(entity.UserInfor)
	 */
	@Override
	public boolean createUser(UserInfor userInfor) {
		return false;
	}
	
}
