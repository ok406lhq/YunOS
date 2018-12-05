package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import sun.management.counter.Variability;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.SystemLogDao;
import com.anchor.service.SystemLogService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class SystemlogoServlet
 */
public class SystemlogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemlogoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String project = (String) request.getSession().getAttribute("projects");
		String oper=request.getParameter("oper");
		Connection con = null;
		DbUtil dbUtil = null;
		 JSONObject result=new JSONObject();
		
		try{
//			dbUtil=new DbUtil();
//			con=dbUtil.getCon();
			if("Query".equals(oper)){
				
				 JSONArray dataArray=new JSONArray();
				 Integer page=Integer.valueOf(request.getParameter("page"));
				 Integer pageSize=Integer.valueOf(request.getParameter("pageSize"));
		    	 String starttime=request.getParameter("starttime");
		    	 String endtime=request.getParameter("endtime")+" 23:59:59";
		    	 int userid=Integer.valueOf(request.getParameter("userid"));
		    	 int totalRecord=0;
		    	 SystemLogDao systemlogdao=new SystemLogDao();
		    	 totalRecord=systemlogdao.QuerySystemLogoTotalRecord(project,userid,starttime,endtime);
		    	 SystemLogService systemLogService=new SystemLogService();
		    	 dataArray= systemLogService.QuerySystemData(project,userid,starttime,endtime,(page-1)*pageSize,pageSize);
		    	
		    	 result.put("totalRecord", totalRecord);
		    	 result.put("data", dataArray);
		    	 HtmlUtil.writerJson(response, result);
		    	 
			}else if("del".equals(oper)){
				String idsString=request.getParameter("ids");
				SystemLogDao systemlogdao=new SystemLogDao();
		    	String res= systemlogdao.SystemLogDelted(con,idsString);
		    	
		    	HtmlUtil.writerHtml(response, res);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
//				if (null != con)
//				{
					if (null != dbUtil)
					{
						dbUtil.closeCon(con);
					}
//				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
