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
 * Servlet implementation class ChangePasswordController
 */
@WebServlet(Constant.CHANGE_PASS_PATH)
public class ChangePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TblUserLogic tblUserLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), 0);
			// check userId tồn tại
			if (userId > 0) {
				if (!tblUserLogic.isExistedUser(userId)) {
					Common.redirectErrorPage(request, response);
					return;
				}
			}
			request.setAttribute(Constant.USER_ID, userId);
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM007);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), 0);
			// kiểm tra user tồn tại
			if (!tblUserLogic.isExistedUser(userId)) {
				Common.redirectErrorPage(request, response);
				return;
			}
			String passWord = request.getParameter(Constant.PASS_ADM003);
			String confirmPass = request.getParameter(Constant.CONFIRM_PASS_ADM003);
			ValidateUser validateUser = new ValidateUser();
			List<String> listMessage = validateUser.validatePassword(passWord, confirmPass);
			// nếu không có lỗi
			if (listMessage.isEmpty()) {
				boolean checkSuccess = tblUserLogic.changePassword(userId, passWord);
				String type = checkSuccess ? Constant.TASK_DONE : Constant.TASK_FAIL;
				// điều hướng đến trang xử lí hiện kết quả change password
				StringBuilder successURL = new StringBuilder();
				successURL.append(request.getContextPath()).append(Constant.SUCCESS_PATH).append("?type=").append(type);
				response.sendRedirect(successURL.toString());
				return;
			}
			request.setAttribute(Constant.USER_ID, userId);
			request.setAttribute(Constant.LIST_ERROR, listMessage);
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM007);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
