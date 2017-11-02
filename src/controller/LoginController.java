/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * LoginController.java, 2017-10-23 luuthanhsang
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Constant;
import validate.ValidateAdmin;


/**
 * Controller xử lí hoạt động login
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.LOG_IN_PATH)
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// forward về trang ADM001
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
			rd.forward(request, response);
			
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_PATH);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy thông tin username, password
			String loginName = request.getParameter(Constant.TXT_LOGIN_ID);
			String password = request.getParameter(Constant.TXT_PASSWORD);
			// gán giá trị trường txtUsername vào request để view in ra
			request.setAttribute(Constant.TXT_USERNAME, loginName);
			
			// khởi tạo danh sách lỗi đăng nhập
			ArrayList<String> errMsgList = ValidateAdmin.validateLogin(loginName, password);
			// nếu có lỗi đăng nhập:
			if (errMsgList.size() > 0) {
				// thiết lập biến lỗi lên request và forward về view ADM001
				request.setAttribute(Constant.LIST_ERROR, errMsgList);
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
				rd.forward(request, response);
				
			} else { // nếu không có lỗi đăng nhập
				// thiết lập thông tin username về user đang đăng nhập lên session
				request.getSession().setAttribute(Constant.CURRENT_LOGIN_USER, loginName);
				// redirect sang màn hình list user
				response.sendRedirect(request.getContextPath() + Constant.LIST_USER_PATH);
					
			}
			
		} catch (Exception e) {
			try {
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_PATH);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
