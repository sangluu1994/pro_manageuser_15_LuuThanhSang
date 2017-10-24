package filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Common;
import properties.ConstantProperties;

/**
 * Servlet Filter implementation class LoginFilter
 */
//@WebFilter(urlPatterns = "*")
public class LoginFilter implements Filter {
	private ArrayList<String> allowedURLList;// store list url allow

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
		// lấy đường dẫn
		String path = req.getRequestURI().substring(req.getContextPath().length());

		// nếu path là /login
		if (ConstantProperties.LOG_IN_ANNOTATION.equals(path)) {
			if (Common.checkAdminLogin(req.getSession())) {
				res.sendRedirect(req.getContextPath() + ConstantProperties.LIST_USER_ANNOTATION);
				// cho phép request vượt qua Filter
				return;
			} else {
				chain.doFilter(req, res);
				return;
			}
		}
		
		if (Common.checkAdminLogin(req.getSession())) {
			// cho phép request vượt qua Filter
			chain.doFilter(req, res);
		}

//		// if is folder /css/, /image/, /js/ then pass
//		if (path.indexOf(Constant.FOLDER_CSS) > 0 || path.indexOf(Constant.FOLDER_IMAGES) > 0
//				|| path.indexOf(Constant.FOLDER_JS) > 0) {
//			chain.doFilter(request, response);
//			return;
//		}

		// nếu đã đăng nhập
		if (Common.checkAdminLogin(req.getSession())) {
			if (!allowedURLList.contains(path)) {
				res.sendRedirect(req.getContextPath() + ConstantProperties.LIST_USER_ANNOTATION);
				return;
			}
			chain.doFilter(request, response);
			return;
		}

		// nếu chưa đăng nhập
		res.sendRedirect(req.getContextPath() + ConstantProperties.LOG_IN_ANNOTATION);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		allowedURLList = new ArrayList<String>();
		allowedURLList.add(ConstantProperties.LOG_IN_ANNOTATION);
		allowedURLList.add(ConstantProperties.LOG_OUT_ANNOTATION);
	}

}
