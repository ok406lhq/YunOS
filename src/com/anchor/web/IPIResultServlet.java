package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.GNSSResultService;
import com.anchor.service.IPIResultService;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class IPIResultServlet
 */
public class IPIResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IPIResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		DbUtil dbUtil = null;
		try{
			dbUtil=new DbUtil();
			dbUtil.SetDatabase(Connest.DbName);
			con = dbUtil.getCon();
//			if (null != con)
//			{
				String prjName = request.getParameter("prjName");
				if (prjName == null)
				{
					prjName = "lz"; //这个默认的
				}
				String sensorName = request.getParameter("sensorName");
				if (sensorName == null)
				{
					sensorName = "KS-IPI4"; //这个默认的
				}
				
				String starttime = request.getParameter("starttime");
				if (starttime == null) starttime = "";
				String endtime=request.getParameter("endtime");
				if (endtime == null) endtime = "";
				String ymw=request.getParameter("ymw"); //周月年
				IPIResultService ptInfoService =  new IPIResultService();
				String ptInfos = ptInfoService.QueryIPIResult(con, prjName, sensorName,starttime,endtime,ymw);
				if (ptInfos == "")
				{
					request.setAttribute("error","无点位数据");
				}
				System.out.println(ptInfos);
				HtmlUtil.writerHtml(response, ptInfos);
				SystemLogService.addSystemLogData("查询","查询点位"+sensorName+","+starttime+"到"+endtime+"数据",request); ///----------------保存查询行为到系统日志
//			}
		}
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if (null != con)
					{
						if (null != dbUtil)
						{
							dbUtil.closeCon(con);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
}
