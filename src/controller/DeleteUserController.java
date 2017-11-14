package controller;

import java.io.IOException;
import javax.servlet.ServletException;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_INFOR_ID));
			// check userId tồn tại
			if (userId > 0) {
				if (!tblUserLogic.isExistedUser(userId)) {
					Common.redirectErrorPage(request, response);
					return;
				}
			}
			// gọi hàm xử lí logic delete user
			boolean success = tblUserLogic.removeUser(userId);
			// điều hướng đến trang xử lí hiện kết quả xóa user
			StringBuilder successURL = new StringBuilder(request.getContextPath()).append(Constant.SUCCESS_PATH).append("?type=");
			String type = success ? Constant.DELETE_SUCCESS : Constant.DELETE_FAIL;
			successURL.append(type);
			response.sendRedirect(successURL.toString());
		} catch (Exception e) {
			
		}
	}

}
