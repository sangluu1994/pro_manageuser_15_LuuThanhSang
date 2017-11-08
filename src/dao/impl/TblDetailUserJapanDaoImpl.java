/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblDetailUserDaoImpl.java, 2017-11-02 luuthanhsang
 */
package dao.impl;

import java.sql.Date;
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
	@SuppressWarnings("finally")
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
				preparedStatement.setInt(++i, tblDetailUserJapan.getTotal());
				// trả về kết quả theo 2 trường hợp add thành công hoặc không thành công
				result = (preparedStatement.executeUpdate() != 0);
			}
		} catch (SQLException e) {
			result = false;
			throw new SQLException();
		} finally {
			return result;
		}
	}

}
