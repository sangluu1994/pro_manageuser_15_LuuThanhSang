/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstJapanLogicImpl.java, 2017-11-02 luuthanhsang
 */
package logic.impl;

import java.sql.SQLException;
import java.util.List;

import dao.impl.MstJapanDaoImpl;
import entity.MstJapan;
import logic.MstJapanLogic;

/**
 * Implement MstJapanLogic để xử lí logic cho các chức năng liên quan đến mst_japan
 *
 * @author luuthanhsang
 */
public class MstJapanLogicImpl implements MstJapanLogic {
	private MstJapanDaoImpl mstJapanDao;
	
	public MstJapanLogicImpl() {
		mstJapanDao = new MstJapanDaoImpl();
	}

	/* (non-Javadoc)
	 * @see logic.MstJapanLogic#getAllMstJapan()
	 */
	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException {
		return mstJapanDao.getAllMstJapan();
	}

	/* (non-Javadoc)
	 * @see logic.MstJapanLogic#isRealJpLv(java.lang.String)
	 */
	@Override
	public boolean isRealJpLv(String codeLevel) throws ClassNotFoundException, SQLException {
		MstJapan mstJapan = mstJapanDao.getJpById(codeLevel);
		return (mstJapan != null);
	}

}
