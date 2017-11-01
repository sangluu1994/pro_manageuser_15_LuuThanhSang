/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * Common.java, 2017-10-23 luuthanhsang
 */
package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import properties.ConfigProperties;

/**
 * Class chứa các phương thức chung thường dùng
 * 
 * @author luuthanhsang
 */
public class Common {
	/**
	 * Phương thức kiểm tra một chuỗi đầu vào có null hoặc rỗng không.
	 * 
	 * @param input - Chuỗi cần kiểm tra.
	 * @return boolean - true nếu chuỗi đầu vào null hoặc rỗng | false trong các trường hợp còn lại.
	 */
	public static boolean isNullOrEmpty(String input) {
		return (input == null || input.trim().isEmpty());
	}
	
	/**
	 * Hàm kiểm tra input truyền vào có phải là số không
	 * @param input - đầu vào
	 * @return boolean - true nếu là số | false nếu ngược lại
	 */
	public static boolean isNumber(String input) {
		return input.matches("-?\\d+(\\.\\d+)?");
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
		// trả về chuỗi đã format
		return ioString;
	}

	/**
	 * Phương thức chuyển đổi dữ liệu String sang Int
	 * 
	 * @param inputString - chuỗi đầu vào
	 * @return outputInt - số nguyên đầu ra
	 */
	public static int convertStringToInt(String inputString) {
		// khởi tạo giá trị trả về
		int outputInt = 0;
		// nếu chuỗi đầu vào là số
		if (isNumber(inputString)) {
			// parse String đầu vào sang kiểu int
			outputInt = Integer.parseInt(inputString);
		}
		// trả về kết quả
		return outputInt;
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
	
	/**
	 * Phương thức lấy danh sách phân trang
	 * 
	 * @param totalRecords - tổng số bản ghi
	 * @param limit - tống số bản ghi trên 1 trang
	 * @param currentPage - trang hiện tại
	 * @return danh sách các phân trang
	 */
	public static List<Integer> getListPaging(int totalRecords, int limit, int currentPage) {
		// khởi tạo danh sách phân trang
		List<Integer> listPaging = new ArrayList<>();
		// lấy tổng số trang
		int totalPage = getTotalPage(totalRecords, limit);
		// kiểm tra tổng số trang rỗng và = 1
		if (totalPage == 0 || totalPage == 1) {
			return listPaging;
		}
		// lấy số trang tối đa được hiển thị trên vùng phân trang
		int pageLimit = convertStringToInt(ConfigProperties.getValue(Constant.PAGE_LIMIT));
		// lấy trang bắt đầu của vùng phân trang sẽ hiển thị
		int startPage = getStartPage(currentPage, pageLimit);
		// lấy trang kết thúc của vùng phân trang sẽ hiển thị
		int endPage = getEndPage(startPage, pageLimit, totalPage);
		// truyền các phân trang vào danh sách phân trang
		for (int i = startPage; i <= endPage; i++) {
			listPaging.add(i);
		}
		// trả về danh sách phân trang
		return listPaging;
	}
	
	/**
	 * Phương thức tính trang cuối cùng của vùng phân trang sẽ hiển thị
	 * 
	 * @param startPage - trang bắt đầu của vùng phân trang sẽ hiển thị
	 * @param pageLimit - số trang tối đa của 1 phân đoạn
	 * @param totalPage - tổng số trang
	 * @return trang cuối cùng của vùng phân trang sẽ hiển thị
	 */
	public static int getEndPage(int startPage, int pageLimit, int totalPage) {
		// khởi tạo giá trị của trang cuối của vùng phân trang sẽ hiển thị
		int endPage = pageLimit;
		// nếu endPage là bội số của số trang tối đa của 1 phân đoạn
		if (startPage + pageLimit - 1 <= totalPage) {
			endPage = startPage + pageLimit - 1;
		} else { // nếu trang cuối không là bội số của số trang tối đa của 1 phân đoạn
			endPage = totalPage;
		}
		// trả về endPage
		return endPage;
	}

	/**
	 * Phương thức tính trang bắt đầu của vùng phân trang sẽ hiển thị
	 * @param currentPage - trang hiện tại
	 * @param pageLimit - số trang tối đa của 1 phân đoạn
	 * @return trang bắt đầu của vùng phân trang sẽ hiển thị
	 */
	public static int getStartPage(int currentPage, int pageLimit) {
		// lấy đoạn phân trang hiện tại
		int currentSegment = getCurrentSegment(currentPage, pageLimit);
		// lấy và trả về trang bắt đầu của vùng phân trang sẽ hiển thị
		int startPage = ((currentSegment - 1) * pageLimit) + 1;
		return startPage;
	}

	/**
	 * Phương thức tính phân đoạn hiện tại của trang hiện tại
	 * 
	 * @param currentPage - trang hiện tại
	 * @param pageLimit - số trang tối đa của 1 phân đoạn
	 * @return chỉ số phân đoạn hiện tại
	 */
	public static int getCurrentSegment(int currentPage, int pageLimit) {
		return (int) Math.ceil((double) currentPage / pageLimit);
	}

	/**
	 * Phương thức tính tổng số trang của chức năng phân trang
	 * 
	 * @param totalRecords - tổng số bản ghi
	 * @param limit - số lượng bản ghi trên 1 phân trang
	 * @return totalPage - tổng số trang
	 */
	public static int getTotalPage(int totalRecords, int limit) {
		if (totalRecords <= 0) {
			return 0;
		}
		return (int) Math.ceil((double) totalRecords / limit);
	}
	
}
