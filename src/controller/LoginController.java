package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.impl.AdminLogicImpl;
import properties.ConstantProperties;
import properties.MessageErrorProperties;
import validate.ValidateAdmin;


/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(ConstantProperties.ADM001);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// lấy thông tin username, password
		String loginName = request.getParameter("loginId").trim();
		String password = request.getParameter("password").trim();
		// khởi tạo mảng lỗi nhập liệu
		ArrayList<String> errMsgList = ValidateAdmin.validateLogin(loginName, password);
		AdminLogicImpl adminLogicImpl = new AdminLogicImpl();
		// nếu có lỗi nhập liệu:
		if (errMsgList.size() > 0) {
			// thiết lập biến lỗi và trả về view ADM001
			request.setAttribute(ConstantProperties.LIST_ERROR, errMsgList);
			RequestDispatcher rd = request.getRequestDispatcher(ConstantProperties.ADM001);
			rd.forward(request, response);
		} else { // nếu không có lỗi nhập liệu
			// kiểm tra thông tin đăng nhập có khớp với thông tin admin
			if (adminLogicImpl.authAdminLogin(loginName, password)) { // nếu trùng khớp
				// gán thông tin loginName cho session
				adminLogicImpl.setSession(request.getSession(), loginName);
//				System.out.println(request.getSession().getAttribute("loginName"));
				// redirect sang màn hình ADM002
				response.sendRedirect(request.getContextPath() + ConstantProperties.LIST_USER_ANNOTATION);
			} else { // nếu không khớp
				// thiết lập biến lỗi và trả về view ADM001
				request.setAttribute(ConstantProperties.LIST_ERROR, MessageErrorProperties.getString("ER016"));
				RequestDispatcher rd = request.getRequestDispatcher(ConstantProperties.ADM001);
				rd.forward(request, response);
			}
		}
	}

}
