/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AddUserInputController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
import entity.MstJapan;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;

/**
 * Controller để xử lí cho màn hình ADM003 trường hợp Add
 * 
 * @author luuthanhsang
 */
@WebServlet(urlPatterns = {Constant.ADD_USER_INPUT_PATH, Constant.ADD_VALIDATE_PATH})
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MstGroupLogicImpl mstGroupLogic;
	private MstJapanLogicImpl mstJapanLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserInputController() {
    	mstGroupLogic = new MstGroupLogicImpl();
    	mstJapanLogic = new MstJapanLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			setDataLogic(request, response);
			UserInfor userDefault = setDefaultValue(request, response);
			request.getSession().setAttribute(Constant.USER_DEFAULT, userDefault);
			
			// forward sang màn hình listUser
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003);
			rd.forward(request, response);
		} catch (Exception e) {
			try {
				// điều hướng về trang lỗi nếu có lỗi
				StringBuilder stringBuilder = new StringBuilder(request.getContextPath());
				response.sendRedirect(stringBuilder.append(Constant.SYSTEM_ERROR_PATH).toString());
			} catch (IOException e1) {
				e.printStackTrace();
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
	
	/**
	 * Phương thức thực hiện thiết lập giá trị cho các hạng mục selectbox ở màn hình ADM003
	 *
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		// Lấy danh sách tất cả các group, set lên request
		List<MstGroup> allMstGroup = mstGroupLogic.getAllMstGroups();
		MstGroup groupDefault = new MstGroup();
		groupDefault.setGroupId(Constant.DEFAULT_GROUP_ID);
		groupDefault.setGroupName(Constant.DEFAULT_SELECTION);
		allMstGroup.add(0, groupDefault);
		request.setAttribute(Constant.ALL_MST_GROUP, allMstGroup);
		
		// Lấy danh sách tất cả trình độ tiếng Nhật, set lên request
		List<MstJapan> allMstJapan = mstJapanLogic.getAllMstJapan();
		MstJapan mstJapanDefault = new MstJapan();
		mstJapanDefault.setCodeLevel(Constant.DEFAULT_CODE_LEVEL);
		mstJapanDefault.setNameLevel(Constant.DEFAULT_SELECTION);
		allMstJapan.add(0, mstJapanDefault);
		request.setAttribute(Constant.ALL_MST_JAPAN, allMstJapan);
		
		// Lấy danh sách năm, tháng, ngày, set lên request
		List<Integer> listYears = Common.getListYear(Constant.START_YEAR, Common.getYearNow() + 1);
		List<Integer> listMonths = Common.getListMonth();
		List<Integer> listDays = Common.getListDay();
		request.setAttribute(Constant.LIST_YEAR, listYears);
		request.setAttribute(Constant.LIST_MONTH, listMonths);
		request.setAttribute(Constant.LIST_DAY, listDays);
		
		// Gán ngày tháng năm hiện tại lên session
		HttpSession session = request.getSession();
		List<Integer> currentTime = Common.getCurrentYearMonthDay();
		session.setAttribute(Constant.CURRENT_YEAR, currentTime.get(0));
		session.setAttribute(Constant.CURRENT_MONTH, currentTime.get(1));
		session.setAttribute(Constant.CURRENT_DAY, currentTime.get(2));
	}
	
	/**
	 * Phương thức thiết lập giá trị search default cho màn hình ADM003
	 * 
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @return
	 * @throws ParseException 
	 */
	private UserInfor setDefaultValue(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// lấy tham số "type" để xác định trường hợp vào màn hình ADM003
		String type = request.getParameter(Constant.TYPE);
		UserInfor userInfor = new UserInfor();
		// nếu là null, trường hợp thêm mới
		if (type == null) {
			// gán giá trị mặc định cho đối tượng userInfor sẽ ghi vào db
			List<Integer> defaultDate = Common.getCurrentYearMonthDay();
			userInfor.setLoginName(Constant.EMPTY_STRING);
			userInfor.setGroupId(Constant.DEFAULT_GROUP_ID);
			userInfor.setFullName(Constant.EMPTY_STRING);
			userInfor.setFullNameKana(Constant.EMPTY_STRING);
			userInfor.setBirthday(Common.toDate(defaultDate.get(0), defaultDate.get(1), defaultDate.get(2)));
			userInfor.setEmail(Constant.EMPTY_STRING);
			userInfor.setTel(Constant.EMPTY_STRING);
			userInfor.setPass(Constant.EMPTY_STRING);
			userInfor.setRePass(Constant.EMPTY_STRING);
			userInfor.setCodeLevel(Constant.DEFAULT_CODE_LEVEL);
			userInfor.setStartDate(Common.toDate(defaultDate.get(0), defaultDate.get(1), defaultDate.get(2)));
			userInfor.setEndDate(Common.toDate(defaultDate.get(0), defaultDate.get(1), defaultDate.get(2)));
			userInfor.setTotal(Constant.DEFAULT_TOTAL);
		} else if (Constant.CONFIRM_TYPE.equals(type)) { // nếu là trường hợp click button xác nhận
			// lấy giá trị từ request lưu vào userInfor
			userInfor.setLoginName(request.getParameter(Constant.LOGIN_NAME_ADM003));
			userInfor.setGroupId(Common.convertStringToInt(request.getParameter(Constant.GROUP_ID_ADM003)));
			userInfor.setFullName(request.getParameter(Constant.FULL_NAME_ADM003));
			userInfor.setFullNameKana(request.getParameter(Constant.KANA_NAME_ADM003));
			userInfor.setBirthYear(request.getParameter(Constant.BIRTH_YEAR_ADM003));
			userInfor.setBirthMonth(request.getParameter(Constant.BIRTH_MONTH_ADM003));
			userInfor.setBirthDate(request.getParameter(Constant.BIRTH_DATE_ADM003));
			userInfor.setEmail(request.getParameter(Constant.EMAIL_ADM003));
			userInfor.setTel(request.getParameter(Constant.TEL_ADM003));
			userInfor.setPass(request.getParameter(Constant.PASS_ADM003));
			userInfor.setRePass(request.getParameter(Constant.RE_PASS_ADM003));
			// kiểm tra xem có những trường thuộc trình độ tiếng Nhật trong request gửi lên hay không
			String codeLevel = request.getParameter(Constant.CODE_LEVEL_ADM003);
			// nếu có, lưu vào userInfor
			if (codeLevel != null && !Constant.DEFAULT_CODE_LEVEL.equals(codeLevel)) {
				userInfor.setCodeLevel(request.getParameter(Constant.CODE_LEVEL_ADM003));
				userInfor.setStartYear(request.getParameter(Constant.START_YEAR_ADM003));
				userInfor.setStartMonth(request.getParameter(Constant.START_MONTH_ADM003));
				userInfor.setStartDay(request.getParameter(Constant.START_DAY));
				userInfor.setEndYear(request.getParameter(Constant.END_YEAR_ADM003));
				userInfor.setEndMonth(request.getParameter(Constant.END_MONTH_ADM003));
				userInfor.setEndDay(request.getParameter(Constant.END_DAY_ADM003));
				userInfor.setTotal(Common.convertStringToInt(request.getParameter(Constant.TOTAL_ADM003)));
			}
		}
		return userInfor;
	}

}
