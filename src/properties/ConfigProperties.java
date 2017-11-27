/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ConfigProperties.java, 2017-10-27 luuthanhsang
 */
package properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import common.Constant;

/**
 * Class chứa phương thức lấy các thông tin config
 *
 * @author luuthanhsang
 */
public class ConfigProperties {
	// khởi tạo map lưu trữ thông tin config
	public static Map<String, String> configProperties = new HashMap<String, String>();
	
	// block đọc thông tin từ file properties vào map lưu trữ
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file config.properties
			properties.load(classLoader.getResourceAsStream(Constant.CONFIG_PROPERTIES_PATH));
			Enumeration<?> e = properties.propertyNames();
            while(e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);
                configProperties.put(key, value);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Phương thức lấy thông tin trong file config.properties
	 * 
	 * @param key - key của giá trị muốn lấy
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getValue(String key) {
		return configProperties.get(key);
	}
	
}
