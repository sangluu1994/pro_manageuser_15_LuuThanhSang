/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ViewDetailUserController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;

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
	TblUserLogic tblUserLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDetailUserController() {
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// kiểm tra userId có tồn tại không
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID));
			if (tblUserLogic.isExistedUser(userId)) {
				// nếu có tồn tại, lấy ra đối tượng UserInfor, set lên request
				UserInfor userInfor = tblUserLogic.getUserInforById(userId);
				request.setAttribute(Constant.USER_INFOR, userInfor);
				// forward đến ADM005
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM005);
				rd.forward(request, response);
			} else {
				// điều hướng sang trang lỗi nếu userId không tồn tại
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				response.sendRedirect(errorURL.append(Constant.SYSTEM_ERROR_PATH).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// điều hướng sang trang lỗi nếu xảy ra exception
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				response.sendRedirect(errorURL.append(Constant.SYSTEM_ERROR_PATH).toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
