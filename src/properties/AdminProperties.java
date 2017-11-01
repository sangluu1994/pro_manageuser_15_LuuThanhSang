/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import common.Constant;

/**
 * Class cài đặt phương thức lấy thông tin từ file properties
 * 
 * @author luuthanhsang
 */
public class AdminProperties {
	/**
	 * Phương thức lấy thông tin đăng nhập của admin
	 *
	 * @return adminInfo - thông tin đăng nhập của admin
	 */
	public static Map<String, String> getAdminInfo() {
		// khởi tạo map lưu trữ thông tin admin sẽ trả về
		Map<String, String> adminInfo = new HashMap<String, String>();
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		try {
			// load file admin.properties
			prop.load(classLoader.getResourceAsStream(Constant.ADMIN_PROPERTIES_PATH));
			
			// lấy thông tin admin_user và password hash để gán vào map
			adminInfo.put(Constant.ADMIN_USER, prop.getProperty(Constant.ADMIN_USER));
			adminInfo.put(Constant.ADMIN_PASS_HASH, prop.getProperty(Constant.ADMIN_PASS_HASH));
			// trả về thông tin admin được lưu vào map
			return adminInfo;
		} catch (Exception e) {
			// show console log ngoại lệ
			System.out.println(e.getMessage());
			// trả về null
			return null;
		} 
	}

}
