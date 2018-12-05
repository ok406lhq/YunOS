package com.anchor.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.util.HttpRequest;

/**
 * Servlet implementation class ReportUrl
 */
public class ReportUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportUrl() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String prjName = request.getParameter("prjName");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String url = "http://118.190.143.70:25036/ExportReport";
		String pament = "prjName=" + prjName + "&start_time=" + start_time + "&end_time=" + end_time;
		String result = HttpRequest.Get(url, pament);
		boolean falg=false;
		if (result!=null) {
			if (result.equals("{success:ok}")) {
				System.err.println(falg);
				falg=true;
			}
		}
		PrintWriter out= response.getWriter();
		out.print(falg);
		out.flush();
		out.close();
	}

}
