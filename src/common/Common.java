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
 * @author sanglt
 *
 */
public class Common {
	/**
	 * Phương thức kiểm tra một chuỗi đầu vào có null hoặc rỗng không.
	 * 
	 * @param input
	 *            Chuỗi cần kiểm tra.
	 * @return true nếu chuỗi đầu vào null hoặc rỗng.<br />
	 *         false trong các trường hợp còn lại.
	 */
	public static boolean isNullOrEmpty(String input) {
		return (input == null || input.isEmpty());
	}
	
	/**
	 * Mã hóa MD5
	 * 
	 * @param text
	 *            chuỗi cần mã hóa
	 * @return chuỗi được mã hóa MD5
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
	 * @param session
	 *            phiên làm việc
	 * @return boolean
	 */
	public static boolean checkAdminLogin(HttpSession session) {
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
//		System.out.println("filter: " + session.getAttribute("loginName"));
		if (session == null || session.getAttribute("loginName") == null) {
			return false;
		} else {
			if (adminInfo.get("system_admin").equals(session.getAttribute("loginName"))) {
				return true;
			} else {
				return false;
			}
		}
	}
}
