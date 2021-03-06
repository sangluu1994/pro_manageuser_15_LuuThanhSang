/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ListUserController.java, 2017-10-25 luuthanhsang
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
import logic.MstGroupLogic;
import logic.TblUserLogic;
import logic.impl.MstGroupLogicImpl;
import logic.impl.TblUserLogicImpl;

/**
 * Controller xử lí in, tìm kiếm danh sách user
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.LIST_USER_SERVLET,
		urlPatterns = Constant.LIST_USER_PATH)
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// khai báo các đối tượng xử lí logic trong màn hình listUser
	private TblUserLogic tblUserLogic;
	private MstGroupLogic mstGroupLogic;
       
    /**
     * Constructor
     */
    public ListUserController() {
    	// khởi tạo các đối tượng logic
    	tblUserLogic = new TblUserLogicImpl();
		mstGroupLogic = new MstGroupLogicImpl();
    }

	/**
	 * Call doPost
	 * 
	 * @param request - request được gửi đến server
	 * @param response - response trả về cho client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Phương thức xử lí các yêu cầu đến màn hình list user
	 * từ các trường hợp khác nhau: lần đầu vào màn hình list user,
	 * back từ màn hình khác, tìm kiếm, sort, paging
	 * 
	 * @param request - request được gửi đến server
	 * @param response - response trả về cho client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// lấy session
			HttpSession session = request.getSession();
			
			// khai báo các tham số để lấy danh sách user
			int groupId;
			String fullName;
			String sortType;
			String sortByFullName;
			String sortByCodeLevel;
			String sortByEndDate;
			int currentPage;
			int limit = Common.getLimit();
			int pageLimit = Common.getPageLimit();
			
			// kiểm tra các trường hợp gọi đến màn hình listUser:
			String type = request.getParameter(Constant.TYPE);
			if (type == null) { // trường hợp lần đầu vào trang
				// khởi tạo các tham số để lấy danh sách user
				groupId = Constant.DEFAULT_GROUP_ID;
				fullName = Constant.EMPTY_STRING;
				sortType = Constant.DEFAULT_SORT_TYPE;
				sortByFullName = Constant.ASC;
				sortByCodeLevel = Constant.ASC;
				sortByEndDate = Constant.DESC;
				currentPage = Constant.DEFAULT_PAGE;
				
			} else { // các trường hợp còn lại
				// lấy điều kiện tìm kiếm trước đó lưu trên session
				Map<String, Object> preSearchCondition = (Map<String, Object>) session.getAttribute(Constant.SEARCH_CONDITION);;
				groupId = (Integer) preSearchCondition.get(Constant.GROUP_ID);
				fullName = (String) preSearchCondition.get(Constant.FULL_NAME);
				sortType = (String) preSearchCondition.get(Constant.SORT_TYPE);
				sortByFullName = (String) preSearchCondition.get(Constant.SORT_BY_FULL_NAME);
				sortByCodeLevel = (String) preSearchCondition.get(Constant.SORT_BY_CODE_LEVEL);
				sortByEndDate = (String) preSearchCondition.get(Constant.SORT_BY_END_DATE);
				currentPage = (Integer) preSearchCondition.get(Constant.CURRENT_PAGE);
				
				if (Constant.TYPE_SEARCH.equals(type)) { // trường hợp tìm kiếm
					// lấy điều kiện tìm kiếm từ request
					groupId = Common.convertStringToInt(request.getParameter(Constant.SL_GROUP_ID), Constant.DEFAULT_GROUP_ID);
					fullName = request.getParameter(Constant.TXT_FULL_NAME);
					// về trang đầu (theo requirement)
					currentPage = Constant.DEFAULT_PAGE;
					
				} else if (Constant.TYPE_SORT.equals(type)) { // trường hợp click vào các button sắp xếp
					// thiết lập trang hiện tại về trang 1 (theo requirement)
					currentPage = Constant.DEFAULT_PAGE;
					// lấy điều kiện sắp xếp
					sortType = request.getParameter(Constant.SORT_TYPE_PARAM);
					
					// kiểm tra sortType
					if (Constant.SORT_BY_FULL_NAME.equals(sortType)) { // trường hợp sắp xếp theo tên
						// lấy kiểu sắp xếp theo tên
						sortByFullName = request.getParameter(Constant.SORT_BY_FULL_NAME);
						// thiết lập kiểu sắp xếp theo trình độ tiếng Nhật, endDate về mặc định
						sortByCodeLevel = Constant.ASC;
						sortByEndDate = Constant.DESC;
						
					} else if (Constant.SORT_BY_CODE_LEVEL.equals(sortType)) { // trường hợp sắp xếp theo trình độ tiếng Nhật
						// lấy kiểu sắp xếp theo trình độ tiếng Nhật
						sortByCodeLevel = request.getParameter(Constant.SORT_BY_CODE_LEVEL);
						// thiết lập kiểu sắp xếp theo fullName, endDate về mặc định
						sortByFullName = Constant.ASC;
						sortByEndDate = Constant.DESC;
						
					} else { // trường hợp sắp xếp theo ngày hết hạn
						// lấy kiểu sắp xếp theo ngày hết hạn
						sortByEndDate = request.getParameter(Constant.SORT_BY_END_DATE);
						// thiết lập kiểu sắp xếp theo fullName, trình độ tiếng Nhật về mặc định
						sortByFullName = Constant.ASC;
						sortByCodeLevel = Constant.ASC;
						
					}
					
				} else if (Constant.TYPE_PAGING.equals(type)) { // trường hợp click vào phân trang
					// lấy trang được yêu cầu
					currentPage = Common.convertStringToInt(request.getParameter(Constant.PAGE_PARAM), Constant.DEFAULT_PAGE);
					
				} else { // trường hợp quay lại màn hình listUser
					// sử dụng các điều kiện tìm kiếm đã lấy từ session
					// do nothing
				}
			}
			
			// lấy và gán danh sách tất cả group lên request
			List<MstGroup> listGroup = mstGroupLogic.getAllMstGroups();
			request.setAttribute(Constant.LIST_GROUP, listGroup);
			
			// lấy tổng số user
			int totalUsers = tblUserLogic.getTotalUsers(groupId, fullName);
			
			// lấy tổng số trang phục vụ việc phân trang
			int totalPage = Common.getTotalPage(totalUsers, limit);
			request.setAttribute(Constant.TOTAL_PAGE, totalPage);
			
			// nếu tìm được user và trang hiện tại có giá trị hợp lệ
			if (totalUsers > 0 && currentPage <= totalPage && currentPage > 0) {
				// lấy danh sách phân trang
				List<Integer> listPaging = Common.getListPaging(totalUsers, limit, currentPage);
				request.setAttribute(Constant.LIST_PAGING, listPaging);
				
				// lấy danh sách user của trang hiện tại
				int offset = Common.getOffset(currentPage, limit);
				List<UserInfor> listUser = tblUserLogic.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
				request.setAttribute(Constant.LIST_USER, listUser);
			}
			
			// khai báo đối tượng lưu trữ, lưu các thông tin cần thiết vào đối tượng lưu trữ
			Map<String, Object> searchCondition = new HashMap<String, Object>();
			searchCondition.put(Constant.GROUP_ID, groupId);
			searchCondition.put(Constant.FULL_NAME, fullName);
			searchCondition.put(Constant.SORT_TYPE, sortType);
			searchCondition.put(Constant.SORT_BY_FULL_NAME, sortByFullName);
			searchCondition.put(Constant.SORT_BY_CODE_LEVEL, sortByCodeLevel);
			searchCondition.put(Constant.SORT_BY_END_DATE, sortByEndDate);
			searchCondition.put(Constant.CURRENT_PAGE, currentPage);
			searchCondition.put(Constant.LIMIT, limit);
			searchCondition.put(Constant.PAGE_LIMIT, pageLimit);
			
			// lưu đối tượng lưu trữ điều kiện tìm kiếm, hiển thị vào session
			session.setAttribute(Constant.SEARCH_CONDITION, searchCondition);
			
			// forward sang view ADM002
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM002);
			rd.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

}
