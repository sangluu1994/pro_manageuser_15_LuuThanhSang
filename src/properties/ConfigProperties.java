/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * ConfigProperties.java, 2017-10-27 luuthanhsang
 */
package properties;

import java.util.Properties;

import common.Constant;

/**
 * Class chứa phương thức lấy các thông tin config
 *
 * @author luuthanhsang
 */
public class ConfigProperties {
	/**
	 * Phương thức lấy ra các thông tin trong file config.properties
	 * 
	 * @param key - key của giá trị muốn lấy
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getValue(String key) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			// load file config.properties
			properties.load(classLoader.getResourceAsStream(Constant.CONFIG_PROPERTIES_PATH));
			// trả về giá trị
			return properties.getProperty(key);
		} catch (Exception e) {
			// show console log ngoại lệ
			System.out.println(e.getMessage());
			// trả về null
			return null;
		}
		
	}
}
