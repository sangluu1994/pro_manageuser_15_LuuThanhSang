/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserDaoImpl.java, 2017-10-25 luuthanhsang
 *
 */
package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Common;
import common.Constant;
import dao.TblUserDao;
import entity.UserInfor;

/**
 * @author luuthanhsang
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getTotalUsers(int, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException {
		int total = 0, i = 0;
		Connection conn = getConnection();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT COUNT(user_id) FROM tbl_user u INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE 1 = 1 ");
		if (groupId != 0) {
			queryBuilder.append("AND u.group_id = ? ");
		}
		if (!Constant.EMPTY_STRING.equals(fullName)) {
			queryBuilder.append("AND u.full_name LIKE ? ");
		}
		String query = queryBuilder.toString();
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			if (groupId != 0) {
				ps.setInt(++i, groupId);
			}
			if (!Constant.EMPTY_STRING.equals(fullName)) {
				ps.setString(++i, "%" + Common.formatCondSearch(fullName) + "%");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
			return total;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		List<UserInfor> listUser = new ArrayList<UserInfor>();
		StringBuilder queryBuilder = new StringBuilder();
		Connection conn = getConnection();
		queryBuilder.append(
				"SELECT u.user_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday, g.group_name, j.name_level, de.start_date, de.end_date, de.total ");
		queryBuilder.append("FROM tbl_user u LEFT JOIN( ");
		queryBuilder.append(
				"mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ON u.user_id = de.user_id ");
		queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE 1 = 1 ");
		
		String query = queryBuilder.toString();
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserInfor tblUserInfo = new UserInfor();
				tblUserInfo.setUserId(rs.getInt("user_id"));
				tblUserInfo.setFullName(rs.getString("full_name"));
				tblUserInfo.setEmail(rs.getString("email"));
				tblUserInfo.setTel(rs.getString("tel"));
				tblUserInfo.setBirthDay(rs.getString("birthday"));
				tblUserInfo.setGroupName(rs.getString("group_name"));
				tblUserInfo.setNameLevel(rs.getString("name_level"));
				tblUserInfo.setEndDate(rs.getString("end_date"));
				tblUserInfo.setTotal(rs.getString("total"));
				listUser.add(tblUserInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
			return listUser;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUsersById(int)
	 */
	@Override
	public UserInfor getUsersById(int id) {
		Connection conn = getConnection();
		UserInfor tblUserInfo = new UserInfor();
		String sql = "SELECT u.user_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday, u.rule, u.salt, u.passwords , g.group_id, g.group_name,j.code_level, j.name_level,de.start_date, de.end_date,de.total ";
		sql = sql.concat(" FROM tbl_user u LEFT JOIN(");
		sql = sql.concat(
				" mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ON u.user_id = de.user_id ");
		sql = sql.concat("INNER JOIN mst_group g ON u.group_id = g.group_id");
		sql = sql.concat(" WHERE u.user_id = ?");
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, id);
			ResultSet rs = ptmt.executeQuery();
			if (rs.next()) {
				tblUserInfo.setUserId(rs.getInt("user_id"));
				tblUserInfo.setLoginName(rs.getString("login_name"));
				tblUserInfo.setFullName(rs.getString("full_name"));
				tblUserInfo.setKataName(rs.getString("full_name_kana"));
				tblUserInfo.setEmail(rs.getString("email"));
				tblUserInfo.setTel(rs.getString("tel"));
				tblUserInfo.setBirthDay(rs.getString("birthday"));
				tblUserInfo.setGroupName(rs.getString("group_name"));
				tblUserInfo.setGroupId(rs.getInt("group_id"));
				tblUserInfo.setCodeLevel(rs.getString("code_level"));
				tblUserInfo.setNameLevel(rs.getString("name_level"));
				tblUserInfo.setStartDate(rs.getString("start_date"));
				tblUserInfo.setEndDate(rs.getString("end_date"));
				tblUserInfo.setTotal(rs.getString("total"));
				tblUserInfo.setRule(rs.getInt("rule"));
				tblUserInfo.setSalt(rs.getString("salt"));
				tblUserInfo.setPassword(rs.getString("passwords"));
				tblUserInfo.setPasswordConfirm(rs.getString("passwords"));
			} else {
				tblUserInfo = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return tblUserInfo;
	}

}
