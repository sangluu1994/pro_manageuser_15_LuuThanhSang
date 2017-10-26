/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogicImpl.java, 2017-10-23 luuthanhsang
 */
package logic.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;
import logic.AdminLogic;
import properties.AdminProperties;

/**
 * Class xử lí logic của những trang quản lí của admin
 * 
 * @author luuthanhsang
 *
 */
public class AdminLogicImpl implements AdminLogic {

	/* (non-Javadoc)
	 * @see logic.AdminLogic#attemptLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean attemptLogin(String username, String password) {
		// lấy thông tin admin
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
		
		// nếu tên đăng nhập không khớp với admin_user, return false
		if (!adminInfo.get(Constant.ADMIN_USER).equals(username)) {
			return false;
		}
		
		// nếu password không trùng với admin password, return false
		if (!adminInfo.get(Constant.ADMIN_PASS_HASH).equals(Common.encodeMD5(password))) {
			return false;
		}
		// nếu không có sai khác, return true
		return true;
	}

	/* (non-Javadoc)
	 * @see logic.AdminLogic#checkLogin(javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean checkLogin(HttpSession curSession) {
		// lấy thông tin của admin
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
		// nếu session null hoặc không có thuộc tính currentUser, return false
		if (curSession == null || curSession.getAttribute(Constant.CURRENT_LOGIN_USER) == null) {
			return false;
		} else { // ngược lại:
			// nếu user đang đăng nhập là admin, return true và ngược lại
			if (adminInfo.get(Constant.ADMIN_USER).equals(curSession.getAttribute(Constant.CURRENT_LOGIN_USER))) {
				return true;
			} else {
				return false;
			}
		}
	}
	
}
