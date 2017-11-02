/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * AddUserInputController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.Constant;
import entity.MstGroup;
import entity.MstJapan;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;

/**
 * Controller để xử lí cho màn hình ADM003 trường hợp Add
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.ADD_USER_INPUT_PATH)
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MstGroupLogicImpl mstGroupLogic;
	private MstJapanLogicImpl mstJapanLogic;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserInputController() {
    	mstGroupLogic = new MstGroupLogicImpl();
    	mstJapanLogic = new MstJapanLogicImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * Phương thức thực hiện thiết lập giá trị cho các hạng mục selectbox ở màn hình ADM003
	 *
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @throws SQLException
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<MstGroup> allMstGroup = mstGroupLogic.getAllMstGroups();
		List<MstJapan> allMstJapan = mstJapanLogic.getAllMstJapan();
	}
	
	/**
	 * Phương thức thiết lập giá trị search default cho màn hình ADM003
	 * 
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @return
	 */
	private UserInfor setDefaultValue(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
