/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogic.java, 2017-10-23 luuthanhsang
 */
package logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author luuthanhsang
 *
 */
public interface AdminLogic {
	/**
	 * Phương thức kiểm tra thông tin đăng nhập của admin
	 * 
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public boolean authAdminLogin(String username, String password);
	
	/**
	 * Phương thức thiết lập giá trị loginUser lên session
	 * 
	 * @param session Đối tượng session cần thiết lập
	 * @param loginName user đang login
	 */
	public void setSession(HttpSession session, String loginName);
	
	public boolean matchAdminInfo(Map<String, String> loginInfo, Map<String, String> adminInfo);
	
}
