/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * MessageErrorProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.Properties;

import common.Constant;

/**
 * Class lấy các câu thông báo lỗi
 * 
 * @author luuthanhsang
 *
 */
public class MessageErrorProperties {
	/**
	 * Phương thức lấy ra các câu thông báo lỗi.
	 * 
	 * @param key - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getString(String key) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream(Constant.MESSAGE_ERROR_PROPERTIES_PATH));
			// trả về thông tin cần lấy trong file properties
			return properties.getProperty(key);
		} catch (Exception e) {
//			System.out.println("err!");
			// trả về null
			return null;
		}
		
	}
	
}
