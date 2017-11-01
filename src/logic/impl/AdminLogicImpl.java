/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AdminLogicImpl.java, 2017-10-23 luuthanhsang
 */
package logic.impl;

import java.util.ArrayList;
import logic.AdminLogic;
import validate.ValidateAdmin;

/**
 * Class xử lí logic của những trang quản lí của admin
 * 
 * @author luuthanhsang
 */
public class AdminLogicImpl implements AdminLogic {
	
	/* (non-Javadoc)
	 * @see logic.AdminLogic#validateLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<String> validateLogin(String loginName, String password) {
		return ValidateAdmin.validateLogin(loginName, password);
	}
	
}
