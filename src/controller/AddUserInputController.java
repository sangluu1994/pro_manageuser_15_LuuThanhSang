/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AddUserInputController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import validate.ValidateUser;

/**
 * Controller xử lí cho màn hình ADM003 trường hợp Add, Edit
 * 
 * @author luuthanhsang
 */
@WebServlet(
		name = Constant.ADD_USER_INPUT_SERVLET,
		urlPatterns = {Constant.ADD_USER_INPUT_PATH, Constant.ADD_USER_VALIDATE_PATH, Constant.EDIT_USER_PATH, Constant.EDIT_VALIDATE_PATH})
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Khai báo các đối tượng xử lí logic được sử dụng trong class
	private MstGroupLogic mstGroupLogic;
	private MstJapanLogic mstJapanLogic;
	private TblUserLogic tblUserLogic;
       
    /**
     * Constructor
     */
    public AddUserInputController() {
    	mstGroupLogic = new MstGroupLogicImpl();
    	mstJapanLogic = new MstJapanLogicImpl();
    	tblUserLogic = new TblUserLogicImpl();
    }

	/**
	 * Phương thức xử lí yêu cầu gọi ra màn hình ADM003 trong các trường hợp add, edit, back
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// kiểm tra trường hợp edit user
			String type = request.getParameter(Constant.TYPE);
			// nếu là trường hợp edit và userId không tồn tại
			if (Constant.TYPE_EDIT.equals(type)) {
				int userId = Common.convertStringToInt(request.getParameter(Constant.USER_INFOR_ID), 0);
				if (!tblUserLogic.isExistedUser(userId)) {
					Common.redirectErrorPage(request, response);
					return;
				}
			} 
			// setDataLogic, setDefaultValue
			setDataLogic(request, response);
			UserInfor userInfor = setDefaultValue(request, response);
			request.setAttribute(Constant.USER_INFOR, userInfor);
			// forward sang màn hình ADM003
			RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003);
			rd.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
	}

	/**
	 * Phương thức xử lí yêu cầu khi submit form tại màn hình ADM003 trong các trường hợp add, edit
	 * 
	 * @param request - request gửi đến server
	 * @param response - response trả về phía client
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// kiểm tra trường hợp edit user
			String userId = request.getParameter(Constant.USER_INFOR_ID);
			if (userId != null) {
				if (!tblUserLogic.isExistedUser(Common.convertStringToInt(userId, 0))) {
					// nếu không tồn tại user, điều hướng về trang lỗi
					Common.redirectErrorPage(request, response);
					return;
				}
			} 
			// lấy thông tin user từ request
			UserInfor userInfor = setDefaultValue(request, response);
			// validate form gửi lên
			ValidateUser validateUser = new ValidateUser();
			List<String> listError = validateUser.validateUserInfor(userInfor);
			if (listError.isEmpty()) { // nếu không có lỗi
				// tạo URL gọi đến AddUserConfirmController
				StringBuilder confirmURL = new StringBuilder();
				confirmURL.append(request.getContextPath());
				// lấy current timestamp làm id cho userInfor lưu lên session
				String id = Common.getTimeStampMillis();
				// xác định trường hợp add hay edit
				String action = userInfor.getUserId() == 0 ? Constant.ADD_USER_CONFIRM_PATH : Constant.EDIT_CONFIRM_PATH;
				confirmURL.append(action);
				confirmURL.append("?");
				confirmURL.append(Constant.USER_INFOR_ID);
				confirmURL.append("=");
				confirmURL.append(id);
				// lưu đối tượng userInfor lên session
				// định dạng ngày sinh
				userInfor.setBirthday(Common.toDate(Common.convertStringToInt(userInfor.getBirthYear(), 0), 
						Common.convertStringToInt(userInfor.getBirthMonth(), 0), 
						Common.convertStringToInt(userInfor.getBirthDate(), 0)));
				// nếu có chọn trình độ tiếng Nhật
				String codeLevel = userInfor.getCodeLevel();
				if (!Common.isNullOrEmpty(codeLevel)) {
					userInfor.setStartDate(Common.toDate(Common.convertStringToInt(userInfor.getStartYear(), 0), 
							Common.convertStringToInt(userInfor.getStartMonth(), 0), 
							Common.convertStringToInt(userInfor.getStartDay(), 0)));
					userInfor.setEndDate(Common.toDate(Common.convertStringToInt(userInfor.getEndYear(), 0), 
							Common.convertStringToInt(userInfor.getEndMonth(), 0), 
							Common.convertStringToInt(userInfor.getEndDay(), 0)));
				}
				// set đối tượng userInfor lên session
				request.getSession().setAttribute(id, userInfor);
				// redirect đến AddUserConfirmController
				response.sendRedirect(confirmURL.toString());
			} else { // nếu có lỗi
				// trả danh sách lỗi và userInfor về màn hình ADM003
				setDataLogic(request, response);
				request.setAttribute(Constant.USER_INFOR, userInfor);
				request.setAttribute(Constant.LIST_ERROR, listError);
				// forward sang màn hình ADM003
				RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM003);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Common.redirectErrorPage(request, response);
		}
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
		
		// Gán ngày tháng năm hiện tại lên request
		List<Integer> currentTime = Common.getCurrentYearMonthDay();
		request.setAttribute(Constant.CURRENT_YEAR, currentTime.get(0));
		request.setAttribute(Constant.CURRENT_MONTH, currentTime.get(1));
		request.setAttribute(Constant.CURRENT_DAY, currentTime.get(2));
	}
	
	/**
	 * Phương thức thiết lập giá trị search default cho màn hình ADM003
	 * 
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @return userInfor - đối tượng userInfor
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private UserInfor setDefaultValue(HttpServletRequest request, HttpServletResponse response) throws ParseException, ClassNotFoundException, SQLException {
		// lấy tham số "type" để xác định trường hợp vào màn hình ADM003
		String type = request.getParameter(Constant.TYPE);
		// khởi tạo đối tượng userInfor trả về
		UserInfor userInfor = new UserInfor();
		// gán giá trị mặc định cho đối tượng userInfor sẽ ghi vào db
		List<Integer> defaultDate = Common.getCurrentYearMonthDay();
		userInfor.setGroupId(Constant.DEFAULT_GROUP_ID);
		userInfor.setBirthYear(defaultDate.get(0).toString());
		userInfor.setBirthMonth(defaultDate.get(1).toString());
		userInfor.setBirthDate(defaultDate.get(2).toString());
		userInfor.setCodeLevel(Constant.DEFAULT_CODE_LEVEL);
		userInfor.setStartYear(defaultDate.get(0).toString());
		userInfor.setStartMonth(defaultDate.get(1).toString());
		userInfor.setStartDay(defaultDate.get(2).toString());
		userInfor.setEndYear(((Integer) ((int) defaultDate.get(0) + 1)).toString());
		userInfor.setEndMonth(defaultDate.get(1).toString());
		userInfor.setEndDay(defaultDate.get(2).toString());
		// xét trường hợp vào màn hình ADM003
		if (type == null) { // nếu là trường hợp thêm mới
			// do nothing
		} else if (Constant.CONFIRM_TYPE.equals(type)) { // nếu là trường hợp click button xác nhận ở màn hình ADM003
			// lấy giá trị từ request lưu vào userInfor
			// kiểm tra trường hợp add và edit
			String id = request.getParameter(Constant.USER_INFOR_ID);
			if (id == null) { // nếu là trường hợp add
				userInfor.setLoginName(request.getParameter(Constant.LOGIN_NAME_ADM003));
				userInfor.setPass(request.getParameter(Constant.PASS_ADM003));
				userInfor.setRePass(request.getParameter(Constant.CONFIRM_PASS_ADM003));
			} else { // nếu là edit
				userInfor.setUserId(Common.convertStringToInt(id, 0));
				userInfor.setLoginName(request.getParameter(Constant.LOGIN_NAME_ADM003));
			}
			userInfor.setGroupId(Common.convertStringToInt(request.getParameter(Constant.GROUP_ID_ADM003), Constant.DEFAULT_GROUP_ID));
			userInfor.setFullName(request.getParameter(Constant.FULL_NAME_ADM003));
			userInfor.setFullNameKana(request.getParameter(Constant.KANA_NAME_ADM003));
			userInfor.setBirthYear(request.getParameter(Constant.BIRTH_YEAR_ADM003));
			userInfor.setBirthMonth(request.getParameter(Constant.BIRTH_MONTH_ADM003));
			userInfor.setBirthDate(request.getParameter(Constant.BIRTH_DATE_ADM003));
			userInfor.setEmail(request.getParameter(Constant.EMAIL_ADM003));
			userInfor.setTel(request.getParameter(Constant.TEL_ADM003));
			// kiểm tra xem có những trường thuộc trình độ tiếng Nhật trong request gửi lên hay không
			String codeLevel = request.getParameter(Constant.CODE_LEVEL_ADM003);
			// nếu có, lưu vào userInfor
			if (!Common.isNullOrEmpty(codeLevel)) {
				userInfor.setCodeLevel(codeLevel);
				userInfor.setStartYear(request.getParameter(Constant.START_YEAR_ADM003));
				userInfor.setStartMonth(request.getParameter(Constant.START_MONTH_ADM003));
				userInfor.setStartDay(request.getParameter(Constant.START_DAY_ADM003));
				userInfor.setEndYear(request.getParameter(Constant.END_YEAR_ADM003));
				userInfor.setEndMonth(request.getParameter(Constant.END_MONTH_ADM003));
				userInfor.setEndDay(request.getParameter(Constant.END_DAY_ADM003));
				userInfor.setTotal(request.getParameter(Constant.TOTAL_ADM003));
			}
		} else if (Constant.TYPE_BACK.equals(type)) { // trường hợp back từ màn hình ADM004
			// lấy đối tượng userInfor từ session
			String id = request.getParameter(Constant.USER_INFOR_ID);
			userInfor = (UserInfor) request.getSession().getAttribute(id);
		} else if (Constant.TYPE_EDIT.equals(type)) { // trường hợp edit user, đi từ ADM005 sang
			// lấy đối tượng userInfor từ database
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_INFOR_ID), 0);
			userInfor = tblUserLogic.getUserInforById(userId);
			List<Integer> listBirday = Common.getYearMonthDay(userInfor.getBirthday());
			userInfor.setBirthYear(listBirday.get(0).toString());
			userInfor.setBirthMonth(listBirday.get(1).toString());
			userInfor.setBirthDate(listBirday.get(2).toString());
			if (userInfor.getCodeLevel() != null) {
				List<Integer> listStartDate = Common.getYearMonthDay(userInfor.getStartDate());
				List<Integer> listEndDate = Common.getYearMonthDay(userInfor.getEndDate());
				userInfor.setStartYear(listStartDate.get(0).toString());
				userInfor.setStartMonth(listStartDate.get(1).toString());
				userInfor.setStartDay(listStartDate.get(2).toString());
				userInfor.setEndYear(listEndDate.get(0).toString());
				userInfor.setEndMonth(listEndDate.get(1).toString());
				userInfor.setEndDay(listEndDate.get(2).toString());
			} else {
				userInfor.setStartYear(defaultDate.get(0).toString());
				userInfor.setStartMonth(defaultDate.get(1).toString());
				userInfor.setStartDay(defaultDate.get(2).toString());
				userInfor.setEndYear(((Integer) ((int) defaultDate.get(0) + 1)).toString());
				userInfor.setEndMonth(defaultDate.get(1).toString());
				userInfor.setEndDay(defaultDate.get(2).toString());
			}
		}
		return userInfor;
	}

}
