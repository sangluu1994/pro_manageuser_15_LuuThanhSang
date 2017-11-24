/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * DeleteUserController.java, 2017-11-02 luuthanhsang
 */
package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
import common.Constant;
import logic.TblUserLogic;
import logic.impl.TblUserLogicImpl;

/**
 * Servlet implementation class DeleteUserController
 */
@WebServlet(Constant.DELETE_USER_PATH)
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TblUserLogic tblUserLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_INFOR_ID), 0);
			// check userId tồn tại
			if (!tblUserLogic.isExistedUser(userId)) {
				Common.redirectErrorPage(request, response);
				return;
			}
			
			// gọi hàm xử lí logic delete user
			boolean success = tblUserLogic.removeUser(userId);
			// điều hướng đến trang xử lí hiện kết quả xóa user
			StringBuilder successURL = new StringBuilder(request.getContextPath()).append(Constant.SUCCESS_PATH).append("?type=");
			String type = success ? Constant.TASK_DONE : Constant.TASK_FAIL;
			successURL.append(type);
			response.sendRedirect(successURL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
