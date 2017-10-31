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
	@SuppressWarnings("finally")
	@Override
	public List<MstGroup> getAllMstGroup() throws SQLException {
		// khởi tạo connection
		Connection con = getConnection();
		// khởi tạo danh sách trả về
		List<MstGroup> listGroup = new ArrayList<MstGroup>();
		// khai báo câu truy vấn
		String query = "SELECT group_id, group_name FROM mst_group";
		try {
			// truy vấn sử dụng preparedStatement
			PreparedStatement ps = con.prepareStatement(query);
			// lấy dữ liệu trả về
			ResultSet rs = ps.executeQuery();
			// format dữ liệu trả về thành các đối tượng MstGroup tương ứng
			while (rs.next()) {
				MstGroup mstGroup = new MstGroup();
				mstGroup.setGroupId(rs.getInt("group_id"));
				mstGroup.setGroupName(rs.getString("group_name"));
				listGroup.add(mstGroup);
			}
		} catch (SQLException e) {
			// show console log ngoại lệ
			e.printStackTrace();
		} finally {
			// đóng kết nối và trả về danh sách
			close(con);
			return listGroup;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.MstGroupDao#getGroup(int)
	 */
	@SuppressWarnings("finally")
	@Override
	public MstGroup getGroup(int id) throws SQLException {
		// khởi tạo connection
		Connection con = getConnection();
		// khai báo câu truy vấn
		String query = "SELECT group_id,group_name FROM mst_group where group_id = ?";
		// khởi tạo đối tượng MstGroup sẽ trả về
		MstGroup mstGroup = new MstGroup();
		try {
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
		} catch (SQLException e) {
			// show console log ngoại lệ
			e.printStackTrace();
		} finally {
			// đóng kết nối và trả về kết quả
			close(con);
			return mstGroup;
		}
		
	}

//	public static void main(String[] args) {
//		MstGroupDaoImpl mstGroupDaoImpl = new MstGroupDaoImpl();
//		List<MstGroup> ls = mstGroupDaoImpl.getAllMstGroup();
//		for (MstGroup group : ls) {
//			System.out.println(group.getGroupId());
//			System.out.println(group.getGroupName());
//		}
//	}
	
}
