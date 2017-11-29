/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * SuccessController.java, 2017-11-02 luuthanhsang
 */
package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
import common.Constant;
import properties.MessageProperties;

/**
 * Controller xử lí hiển thị thông báo add, edit, delete thành công
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.SUCCESS_SERVLET,
		urlPatterns = Constant.SUCCESS_PATH)
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor
     */
    public SuccessController() {
        super();
    }

	/**
	 * Phương thức xử lí in ra màn hình thông báo kết quả của các thao tác thay đổi dữ liệu trong CSDL
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy tham số được gửi sang để xác định thông báo thành công hoặc không thành công
			String type = request.getParameter(Constant.TYPE);
			// tạo message hiển thị trên màn hình ADM006
			StringBuilder msg = new StringBuilder();
			if (Constant.INSERT.equals(type)) {
				msg.append(MessageProperties.getString(Constant.MSG001));
			} else if (Constant.UPDATE.equals(type)) {
				msg.append(MessageProperties.getString(Constant.MSG002));
			} else {
				msg.append(MessageProperties.getString(Constant.MSG003));
			}
			request.setAttribute(Constant.MESSAGE, msg.toString());
			// forward đến ADM006
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM006);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
