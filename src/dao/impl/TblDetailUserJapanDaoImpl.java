/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblDetailUserDaoImpl.java, 2017-11-02 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.TblDetailUserJapanDao;
import entity.TblDetailUserJapan;

/**
 * Implement TblDeatilUserJapanDao để Xử lí thao tác với bảng tbl_detail_user_japan
 *
 * @author luuthanhsang
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/* (non-Javadoc)
	 * @see dao.TblDetailUserJapanDao#insertDetailUserJapan(entity.TblDetailUserJapan)
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		boolean result = false;
		// xây dựng truy vấn
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO tbl_detail_user_japan (user_id, code_level, start_date, end_date, total) ");
		query.append("VALUES (?, ?, ?, ?, ?) ");
		try {
			if (connection != null) {
				// thực thi truy vấn sử dụng preparedStatement
				int i = 0;
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.setInt(++i, tblDetailUserJapan.getUserId());
				preparedStatement.setString(++i, tblDetailUserJapan.getCodeLevel());
				preparedStatement.setDate(++i, new Date(tblDetailUserJapan.getStartDate().getTime()));
				preparedStatement.setDate(++i, new Date(tblDetailUserJapan.getEndDate().getTime()));
				preparedStatement.setString(++i, tblDetailUserJapan.getTotal());
				// trả về kết quả theo 2 trường hợp add thành công hoặc không thành công
				result = (preparedStatement.executeUpdate() != 0);
			}
			return result;
		} catch (SQLException e) {
			result = false;
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblDetailUserJapanDao#getDetailUserJapanByUserId(int)
	 */
	@Override
	public TblDetailUserJapan getDetailUserJapanByUserId(int userId) throws SQLException, ClassNotFoundException {
		Connection con = null;
		TblDetailUserJapan tblDetailUserJapan = null;
		try {
			con = getConnection();// get connection
			// if connect null then return
			if (con == null) {
				return null;
			}
			StringBuilder query = new StringBuilder()
					.append("SELECT tbl.detail_user_japan_id, tbl.user_id, tbl.code_level, ")
					.append("tbl.start_date, tbl.end_date, tbl.total FROM tbl_detail_user_japan tbl ")
					.append("WHERE tbl.user_id = ?");
			PreparedStatement ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();// execute sql
			int i;
			while (rs.next()) {
				i = 0;
				tblDetailUserJapan = new TblDetailUserJapan();
				tblDetailUserJapan.setDetailUserJapanId(rs.getInt(++i));
				tblDetailUserJapan.setUserId(rs.getInt(++i));
				tblDetailUserJapan.setCodeLevel(rs.getString(++i));
				tblDetailUserJapan.setStartDate(rs.getDate(++i));
				tblDetailUserJapan.setEndDate(rs.getDate(++i));
				tblDetailUserJapan.setTotal(rs.getString(++i));
			}
		} finally {
			close(con);
		}
		return tblDetailUserJapan;
	}

	/* (non-Javadoc)
	 * @see dao.TblDetailUserJapanDao#updateDetailUserJapan(entity.TblDetailUserJapan)
	 */
	@Override
	public boolean updateDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		StringBuilder query = new StringBuilder()
				.append("UPDATE tbl_detail_user_japan ")
				.append("SET tbl_detail_user_japan.code_level = ?, tbl_detail_user_japan.start_date = ?, ")
				.append("tbl_detail_user_japan.end_date = ?, tbl_detail_user_japan.total=? WHERE user_id = ? ");
		int i = 0;
		preparedStatement = connection.prepareStatement(query.toString());
		preparedStatement.setString(++i, tblDetailUserJapan.getCodeLevel());
		preparedStatement.setDate(++i, new Date(tblDetailUserJapan.getStartDate().getTime()));
		preparedStatement.setDate(++i, new Date(tblDetailUserJapan.getEndDate().getTime()));
		preparedStatement.setString(++i, tblDetailUserJapan.getTotal());
		preparedStatement.setInt(++i, tblDetailUserJapan.getUserId());
		return (preparedStatement.executeUpdate() != 0);
	}

	/* (non-Javadoc)
	 * @see dao.TblDetailUserJapanDao#deleteDetailUserJapan(int)
	 */
	@Override
	public boolean deleteDetailUserJapan(int userId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM tbl_detail_user_japan ");
		query.append("WHERE tbl_detail_user_japan.user_id = ? ");
		preparedStatement = connection.prepareStatement(query.toString());
		preparedStatement.setInt(1, userId);
		System.out.println(preparedStatement.toString());
		return (preparedStatement.executeUpdate() != 0);
	}

}
