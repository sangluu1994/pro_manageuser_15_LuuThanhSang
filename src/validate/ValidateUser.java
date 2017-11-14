/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ValidateUser.java, 2017-11-06 luuthanhsang
 */
package validate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import common.Common;
import common.Constant;
import entity.UserInfor;
import logic.MstGroupLogic;
import logic.MstJapanLogic;
import logic.TblUserLogic;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import logic.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;

/**
 * Class chứa các phương thức thực hiện kiểm tra dữ liệu nhập vào
 *
 * @author luuthanhsang
 */
public class ValidateUser {
	private TblUserLogic tblUserLogic;
	private MstGroupLogic mstGroupLogic;
	private MstJapanLogic mstJapanLogic;
	
	/**
	 * constructor
	 */
	public ValidateUser() {
		tblUserLogic = new TblUserLogicImpl();
		mstGroupLogic = new MstGroupLogicImpl();
		mstJapanLogic = new MstJapanLogicImpl();
	}
	
	/**
	 * Phương thức validate đối tượng user
	 * 
	 * @param userInfor - đối tượng user cần kiểm tra
	 * @return listError : danh sách lỗi
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ParseException 
	 */
	public List<String> validateUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException, ParseException {
		List<String> listError = new ArrayList<>();
		int userId = userInfor.getUserId();
		System.out.println("userId: " + userId);
		
		// nếu là trường hợp add thì kiểm tra loginName, pass, re-pass
		if (userId == 0) {
			// check login name (4)
			String loginName = userInfor.getLoginName();
			if (loginName == null || Constant.EMPTY_STRING.equals(loginName.trim())) { // check empty
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER001LOGIN));
			} else if (loginName.length() < Constant.MIN_LENGTH_LOGIN_NAME
					|| loginName.length() > Constant.MAX_LENGTH_LOGIN_NAME) { // check length
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER007LOGIN));
			} else if (!loginName.matches(Constant.LOGIN_NAME_PATTERN)) { // check format
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER019));
			} else if (tblUserLogic.checkExistedLoginName(userId, loginName)) { // check exist
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER003LOGIN));
			}
			
			// check password (3)
			String pass = userInfor.getPass();
			if (pass == null || Constant.EMPTY_STRING.equals(pass.trim())) { // check empty
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER001PASS));
			} else {
				if (pass.length() < Constant.MIN_LENGTH_PASSWORD
						|| pass.length() > Constant.MAX_LENGTH_PASSWORD) { // check length
					listError.add(MessageErrorProperties.getErrMsg(Constant.ER007PASS));
				} else if (!pass.matches(Constant.PASSWORD_PATTERN)) { // check format
					listError.add(MessageErrorProperties.getErrMsg(Constant.ER008PASS));
				}
	
				// check confirm password (1)
				String rePass = userInfor.getRePass();
				if (rePass == null || Constant.EMPTY_STRING.equals(rePass.trim()) || !rePass.equals(pass)) { // check not match
					listError.add(MessageErrorProperties.getErrMsg(Constant.ER017));
				}
			}
		}
		
		// check group id (2)
		Integer groupId = userInfor.getGroupId();
		if (groupId == null || Constant.DEFAULT_GROUP_ID == groupId) { // check not select
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER002GROUP));
		} else if (!mstGroupLogic.isRealGroup(groupId)) { // check exist
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER004GROUP));
		}

		// check full name (2)
		String fullName = userInfor.getFullName();
		if (fullName == null || Constant.EMPTY_STRING.equals(fullName.trim())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001FULL));
		} else if (fullName.length() > Constant.MAX_LENGTH_FULL_NAME) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006FULL));
		}

		// check full name kana (2)
		String fullNameKana = userInfor.getFullNameKana();
		if (fullNameKana != null || !Constant.EMPTY_STRING.equals(fullNameKana)) {
			if (fullNameKana.length() > Constant.MAX_LENGTH_FULL_NAME_KANA) { // check length
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER006FULL));
			} else if (!Common.isKanaString(fullNameKana)) { // check format
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER009KATA));
			}
		}

		// check birthday (1)
		String birthYearInfo = userInfor.getBirthYear();
		String birthMonthInfo = userInfor.getBirthMonth();
		String birthDateInfo = userInfor.getBirthDate();
		if (birthYearInfo != null && birthMonthInfo != null && birthDateInfo != null) { // (check not null)
			int birthYear = Common.convertStringToInt(birthYearInfo);
			int birthMonth = Common.convertStringToInt(birthMonthInfo);
			int birthDate = Common.convertStringToInt(birthDateInfo);
			if (!Common.isRealDay(birthYear, birthMonth, birthDate) 
					|| Common.toDate(birthYear, birthMonth, birthDate).compareTo(new Date()) >= 0) { // check real day and < current date
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER011BIRTH));
			} else {
				userInfor.setBirthday(Common.toDate(birthYear, birthMonth, birthDate));
			}
		} 
		
		// check email (4)
		String email = userInfor.getEmail();
		if (email == null || Constant.EMPTY_STRING.equals(email.trim())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001MAIL));
		} else if (email.length() > Constant.EMAIL_MAX_LENGTH) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006MAIL));
		} else if (!email.matches(Constant.EMAIL_PATTERN)) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER005MAIL));
		} else if (tblUserLogic.checkExistedEmail(userId, email)) { // check exist
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER003MAIL));
		}

		// check phone number (3)
		String tel = userInfor.getTel();
		if (tel == null || Constant.EMPTY_STRING.equals(tel.trim())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001TEL));
		} else if (tel.length() > Constant.TEL_MAX_LENGTH) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006TEL));
		} else if (!tel.matches(Constant.TEL_PATTERN)) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER005TEL));
		}

		// nếu có chọn vùng trình độ tiếng Nhật
		String codeLevel = userInfor.getCodeLevel();
		if (codeLevel != null && !Constant.DEFAULT_CODE_LEVEL.equals(codeLevel)) { // (check not null)
			// check code level exist (1)
			if (!mstJapanLogic.isRealJpLv(codeLevel)) { // check exist
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER004JAPAN));
			}
			
			String startYearInfo = userInfor.getStartYear();
			String startMonthInfo = userInfor.getStartMonth();
			String startDayInfo = userInfor.getStartDay();
			String endYearInfo = userInfor.getEndYear();
			String endMonthInfo = userInfor.getEndMonth();
			String endDayInfo = userInfor.getEndDay();
			if (startYearInfo != null && startMonthInfo != null && startDayInfo != null 
					&& endYearInfo != null && endMonthInfo != null && endDayInfo != null) { // (check not null)
				int startYear = Common.convertStringToInt(startYearInfo);
				int startMonth = Common.convertStringToInt(startMonthInfo);
				int startDay = Common.convertStringToInt(startDayInfo);
				int endYear = Common.convertStringToInt(endYearInfo);
				int endMonth = Common.convertStringToInt(endMonthInfo);
				int endDay = Common.convertStringToInt(endDayInfo);
	
				// check start date (1), check end date (2)
				if (!Common.isRealDay(startYear, startMonth, startDay)) { // check real day
					listError.add(MessageErrorProperties.getErrMsg(Constant.ER011START));
				} else if (!Common.isRealDay(endYear, endMonth, endDay)) { // check real day
					listError.add(MessageErrorProperties.getErrMsg(Constant.ER011END));
				} else {
					Date startDate = Common.toDate(startYear, startMonth, startDay);
					Date endDate = Common.toDate(endYear, endMonth, endDay);
					if (endDate.compareTo(startDate) <= 0) { // check endDate <= startDate
						listError.add(MessageErrorProperties.getErrMsg(Constant.ER012));
					}
				}
			}

			// check total (2)
			Integer total = userInfor.getTotal();
			if (total == null || Constant.DEFAULT_TOTAL == total) { // check empty
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER001TOTAL));
			} else if (!Common.isHalfSizeNumber(total)) { // check halfsize
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER018TOTAL));
			}
		}

		return listError;
	}

	/**
	 * Validate password
	 *
	 * @param passWord - mật khẩu
	 * @param confirmPass - mật khẩu xác nhận
	 * @return errList - danh sách lỗi
	 */
	public List<String> validatePassword(String passWord, String confirmPass) {
		List<String> errList = new ArrayList<>();
		// check password
		if (passWord == null || Constant.EMPTY_STRING.equals(passWord)) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER001PASS));
		} else {
			if (passWord.length() < Constant.MIN_LENGTH_PASSWORD
					|| passWord.length() > Constant.MAX_LENGTH_PASSWORD) {
				errList.add(MessageErrorProperties.getErrMsg(Constant.ER007PASS));
			} else if (!passWord.matches(Constant.PASSWORD_PATTERN)) {
				errList.add(MessageErrorProperties.getErrMsg(Constant.ER008PASS));
			}
			// check confirm password
			if (confirmPass == null || Constant.EMPTY_STRING.equals(confirmPass)) {
				errList.add(MessageErrorProperties.getErrMsg(Constant.ER017));
			} else if (!confirmPass.equals(passWord)) {
				errList.add(MessageErrorProperties.getErrMsg(Constant.ER017));
			}
		}
		return errList;
	}
	
}
