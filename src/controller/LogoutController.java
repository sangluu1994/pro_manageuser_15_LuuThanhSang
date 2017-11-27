/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * LogoutController.java, 2017-10-23 luuthanhsang
 */
package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
import common.Constant;

/**
 * Controller xử lí thao tác logout 
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.LOG_OUT_PATH)
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     */
    public LogoutController() {
        super();
    }

	/**
	 * Phương thức xử lí yêu cầu logout
	 * 
	 * @param request - request được gửi đến server
	 * @param response - response trả về cho client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// hủy session và redirect về màn hình login
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + Constant.LOG_IN_PATH);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
