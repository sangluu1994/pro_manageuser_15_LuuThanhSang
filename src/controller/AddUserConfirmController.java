/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AddUserConfirmController.java, 2017-11-02 luuthanhsang
 */
package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.Common;
import common.Constant;
import entity.MstGroup;
import entity.MstJapan;
import entity.UserInfor;
import logic.MstGroupLogic;
import logic.MstJapanLogic;
import logic.TblUserLogic;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import logic.impl.TblUserLogicImpl;

/**
 * Controller xử lí xác nhận add user
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.ADD_USER_CONFIRM_SERVLET,
		urlPatterns = {Constant.ADD_USER_CONFIRM_PATH, Constant.ADD_USER_OK_PATH, Constant.EDIT_CONFIRM_PATH, Constant.EDIT_OK_PATH})
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo các đối tượng xử lí logic được sử dụng trong class
	private MstGroupLogic mstGroupLogic;
	private MstJapanLogic mstJapanLogic;
	private TblUserLogic tblUserLogic;
       
    /**
     * Constructor
     */
    public AddUserConfirmController() {
    	mstJapanLogic = new MstJapanLogicImpl();
		mstGroupLogic = new MstGroupLogicImpl();
		tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * Phương thức xử lí các yêu cầu gọi đến màn hình xác nhận thông tin ADM004
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy đối tượng userInfor trên session, set thêm thông tin cho userInfor
			String id = request.getParameter(Constant.USER_INFOR_ID);
			UserInfor userInfor = (UserInfor) request.getSession().getAttribute(id);
			// kiểm tra userInfor
			if (userInfor == null) {
				// điều hướng sang trang lỗi
				Common.redirectErrorPage(request, response);
				return;
			}
			// set group name
			MstGroup mstGroup = mstGroupLogic.getGroupById(userInfor.getGroupId());
			userInfor.setGroupName(mstGroup.getGroupName());
			// set name level
			String codeLevel = userInfor.getCodeLevel();
			if (!Common.isNullOrEmpty(codeLevel)) {
				MstJapan mstJapan = mstJapanLogic.getJpById(codeLevel);
				userInfor.setNameLevel(mstJapan.getNameLevel());
			}
			// xác định trường hợp add hay edit
			int userId = userInfor.getUserId();
			String actionType = userId > 0 ? Constant.EDIT_OK_PATH : Constant.ADD_USER_OK_PATH;
			String backType = userId > 0 ? Constant.EDIT_USER_PATH : Constant.ADD_USER_INPUT_PATH;
			request.setAttribute(Constant.ACTION_TYPE, actionType);
			request.setAttribute(Constant.BACK_TYPE, backType);
			// set đối tượng userInfor và id của đối tượng sang ADM004
			request.setAttribute(Constant.USER_INFOR, userInfor);
			request.setAttribute(Constant.USER_INFOR_ID, id);
			// forward đến ADM004
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM004);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

	/**
	 * Phương thức xử lí insert, update đối tượng userInfor vào CSDL
	 * 
	 * @param request - request gửi lên server
	 * @param response - response trả về phía client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy đối tượng userInfor cần insert
			HttpSession session = request.getSession();
			String userInforId = request.getParameter(Constant.USER_INFOR_ID);
			UserInfor userInfor = (UserInfor) session.getAttribute(userInforId);
			boolean taskSuccess = false;
			String type = null;
			int userId = userInfor.getUserId();
			if (userId == 0) { // trường hợp insert
				// insert userInfor vào cơ sở dữ liệu
				if (userInfor != null) {
					taskSuccess = tblUserLogic.createUser(userInfor);
					session.removeAttribute(userInforId);
				}
			} else { // trường hợp edit
				if (!tblUserLogic.isExistedUser(userId)) {
					// nếu không tồn tại user, điều hướng về trang lỗi
					Common.redirectErrorPage(request, response);
					return;
				}
				// gọi hàm xử lí logic edit user
				taskSuccess = tblUserLogic.editUser(userInfor);
				session.removeAttribute(userInforId);
			}
			// điều hướng sang trang kết quả insert tùy theo các trường hợp thành công/không thành công
			StringBuilder successURL = new StringBuilder(request.getContextPath());
			successURL.append(Constant.SUCCESS_PATH);
			successURL.append("?");
			successURL.append(Constant.TYPE);
			successURL.append("=");
			type = taskSuccess ? Constant.TASK_DONE : Constant.TASK_FAIL;
			successURL.append(type);
			response.sendRedirect(successURL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
