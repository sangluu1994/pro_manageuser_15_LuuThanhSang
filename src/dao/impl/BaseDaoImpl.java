/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * BaseDaoImpl.java, 2017-10-25 luuthanhsang
 *
 */
package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.BaseDao;
import properties.DatabaseProperties;

/**
 * @author luuthanhsang
 *
 */
public class BaseDaoImpl implements BaseDao{

	/* (non-Javadoc)
	 * @see dao.BaseDao#getConnection()
	 */
	@Override
	public Connection getConnection() {
		Connection conn = null;
		String DB_URL = DatabaseProperties.getString("url");
		String USER_NAME = DatabaseProperties.getString("user");
		String PASS_WORD = DatabaseProperties.getString("password");
		String DRIVE = DatabaseProperties.getString("driver");
		try {
			Class.forName(DRIVE);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASS_WORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#close(java.sql.Connection)
	 */
	@Override
	public void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#commit(java.sql.Connection)
	 */
	@Override
	public void commit(Connection connection) {
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#rollback(java.sql.Connection)
	 */
	@Override
	public void rollback(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.BaseDao#disableAutoCommit(java.sql.Connection)
	 */
	@Override
	public void disableAutoCommit(Connection connection) {
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
