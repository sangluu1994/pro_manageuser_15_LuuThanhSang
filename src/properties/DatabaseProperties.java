/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * DatabaseProperties.java, 2017-10-25 luuthanhsang
 */
package properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import common.Constant;

/**
 * Class chứa phương thức lấy các thông tin database config
 * 
 * @author luuthanhsang
 */
public class DatabaseProperties {
	// khởi tạo map lưu trữ thông tin database
	public static Map<String, String> databaseProperties = new HashMap<String, String>();
	
	// block đọc thông tin từ file properties vào map lưu trữ
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file database.properties
			properties.load(classLoader.getResourceAsStream(Constant.DATABASE_PROPERTIES_PATH));
			Enumeration<?> e = properties.propertyNames();
            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                databaseProperties.put(key, value);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Phương thức lấy thông tin trong file database.properties
	 * 
	 * @param key - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getValue(String key) {
		return databaseProperties.get(key);
	}
	
}
