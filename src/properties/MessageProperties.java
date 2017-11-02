/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MessageProperties.java, 2017-11-03 luuthanhsang
 */
package properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import common.Constant;

/**
 * Class đọc thông tin từ file message properties
 *
 * @author luuthanhsang
 *
 */
public class MessageProperties {
	// khởi tạo map lưu trữ thông tin message
	public static Map<String, String> messageProperties = new HashMap<String, String>();
	
	// block đọc thông tin từ file properties vào map lưu trữ
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file message_ja.properties
			properties.load(classLoader.getResourceAsStream(Constant.MESSAGE_PROPERTIES_PATH));
			Enumeration<?> e = properties.propertyNames();
            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                messageProperties.put(key, value);
            }
		} catch (Exception e) {
			// show console log ngoại lệ
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Phương thức lấy ra các câu thông báo.
	 * 
	 * @param key - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getString(String key) {
		return messageProperties.get(key);
	}
		
}
