/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * LoginController.java, 2017-10-23 luuthanhsang
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Constant;
import logic.impl.AdminLogicImpl;
import properties.MessageErrorProperties;
import validate.ValidateAdmin;


/**
 * Class xử lí hoạt động login
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.LOG_IN_PATH)
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo đối tượng logic sử dụng trong class
	private AdminLogicImpl adminLogicImpl;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        // khởi tạo đối tượng logic
    	adminLogicImpl = new AdminLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// forward về trang ADM001
		RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// lấy thông tin username, password
			String loginName = request.getParameter(Constant.TXT_LOGIN_ID);
			String password = request.getParameter(Constant.TXT_PASSWORD);
			// gán giá trị trường txtUsername vào request để view in ra
			request.setAttribute(Constant.TXT_USERNAME, loginName);
			
			// khởi tạo danh sách lỗi nhập liệu
			ArrayList<String> errMsgList = ValidateAdmin.validateFormInput(loginName, password);
			// nếu có lỗi nhập liệu:
			if (errMsgList.size() > 0) {
				// thiết lập biến lỗi và forward về view ADM001
				request.setAttribute(Constant.LIST_ERROR, errMsgList);
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
				rd.forward(request, response);
				
			} else { // nếu không có lỗi nhập liệu
				// kiểm tra thông tin đăng nhập có khớp với thông tin admin
				if (adminLogicImpl.attemptLogin(loginName, password)) { // nếu trùng khớp
					// thiết lập thông tin về user đang đăng nhập cho session
					request.getSession().setAttribute(Constant.CURRENT_LOGIN_USER, loginName);
					// redirect sang màn hình list user
					response.sendRedirect(request.getContextPath() + Constant.LIST_USER_PATH);
					
				} else { // nếu không khớp
					// thiết lập biến lỗi và forward về view ADM001
					request.setAttribute(Constant.LIST_ERROR, MessageErrorProperties.getErrMsg(Constant.ER016));
					RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM001);
					rd.forward(request, response);
					
				}
			}
		} catch (SQLException e) {
			// show console log ngoại lệ
			System.out.println(e.getMessage());
			// khai báo, truyền message lỗi sang view
			String errMsg = MessageErrorProperties.getErrMsg(Constant.ER015);
			request.setAttribute(Constant.ERR_MSG, errMsg);
			// forward sang màn hình listUser
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM_SYSTEM_ERROR);
			rd.forward(request, response);
		}
	}

}
