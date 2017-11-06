/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
import java.util.List;

import dao.impl.MstGroupDaoImpl;
import entity.MstGroup;
import logic.MstGroupLogic;

/**
 * Class cài đặt các phương thức thao tác với bảng mst_group
 *
 * @author luuthanhsang
 */
public class MstGroupLogicImpl implements MstGroupLogic {
	private MstGroupDaoImpl mstGroupDaoImpl;
	
	/**
	 * Constructor
	 */
	public MstGroupLogicImpl() {
		mstGroupDaoImpl = new MstGroupDaoImpl();
	}

	/* (non-Javadoc)
	 * @see logic.MstGroupLogic#getAllGroups()
	 */
	@Override
	public List<MstGroup> getAllMstGroups() throws SQLException, ClassNotFoundException {
		return mstGroupDaoImpl.getAllMstGroup();
	}

	/* (non-Javadoc)
	 * @see logic.MstGroupLogic#checkExistGroup(int)
	 */
	@Override
	public boolean isRealGroup(int groupId) throws SQLException, ClassNotFoundException {
		MstGroup mstGroup = mstGroupDaoImpl.getGroupById(groupId);
		return (mstGroup != null);
	}

}
