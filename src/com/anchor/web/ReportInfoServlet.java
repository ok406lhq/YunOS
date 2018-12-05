package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dao.ReportInfoDao;
import com.anchor.service.ReportInfoService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class ReportInfoServlet
 */
public class ReportInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportInfoServlet() {
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
			con = dbUtil.getCon();
//			if (null != con)
//			{
				String qType = request.getParameter("qType");
				String oper=request.getParameter("oper");
				ReportInfoService reportInfoService =  new ReportInfoService();
				String prjInfo = "";				
				ReportInfoDao resDao = new ReportInfoDao();
				
				if("addViewNum".equals(oper)){//查看次数
					String id=request.getParameter("id");
					prjInfo=Integer.toString(resDao.AddReportViewNums(con,id));
//					prjInfo="views";
					HtmlUtil.writerHtml(response, prjInfo);
					return;
				}else if("queryReportsForPrjlist".equals(oper)){//结构信息页面日常报表查询
					String prjIds=request.getParameter("prjName");
					String reportType=request.getParameter("reportType");
					prjInfo = resDao.queryReportsForPrjlist(prjIds, reportType);
					HtmlUtil.writerHtml(response, prjInfo);
					return;
				}
				
				if (qType == "Q")
				{//查询日志信息
					
				}
				else
				{//"JCJB"
					String prjName=request.getParameter("prjName");
					String starttime=request.getParameter("starttime");
					String endtime=request.getParameter("endtime");
					String sReportType=request.getParameter("sReportType"); //报表类型 日常报表->daily 分析报告->analysis 预警报表->warning
					
					prjInfo = reportInfoService.Query(con, prjName, starttime, endtime,sReportType);
				}
				
				
				if (prjInfo == "")
				{
					//将前端js的error变量赋值。重新加载后，会显示错误信息
					request.setAttribute("error","用户名或密码错误");
					//服务器跳转，重新装载页面。
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				else
				{
					HtmlUtil.writerHtml(response, prjInfo);
				}
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
