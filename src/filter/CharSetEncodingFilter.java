/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * CharSetEncodingFilter.java, 2017-10-25 luuthanhsang
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
 * Filter thiết lập charset cho các request và response
 * 
 * @author luuthanhsang
 */
@WebFilter(urlPatterns = Constant.FILTER_URL_PATTERN)
public class CharSetEncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharSetEncodingFilter() {
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
		// thiết lập charset cho request và response
		req.setCharacterEncoding(Constant.DEFAULT_CHARSET_ENCODING);
		res.setCharacterEncoding(Constant.DEFAULT_CHARSET_ENCODING);
		// cho phép đi qua filter hiện tại
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
