/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * Common.java, 2017-10-23 luuthanhsang
 */
package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * Phương thức lấy số lượng tối đa các bản ghi hiển thị trên 1 trang
	 *
	 * @return số lượng tối đa các bản ghi hiển thị trên 1 trang
	 */
	public static int getLimit() {
		return Common.convertStringToInt(ConfigProperties.getValue(Constant.LIMIT));
	}
	
	/**
	 * Phương thức lấy số lượng page tối đa hiển thị ở vùng paging 
	 *
	 * @return số lượng page tối đa hiển thị ở vùng paging
	 */
	public static int getPageLimit() {
		return Common.convertStringToInt(ConfigProperties.getValue(Constant.PAGE_LIMIT));
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
	
	/**
	 * Lấy danh sách các năm từ năm 1900 -> năm hiện tại + 1
	 *
	 * @param fromYear - Lấy từ năm nào
	 * @param toYear - Lấy đến năm nào
	 * @return Danh sách các năm từ năm 1900 -> năm hiện tại + 1
	 */
	public static List<Integer> getListYear(int fromYear , int toYear) {
		List<Integer> listYears = new ArrayList<>();
		for (int i = fromYear; i <= toYear; i++) {
			listYears.add(i);
		}
		return listYears;
	}
	
	/**
	 * Lấy năm hiện tại
	 * 
	 * @return năm hiện tại
	 */
	public static int getYearNow() {
		LocalDate now = LocalDate.now();
		return now.getYear();
	}
	
	/**
	 * Lấy danh sách các tháng  từ 1->12
	 * 
	 * @return danh sách các tháng  từ 1->12
	 */
	public static List<Integer> getListMonth() {
		List<Integer> listMonths = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			listMonths.add(i);
		}
		return listMonths;
	}
	
	/**
	 * Lấy danh sách các ngày trong 1 tháng
	 * 
	 * @return danh sách ngày trong tháng
	 */
	public static List<Integer> getListDay() {
		List<Integer> listDays = new ArrayList<>();
		for (int i = 1; i <= 31; i++) {
			listDays.add(i);
		}
		return listDays;
	}
	
	/**
	 * Convert các số năm, tháng, ngày thành 1 chuỗi ngày tháng có format yyyy/mm/dd
	 * 
	 * @param year - năm
	 * @param month - tháng
	 * @param day - ngày
	 * @return chuỗi ngày tháng năm dạng yyyy/mm/dd
	 */
	public static String convertToString(int year, int month, int day) {
		String date = "";
		date = date.concat(String.valueOf(year)).concat("/").concat(String.valueOf(month).concat("/").concat(String.valueOf(day)));
		return date;
	}
	
	/**
	 * Phương thức lấy ra năm, tháng, ngày hiện tại.
	 * 
	 * @return List<Integer> {Năm, Tháng, Ngày} hiện tại
	 */
	public static List<Integer> getCurrentYearMonthDay() {
		LocalDate now = LocalDate.now();
		int currentDay = now.getDayOfMonth();
		int currentMonth = now.getMonthValue();
		int currentYear = now.getYear();
		List<Integer> currentDate = new ArrayList<>();
		currentDate.add(currentYear);
		currentDate.add(currentMonth);
		currentDate.add(currentDay);
		return currentDate;
	}
	
	/**
	 * Convert các số năm, tháng, ngày thành 1 ngày tháng có format yyyy/MM/dd
	 * 
	 * @param year - năm 
	 * @param month - tháng
	 * @param day - ngày
	 * @return date dưới dạng yyyy/MM/dd
	 * @throws ParseException
	 */
	public static Date toDate(int year, int month, int day) throws ParseException {
		Date date = new SimpleDateFormat("yyyy/MM/dd").parse(convertToString(year, month, day));
		return date;
	}
	
	/**
	 * Hàm check số halfSize
	 * 
	 * @param total - tổng điểm cần check
	 * @return true nếu là số halfSize - false nếu không phải
	 */
	public static boolean isHalfSizeNumber(String total) {
		if (total == null) {
			throw new NullPointerException("String input cannot be null");
		}
		return total.matches("^\\d+$");

	}
	
	/**
	 * Phương thức kiểm tra xem 1 ký tự có phải là ký tự kana hay không.
	 * 
	 * @param input - kí tự cần kiểm tra.
	 * @return true nếu ký tự là ký tự Katakana | false trong trường hợp còn lại
	 */
	public static boolean isKanaChar(char input) {
		int temp = (int) input;
		if ((temp >= 0x30A0 && temp <= 0x30FF) || (temp >= 0xFF65 && temp <= 0xFF9F)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Phương thức kiểm tra chuỗi đầu vào có phải là chuỗi ký tự Katakana hay không.
	 * (Chỉ chấp nhận các ký tự fullsize)
	 * 
	 * @param input - chuỗi cần kiểm tra.
	 * @return true nếu chuỗi đầu vào là chuỗi ký tự Katakana | false trong các trường hợp còn lại.
	 */
	public static boolean isKanaString(String input) {
		if (input == null) {
			throw new NullPointerException("String input cannot be null");
		}
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (!isKanaChar(charArray[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean areAnyFullWidth(String input) {
	    for(char c : input.toCharArray())
	        if(c >= 0xFF00 && c <= 0xFFEF)
	            return true;
	        return false;
	}
	
	/**
	 * Phương thức kiểm tra tính tồn tại của ngày nhập vào.
	 * 
	 * @param year
	 *            Năm cần kiểm tra.
	 * @param month
	 *            Tháng cần kiểm tra.
	 * @param day
	 *            Ngày cần kiểm tra
	 * 
	 * @return true nếu ngày nhập vào thực sự tồn tại. <br />
	 *         false trong các trường hợp còn lại.
	 */
	public static boolean isRealDay(int year, int month, int day) {
		int yearNow = getYearNow();
		if (year < 1900 || year > yearNow + 1) {
			return false;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day < 1 || day > 31) {
				return false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day < 1 || day > 30) {
				return false;
			}
			break;
		case 2:
			if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
				if (day < 1 || day > 29) {
					return false;
				}
			} else {
				if (day < 1 || day > 28) {
					return false;
				}
			}
			break;
		default:
			return false;
		}
		return true;
	}

	
}
