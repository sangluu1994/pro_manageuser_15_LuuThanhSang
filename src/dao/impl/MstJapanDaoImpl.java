/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstJapanDaoImpl.java, 2017-11-02 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.MstJapanDao;
import entity.MstJapan;

/**
 * Implement MstJapanDao để Xử lý thao tác với bảng mst_japan
 *
 * @author luuthanhsang
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/* (non-Javadoc)
	 * @see dao.MstJapanDao#getAllMstJapan()
	 */
	@SuppressWarnings("finally")
	@Override
	public List<MstJapan> getAllMstJapan() throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo danh sách trả về
		List<MstJapan> listJapanese = new ArrayList<MstJapan>();
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// khai báo câu truy vấn
				String query = "SELECT code_level, name_level FROM mst_japan";
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// format dữ liệu trả về thành các đối tượng MstGroup tương ứng
				while (rs.next()) {
					MstJapan mstJapan = new MstJapan();
					mstJapan.setCodeLevel(rs.getString("code_level"));
					mstJapan.setNameLevel(rs.getString("name_level"));
					listJapanese.add(mstJapan);
				}
			}
		} finally {
			// đóng kết nối và trả về danh sách
			close(con);
			return listJapanese;
		}
	}

	/* (non-Javadoc)
	 * @see dao.MstJapanDao#getJpById(java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public MstJapan getJpById(String codeLevel) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo danh sách trả về
		MstJapan mstJapan = new MstJapan();
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// khai báo câu truy vấn
				String query = "SELECT code_level, name_level FROM mst_japan WHERE code_level = ? ";
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, codeLevel);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// format dữ liệu trả về thành các đối tượng MstGroup tương ứng
				while (rs.next()) {
					mstJapan.setCodeLevel(rs.getString("code_level"));
					mstJapan.setNameLevel(rs.getString("name_level"));
				}
			}
		} finally {
			// đóng kết nối và trả về danh sách
			close(con);
			return mstJapan;
		}
	}
	
}
