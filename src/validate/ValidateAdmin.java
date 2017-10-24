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
 * @author luuthanhsang
 *
 */
public class ValidateAdmin {
	/**
	 * Kiểm tra thông tin về tài khoản đăng nhập vào hệ thống
	 * 
	 * @param loginName
	 *            tên đăng nhâp
	 * @param password
	 *            mật khẩu
	 * @return List<String> danh sách lỗi
	 */
	public static ArrayList<String> validateLogin(String loginName, String password) {
		ArrayList<String> errList = new ArrayList<String>();
		if (Common.isNullOrEmpty(loginName)) {
			errList.add(MessageErrorProperties.getString("ER001LOGIN"));
		}
		if (Common.isNullOrEmpty(password)) {
			errList.add(MessageErrorProperties.getString("ER001PASS"));
		}
		return errList;
	}
//	public static void main(String[] args) {
//		System.out.println(MessageErrorProperties.getString("ER001LOGIN"));
//		System.out.println(MessageErrorProperties.getString("ER001PASS"));
//	}
}
