/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * LogFile.java, 2017-11-27 luuthanhsang
 */
package common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class xử lí ghi log
 *
 * @author luuthanhsang
 */
public class LogFile {
	private static Logger logger = Logger.getLogger("log");

	static {
		try {
			FileHandler handler = new FileHandler("default.log", true);
			SimpleFormatter simpleFormatter = new SimpleFormatter();
			handler.setFormatter(simpleFormatter);
			logger.addHandler(handler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Phương thức ghi warning log 
	 *
	 * @param value - giá trị ghi ra log
	 */
	public static void warning(String value) {
		logger.warning(value);
	}

	/**
	 * Phương thức ghi info log
	 *
	 * @param value - giá trị ghi ra log
	 */
	public static void info(String value) {
		logger.info(value);
	}
	
}
