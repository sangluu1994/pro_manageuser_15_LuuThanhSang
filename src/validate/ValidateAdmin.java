/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ValidateAdmin.java, 2017-10-23 luuthanhsang
 */
package validate;

import java.util.ArrayList;
import common.Common;
import common.Constant;
import properties.AdminProperties;
import properties.MessageErrorProperties;

/**
 * Class validate yêu cầu đăng nhập vào trang admin
 * 
 * @author luuthanhsang
 */
public class ValidateAdmin {
	/**
	 * Phương thức kiểm tra thông tin login
	 * 
	 * @param loginName - tên đăng nhâp
	 * @param password - mật khẩu
	 * @return errList - danh sách lỗi đăng nhập
	 */
	public static ArrayList<String> validateLogin(String loginName, String password) {
		// khởi tạo danh sách lỗi
		ArrayList<String> errList = new ArrayList<String>();
		
		// kiểm tra username, nếu rỗng thì gán lỗi vào danh sách lỗi và trả về
		if (Common.isNullOrEmpty(loginName)) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER001LOGIN));
			return errList;
		}
		// nếu tên đăng nhập không khớp với admin_user, gán lỗi tương ứng và trả về danh sách lỗi
		if (!AdminProperties.getValue(Constant.ADMIN_USER).equals(loginName)) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER016));
			return errList;
		}
		// nếu password không trùng với admin password, gán lỗi tương ứng và trả về danh sách lỗi
		if (!AdminProperties.getValue(Constant.ADMIN_PASS_HASH).equals(Common.SHA1(password))) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER016));
			return errList;
		}
		// trả về danh sách lỗi là rỗng nếu không có lỗi
		return errList;
	}
	
}
