package com.anchor.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dal.AuthorDao;
import com.anchor.dal.ReportDao2;
import com.anchor.dal.ProjectDao;
import net.sf.json.JSONObject;

import com.anchor.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;

public class MonitorReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//分页
//    private int pageNum; //当前页码
//    private int pageSize; //每页显示的条数
//    private int totalRecord; //总的记录条数 
//    
//    private int totalPage; //总页数
//    private int startSize; //开始索引
//    List list;//每页显示的数据
//    
//    private int start;
//    private int end;
	
	
	
    public MonitorReportServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	private final String GET_PROJECT="getProject";
 	private final String GET_AUTHOR="getAuthor";
 	private final String GET_REPORT="getReport";
 	private final String GET_REPORTALL="getReportAll";
 	
 	private final String GET_REPORTWARNING="getReportWarning";
 	
//	private final String GET_GNSS_COMPARE_DATA="getGnssCompareData";
	private ProjectDao projectDao = new ProjectDao();
    private AuthorDao authorDao = new AuthorDao();
    private ReportDao2 reportDao = new ReportDao2();
//	private GnssStaticDao gnssStaticDao = new GnssStaticDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter("requestType");
		switch (requestType) {
			case GET_PROJECT:
				getProject(request, response);
				break;
			case GET_AUTHOR:
				getAuthor(request,response);
				break;
			case GET_REPORT:
			    getReportInfo(request,response);
			    break;
			case GET_REPORTALL:
				getReportAll(request,response);
			    break;
			case GET_REPORTWARNING:
				getReportWarning(request,response);
			    break;
			    
		}
	}
	
   /**
    *   获取结构物列表
    */
	private void getProject(HttpServletRequest request, HttpServletResponse response){
		String projectsIds = (String) request.getSession().getAttribute("projects");
//		System.out.println("projectsIds"+projectsIds);
		List<Map<String, Object>> projectList = projectDao.findProjectByIds(projectsIds);
		HtmlUtil.writerJson(response, projectList);
	}
	
	/**
	 *   获取创建人列表
	 */
	private void getAuthor(HttpServletRequest request, HttpServletResponse response){
		//String authorIds = (String) request.getSession().getAttribute("userID");
		//System.out.println("authorIds:"+authorIds);
		List<Map<String, Object>> authorList = authorDao.findProjectByIds();
		HtmlUtil.writerJson(response, authorList);	
	}
	
	
	/**
	 * 获取报表信息 
	 */
	private void getReportInfo(HttpServletRequest request, HttpServletResponse response) {
		String projectsIds = (String) request.getSession().getAttribute("projects");
		String reportType = request.getParameter("reportType"); //报表类型
		String projectId = request.getParameter("projectId"); //结构物ID
//		String authorId = request.getParameter("authorId");   //创建人ID
		String startTime = request.getParameter("startTime")+" 00:00:00";
		String endTime = request.getParameter("endTime")+" 23:59:59";
		
		
		JSONObject jsonResult = new JSONObject();
		
		int count=0;
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		
		
		com.alibaba.fastjson.JSONArray jsonData = new com.alibaba.fastjson.JSONArray();
		
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			List<Map<String, Object>> reportResult = reportDao.findReportDataPage(projectsIds, reportType, projectId, startTime, endTime, page, limit);
			
    		count = reportDao.findReportData(projectsIds, reportType, projectId, startTime, endTime).size();
			
			for (Map<String, Object> result : reportResult) {
				com.alibaba.fastjson.JSONObject jsonDataItem = new com.alibaba.fastjson.JSONObject();
				jsonDataItem.put("sReportName", result.get("sReportName"));
				if ( result.get("dtCreatAt") != null) {
					jsonDataItem.put("dtCreatAt", result.get("dtCreatAt").toString());
				}
				
				jsonDataItem.put("sUserName", result.get("sUserName"));
				jsonDataItem.put("sURLAddress", result.get("sURLAddress"));
				jsonData.add(jsonDataItem);
			}
		}
		
		jsonResult.put("code", "0");
		jsonResult.put("msg", "0");
		jsonResult.put("count",count);
		jsonResult.put("data", jsonData);
		
		HtmlUtil.writerJson(response, jsonResult);	
	}
	
	
	   /**
	    *   获取全部报表列表
	    */
		private void getReportAll(HttpServletRequest request, HttpServletResponse response){
			//结构物ID
			String projectsIds = (String) request.getSession().getAttribute("projects");
//			System.out.println("projectsIds"+projectsIds);
			//结构物信息
			List<Map<String, Object>> projectList = projectDao.findProjectByIds(projectsIds);

			HtmlUtil.writerJson(response, projectList);
		}
		
		/**
		 * 获取预警报高信息 
		 */
		private void getReportWarning(HttpServletRequest request, HttpServletResponse response) {
			String projectsIds = (String) request.getSession().getAttribute("projects");
			String reportType = request.getParameter("reportType"); //报表类型
			String projectId = request.getParameter("projectId"); //结构物ID
			String warningLevel = request.getParameter("warningLevel");   //创建人ID
			String startTime = request.getParameter("startTime")+" 00:00:00";
			String endTime = request.getParameter("endTime")+" 23:59:59";
			
			
			JSONObject jsonResult = new JSONObject();
			
			int count=0;
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			
			
			com.alibaba.fastjson.JSONArray jsonData = new com.alibaba.fastjson.JSONArray();
			
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
				List<Map<String, Object>> reportResult = reportDao.findReportWarning(projectsIds,reportType, projectId, warningLevel, startTime, endTime);
				
	    		count = reportDao.findReportWarningPage(projectsIds, reportType, projectId, warningLevel, startTime, endTime, page, limit).size();
				
				for (Map<String, Object> result : reportResult) {
					com.alibaba.fastjson.JSONObject jsonDataItem = new com.alibaba.fastjson.JSONObject();
					jsonDataItem.put("sReportName", result.get("sReportName"));
					if ( result.get("dtCreatAt") != null) {
						jsonDataItem.put("dtCreatAt", result.get("dtCreatAt").toString());
					}
					
					jsonDataItem.put("sUserName", result.get("sUserName"));
					jsonDataItem.put("sURLAddress", result.get("sURLAddress"));
					jsonDataItem.put("sWarningLevel", result.get("sWarningLevel"));
					jsonData.add(jsonDataItem);
				}
			}
			
			jsonResult.put("code", "0");
			jsonResult.put("msg", "0");
			jsonResult.put("count",count);
			
			jsonResult.put("data", jsonData);
			
			HtmlUtil.writerJson(response, jsonResult);	
		}

}
