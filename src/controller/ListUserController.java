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

import common.Common;
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
			// khởi tạo các tham số để lấy danh sách user
			int offset = 1;
			int limit = Constant.LIMIT_RECORD_ON_PAGE;
			int groupId = Common.convertStringToInt(request.getParameter("group_id"));
			String fullName = request.getParameter("full_name");
			String sortType = Constant.EMPTY_STRING;
			String sortByFullName = Constant.ASC;
			String sortByCodeLevel = Constant.ASC;
			String sortByEndDate = Constant.ASC;
			
			// lấy session
			HttpSession session = request.getSession(); 
			
			// kiểm tra trường hợp gọi đến màn hình listUser:
			// lấy type param
			String type = request.getParameter(Constant.TYPE);
			if (type == null) { // trường hợp lần đầu vào trang
				
			} else if (Constant.TYPE_SEARCH.equals(type)) { // trường hợp tìm kiếm
				
			} else if (Constant.TYPE_SORT.equals(type)) { // trường hợp click vào các button sắp xếp
				
			} else if (Constant.TYPE_PAGING.equals(type)) { // trường hợp click vào phân trang
				
			} else if (Constant.TYPE_BACK.equals(type)) { // trường hợp quay lại màn hình listUser
				
			}
			
			// lấy danh sách user và truyền sang view
			List<UserInfor> listUser = tblUserLogicImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
			request.setAttribute("listUser", listUser);
			// lấy và truyền danh sách group sang view
			List<MstGroup> listGroup = mstGroupLogicImpl.getAllGroups();
			request.setAttribute("listGroup", listGroup);
			
			// forward sang ADM002
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(Constant.ADM002);
			dispatcher.forward(request, response);
			
//			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM006);
//			rd.forward(request, response); 
			
		} catch (Exception e) {
			
		}
	}

}
