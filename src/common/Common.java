/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * Common.java, 2017-10-23 luuthanhsang
 */
package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import properties.AdminProperties;

/**
 * Class chứa các phương thức chung thường dùng
 * 
 * @author luuthanhsang
 *
 */
public class Common {
	/**
	 * Phương thức kiểm tra một chuỗi đầu vào có null hoặc rỗng không.
	 * 
	 * @param input - Chuỗi cần kiểm tra.
	 * @return boolean - true nếu chuỗi đầu vào null hoặc rỗng | false trong các trường hợp còn lại.
	 */
	public static boolean isNullOrEmpty(String input) {
		return (input == null || input.isEmpty());
	}
	
	/**
	 * Phương thức băm MD5
	 * 
	 * @param text - chuỗi cần băm
	 * @return chuỗi băm
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeMD5(String text) {
		String encryptedPass = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] encryptText = md.digest(text.getBytes());
			BigInteger bigInt = new BigInteger(1, encryptText);
			encryptedPass = bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedPass;
	}

	/**
	 * Kiểm tra admin login
	 * 
	 * @param session - phiên làm việc
	 * @return boolean - true nếu user admin đang đăng nhập | false nếu ngược lại
	 */
	public static boolean checkAdminLogin(HttpSession session) {
		// lấy thông tin của admin
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
		// nếu session null hoặc không có thuộc tính currentUser, return false
		if (session == null || session.getAttribute(Constant.CURRENT_LOGIN_USER) == null) {
			return false;
		} else { // ngược lại:
			// nếu user đang đăng nhập là admin, return true và ngược lại
			if (adminInfo.get(Constant.ADMIN_USER).equals(session.getAttribute(Constant.CURRENT_LOGIN_USER))) {
				return true;
			} else {
				return false;
			}
		}
	}
	
}
