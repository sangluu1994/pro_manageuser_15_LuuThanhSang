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
	@SuppressWarnings("finally")
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
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT COUNT(user_id) FROM tbl_user u INNER JOIN mst_group g ON u.group_id = g.group_id ");
				queryBuilder.append("WHERE 1 = 1 ");
				if (groupId != 0) {
					queryBuilder.append("AND u.group_id = ? ");
				}
				if (!Constant.EMPTY_STRING.equals(fullName) && fullName != null) {
					queryBuilder.append("AND u.full_name REGEXP ?");
				}
				String query = queryBuilder.toString();
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				int i = 0;
				if (groupId != 0) {
					ps.setInt(++i, groupId);
				}
				if (!Constant.EMPTY_STRING.equals(fullName)) {
					ps.setString(++i, fullName);
				}
				// lấy kết quả trả về
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					totalUsers = rs.getInt(1);
				}
			}
		} finally {
			// đóng kết nối và trả về tổng số user
			close(con);
			return totalUsers;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("finally")
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
				// khởi tạo câu truy vấn
				StringBuilder queryBuilder = new StringBuilder();
				// xây dựng truy vấn
				queryBuilder.append("SELECT u.user_id, u.full_name, u.birthday, g.group_name, u.email, u.tel, j.name_level, de.end_date, de.total ");
				queryBuilder.append("FROM tbl_user u LEFT JOIN (mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ");
				queryBuilder.append("ON u.user_id = de.user_id ");
				queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
				queryBuilder.append("WHERE 1 = 1 ");
				if (groupId != 0) {
					queryBuilder.append("AND u.group_id = ? ");
				}
				if (!Constant.EMPTY_STRING.equals(fullName) && fullName != null) {
					queryBuilder.append("AND u.full_name REGEXP ? ");
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
				if (groupId != 0) {
					ps.setInt(++i, groupId);
				}
				if (!Constant.EMPTY_STRING.equals(fullName) && fullName != null) {
					ps.setString(++i, fullName);
				}
				ps.setInt(++i, limit);
				ps.setInt(++i, offset);
				
				System.out.println(ps.toString());
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
					userInfor.setTotal(rs.getInt("total"));
					listUser.add(userInfor);
				}
			}
		} finally {
			// đóng kết nối và trả về danh sách user
			close(con);
			return listUser;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUsersById(int)
	 */
	@SuppressWarnings("finally")
	@Override
	public UserInfor getUsersById(int id) throws SQLException, ClassNotFoundException {
		Connection con = null;
		// khai báo đối tượng tblUserInfor sẽ trả về
		UserInfor tblUserInfo = new UserInfor();
		try {
			// khởi tạo kết nối
			con = getConnection();
			if (con != null) {
				// khởi tạo câu truy vấn
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT u.login_name, g.group_name, u.full_name, u.full_name_kana, u.birthday, u.email, u.tel, j.name_level, de.start_date, de.end_date, de.total ");
				queryBuilder.append("FROM tbl_user u LEFT JOIN (mst_japan j INNER JOIN  tbl_detail_user_japan de ON de.code_level = j.code_level) ");
				queryBuilder.append("ON u.user_id = de.user_id ");
				queryBuilder.append("INNER JOIN mst_group g ON u.group_id = g.group_id ");
				queryBuilder.append("WHERE u.user_id = ?");
				String query = queryBuilder.toString();
				
				// truy vấn sử dụng preparedStatement
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, id);
				// lấy dữ liệu trả về
				ResultSet rs = ps.executeQuery();
				// thiết lập đối tượng UserInfor sẽ trả về dựa vào dữ liệu đã lấy được
				if (rs.next()) {
					tblUserInfo.setLoginName(rs.getString("login_name"));
					tblUserInfo.setFullName(rs.getString("full_name"));
					tblUserInfo.setFullNameKana(rs.getString("full_name_kana"));
					tblUserInfo.setEmail(rs.getString("email"));
					tblUserInfo.setTel(rs.getString("tel"));
					tblUserInfo.setBirthday(rs.getDate("birthday"));
					tblUserInfo.setGroupName(rs.getString("group_name"));
					tblUserInfo.setNameLevel(rs.getString("name_level"));
					tblUserInfo.setStartDate(rs.getDate("start_date"));
					tblUserInfo.setEndDate(rs.getDate("end_date"));
					tblUserInfo.setTotal(rs.getInt("total"));
				} else {
					tblUserInfo = null;
				}
			}
		} finally {
			// đóng kết nối và trả về dữ liệu
			close(con);
			return tblUserInfo;
		}
		
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUserByLoginName(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public TblUser getUserByLoginName(Integer userId, String loginName) throws ClassNotFoundException, SQLException {
		Connection con = null;
		TblUser tblUser = null;
		try {
			// khởi tạo connection
			con = getConnection();
			// xây dựng truy vấn
			StringBuilder queryBuilder = new StringBuilder("SELECT u.user_id, u.group_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday ");
			queryBuilder.append("FROM tbl_user u WHERE u.login_name = ? ");
			if (userId > 0) {
				queryBuilder.append("AND u.user_id = ? ");
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
			int i;
			while (rs.next()) {
				i = 0;
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
		} finally {
			// đóng kết nối, trả về kết quả
			close(con);
			return tblUser;
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#getUserByEmail(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("finally")
	@Override
	public TblUser getUserByEmail(final Integer userId, String email) throws ClassNotFoundException, SQLException {
		Connection con = null;
		TblUser tblUser = null;
		try {
			// khởi tạo connection
			con = getConnection();
			// xây dựng truy vấn
			StringBuilder queryBuilder = new StringBuilder("SELECT u.user_id, u.group_id, u.login_name, u.full_name, u.full_name_kana, u.email, u.tel, u.birthday ");
			queryBuilder.append("FROM tbl_user u WHERE u.email = ? ");
			if (userId > 0) {
				queryBuilder.append("AND u.user_id = ? ");
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
			int i;
			while (rs.next()) {
				i = 0;
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
		} finally {
			// đóng kết nối, trả về kết quả
			close(con);
			return tblUser;
		}
	}

	/* (non-Javadoc)
	 * @see dao.TblUserDao#insertUser(entity.TblUser)
	 */
	@SuppressWarnings("finally")
	@Override
	public Integer insertUser(TblUser tblUser) throws SQLException {
		// khai báo giá trị trả về
		Integer userId = null;
		// xây dựng truy vấn
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO tbl_user (group_id, login_name, password, salt, full_name, full_name_kana, email, tel, birthday) ");
		query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		int i = 0;
		try {
			if (connection == null) {
				return userId;
			}
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
			if (resultSet.next()) {
				userId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			userId = null;
			throw new SQLException();
		} finally {
			// trả về giá trị
			return userId;
		}
	}
	
}
