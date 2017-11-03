/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ConstantProperties.java, 2017-10-23 luuthanhsang
 */
package common;

/**
 * Class lưu trữ các giá trị hằng số thường sử dụng
 * 
 * @author luuthanhsang
 */
public class Constant {
	// các đường dẫn chính tương ứng với các controller
	public static final String ROOT_PATH = "/";
	public static final String LOG_IN_PATH = "/login.do";
	public static final String LIST_USER_PATH = "/listAllUser.do";
	public static final String ADD_USER_INPUT_PATH = "/addUserInput.do";
	public static final String ADD_VALIDATE_PATH = "/addUserValidate.do";
	public static final String EDIT_VALIDATE_PATH = "/editUserValidate.do";
	public static final String EDIT_CONFIRM_PATH = "/editUserConfirm.do";
	public static final String ADD_CONFIRM_PATH = "/addUserConfirm.do";
	public static final String EDIT_USER_PATH = "/editUser.do";
	public static final String SYSTEM_ERROR_PATH = "/error.do";
	public static final String SUCCESS_PATH = "/success.do";
	public static final String DETAIL_USER_PATH = "/detailUser.do";
	public static final String DELETE_USER_PATH = "/deleteUser.do";
	public static final String LOG_OUT_PATH = "/logout.do";
	public static final String FILTER_URL_PATTERN = "*";
	
	// các trang jsp chính
	public static final String ADM001 = "/view/jsp/ADM001.jsp";
	public static final String ADM002 = "/view/jsp/ADM002.jsp";
	public static final String ADM003 = "/view/jsp/ADM003.jsp";
	public static final String ADM004 = "/view/jsp/ADM004.jsp";
	public static final String ADM005 = "/view/jsp/ADM005.jsp";
	public static final String ADM006 = "/view/jsp/ADM006.jsp";
	public static final String ADM_SYSTEM_ERROR = "/view/jsp/System_Error.jsp";
	
	// các thư mục css/js/img
	public static final String CSS_FOLDER_PATTERN = "^/view/css/.*";
	public static final String JS_FOLDER_PATTERN = "^/view/js/.*";
	public static final String IMG_FOLDER_PATTERN = "^/view/img/.*";
	
	// thư mục chứa file jsp
	public static final String JSP_FOLDER_PATTERN = "^/view/jsp/.*";
	
	// đường dẫn lưu các file properties
	public static final String ADMIN_PROPERTIES_PATH = "properties/files/admin.properties";
	public static final String DATABASE_PROPERTIES_PATH = "properties/files/database.properties";
	public static final String MESSAGE_ERROR_PROPERTIES_PATH = "properties/files/message_error_ja.properties";
	public static final String MESSAGE_PROPERTIES_PATH = "properties/files/message_ja.properties";
	public static final String CONFIG_PROPERTIES_PATH = "properties/files/config.properties";
	
	// tên key admin_user, password_hash trong file admin.properties
	public static final String ADMIN_USER = "admin_user";
	public static final String ADMIN_PASS_HASH = "passwd_hash";
	
	// currentUser
	public static final String CURRENT_LOGIN_USER = "currentUser";
	public static final String TXT_USERNAME = "txtUsername";
	
	// constant trong màn hình login
	public static final String TXT_LOGIN_ID = "loginId";
	public static final String TXT_PASSWORD = "password";
	
	// constant trong màn hình listUser
	public static final String TYPE = "type";
	public static final String TYPE_SEARCH = "search";
	public static final String TYPE_SORT = "sort";
	public static final String TYPE_PAGING = "paginate";
	public static final String TYPE_BACK = "back";
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String FULL_NAME = "fullName";
	public static final String GROUP_ID = "groupId";
	public static final String SORT_TYPE = "sortType";
	public static final String SORT_BY_FULL_NAME = "sortByFullName";
	public static final String SORT_BY_CODE_LEVEL = "sortByCodeLevel";
	public static final String SORT_BY_END_DATE = "sortByEndDate";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String LIMIT = "limit";
	public static final String PAGE_LIMIT = "pageLimit";
	public static final int DEFAULT_GROUP_ID = 0;
	public static final int DEFAULT_TOTAL = 0;
	public static final String DEFAULT_SELECTION = "選択してください";
	public static final int DEFAULT_PAGE = 1;
	public static final String DEFAULT_SORT_TYPE = "sortByFullName";
	public static final String SEARCH_CONDITION = "searchCondition";
	public static final String LIST_PAGING = "listPaging";
	public static final String LIST_USER = "listUser";
	public static final String LIST_GROUP = "listGroup";
	
	public static final String TXT_FULL_NAME = "full_name";
	public static final String SL_GROUP_ID = "group_id";
	public static final String SORT_TYPE_PARAM = "sortType";
	public static final String PAGE_PARAM = "page";
	public static final String TOTAL_PAGE = "totalPage";
	
	// error message
	public static final String ER001 = "ER001";
	public static final String ER001TOTAL = "ER001TOTAL";
	public static final String ER001PASS = "ER001PASS";
	public static final String ER001LOGIN = "ER001LOGIN";
	public static final String ER001FULL = "ER001FULL";
	public static final String ER001KATA = "ER001KATA";
	public static final String ER001MAIL = "ER001MAIL";
	public static final String ER001TEL = "ER001TEL";
	public static final String ER002 = "ER002";
	public static final String ER002GROUP = "ER002GROUP";
	public static final String ER003 = "ER003";
	public static final String ER003LOGIN = "ER003LOGIN";
	public static final String ER003MAIL = "ER003MAIL";
	public static final String ER004 = "ER004";
	public static final String ER004GROUP = "ER004GROUP";
	public static final String ER004JAPAN = "ER004JAPAN";
	public static final String ER005TOTAL = "ER005TOTAL";
	public static final String ER005 = "ER005";
	public static final String ER005MAIL = "ER005MAIL";
	public static final String ER005TEL = "ER005TEL";
	public static final String ER005DATE = "ER005DATE";
	public static final String ER006 = "ER006";
	public static final String ER006FULL = "ER006FULL";
	public static final String ER006KATA = "ER006KATA";
	public static final String ER006MAIL = "ER006MAIL";
	public static final String ER006TEL = "ER006TEL";
	public static final String ER007 = "ER007";
	public static final String ER007PASS = "ER007PASS";
	public static final String ER007LOGIN = "ER007LOGIN";
	public static final String ER008 = "ER008";
	public static final String ER008PASS = "ER008PASS";
	public static final String ER008LOGIN = "ER008LOGIN";
	public static final String ER008MAIL = "ER008MAIL";
	public static final String ER008TEL = "ER008TEL";
	public static final String ER009 = "ER009";
	public static final String ER009KATA = "ER009KATA";
	public static final String ER010 = "ER010";
	public static final String ER011 = "ER011";
	public static final String ER011START = "ER011START";
	public static final String ER011END = "ER011END";
	public static final String ER011TOTAL = "ER011TOTAL";
	public static final String ER011BIRTH = "ER011BIRTH";
	public static final String ER012 = "ER012";
	public static final String ER013 = "ER013";
	public static final String ER014 = "ER014";
	public static final String ER015 = "ER015";
	public static final String ER016 = "ER016";
	public static final String ER017 = "ER017";
	public static final String ER018 = "ER018";
	public static final String ER018TOTAL = "ER018TOTAL";
	public static final String ER019 = "ER019";
	
	// database connect
	public static final String URL = "url";
	public static final String USERNAME = "user";
	public static final String PASSWORD = "password";
	public static final String DRIVER = "driver";
	
	// khác...
	public static final String EMPTY_STRING = "";
	public static final String OK = "OK";
	public static final String DEFAULT_CHARSET_ENCODING = "UTF-8";
	public static final String DEFAULT_RESPONSE_CONTENT_TYPE = "text/html; charset=UTF-8";
	public static final String LIST_ERROR = "errList";
	public static final String ERR_MSG = "errMsg";
	public static final int START_YEAR = 1900;
	public static final String ALL_MST_GROUP = "allMstGroup";
	public static final String DEFAULT_CODE_LEVEL = "";
	public static final String ALL_MST_JAPAN = "allMstJapan";
	public static final String LIST_YEAR = "listYears";
	public static final String LIST_MONTH = "listMonths";
	public static final String LIST_DAY = "listDays";
	public static final String USER_DEFAULT = "userDefault";
	public static final String CONFIRM_TYPE = "confirm";
	public static final String CURRENT_YEAR = "currentYear";
	public static final String CURRENT_MONTH = "currentMonth";
	public static final String CURRENT_DAY = "currentDay";
	
}
