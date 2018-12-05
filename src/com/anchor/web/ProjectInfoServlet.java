package com.anchor.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.ProjectInfoDao;
import com.anchor.model.ProjectInfo;
import com.anchor.model.WarningInfo;
import com.anchor.service.ProjectInfoService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.StringUtil;

/**
 * Servlet implementation class ProjectInfoServlet
 */
public class ProjectInfoServlet extends HttpServlet {
public final static String SUCCESS ="success";  
	
	public final static String MSG ="msg";  
	
	
	public final static String DATA ="data";  
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectInfoServlet() {
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
	 * 功能：查询所有项目基本信息，用于网站上《项目基本信息》的页面内容显示
	 * 以json形式返回 -wtyang2017/07/06
	 */
	//sample
	//[{"Status":"","结构概述":"","标段":"","prjName":"WQX","StatusDes":"\"结构\":\"木头\",\"设备\":\"我不”","prjType":"边坡","Latitude":0,"Longitude":0,"主要监测内容":""},
	//{"结构概述":"","标段":"","prjName":"wqx2","prjType":"桥梁","Latitude":0,"Longitude":0,"主要监测内容":""},
	//{"结构概述":"","标段":"","prjName":"wqx3","prjType":"大坝","Latitude":0,"Longitude":0,"主要监测内容":""}]
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String prjName = request.getParameter("prjName");
		ProjectInfoService prjInfoService =  new ProjectInfoService();
		String json = prjInfoService.SummaryProjectStatus(prjName);
		System.out.println("json:"+json);
		HtmlUtil.writerHtml(response, json);
		return ;
/*		Connection con = null;
		DbUtil dbUtil = null;
		try{
			dbUtil=new DbUtil();
			con = dbUtil.getCon();
			if (null != con)
			{
				ProjectInfoService prjInfoService =  new ProjectInfoService();
				String prjInfo = prjInfoService.QueryProjectInfo(con);
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
			}
		}
		catch (Exception e) {
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
		*/
	}/**
	 * 功能：查询所有项目基础信息，以json形式返回 -wtyang2017/07/06
	 */
	//sample
	//[{"Status":"","结构概述":"","标段":"","prjName":"WQX","StatusDes":"\"结构\":\"木头\",\"设备\":\"我不”","prjType":"边坡","Latitude":0,"Longitude":0,"主要监测内容":""},
	//{"结构概述":"","标段":"","prjName":"wqx2","prjType":"桥梁","Latitude":0,"Longitude":0,"主要监测内容":""},
	//{"结构概述":"","标段":"","prjName":"wqx3","prjType":"大坝","Latitude":0,"Longitude":0,"主要监测内容":""}]
	/*
	protected String SummaryProject() {
		// TODO Auto-generated method stub
		Connection con = null;
		DbUtil dbUtil = null;
		String json = "";
		try{
			dbUtil=new DbUtil();
			con = dbUtil.getCon();
			if (null != con)
			{
				JSONArray summaryArray = new JSONArray();
				//prjInfo
				ProjectInfoService prjInfoService =  new ProjectInfoService();
				JSONObject prjInfoItem = new JSONObject();
				JSONArray prjInfoArray = prjInfoService.QueryProjectInfo2(con);
				prjInfoItem.put("PrjInfo", prjInfoArray);
				summaryArray.add(prjInfoItem);
				//prjStatus
				JSONObject prjStatusItem = new JSONObject();
				JSONArray prjStatusArray = prjInfoService.QueryProjectStatus(con);
				prjInfoItem.put("prjStatus", prjStatusArray);
				summaryArray.add(prjInfoItem);
				
				json = summaryArray.toString();
			}
		}
		catch (Exception e) {
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
		return json;
	}
	*/
}
