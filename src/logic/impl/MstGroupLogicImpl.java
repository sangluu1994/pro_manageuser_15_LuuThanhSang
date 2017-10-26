/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupLogicImpl.java, 2017-10-26 luuthanhsang
 */
package logic.impl;

import java.util.List;

import dao.impl.MstGroupDaoImpl;
import entity.MstGroup;
import logic.MstGroupLogic;

/**
 * 
 *
 * @author luuthanhsang
 *
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
	public List<MstGroup> getAllGroups() {
		return mstGroupDaoImpl.getAllMstGroup();
	}

}