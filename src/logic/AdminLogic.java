/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogic.java, 2017-10-23 luuthanhsang
 */
package logic;

import java.util.ArrayList;


/**
 * Interface chứa các phương thức của class AdminLogicImpl
 * 
 * @author luuthanhsang
 */
public interface AdminLogic {
	/**
	 * Phương thức kiểm tra thông tin login
	 * 
	 * @param loginName - tên đăng nhâp
	 * @param password - mật khẩu
	 * @return errList - danh sách lỗi đăng nhập
	 */
	public ArrayList<String> validateLogin(String loginName, String password);
}
