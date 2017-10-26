/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * Common.java, 2017-10-23 luuthanhsang
 */
package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	 * Phương thức format điều kiện search, format lại kí tự đặc biệt
	 * 
	 * @param ioString
	 * @return ioString
	 */
	public static String formatCondSearch(String ioString) {
		// Khai báo danh sách các kí tự đặc biệt
		final String[] specialChars = { "\\", "%", "_", "[", "]", "-", "!" };
		// Thêm \ vào trước các kí tự đặc biệt
		int specialCharsLeng = specialChars.length;
		for (int i = 0; i < specialCharsLeng; i++) {
			if (ioString.contains(specialChars[i])) {
				ioString = ioString.replace(specialChars[i], "\\" + specialChars[i]);
			}
		}
		return ioString;
	}

	/**
	 * Phương thức chuyển đổi dữ liệu String sang Int
	 * 
	 * @param inputString - chuỗi đầu vào
	 * @return outputInt - số nguyên đầu ra
	 */
	@SuppressWarnings("finally")
	public static int convertStringToInt(String inputString) {
		// khởi tạo giá trị trả về
		int outputInt = 0;
		try {
			// parse String đầu vào sang kiểu int
			outputInt = Integer.parseInt(inputString);
		} catch (Exception e) {
			// show console log ngoại lệ
			e.printStackTrace();
		} finally {
			// trả về kết quả
			return outputInt;
		}
	}
	
	/**
	 * Lấy offset của trang hiện tại
	 * 
	 * @param currentPage - trang hiện tại
	 * @param limit - giới hạn số user trên 1 trang
	 * @return offset của trang hiện tại
	 */
	public static int getOffset(int currentPage, int limit) {
		return (currentPage - 1) * limit;
	}
	
}
