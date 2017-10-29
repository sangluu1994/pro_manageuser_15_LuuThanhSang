/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
import java.util.List;

import dao.impl.TblUserDaoImpl;
import entity.UserInfor;
import logic.TblUserLogic;

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
	public int getTotalUsers(int groupId, String fullName) throws SQLException {
		return tblUserDaoImpl.getTotalUsers(groupId, fullName);
	}

	/* (non-Javadoc)
	 * @see logic.TblUserLogic#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		return tblUserDaoImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
	}

}
