/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.Enumeration;
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
	// khởi tạo map lưu trữ thông tin admin
	public static Map<String, String> adminProperties = new HashMap<String, String>();
	
	// block đọc thông tin từ file properties vào map lưu trữ
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file admin.properties
			properties.load(classLoader.getResourceAsStream(Constant.ADMIN_PROPERTIES_PATH));
			Enumeration<?> e = properties.propertyNames();
            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                adminProperties.put(key, value);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Phương thức lấy thông tin đăng nhập của admin
	 *
	 * @param key - key của giá trị muốn lấy
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getValue(String key) {
		return adminProperties.get(key);
	}

}
