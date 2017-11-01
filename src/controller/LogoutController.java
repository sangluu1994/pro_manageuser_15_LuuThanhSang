/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * LogoutController.java, 2017-10-23 luuthanhsang
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import properties.MessageErrorProperties;

/**
 * Controller xử lí thao tác logout 
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.LOG_OUT_PATH)
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// hủy session và redirect về màn hình login
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(request.getContextPath() + Constant.LOG_IN_PATH);
		} catch (Exception e) {
			// show console log ngoại lệ
			e.printStackTrace();
			// khai báo, truyền message lỗi sang view
			String errMsg = MessageErrorProperties.getErrMsg(Constant.ER015);
			request.setAttribute(Constant.ERR_MSG, errMsg);
			// forward sang màn hình listUser
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM_SYSTEM_ERROR);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
