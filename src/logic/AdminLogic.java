/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogic.java, 2017-10-23 luuthanhsang
 */
package logic;

/**
 * Interface chứa các phương thức của class AdminLogicImpl
 * 
 * @author luuthanhsang
 *
 */
public interface AdminLogic {
	/**
	 * Phương thức xác thực đăng nhập trên trang login
	 * 
	 * @param username - tài khoản đăng nhập
	 * @param password - mật khẩu đăng nhập
	 * @return boolean - true nếu xác thực thành công | false nếu ngược lại
	 */
	public boolean authAdminLogin(String username, String password);
	
}
