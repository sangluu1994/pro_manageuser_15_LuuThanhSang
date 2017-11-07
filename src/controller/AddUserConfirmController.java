package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;

/**
 * Servlet implementation class AddUserConfirmController
 */
@WebServlet(urlPatterns = {Constant.ADD_USER_CONFIRM_PATH, Constant.ADD_USER_OK_PATH})
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MstGroupLogic mstGroupLogic;
	private MstJapanLogic mstJapanLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserConfirmController() {
    	mstJapanLogic = new MstJapanLogicImpl();
		mstGroupLogic = new MstGroupLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter(Constant.USER_INFOR_ID);
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute(id);
//			System.out.println("getLoginName: " + userInfor.getLoginName());
//			System.out.println("getGroupId: " + userInfor.getGroupId());
//			System.out.println("getFullName: " + userInfor.getFullName());
//			System.out.println("getFullNameKana: " + userInfor.getFullNameKana());
//			System.out.println("getBirthYear: " + userInfor.getBirthYear());
//			System.out.println("getBirthMonth: " + userInfor.getBirthMonth());
//			System.out.println("getBirthDate: " + userInfor.getBirthDate());
//			System.out.println("getEmail: " + userInfor.getEmail());
//			System.out.println("getTel: " + userInfor.getTel());
//			System.out.println("getPass: " + userInfor.getPass());
//			System.out.println("getRePass: " + userInfor.getRePass());
//			System.out.println("getCodeLevel: " + userInfor.getCodeLevel());
//			System.out.println("getStartYear: " + userInfor.getStartYear());
//			System.out.println("getStartMonth: " + userInfor.getStartMonth());
//			System.out.println("getStartDay: " + userInfor.getStartDay());
//			System.out.println("getEndYear: " + userInfor.getEndYear());
//			System.out.println("getEndMonth: " + userInfor.getEndMonth());
//			System.out.println("getEndDay: " + userInfor.getEndDay());
//			System.out.println("getTotal: " + userInfor.getTotal());
			MstGroup mstGroup = mstGroupLogic.getGroupById(userInfor.getGroupId());
			userInfor.setGroupName(mstGroup.getGroupName());
			MstJapan mstJapan = null;
			String codeLevel = userInfor.getCodeLevel();
			if (codeLevel != null && !Constant.DEFAULT_CODE_LEVEL.equals(codeLevel)) {
				mstJapan = mstJapanLogic.getJpById(codeLevel);
				userInfor.setNameLevel(mstJapan.getNameLevel());
			}
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
