/**
 * Copyright(C) 2017 Luvina Software Company
 *
 * ErrorController.java, 2017-11-02 luuthanhsang
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;

/**
 * Controller xử lí lỗi
 * 
 * @author luuthanhsang
 */
@WebServlet(Constant.SYSTEM_ERROR_PATH)
public class ErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// forward đến ADM_SYSTEM_ERROR
		RequestDispatcher rd = request.getRequestDispatcher(Constant.ADM_SYSTEM_ERROR);
		rd.forward(request, response);
	}

}
