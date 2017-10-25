/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * DatabaseProperties.java, 2017-10-25 luuthanhsang
 *
 */
package properties;

import java.util.Properties;

import common.Constant;

/**
 * Class chứa phương thức lấy các thông tin database config
 * 
 * @author luuthanhsang
 *
 */
public class DatabaseProperties {
	/**
	 * Phương thức lấy ra config database
	 * 
	 * @param key - tương ứng với giá trị muốn đọc ra
	 * @return Giá trị tương ứng với key đầu vào
	 */
	public static String getString(String key) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream(Constant.DATABASE_PROPERTIES_PATH));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return properties.getProperty(key);
	}
}
