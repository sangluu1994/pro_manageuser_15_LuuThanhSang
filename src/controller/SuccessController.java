/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * SuccessController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Constant;
import properties.MessageErrorProperties;
import properties.MessageProperties;

/**
 * Controller xử lí hiển thị thông báo add, edit, delete thành công
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.SUCCESS_PATH)
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuccessController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy tham số được gửi sang để xác định thông báo thành công hoặc không thành công
			String type = request.getParameter(Constant.TYPE);
			StringBuilder msg = new StringBuilder();
			StringBuilder status = new StringBuilder();
			if (Constant.INSERT_DONE.equals(type)) {
				msg.append(MessageProperties.getString(Constant.MSG001));
				status.append(Constant.SUCCESS);
			} else if (Constant.INSERT_FAIL.equals(type)) {
				msg.append(MessageErrorProperties.getErrMsg(Constant.ER015));
				status.append(Constant.FAIL);
			}
			request.setAttribute(Constant.MESSAGE, msg.toString());
			request.setAttribute(Constant.STATUS, status.toString());
			// forward đến ADM006
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM006);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// điều hướng sang trang lỗi nếu xảy ra exception
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				errorURL.append(Constant.SYSTEM_ERROR_PATH);
				response.sendRedirect(errorURL.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
