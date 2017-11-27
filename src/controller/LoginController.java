/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * LoginController.java, 2017-10-23 luuthanhsang
 */
package controller;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
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
     * Constructor
     */
    public LoginController() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Method trả về giao diện màn hình đăng nhập
	 * 
	 * @param request - request được gửi đến server
	 * @param response - response trả về cho client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// forward về trang ADM001
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
			rd.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

	/**
	 * Phương thức xử lí yêu cầu khi submit form đăng nhập
	 * 
	 * @param request - request được gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy thông tin username, password
			String loginName = request.getParameter(Constant.TXT_LOGIN_ID);
			String password = request.getParameter(Constant.TXT_PASSWORD);
			
			// khởi tạo danh sách lỗi đăng nhập
			ArrayList<String> errMsgList = ValidateAdmin.validateLogin(loginName, password);
			// nếu có lỗi đăng nhập:
			if (errMsgList.size() > 0) {
				// thiết lập biến lỗi lên request và forward về view ADM001
				// gán giá trị tên đăng nhập vừa gửi lên vào request để giữ lại giá trị trong textbox của view ADM001
				request.setAttribute(Constant.TXT_USERNAME, loginName);
				request.setAttribute(Constant.LIST_ERROR, errMsgList);
				// forward về view ADM001
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
				rd.forward(request, response);
				
			} else { // nếu không có lỗi đăng nhập
				// thiết lập thông tin username về user đang đăng nhập lên session
				request.getSession().setAttribute(Constant.CURRENT_LOGIN_USER, loginName);
				// redirect sang màn hình list user
				response.sendRedirect(request.getContextPath() + Constant.LIST_USER_PATH);
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
