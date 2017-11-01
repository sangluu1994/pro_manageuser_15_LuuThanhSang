/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ValidateAdmin.java, 2017-10-23 luuthanhsang
 */
package validate;

import java.util.ArrayList;
import java.util.Map;
import common.Common;
import common.Constant;
import properties.AdminProperties;
import properties.MessageErrorProperties;

/**
 * Class validate admin login
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
		// lấy thông tin admin
		Map<String, String> adminInfo = AdminProperties.getAdminInfo();
		// khởi tạo danh sách lỗi
		ArrayList<String> errList = new ArrayList<String>();
		
		// kiểm tra username, nếu rỗng thì gán lỗi vào danh sách lỗi và trả về
		if (Common.isNullOrEmpty(loginName)) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER001));
			return errList;
		}
		// nếu tên đăng nhập không khớp với admin_user, return lỗi
		if (!adminInfo.get(Constant.ADMIN_USER).equals(loginName)) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER016));
			return errList;
		}
		// nếu password không trùng với admin password, return lỗi
		if (!adminInfo.get(Constant.ADMIN_PASS_HASH).equals(Common.encodeMD5(password))) {
			errList.add(MessageErrorProperties.getErrMsg(Constant.ER016));
			return errList;
		}
		// trả về danh sách lỗi là null nếu không có lỗi
		return errList;
	}
	
}
