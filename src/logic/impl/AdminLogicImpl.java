/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogicImpl.java, 2017-10-23 luuthanhsang
 */
package logic.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import common.Common;
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
	 * Phương thức kiểm tra thông tin đăng nhập vào trang admin
	 * 
	 * @param username
	 * @param password
	 * @return boolean
	 */ 
	@Override
	public boolean authAdminLogin(String username, String password) {
		// lấy thông tin đăng nhập và thông tin admin
		Map<String, String> loginInfo = new HashMap<String, String>();
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
		String loginPasswdHash = Common.encodeMD5((String) (password + adminInfo.get("passwd_salt")));
		loginInfo.put("user_name", username);
		loginInfo.put("passwd_hash", loginPasswdHash);
		// kiểm tra so khớp thông tin đăng nhập với thông tin admin
		if (matchAdminInfo(loginInfo, adminInfo)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Phương thức thiết lập giá trị loginUser lên session
	 * 
	 * @param session Đối tượng session cần thiết lập
	 * @param loginName user đang login
	 */
	@Override
	public void setSession(HttpSession session, String loginName) {
		session.setAttribute("loginName", loginName);
	}
	
	/**
	 * Phương thức so khớp thông tin đăng nhập với thông tin admin
	 * 
	 * @param loginInfo thông tin đăng nhập
	 * @param adminInfo thông tin admin
	 * @return boolean đúng khi so khớp | sai khi không so khớp
	 */
	@Override
	public boolean matchAdminInfo(Map<String, String> loginInfo, Map<String, String> adminInfo) {
		if (!adminInfo.get("system_admin").equals(loginInfo.get("user_name"))) {
			return false;
		}
		if (!adminInfo.get("passwd_hash").equals(loginInfo.get("passwd_hash"))) {
			return false;
		}
		return true;
	}
}
