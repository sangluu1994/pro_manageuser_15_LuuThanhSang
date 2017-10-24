/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * MessageErrorProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.Properties;

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
	 * @param key
	 *          - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getString(String key) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("properties/message_error_ja.properties"));
		} catch (Exception e) {
			System.out.println("err!");
		}
		return properties.getProperty(key);
	}
	
//	public static void main(String[] args) {
//		System.out.println(getString("ER001LOGIN"));
//	}
}
