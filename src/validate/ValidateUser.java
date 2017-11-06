/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ValidateUser.java, 2017-11-06 luuthanhsang
 */
package validate;

import java.sql.SQLException;
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
 * 
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
	 * Phương thức validate user infor
	 * 
	 * @param userInfor - đối tượng user infor
	 * @return List<String> : list message error
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<String> validateUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		List<String> listError = new ArrayList<>();
		// check login name (4)
		if (Constant.EMPTY_STRING.equals(userInfor.getLoginName())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001LOGIN));
		} else if (userInfor.getLoginName().length() < Constant.MIN_LENGTH_LOGIN_NAME
				|| userInfor.getLoginName().length() > Constant.MAX_LENGTH_LOGIN_NAME) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER007LOGIN));
		} else if (!userInfor.getLoginName().matches(Constant.LOGIN_NAME_PATTERN)) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER019));
		} else if (tblUserLogic.checkExistedLoginName(null, userInfor.getLoginName())) { // check exist
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER003));
		}

		// check group id (2)
		if (Constant.DEFAULT_GROUP_ID == userInfor.getGroupId()) { // check not select
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER002GROUP));
		} else if (!mstGroupLogic.checkExistGroup(userInfor.getGroupId())) { // check exist
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER004GROUP));
		}

		// check full name (2)
		if (Constant.EMPTY_STRING.equals(userInfor.getFullName())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001FULL));
		} else if (userInfor.getFullName().length() > Constant.MAX_LENGTH_FULL_NAME) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006FULL));
		}

		// check full name kana (2)
		if (userInfor.getFullNameKana() != null
				&& userInfor.getFullNameKana().length() > Constant.MAX_LENGTH_FULL_NAME_KANA) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006FULL));
		} else if (!Common.isKanaString(userInfor.getFullNameKana())) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER009KATA));
		}

		// check birthday (1)
		if (!Common.isRealDay(Common.convertStringToInt(userInfor.getBirthYear()), Common.convertStringToInt(userInfor.getBirthMonth()),
				Common.convertStringToInt(userInfor.getBirthDate()))) { // check real day
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER011BIRTH));
		}

		// check email (4)
		if (Constant.EMPTY_STRING.equals(userInfor.getEmail())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001MAIL));
		} else if (userInfor.getEmail().length() > 255) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006MAIL));
		} else if (!userInfor.getEmail().matches(Constant.EMAIL_PATTERN)) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER005MAIL));
		} else if (tblUserLogic.checkExistedEmail(null, userInfor.getEmail())) { // check exist
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER003MAIL));
		}

		// check phone number (3)
		if (Constant.EMPTY_STRING.equals(userInfor.getTel())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001TEL));
		} else if (userInfor.getTel().length() > Constant.MAX_LENGTH_TEL) { // check length
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER006TEL));
		} else if (!userInfor.getTel().matches(Constant.TEL_PATTERN)) { // check format
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER005TEL));
		}

		// check password (3)
		if (Constant.EMPTY_STRING.equals(userInfor.getPass())) { // check empty
			listError.add(MessageErrorProperties.getErrMsg(Constant.ER001PASS));
		} else {
			if (userInfor.getPass().length() < Constant.MIN_LENGTH_PASSWORD
					|| userInfor.getPass().length() > Constant.MAX_LENGTH_PASSWORD) { // check length
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER007PASS));
			} else if (!userInfor.getPass().matches(Constant.PASSWORD_PATTERN)) { // check format
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER008PASS));
			}

			// check confirm password (1)
			if (Constant.EMPTY_STRING.equals(userInfor.getRePass())) { // check empty
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER017));
			} else if (!userInfor.getRePass().equals(userInfor.getPass())) { // check not match
				listError.add(MessageErrorProperties.getErrMsg(Constant.ER017));
			}
		}

		// nếu có chọn vùng trình độ tiếng Nhật
		String codeLevel = userInfor.getCodeLevel();
		if (!Constant.DEFAULT_CODE_LEVEL.equals(codeLevel)) {
			int startYear = Common.convertStringToInt(userInfor.getStartYear());
			int startMonth = Common.convertStringToInt(userInfor.getStartMonth());
			int startDay = Common.convertStringToInt(userInfor.getStartDay());
			Date startDate = Common.toDate(startYear, startMonth, startDay);

//			// check code level exist (1)
//			if (!mstJapanLogic.checkExistJapan(codeLevel)) { // check exist
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER004JAPAN));
//			}
//
//			// check start date (1)
//			if (startDate == null) { // check
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER011START));
//			}
//			int endYear = Common.convertStringToInt(userInfor.getEndYear());
//			int endMonth = Common.convertStringToInt(userInfor.getEndMonth());
//			int endDay = Common.convertStringToInt(userInfor.getEndDay());
//			Date endDate = Common.toDate(endYear, endMonth, endDay);
//
//			// check end date (2)
//			if (endDate == null) {
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER011END));
//			} else if (endDate.compareTo(startDate) <= 0) {
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER012));
//			}
//
//			// check total (2)
//			String total = 
//			if (Constant.DEFAULT_TOTAL == userInfor.getTotal()) {
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER001TOTAL));
//			} else if (!userInfor.getTotal().matches(Constant.TOTAL_PATTERN)) {
//				listError.add(MessageErrorProperties.getErrMsg(Constant.ER018TOTAL));
//			}
		}

		return listError;
	}
}
