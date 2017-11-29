/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ErrorController.java, 2017-11-02 luuthanhsang
 */
package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Constant;
import properties.MessageErrorProperties;

/**
 * Controller xử lí lỗi
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.ERROR_SERVLET,
		urlPatterns = Constant.SYSTEM_ERROR_PATH)
public class ErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     */
    public ErrorController() {
        super();
    }

	/**
	 * Phương thức xử lí in ra màn hình lỗi hệ thống
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// thiết lập thông báo lỗi lên request, forward đến view ADM_SYSTEM_ERROR
			String errMsg = MessageErrorProperties.getErrMsg(Constant.ER015);
			request.setAttribute(Constant.ERR_MSG, errMsg);
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM_SYSTEM_ERROR);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
