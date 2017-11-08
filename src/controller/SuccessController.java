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
import properties.MessageProperties;

/**
 * Controller xử lí hiển thị thông báo add, edit, delete thành công
 * 
 * @author luuthanhsang
 */
@WebServlet("/SuccessController")
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
			String type = request.getParameter(Constant.TYPE);
			StringBuilder msg = new StringBuilder();
			if (Constant.INSERT_DONE.equals(type)) {
				msg.append(MessageProperties.getString(Constant.MSG001));
			} else if (Constant.INSERT_FAIL.equals(type)) {
				msg.append(MessageProperties.getString(Constant.SYSTEM_ERROR_PATH));
			}
			request.setAttribute(Constant.MESSAGE, msg.toString());
			// forward đến ADM006
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM006);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				errorURL.append(Constant.SYSTEM_ERROR_PATH);
				response.sendRedirect(errorURL.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
