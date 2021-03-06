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
 * Class xử lí yêu cầu delete user
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.DELETE_USER_SERVLET,
		urlPatterns = Constant.DELETE_USER_PATH)
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo các đối tượng xử lí logic sẽ được sử dụng trong class
	private TblUserLogic tblUserLogic;
       
    /**
     * Constructor
     */
    public DeleteUserController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * Phương thức xử lí yêu cầu delete user
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// check userId tồn tại
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_INFOR_ID), 0);
			if (!tblUserLogic.isExistedUser(userId)) {
				Common.redirectErrorPage(request, response);
				return;
			}
			
			// gọi hàm xử lí logic delete user
			boolean success = tblUserLogic.removeUser(userId);
			// điều hướng đến trang xử lí hiện kết quả xóa user
			if (success) {
				StringBuilder successURL = new StringBuilder();
				successURL.append(request.getContextPath()).append(Constant.SUCCESS_PATH).append("?").append(Constant.TYPE).append("=").append(Constant.DELETE);
				response.sendRedirect(successURL.toString());
			} else {
				Common.redirectErrorPage(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
