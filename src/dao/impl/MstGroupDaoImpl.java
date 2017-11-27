/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupDaoImpl.java, 2017-10-25 luuthanhsang
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
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	
	/* (non-Javadoc)
	 * @see dao.MstGroupDao#getAllMstGroup()
	 */
	@Override
	public List<MstGroup> getAllMstGroup() throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo danh sách trả về
		List<MstGroup> listGroup = new ArrayList<MstGroup>();
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// khai báo câu truy vấn
				String query = "SELECT group_id, group_name FROM mst_group";
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// chuyển đổi dữ liệu trả về thành các đối tượng MstGroup tương ứng
				while (rs.next()) {
					MstGroup mstGroup = new MstGroup();
					mstGroup.setGroupId(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
					listGroup.add(mstGroup);
				}
			}
		} finally {
			close(con);
		}
		return listGroup;
	}

	/* (non-Javadoc)
	 * @see dao.MstGroupDao#getGroup(int)
	 */
	@Override
	public MstGroup getGroupById(int id) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo đối tượng MstGroup sẽ trả về
		MstGroup mstGroup = new MstGroup();
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// khai báo câu truy vấn
				String query = "SELECT group_id, group_name FROM mst_group where group_id = ?";
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, id);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// format dữ liệu trả về sang đối tượng MstGroup tương ứng
				if (rs.next()) {
					mstGroup.setGroupId(rs.getInt("group_id"));
					mstGroup.setGroupName(rs.getString("group_name"));
				} else {
					mstGroup = null;
				}
			}
		} finally {
			close(con);
		}
		return mstGroup;
	}

}
