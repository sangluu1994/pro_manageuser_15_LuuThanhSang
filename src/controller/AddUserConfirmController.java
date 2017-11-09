/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AddUserConfirmController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@WebServlet(urlPatterns = {Constant.ADD_USER_CONFIRM_PATH, Constant.ADD_USER_OK_PATH})
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MstGroupLogic mstGroupLogic;
	private MstJapanLogic mstJapanLogic;
	private TblUserLogic tblUserLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserConfirmController() {
    	mstJapanLogic = new MstJapanLogicImpl();
		mstGroupLogic = new MstGroupLogicImpl();
		tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// Lấy đối tượng userInfor trên session, set thêm thông tin cho userInfor
			String id = request.getParameter(Constant.USER_INFOR_ID);
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute(id);
			MstGroup mstGroup = mstGroupLogic.getGroupById(userInfor.getGroupId());
			userInfor.setGroupName(mstGroup.getGroupName());
			MstJapan mstJapan = null;
			String codeLevel = userInfor.getCodeLevel();
			if (codeLevel != null && !Constant.DEFAULT_CODE_LEVEL.equals(codeLevel)) {
				mstJapan = mstJapanLogic.getJpById(codeLevel);
				userInfor.setNameLevel(mstJapan.getNameLevel());
			}
			// chuyển đối tượng userInfor và id của đối tượng sang ADM004
			request.setAttribute(Constant.USER_INFOR, userInfor);
			request.setAttribute(Constant.USER_INFOR_ID, id);
			// forward đến ADM004
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM004);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// điều hướng sang trang lỗi nếu có lỗi
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				response.sendRedirect(errorURL.append(Constant.SYSTEM_ERROR_PATH).toString());
			} catch (IOException e1) {

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy đối tượng userInfor cần insert
			HttpSession session = request.getSession();
			String userInforId = request.getParameter(Constant.USER_INFOR_ID);
			UserInfor userInfor = (UserInfor) session.getAttribute(userInforId);
			boolean insertSuccess = false;
			// insert userInfor vào cơ sở dữ liệu
			if (userInfor != null) {
				insertSuccess = tblUserLogic.createUser(userInfor);
				session.removeAttribute(userInforId);
			}
			// điều hướng sang trang kết quả insert với các trường hợp thành công/ không thành công
			StringBuilder successURL = new StringBuilder(request.getContextPath());
			successURL.append(Constant.SUCCESS_PATH);
			successURL.append("?");
			successURL.append(Constant.TYPE);
			successURL.append("=");
			if (insertSuccess) {
				successURL.append(Constant.INSERT_DONE);
			} else {
				successURL.append(Constant.INSERT_FAIL);
			}
			response.sendRedirect(successURL.toString());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// điều hướng sang trang lỗi nếu xảy ra exception
				StringBuilder errorURL = new StringBuilder(request.getContextPath());
				errorURL.append(Constant.SYSTEM_ERROR_PATH);
				response.sendRedirect(errorURL.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
