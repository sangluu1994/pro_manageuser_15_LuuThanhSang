/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblUserDaoImpl.java, 2017-10-25 luuthanhsang
 */
package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import common.Constant;
import dao.TblUserDao;
import entity.TblUser;
import entity.UserInfor;

/**
 * Class chứa các phương thức thao tác với bảng tbl_user 
 * 
 * @author luuthanhsang
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getTotalUsers(int, java.lang.String, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo tổng số user trả về
		int totalUsers = 0;
		try {
			// khởi tạo kết nối đến db
			con = getConnection();
			if (con != null) {
				// xây dựng truy vấn
				StringBuilder query = new StringBuilder()
					.append("SELECT COUNT(user_id) ")
					.append("FROM tbl_user u ")
					.append("INNER JOIN mst_group g ON u.group_id = g.group_id ")
					.append("WHERE 1 = 1 ");
				boolean hasGroupSearch = (groupId != 0);
				if (hasGroupSearch) {
					query.append("AND u.group_id = ? ");
				}
				boolean hasFullNameSearch = (fullName != null && !Constant.EMPTY_STRING.equals(fullName));
				if (hasFullNameSearch) {
					query.append("AND u.full_name LIKE ?");
				}
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query.toString());
				int i = 0;
				if (hasGroupSearch) {
					ps.setInt(++i, groupId);
				}
				if (hasFullNameSearch) {
					StringBuilder fullNameInput = new StringBuilder();
					fullNameInput.append("%");
					fullNameInput.append(fullName);
					fullNameInput.append("%");
					ps.setString(++i, fullNameInput.toString());
				}
				// lấy kết quả trả về
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					totalUsers = rs.getInt(1);
				}
			}
			return totalUsers;
		} finally {
			close(con);
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khởi tạo danh sách user trả về
		List<UserInfor> listUser = new ArrayList<UserInfor>();
		try {
			// khởi tạo kết nối đến db
			con = getConnection();
			if (con != null) {	
				// xây dựng truy vấn
				StringBuilder queryBuilder = new StringBuilder()
						.append("SELECT u.user_id, u.full_name, u.birthday, g.group_name, u.email, u.tel, j.name_level, de.end_date, de.total ")
						.append("FROM tbl_user u LEFT JOIN (mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ")
						.append("ON u.user_id = de.user_id ")
						.append("INNER JOIN mst_group g ON u.group_id = g.group_id ")
						.append("WHERE 1 = 1 ");
				boolean hasGroupSearch = (groupId != 0);
				if (hasGroupSearch) {
					queryBuilder.append("AND u.group_id = ? ");
				}
				boolean hasFullNameSearch = (fullName != null && !Constant.EMPTY_STRING.equals(fullName));
				if (hasFullNameSearch) {
					queryBuilder.append("AND u.full_name LIKE ? ");
				}
				queryBuilder.append("ORDER BY ");
				if (Constant.SORT_BY_FULL_NAME.equals(sortType)) {
					queryBuilder.append("u.full_name ").append(sortByFullName).append(", j.name_level ASC, de.end_date DESC ");
				} else if (Constant.SORT_BY_CODE_LEVEL.equals(sortType)) {
					queryBuilder.append("j.name_level ").append(sortByCodeLevel).append(", u.full_name ASC, de.end_date DESC ");
				} else if (Constant.SORT_BY_END_DATE.equals(sortType)) {
					queryBuilder.append("de.end_date ").append(sortByEndDate).append(", u.full_name ASC, j.name_level ASC ");
				} else {
					queryBuilder.append("u.full_name ASC, j.name_level ASC, de.end_date DESC ");
				}
				queryBuilder.append("LIMIT ? ");
				queryBuilder.append("OFFSET ?");
				String query = queryBuilder.toString();
				
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				int i = 0;
				if (hasGroupSearch) {
					ps.setInt(++i, groupId);
				}
				if (hasFullNameSearch) {
					StringBuilder fullNameInput = new StringBuilder();
					fullNameInput.append("%");
					fullNameInput.append(fullName);
					fullNameInput.append("%");
					ps.setString(++i, fullNameInput.toString());
				}
				ps.setInt(++i, limit);
				ps.setInt(++i, offset);
				
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// thiết lập danh sách các đối tượng UserInfor trả về dựa trên dữ liệu đã lấy được
				while (rs.next()) {
					UserInfor userInfor = new UserInfor();
					userInfor.setUserId(rs.getInt("user_id"));
					userInfor.setFullName(rs.getString("full_name"));
					userInfor.setBirthday(rs.getDate("birthday"));
					userInfor.setGroupName(rs.getString("group_name"));
					userInfor.setEmail(rs.getString("email"));
					userInfor.setTel(rs.getString("tel"));
					userInfor.setNameLevel(rs.getString("name_level"));
					userInfor.setEndDate(rs.getDate("end_date"));
					userInfor.setTotal(rs.getString("total"));
					listUser.add(userInfor);
				}
			}
			return listUser;
		} finally {
			close(con);
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUsersById(int)
	 */
	@Override
	public UserInfor getUserInforById(int id) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khai báo đối tượng tblUserInfor sẽ trả về
		UserInfor userInfor = null;
		try {
			// khởi tạo kết nối
			con = getConnection();
			if (con != null) {
				// khởi tạo câu truy vấn
				StringBuilder queryBuilder = new StringBuilder()
						.append("SELECT u.user_id, u.login_name, u.group_id, g.group_name, u.full_name, u.full_name_kana, u.salt, u.birthday, ")
						.append("u.email, u.tel, de.code_level, j.name_level, de.start_date, de.end_date, de.total ")
						.append("FROM tbl_user u LEFT JOIN (mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ")
						.append("ON u.user_id = de.user_id ")
						.append("INNER JOIN mst_group g ON u.group_id = g.group_id ")
						.append("WHERE u.user_id = ?");
				
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(queryBuilder.toString());
				ps.setInt(1, id);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// thiết lập đối tượng UserInfor sẽ trả về dựa vào dữ liệu đã lấy được
				while (rs.next()) {
					userInfor = new UserInfor();
					userInfor.setUserId(rs.getInt("user_id"));
					userInfor.setLoginName(rs.getString("login_name"));
					userInfor.setGroupId(rs.getInt("group_id"));
					userInfor.setGroupName(rs.getString("group_name"));
					userInfor.setFullName(rs.getString("full_name"));
					userInfor.setFullNameKana(rs.getString("full_name_kana"));
					userInfor.setSalt(rs.getString("salt"));
					userInfor.setBirthday(rs.getDate("birthday"));
					userInfor.setEmail(rs.getString("email"));
					userInfor.setTel(rs.getString("tel"));
					userInfor.setCodeLevel(rs.getString("code_level"));
					userInfor.setNameLevel(rs.getString("name_level"));
					userInfor.setStartDate(rs.getDate("start_date"));
					userInfor.setEndDate(rs.getDate("end_date"));
					userInfor.setTotal(rs.getString("total"));
				} 
			}
			return userInfor;
		} finally {
			close(con);
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUserByLoginName(java.lang.Integer, java.lang.String)
	 */
	@Override
	public TblUser getUserByLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException {
		Connection con = null;
		TblUser tblUser = null;
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// xây dựng truy vấn
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT u.user_id, u.group_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday ");
				queryBuilder.append("FROM tbl_user u WHERE u.login_name = ? ");
				if (userId > 0) {
					queryBuilder.append("AND u.user_id <> ? ");
				}
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(queryBuilder.toString());
				int j = 0;
				ps.setString(++j, loginName);
				if (userId > 0) {
					ps.setInt(++j, userId);
				}
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// thiết lập đối tượng tblUser trả về dựa vào dữ liệu lấy được
				while (rs.next()) {
					int i = 0;
					tblUser = new TblUser();
					tblUser.setUserId(rs.getInt(++i));
					tblUser.setGroupId(rs.getInt(++i));
					tblUser.setLoginName(rs.getString(++i));
					tblUser.setFullName(rs.getString(++i));
					tblUser.setFullNameKana(rs.getString(++i));
					tblUser.setEmail(rs.getString(++i));
					tblUser.setTel(rs.getString(++i));
					tblUser.setBirthday(rs.getDate(++i));
				}
			}
			return tblUser;
		} finally {
			close(con);
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUserByEmail(java.lang.Integer, java.lang.String)
	 */
	@Override
	public TblUser getUserByEmail(final Integer userId, String email) throws ClassNotFoundException, SQLException {
		Connection con = null;
		TblUser tblUser = null;
		try {
			// khởi tạo connection
			con = getConnection();
			if (con != null) {
				// xây dựng truy vấn
				StringBuilder queryBuilder = new StringBuilder("SELECT u.user_id, u.group_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday ");
				queryBuilder.append("FROM tbl_user u WHERE u.email = ? ");
				if (userId > 0) {
					queryBuilder.append("AND u.user_id <> ? ");
				}
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(queryBuilder.toString());
				int j = 0;
				ps.setString(++j, email);
				if (userId > 0) {
					ps.setInt(++j, userId);
				}
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// thiết lập đối tượng tblUser trả về dựa vào dữ liệu lấy được
				while (rs.next()) {
					int i = 0;
					tblUser = new TblUser();
					tblUser.setUserId(rs.getInt(++i));
					tblUser.setGroupId(rs.getInt(++i));
					tblUser.setLoginName(rs.getString(++i));
					tblUser.setFullName(rs.getString(++i));
					tblUser.setFullNameKana(rs.getString(++i));
					tblUser.setEmail(rs.getString(++i));
					tblUser.setTel(rs.getString(++i));
					tblUser.setBirthday(rs.getDate(++i));
				}
			}
			return tblUser;
		} finally {
			close(con);
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#insertUser(entity.TblUser)
	 */
	@Override
	public Integer insertUser(TblUser tblUser) throws SQLException {
		// khai báo giá trị trả về
		Integer userId = null;
		// xây dựng truy vấn
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO tbl_user (group_id, login_name, password, salt, full_name, full_name_kana, email, tel, birthday) ");
		query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		int i = 0;
		if (connection != null) {
			// thực thi truy vấn sử dụng preparedStatement
			preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(++i, tblUser.getGroupId());
			preparedStatement.setString(++i, tblUser.getLoginName());
			preparedStatement.setString(++i, tblUser.getPassword());
			preparedStatement.setString(++i, tblUser.getSalt());
			preparedStatement.setString(++i, tblUser.getFullName());
			preparedStatement.setString(++i, tblUser.getFullNameKana());
			preparedStatement.setString(++i, tblUser.getEmail());
			preparedStatement.setString(++i, tblUser.getTel());
			preparedStatement.setDate(++i, new Date(tblUser.getBirthday().getTime()));
			// execute
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			// lấy auto-generated keys 
			while (resultSet.next()) {
				userId = resultSet.getInt(1);
			}
		}
		return userId;
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getTblUserById(int)
	 */
	@Override
	public TblUser getTblUserById(int userId) throws SQLException, ClassNotFoundException {
		Connection con = null;
		TblUser tblUser = null;
		try {
			// khởi tạo kết nối đến db
			con = getConnection();
			if (con != null) {
				// xây dựng truy vấn
				StringBuilder query = new StringBuilder();
				query.append("SELECT u.user_id, u.group_id, u.login_name, u.password, u.salt, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday ");
				query.append("FROM tbl_user u WHERE u.user_id = ? ");
				// truy vấn dữ liệu sử dụng PreparedStatement
				PreparedStatement ps = con.prepareStatement(query.toString());
				ps.setInt(1, userId);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// set các thuộc tính của đối tượng TblUser trả về
				while (rs.next()) {
					tblUser = new TblUser();
					tblUser.setUserId(rs.getInt("user_id"));
					tblUser.setGroupId(rs.getInt("group_id"));
					tblUser.setLoginName(rs.getString("login_name"));
					tblUser.setPassword(rs.getString("password"));
					tblUser.setSalt(rs.getString("salt"));
					tblUser.setFullName(rs.getString("full_name"));
					tblUser.setFullNameKana(rs.getString("full_name_kana"));
					tblUser.setEmail(rs.getString("email"));
					tblUser.setTel(rs.getString("tel"));
					tblUser.setBirthday(rs.getDate("birthday"));
				} 
			}
			return tblUser;
		} finally {
			close(con);
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#updateUser(entity.TblUser)
	 */
	@Override
	public boolean updateUser(TblUser tblUser) throws ClassNotFoundException, SQLException {
		// chuẩn bị câu truy vấn
		StringBuilder query = new StringBuilder();
		query.append("UPDATE tbl_user u ");
		query.append("SET u.group_id = ?, u.full_name = ?, u.full_name_kana = ?, u.email = ?, u.tel = ?, u.birthday = ? ");
		query.append("WHERE u.user_id = ?");
		int i = 0;
		// truy vấn dữ liệu sử dụng PreparedStatement
		preparedStatement = connection.prepareStatement(query.toString());
		preparedStatement.setInt(++i, tblUser.getGroupId());
		preparedStatement.setString(++i, tblUser.getFullName());
		preparedStatement.setString(++i, tblUser.getFullNameKana());
		preparedStatement.setString(++i, tblUser.getEmail());
		preparedStatement.setString(++i, tblUser.getTel());
		preparedStatement.setDate(++i, new Date(tblUser.getBirthday().getTime()));
		preparedStatement.setInt(++i, tblUser.getUserId());
		return (preparedStatement.executeUpdate() != 0);
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#deleteUser(int)
	 */
	@Override
	public boolean deleteUser(int userId) throws ClassNotFoundException, SQLException {
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM tbl_user ");
		query.append("WHERE tbl_user.user_id = ?");
		preparedStatement = connection.prepareStatement(query.toString());
		preparedStatement.setInt(1, userId);
		return (preparedStatement.executeUpdate() != 0);
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#updatePassword(int, java.lang.String)
	 */
	@Override
	public boolean updatePassword(int userId, String newPassword) throws ClassNotFoundException, SQLException {
		Connection con = null;
		boolean result = false;
		try {
			con = getConnection();
			if (con != null) {
				StringBuilder query = new StringBuilder();
				query.append("UPDATE tbl_user SET tbl_user.password = ? ");
				query.append("WHERE tbl_user.user_id = ? ");
				int i = 0;
				PreparedStatement ps = con.prepareStatement(query.toString());
				ps.setString(++i, newPassword);
				ps.setInt(++i, userId);
				result = (ps.executeUpdate() != 0);
			}
			return result;
		} finally {
			close(con);
		}
	}
	
}
