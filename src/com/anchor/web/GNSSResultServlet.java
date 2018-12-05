package com.anchor.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.GNSSResultService;
import com.anchor.service.PointInfoService;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;


/**
 * Servlet implementation class GNSSResultServlet
 */
public class GNSSResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GNSSResultServlet() {
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
	 * 功能：查询指定点名(prjName)的测点数据，以json形式返回 -wtyang2017/07/06
	 */
	//[{"sdatetime":"2017-03-10 17:59:50","east":"-1.0747380000539124","north":"2.9663230000296608","up":"-2.6221179999993183"},{"sdatetime":"2017-03-10 18:59:50","east":"-13.44877500005532","north":"12.487308999989182","up":"-10.624877000000197"},{"sdatetime":"2017-03-10 19:59:50","east":"-6.326885999995284","north":"9.406378000043333","up":"1.7491040000004432"},{"sdatetime":"2017-03-10 20:59:50","east":"-9.760560999973677","north":"8.952799000078812","up":"2.8898489999992307"},{"sdatetime":"2017-03-10 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = null;
		DbUtil dbUtil = null;
		try{
			dbUtil=new DbUtil();
			dbUtil.SetDatabase(Connest.DbName);
			con = dbUtil.getCon();
			//if (null != con)
//			{
				String prjName = request.getParameter("prjName");
				//System.out.print(prjName);
				if (prjName == null)
				{
					prjName = "lwjc"; //这个默认的
				}
				String sensorName = request.getParameter("sensorName");
				if (sensorName == null)
				{
					sensorName = "pt01"; //这个默认的
				}
				
				String starttime=request.getParameter("starttime");
				String endtime=request.getParameter("endtime");
				String ymw=request.getParameter("ymw"); //周月年
				String filter=request.getParameter("filter"); //滤波结果 0无，1有
//				if(ymw == null||ymw.equals("")){
//					ymw = "day";
//				}
				GNSSResultService ptInfoService =  new GNSSResultService();
				String ptInfos = ptInfoService.QueryGNSSResult(con, prjName, sensorName,starttime,endtime,ymw,filter);
				if (ptInfos == "")
				{
					request.setAttribute("error","无点位数据");
				}
				HtmlUtil.writerHtml(response, ptInfos);
				//SystemLogService.addSystemLogData("查询","查询点位"+sensorName+","+starttime+"到"+endtime+"数据",request); ///----------------保存查询行为到系统日志
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
