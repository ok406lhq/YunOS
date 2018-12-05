package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dao.updateinfoDao;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class updateinfo
 */
public class updateinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updateinfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String info = request.getParameter("info");
		String userId = request.getParameter("userId");
		
		Connection con = null;
		DbUtil dbUtil = null;
		dbUtil = new DbUtil();
		try {
			con = dbUtil.getCon();
			updateinfoDao dao = new updateinfoDao();
			boolean falg = dao.updateinfo(con, id, info,userId);
			if (falg) {
				HtmlUtil.writerHtml(response, "处理成功!");
			} else {
				HtmlUtil.writerHtml(response, "处理失败!请重试");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
