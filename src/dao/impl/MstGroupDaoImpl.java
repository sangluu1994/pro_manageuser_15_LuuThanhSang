/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupDaoImpl.java, 2017-10-25 luuthanhsang
 *
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MstGroupDao;
import entity.MstGroup;

/**
 * Class thực thi Interface MstGroupDao
 * 
 * @author luuthanhsang
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/* (non-Javadoc)
	 * @see dao.MstGroupDao#getAllMstGroup()
	 */
	@SuppressWarnings("finally")
	@Override
	public List<MstGroup> getAllMstGroup() {
		Connection conn = getConnection();
		List<MstGroup> listGroup = new ArrayList<MstGroup>();
		String query = "SELECT group_id, group_name FROM mst_group";
		try {
			PreparedStatement ptmt = conn.prepareStatement(query);
			ResultSet rs = ptmt.executeQuery();
			while (rs.next()) {
				MstGroup mstGroup = new MstGroup();
				mstGroup.setGroupId(rs.getInt("group_id"));
				mstGroup.setGroupName(rs.getString("group_name"));
				listGroup.add(mstGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
			return listGroup;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.MstGroupDao#getGroup(int)
	 */
	@Override
	public MstGroup getGroup(int id) {
		Connection conn = getConnection();
		String sql = "SELECT group_id,group_name FROM mst_group where group_id = ?";
		MstGroup mstGroup = new MstGroup();
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				mstGroup.setGroupId(rs.getInt("group_id"));
				mstGroup.setGroupName(rs.getString("group_name"));
			} else {
				mstGroup = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return mstGroup;
	}

}
