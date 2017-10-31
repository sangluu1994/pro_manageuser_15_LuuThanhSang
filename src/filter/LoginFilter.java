/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * LoginFilter.java, 2017-10-25 luuthanhsang
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import logic.impl.AdminLogicImpl;

/**
 * Filter kiểm tra login
 * 
 * @author luuthanhsang
 */
@WebFilter(urlPatterns = Constant.FILTER_URL_PATTERN)
public class LoginFilter implements Filter {
	// khai báo đối tượng xử lí logic được sử dụng đến trong filter
	private AdminLogicImpl adminLogicImpl;

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// xóa cache
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		// set timeout
		req.getSession().setMaxInactiveInterval(300);
		// lấy đường dẫn của request gửi đến
		String path = req.getRequestURI().substring(req.getContextPath().length());
		
		// nếu gọi đến controller logout
		if (Constant.LOG_OUT_PATH.equals(path)) {
			// cho phép vượt qua LoginFilter
			chain.doFilter(req, res);
			return;
		}
		
		// nếu gọi đến controller login hoặc đến đường dẫn mặc định "/"
		if (Constant.LOG_IN_PATH.equals(path) || Constant.ROOT_PATH.equals(path)) {
			// kiểm tra đã đăng nhập chưa
			if (adminLogicImpl.checkLogin(req.getSession())) {
				// nếu đã đăng nhập thì redirect về trang list user
				res.sendRedirect(req.getContextPath() + Constant.LIST_USER_PATH);
				return;
			} else {
				// nếu chưa đăng nhập:
				// cho phép request vượt qua Filter
				chain.doFilter(req, res);
				return;
			}
		}
		
		// nếu gọi đến các thư mục js/css/img
		if (path.matches(Constant.CSS_FOLDER_PATTERN) || path.matches(Constant.JS_FOLDER_PATTERN)
				|| path.matches(Constant.IMG_FOLDER_PATTERN)) {
			// cho phép vượt qua Filter
			chain.doFilter(req, res);
			return;
		}
		
		// nếu gọi đến thư mục chứa file jsp
		if (path.matches(Constant.JSP_FOLDER_PATTERN)) {
			// redirect về trang login
			res.sendRedirect(req.getContextPath() + Constant.LOG_IN_PATH);
			return;
		}
		
		// nếu không phải các đường dẫn trên
		// kiểm tra đã đăng nhập chưa
		if (adminLogicImpl.checkLogin(req.getSession())) {
			// nếu đã đăng nhập
			// cho phép request vượt qua Filter
			chain.doFilter(req, res);
			return;
		} else {
			// nếu chưa đăng nhập, redirect về trang login
			res.sendRedirect(req.getContextPath() + Constant.LOG_IN_PATH);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// khởi tạo đối tượng logic sẽ được sử dụng trong filter
		adminLogicImpl = new AdminLogicImpl();
	}

}
