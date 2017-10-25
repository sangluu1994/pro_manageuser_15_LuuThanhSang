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

import common.Constant;
import dao.TblUserDao;
import entity.TblUser;
import entity.UserInfor;

/**
 * @author luuthanhsang
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getSalt(java.lang.String)
	 */
	@Override
	public String getSalt(String loginName) {
		Connection conn = getConnection();
		String sql = "SELECT salt FROM tbl_user WHERE login_name = ?";
		String salt = Constant.EMPTY_STRING;
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, loginName);
			ResultSet rs = ptmt.executeQuery();
			if (!rs.next()) {
				return null;
			} else {
				salt = rs.getString("salt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return salt;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getTotalUsers(int, java.lang.String, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName, String birthDay) throws SQLException {
		int total = 0, i = 0;
		Connection conn = getConnection();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT COUNT(user_id) FROM tbl_user u INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE 1 = 1 ");
		if (groupId != 0) {
			queryBuilder.append("AND u.group_id = ? ");
		}
		if (!Constant.EMPTY_STRING.equals(birthDay)) {
			queryBuilder.append("AND u.birthday = ? ");
		}
		if (!Constant.EMPTY_STRING.equals(fullName)) {
			queryBuilder.append("AND u.full_name LIKE ? ");
		}
		String sql = queryBuilder.toString();
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			if (groupId != 0) {
				ptmt.setInt(++i, groupId);
			}
			if (!Constant.EMPTY_STRING.equals(birthDay)) {
				ptmt.setString(++i, birthDay);
			}
			if (!Constant.EMPTY_STRING.equals(fullName)) {
				ptmt.setString(++i, "%" + fullName + "%");
			}
			ResultSet rs = ptmt.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return total;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#insertUser(entity.TblUser, java.sql.Connection)
	 */
	@Override
	public boolean insertUser(TblUser tblUser, Connection connection) throws SQLException {
		return false;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#editUser(entity.TblUser, java.sql.Connection)
	 */
	@Override
	public boolean editUser(TblUser tblUser, Connection connection) throws SQLException {
		return false;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		List<UserInfor> listUser = new ArrayList<UserInfor>();
		StringBuilder queryBuilder = new StringBuilder();
		Connection conn = getConnection();
		int i = 0;
		queryBuilder.append(
				"SELECT u.user_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday, g.group_name, j.name_level, de.start_date, de.end_date, de.total ");
		queryBuilder.append("FROM tbl_user u LEFT JOIN( ");
		queryBuilder.append(
				"mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ON u.user_id = de.user_id ");
		queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
		queryBuilder.append("WHERE 1 = 1 ");
		if (groupId != 0) {
			queryBuilder.append("AND u.group_id = ? ");
		}
		if (!Constant.EMPTY_STRING.equals(fullName)) {
			queryBuilder.append("AND u.full_name LIKE ? ");
		}
		queryBuilder.append("ORDER BY ");
		if (Constant.SORT_NAME.equals(sortType)) {
			queryBuilder.append("u.full_name ").append(sortByFullName).append(", j.name_level ASC, de.end_date DESC ");
		}
		if (Constant.SORT_CODE.equals(sortType)) {
			queryBuilder.append("j.name_level ").append(sortByCodeLevel).append(", u.full_name ASC, de.end_date DESC ");
		}
		if (Constant.SORT_DATE.equals(sortType)) {
			queryBuilder.append("de.end_date ").append(sortByEndDate).append(", u.full_name ASC, j.name_level ASC ");
		}
		if (Constant.EMPTY_STRING.equals(sortType)) {
			queryBuilder.append("u.full_name ASC, j.name_level ASC, de.end_date DESC ");
		}
		queryBuilder.append("LIMIT ? ");
		queryBuilder.append("OFFSET ? ");
		String sql = queryBuilder.toString();
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			if (groupId != 0) {
				ptmt.setInt(++i, groupId);
			}
			if (!Constant.EMPTY_STRING.equals(fullName)) {
				ptmt.setString(++i, "%" + fullName + "%");
			}
			ptmt.setInt(++i, limit);
			ptmt.setInt(++i, offset);
			ResultSet rs = ptmt.executeQuery();
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
		}
		return listUser;
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

	/* (non-Javadoc)
	 * @see dao.TblUserDao#deleteUser(entity.TblUser, java.sql.Connection)
	 */
	@Override
	public boolean deleteUser(TblUser tblUser, Connection connection) throws SQLException {
		String query = "DELETE FROM tbl_user WHERE user_id = ?";
		boolean result = false;
		int rowChange = -1;
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, tblUser.getUserId());
			rowChange = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rowChange > 0) {
			result = true;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getMail(int, java.lang.String)
	 */
	@Override
	public TblUser getMail(int userId, String mail) {
		Connection conn = getConnection();
		TblUser tblUser = new TblUser();
		String sql = "SELECT login_name, passwords FROM tbl_user WHERE email = ? AND user_id <> ?";
		try {
			PreparedStatement ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, mail);
			ptmt.setInt(2, userId);
			ResultSet rs = ptmt.executeQuery();
			if (!rs.next()) {
				return null;
			} else {
				tblUser.setLoginName(rs.getString("login_name"));
				tblUser.setPassword(rs.getString("passwords"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return tblUser;
	}

}
