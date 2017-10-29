/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogic.java, 2017-10-23 luuthanhsang
 */
package logic;

import javax.servlet.http.HttpSession;

/**
 * Interface chứa các phương thức của class AdminLogicImpl
 * 
 * @author luuthanhsang
 */
public interface AdminLogic {
	/**
	 * Phương thức đăng nhập vào trang admin bằng username, password được nhập vào
	 * 
	 * @param username - tài khoản đăng nhập
	 * @param password - mật khẩu đăng nhập
	 * @return boolean - true nếu xác thực thành công | false nếu ngược lại
	 */
	public boolean attemptLogin(String username, String password);
	
	/**
	 * Phương thức kiểm tra đăng nhập dựa trên session
	 * 
	 * @param curSession - phiên làm việc hiện thời
	 * @return boolean - true nếu user admin đang đăng nhập | false nếu ngược lại
	 */
	public boolean checkLogin(HttpSession curSession);
}
