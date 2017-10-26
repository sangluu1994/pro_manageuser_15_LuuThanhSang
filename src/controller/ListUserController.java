/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ListUserController.java, 2017-10-25 luuthanhsang
 *
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import entity.MstGroup;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.TblUserLogicImpl;

/**
 * Controller xử lí in, tìm kiếm danh sách user
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.LIST_USER_PATH)
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TblUserLogicImpl tblUserLogicImpl;
	private MstGroupLogicImpl mstGroupLogicImpl;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListUserController() {
    	tblUserLogicImpl = new TblUserLogicImpl();
		mstGroupLogicImpl = new MstGroupLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String type = request.getParameter(Constant.TYPE); // lấy type param
			HttpSession session = request.getSession(); // lấy session
			List<MstGroup> listGroup = mstGroupLogicImpl.getAllGroups(); // lấy danh sách group
			List<UserInfor> listUser = null;
			
			if (type == null) { // trường hợp lần đầu vào trang
				int offset = 0;
				int limit = 0;
				int groupId = 0;
				String fullName = "";
				String sortType = "";
				String sortByFullName = "";
				String sortByCodeLevel = "";
				String sortByEndDate = "";
				// lấy danh sách user
				listUser = tblUserLogicImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
				// gửi danh sách tất cả nhóm, group sang view
				request.setAttribute("listUser", listUser);
				request.setAttribute("listGroup", listGroup);
			} else if (Constant.SEARCH.equals(type)) { // trường hợp tìm kiếm
				
			} else if (Constant.TYPE_SORT.equals(type)) { // trường hợp click vào các button sắp xếp
				
			} else if (Constant.TYPE_BACK.equals(type)) { // trường hợp quay lại từ màn hình ADM00
				
			}
			
			for (UserInfor user : listUser) {
				System.out.println("id: " + user.getUserId());
				System.out.println("fullName: " + user.getFullName());
				System.out.println("groupId: " + user.getGroupId());
				System.out.println("loginName: " + user.getLoginName());
				System.out.println("kataName: " + user.getKataName());
				System.out.println("email: " + user.getEmail());
				System.out.println("tel: " + user.getTel());
				System.out.println("salt: " + user.getSalt());
				System.out.println("birthday: " + user.getBirthDay());
			}
			
//			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM002);
//			rd.forward(request, response); // forward sang ADM002
			
		} catch (Exception e) {
			
		}
	}

}
