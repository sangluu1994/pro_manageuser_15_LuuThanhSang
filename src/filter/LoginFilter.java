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

/**
 * Filter kiểm tra login
 * 
 * @author luuthanhsang
 */
@WebFilter(urlPatterns = Constant.FILTER_URL_PATTERN)
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
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
		
		// lấy đường dẫn của request gửi đến
		String path = req.getRequestURI().substring(req.getContextPath().length());
		// lấy user đang đăng nhập
		String loginUser = (String) req.getSession().getAttribute(Constant.CURRENT_LOGIN_USER);
		// lấy contextPath
		String contextPath = req.getContextPath();
		
		// kiểm tra đường dẫn được yêu cầu
		if (Constant.LOG_OUT_PATH.equals(path)) {
			// nếu gọi đến url logout
			// cho phép vượt qua LoginFilter
			chain.doFilter(req, res);
		} else if (Constant.LOG_IN_PATH.equals(path) || Constant.ROOT_PATH.equals(path)) {
			// nếu gọi đến url login hoặc đến đường dẫn mặc định "/"
			// kiểm tra có user đang đăng nhập không
			if (loginUser != null) {
				// nếu đã đăng nhập, redirect về trang list user
				res.sendRedirect(contextPath + Constant.LIST_USER_PATH);
			} else {
				// nếu chưa đăng nhập
				// cho phép request vượt qua Filter
				chain.doFilter(req, res);
			}
		} else if (path.matches(Constant.CSS_FOLDER_PATTERN) || path.matches(Constant.JS_FOLDER_PATTERN)
				|| path.matches(Constant.IMG_FOLDER_PATTERN)) {
			// nếu gọi đến các thư mục js/css/img
			// cho phép vượt qua Filter
			chain.doFilter(req, res);
		} else if (path.matches(Constant.JSP_FOLDER_PATTERN)) {
			// nếu gọi đến thư mục chứa file jsp
			// redirect về trang login
			res.sendRedirect(contextPath + Constant.LOG_IN_PATH);
		} else if (loginUser != null) {
			// nếu không phải các đường dẫn trên
			// kiểm tra có user đang đăng nhập không
			// nếu đã có user đăng nhập
			// cho phép request vượt qua Filter
			chain.doFilter(req, res);
		} else {
			// nếu chưa đăng nhập, redirect về trang login
			res.sendRedirect(contextPath + Constant.LOG_IN_PATH);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
