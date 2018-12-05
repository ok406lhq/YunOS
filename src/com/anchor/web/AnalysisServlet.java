package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.CompareDataDao;
import com.anchor.service.AnalysisService;
import com.anchor.service.GNSSResultService;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class AnalysisServlet
 */
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
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
			String prjName=request.getParameter("prjName");
			if("".equals(prjName) || null==prjName){
				prjName=Connest.DefaultPrjName;
			}
			//if(null!=con){
			
				String oper=request.getParameter("op");
				
				if("analysis".equals(oper)){ //如果是数据比对
				
					String sensorName=request.getParameter("sensorName");
				
					String starttime=request.getParameter("start_time");
					String endtime=request.getParameter("end_time");
					System.out.print(prjName+sensorName);
					AnalysisService QueryResult =  new AnalysisService();
					 JSONArray series=QueryResult.QueryAnalysisResult(con, prjName, sensorName, starttime, endtime);
					 JSONArray items=QueryResult.QueryAnalysisFilterResult(con, prjName, sensorName, starttime, endtime);
					 JSONObject DataResult=new JSONObject();
					 DataResult.put("series", series);
					 DataResult.put("items", items);
					 HtmlUtil.writerJson(response, DataResult);
					SystemLogService.addSystemLogData("查询",sensorName+"数据关联,时间范围"+starttime+"至"+endtime,request); ///----------------保存查询行为到系统日志
				}
			//}
		}catch(Exception e){
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
