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
@WebServlet(Constant.ADD_CONFIRM_PATH)
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//			HttpSession session = request.getSession();
//			UserInfor userInfor = (UserInfor) session.getAttribute(Constant.SESSION_USER_INFOR);
//			MstGroup mstGroup = mstGroupLogic.getGroupById(userInfor.getGroupId());
//			//MstJapan mstJapan = null;
//			String codeLevel = userInfor.getCodeLevel();
//			if (codeLevel != null && !Constant.EMPTY_STRING.equals(codeLevel) && !Constant.ZERO.equals(codeLevel)) {
//				MstJapan mstJapan = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel());
//				request.setAttribute("mstJapan", mstJapan);
//			}
//			request.setAttribute("userInfor", userInfor);
//			request.setAttribute("mstGroup", mstGroup);
//			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM004);
//			dispatcher.forward(request, response);// forward to page jsp
//		} catch (Exception e) {
//			StringBuffer stringBuffer = new StringBuffer(request.getContextPath());
//			try {
//				// in case have error then send redirect to view error
//				response.sendRedirect(stringBuffer.append(Constant.URL_VIEW_EROR).toString());
//			} catch (IOException e1) {
//
//			}
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
