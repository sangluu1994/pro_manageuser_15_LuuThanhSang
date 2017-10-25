/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogicImpl.java, 2017-10-23 luuthanhsang
 */
package logic.impl;

import java.util.Map;

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
	/**
	 * Phương thức xác thực đăng nhập trên trang login
	 * 
	 * @param username - tài khoản đăng nhập
	 * @param password - mật khẩu đăng nhập
	 * @return boolean - true nếu xác thực thành công | false nếu ngược lại
	 */ 
	@Override
	public boolean authAdminLogin(String username, String password) {
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
	
}
