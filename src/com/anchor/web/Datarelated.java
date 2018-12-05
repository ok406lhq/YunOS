package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dao.DatarelatedDao;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONObject;

/**
 * 数据关联
 */
public class Datarelated extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Datarelated() {
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
		// 获取数据库连接
		Connection con = null;
		DbUtil dbUtil = null;
		dbUtil = new DbUtil();
		dbUtil.SetDatabase(Connest.DbName);
		try {
			con = dbUtil.getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取参数
		String start_time1 = request.getParameter("start_time1");
		String start_time2 = request.getParameter("start_time2");
		String end_time1 = request.getParameter("end_time1");
		String end_time2 = request.getParameter("end_time2");
		String sensortype = request.getParameter("sensortype");
		String prjName = request.getParameter("prjName");
		String starttime[] = { start_time1, start_time2 };
		String endtime[] = { end_time1, end_time2 };
		DatarelatedDao dao = new DatarelatedDao();
		try {
			JSONObject jsonArray = dao.getdata(con, starttime, endtime, sensortype, prjName);
			System.err.println(jsonArray.size());
			HtmlUtil.writerJson(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
