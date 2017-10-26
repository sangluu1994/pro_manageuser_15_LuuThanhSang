/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ListUserController.java, 2017-10-25 luuthanhsang
 *
 */
package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import properties.ConfigProperties;

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
			// lấy session
			HttpSession session = request.getSession();
			// khởi tạo map lưu trữ điều kiện tìm kiếm trước đó trên session
			Map<String, Object> preSearchCondition = (Map<String, Object>) session.getAttribute(Constant.SEARCH_CONDITION);;
			
			// khởi tạo các tham số để lấy danh sách user
			int currentPage = Constant.DEFAULT_PAGE;
			int offset = Constant.DEFAULT_OFFSET;
			int limit = Common.convertStringToInt(ConfigProperties.getString(Constant.LIMIT));
			int groupId = Constant.DEFAULT_GROUP_ID;
			String fullName = Constant.EMPTY_STRING;
			String sortType = Constant.EMPTY_STRING;
			String sortByFullName = Constant.ASC;
			String sortByCodeLevel = Constant.ASC;
			String sortByEndDate = Constant.DESC;
			
			// lấy type param
			String type = request.getParameter(Constant.TYPE);
			// kiểm tra trường hợp gọi đến màn hình listUser:
			if (type == null) { // trường hợp lần đầu vào trang
				
			} else if (Constant.TYPE_SEARCH.equals(type)) { // trường hợp tìm kiếm
				// lấy điều kiện tìm kiếm
				groupId = Common.convertStringToInt(request.getParameter("group_id"));
				fullName = request.getParameter("full_name");
				
			} else if (Constant.TYPE_SORT.equals(type)) { // trường hợp click vào các button sắp xếp
				// lấy điều kiện tìm kiếm
				groupId = (Integer) preSearchCondition.get(Constant.GROUP_ID);
				fullName = (String) preSearchCondition.get(Constant.FULL_NAME);
				// lấy điều kiện sắp xếp
				sortType = request.getParameter("sortType");
				
				// kiểm tra sortType
				if (Constant.SORT_BY_FULL_NAME.equals(sortType)) { // trường hợp sắp xếp theo tên
					// lấy kiểu sortByFullName trước đó được lưu trên session
					String preSortByFullName = (String) preSearchCondition.get(Constant.SORT_BY_FULL_NAME);
					// thay đổi kiểu của sortByFullName ngược lại so với trước đó
					sortByFullName = Constant.ASC.equals(preSortByFullName) ? Constant.DESC : Constant.ASC;
					
				} else if (Constant.SORT_BY_CODE_LEVEL.equals(sortType)) { // trường hợp sắp xếp theo trình độ tiếng Nhật
					// lấy kiểu sortByCodeLevel trước đó được lưu trên session
					String preSortByCodeLevel = (String) preSearchCondition.get(Constant.SORT_BY_CODE_LEVEL);
					// thay đổi kiểu của sortByCodeLevel ngược lại so với trước đó
					sortByCodeLevel = Constant.ASC.equals(preSortByCodeLevel) ? Constant.DESC : Constant.ASC;
					
				} else if (Constant.SORT_BY_END_DATE.equals(sortType)) { // trường hợp sắp xếp theo ngày hết hạn
					// lấy kiểu sortByEndDate trước đó được lưu trên session
					String preSortByEndDate = (String) preSearchCondition.get(Constant.SORT_BY_END_DATE);
					// thay đổi kiểu của sortByEndDate ngược lại so với trước đó
					sortByEndDate = Constant.ASC.equals(preSortByEndDate) ? Constant.DESC : Constant.ASC;
					
				}
				
			} else if (Constant.TYPE_PAGING.equals(type)) { // trường hợp click vào phân trang
				// lấy điều kiện tìm kiếm
				groupId = (Integer) preSearchCondition.get(Constant.GROUP_ID);
				fullName = (String) preSearchCondition.get(Constant.FULL_NAME);
				sortType = (String) preSearchCondition.get(Constant.SORT_TYPE);
				sortByFullName = (String) preSearchCondition.get(Constant.SORT_BY_FULL_NAME);
				sortByCodeLevel = (String) preSearchCondition.get(Constant.SORT_BY_CODE_LEVEL);
				sortByEndDate = (String) preSearchCondition.get(Constant.SORT_BY_END_DATE);
				// lấy trang hiện tại
				currentPage = Common.convertStringToInt(request.getParameter("page"));
				// lấy offset tương ứng
				offset = Common.getOffset(currentPage, limit);
				
			} else if (Constant.TYPE_BACK.equals(type)) { // trường hợp quay lại màn hình listUser
				// lấy điều kiện tìm kiếm
				offset = (Integer) preSearchCondition.get(Constant.OFFSET);
				limit = (Integer) preSearchCondition.get(Constant.LIMIT);
				groupId = (Integer) preSearchCondition.get(Constant.GROUP_ID);
				fullName = (String) preSearchCondition.get(Constant.FULL_NAME);
				sortType = (String) preSearchCondition.get(Constant.SORT_TYPE);
				sortByFullName = (String) preSearchCondition.get(Constant.SORT_BY_FULL_NAME);
				sortByCodeLevel = (String) preSearchCondition.get(Constant.SORT_BY_CODE_LEVEL);
				sortByEndDate = (String) preSearchCondition.get(Constant.SORT_BY_END_DATE);
			}
			
			// lấy danh sách phân trang, lưu lên session
			int totalUsers = tblUserLogicImpl.getTotalUsers(groupId, fullName);
			List<Integer> listPaging = Common.getListPaging(totalUsers, limit, currentPage);
			session.setAttribute(Constant.LIST_PAGING, listPaging);
//			for (int ii : listPaging) {
//				System.out.println(ii);
//			}
			
			// lấy danh sách user và truyền sang view
			List<UserInfor> listUser = tblUserLogicImpl.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
			request.setAttribute("listUser", listUser);
			// lấy và truyền danh sách group sang view
			List<MstGroup> listGroup = mstGroupLogicImpl.getAllGroups();
			request.setAttribute("listGroup", listGroup);
			
			// khai báo đối tượng lưu trữ các thông tin trên màn hình listUser
			Map<String, Object> searchCondition = new HashMap<String, Object>();
			// lưu các thông tin cần thiết vào đối tượng lưu trữ
			searchCondition.put(Constant.OFFSET, offset);
			searchCondition.put(Constant.LIMIT, limit);
			searchCondition.put(Constant.GROUP_ID, groupId);
			searchCondition.put(Constant.FULL_NAME, fullName);
			searchCondition.put(Constant.SORT_TYPE, sortType);
			searchCondition.put(Constant.SORT_BY_FULL_NAME, sortByFullName);
			searchCondition.put(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
			searchCondition.put(Constant.SORT_BY_END_DATE, sortByEndDate);
			// lưu các thông tin cần thiết vào session
			session.setAttribute(Constant.SEARCH_CONDITION, searchCondition);
			
			
			// forward sang ADM002
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM002);
			rd.forward(request, response);
			
		} catch (Exception e) {
			
		}
	}

}
