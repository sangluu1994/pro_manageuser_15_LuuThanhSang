/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ValidateAdmin.java, 2017-10-23 luuthanhsang
 */
package validate;

import java.util.ArrayList;

import common.Common;
import properties.MessageErrorProperties;

/**
 * Class validate admin login
 * 
 * @author luuthanhsang
 */
public class ValidateAdmin {
	/**
	 * Phương thức kiểm tra nhập liệu từ form đăng nhập
	 * 
	 * @param loginName - tên đăng nhâp
	 * @param password - mật khẩu
	 * @return ArrayList<String> danh sách lỗi
	 */
	public static ArrayList<String> validateLogin(String loginName, String password) {
		// khởi tạo danh sách lỗi nhập liệu
		ArrayList<String> errList = new ArrayList<String>();
		// kiểm tra username và password, nếu rỗng thì gán lỗi vào danh sách lỗi
		if (Common.isNullOrEmpty(loginName)) {
			errList.add(MessageErrorProperties.getString("ER001LOGIN"));
		}
		if (Common.isNullOrEmpty(password)) {
			errList.add(MessageErrorProperties.getString("ER001PASS"));
		}
		// trả về danh sách lỗi nhập liệu
		return errList;
	}
	
}
