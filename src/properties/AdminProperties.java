/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminProperties.java, 2017-10-23 luuthanhsang
 */
package properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author luuthanhsang
 *
 */
public class AdminProperties {
	@SuppressWarnings("finally")
	public static Map<String, String> getAdminInfo() {
		Map<String, String> adminInfo = new HashMap<String, String>();
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		try {
			// load a properties file
			prop.load(classLoader.getResourceAsStream(ConstantProperties.ADMIN_PROPERTIES_PATH));
		
			
			// read information
			adminInfo.put("system_admin", prop.getProperty("system_admin"));
			adminInfo.put("passwd_salt", prop.getProperty("passwd_salt"));
			adminInfo.put("passwd_hash", prop.getProperty("passwd_hash"));
			adminInfo.put("hash_function", prop.getProperty("hash_function"));
			return adminInfo;
		} catch (Exception e) {
			// Print error message and return
			System.out.println("Error.");
			return null;
		} 
	}
//	public static void main(String[] args) {
//		for (String t : getAdminInfo()) {
//			System.out.println(t);
//		}
//	}
}
