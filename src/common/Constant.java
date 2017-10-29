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
	public static final String ADD_INPUT_PATH = "/addUserInput.do";
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
	
	// khác...
	public static final String EMPTY_STRING = "";
	public static final String OK = "OK";
	public static final String DEFAULT_CHARSET_ENCODING = "UTF-8";
	public static final String LIST_ERROR = "errList";
	
	
}
