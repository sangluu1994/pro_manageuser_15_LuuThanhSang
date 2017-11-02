/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * MessageErrorProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import common.Constant;

/**
 * Class lấy các câu thông báo lỗi
 * 
 * @author luuthanhsang
 */
public class MessageErrorProperties {
	// khởi tạo map lưu trữ thông tin message error
	public static Map<String, String> messageErrorProperties = new HashMap<String, String>();
	
	// block đọc thông tin từ file properties vào map lưu trữ
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file message_error_ja.properties
			properties.load(classLoader.getResourceAsStream(Constant.MESSAGE_ERROR_PROPERTIES_PATH));
			Enumeration<?> e = properties.propertyNames();
            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                messageErrorProperties.put(key, value);
            }
		} catch (Exception e) {
			// show console log ngoại lệ
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Phương thức lấy ra các câu thông báo lỗi.
	 * 
	 * @param key - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getErrMsg(String key) {
		return messageErrorProperties.get(key);
	}
	
}
