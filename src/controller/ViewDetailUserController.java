/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ViewDetailUserController.java, 2017-11-02 luuthanhsang
 */
package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Common;
import common.Constant;
import entity.UserInfor;
import logic.TblUserLogic;
import logic.impl.TblUserLogicImpl;

/**
 * Controller xử lí view chi tiết 1 user
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.DETAIL_USER_PATH)
public class ViewDetailUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo đối tượng xử lí logic sử dụng trong class
	TblUserLogic tblUserLogic;
       
    /**
     * Constructor
     */
    public ViewDetailUserController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * Phương thức xử lí yêu cầu view chi tiết user, màn hình ADM005
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// kiểm tra userId có tồn tại không
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), 0);
			if (tblUserLogic.isExistedUser(userId)) {
				// nếu có tồn tại, lấy ra đối tượng UserInfor, set lên request
				UserInfor userInfor = tblUserLogic.getUserInforById(userId);
				request.setAttribute(Constant.USER_INFOR, userInfor);
				// forward đến ADM005
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM005);
				rd.forward(request, response);
			} else {
				// điều hướng sang trang lỗi nếu userId không tồn tại
				Common.redirectErrorPage(request, response);
			}
		} catch (Exception e) {
			System.out.println("Error in ViewDetailUserController#doGet: " + e.getMessage());
			Common.redirectErrorPage(request, response);
		}
	}

}
