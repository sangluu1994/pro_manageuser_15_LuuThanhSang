/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ChangePasswordController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
import common.Constant;
import logic.TblUserLogic;
import logic.impl.TblUserLogicImpl;
import validate.ValidateUser;

/**
 * Class xử lí yêu cầu change password user
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.CHANGE_PASSWORD_SERVLET,
		urlPatterns = Constant.CHANGE_PASS_PATH)
public class ChangePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo đối tượng xử lí logic sử dụng trong class
	private TblUserLogic tblUserLogic;
       
    /**
     * Constructor
     */
    public ChangePasswordController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * Phương thức xử lí yêu cầu gọi đến màn hình change password
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// check userId tồn tại
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), 0);
			if (!tblUserLogic.isExistedUser(userId)) {
				Common.redirectErrorPage(request, response);
				return;
			}
			// set userId sang view ADM007 - changePass
			request.setAttribute(Constant.USER_ID, userId);
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM007);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

	/**
	 * Phương thức xử lí yêu cầu change password user
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// kiểm tra user tồn tại
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), 0);
			if (!tblUserLogic.isExistedUser(userId)) {
				Common.redirectErrorPage(request, response);
				return;
			}
			// lấy các giá trị password muốn thay đổi được truyền lên
			String passWord = request.getParameter(Constant.PASS_ADM003);
			String confirmPass = request.getParameter(Constant.CONFIRM_PASS_ADM003);
			// validate thông tin truyền lên
			ValidateUser validateUser = new ValidateUser();
			List<String> listMessage = validateUser.validatePassword(passWord, confirmPass);
			// nếu không có lỗi
			if (listMessage.isEmpty()) {
				// thực hiện change password
				boolean checkSuccess = tblUserLogic.changePassword(userId, passWord);
				String type = checkSuccess ? Constant.TASK_DONE : Constant.TASK_FAIL;
				// điều hướng đến trang xử lí hiện kết quả change password
				StringBuilder successURL = new StringBuilder();
				successURL.append(request.getContextPath()).append(Constant.SUCCESS_PATH).append("?type=").append(type);
				response.sendRedirect(successURL.toString());
			} else { // nếu có lỗi, trả về view ADM007 với danh sách lỗi
				request.setAttribute(Constant.USER_ID, userId);
				request.setAttribute(Constant.LIST_ERROR, listMessage);
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM007);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
