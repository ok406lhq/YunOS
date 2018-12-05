package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.anchor.dao.PointInfoDao;
import com.anchor.dao.ProjectImageBg;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.model.ProjectInfo;
import com.anchor.service.PointInfoService;
import com.anchor.service.ProjectInfoService;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class ProjectUpdateServlet
 */
public class ProjectUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectUpdateServlet() {
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
		String prjName = request.getParameter("prjName");
		String realDir = request.getSession().getServletContext().getRealPath("")+"\\uploadfiles\\";
		String op = request.getParameter("op");
		if(op.equals("query")){
			ProjectInfoService prjInfoService =  new ProjectInfoService();
			JSONObject projectJsonObject=null;
			projectJsonObject=prjInfoService.QueryProjectByPrjName(prjName,realDir);
			HtmlUtil.ToJson(response, projectJsonObject);
		}else if(op.equals("updatePointImageXY")){
			String imgX=request.getParameter("imgX");
			String imgY=request.getParameter("imgY");
			String sensorname=request.getParameter("sensorname");
			
			Connection conn = null;
			DbUtil dbUtil = null;
			
			try {
				dbUtil=new DbUtil();
				dbUtil.SetDatabase(Connest.DbName);
				conn=dbUtil.getCon();
				PointInfoDao pointInfoDao=new PointInfoDao();
				String string=pointInfoDao.updatePointImageXY(conn,imgX,imgY,sensorname);
				HtmlUtil.writerHtml(response, string);
				SystemLogService.addSystemLogData("编辑","编辑点位"+sensorname+"的背景图像坐标",request); ///----------------保存查询行为到系统日志
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
//					if (null != conn)
//					{
						if (null != dbUtil)
						{
							dbUtil.closeCon(conn);
						}
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else if(op.equals("group")){
			Connection conn = null;
			DbUtil dbUtil = null;
			try {
				dbUtil=new DbUtil();
				conn=dbUtil.getCon();
				
				ProjectInfoDao projectInfoDao =  new ProjectInfoDao();
				String result=projectInfoDao.queryPtInfoJson(conn);
				HtmlUtil.writerHtml(response, result);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
//					if (null != conn)
//					{
						if (null != dbUtil)
						{
							dbUtil.closeCon(conn);
						}
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}else if(op.equals("cropimgurl")){
			Connection conn = null;
			DbUtil dbUtil = null;
			String cropimgurl=request.getParameter("cropimgurl");
			try {
				dbUtil=new DbUtil();
				conn=dbUtil.getCon();
				ProjectImageBg projectInfoDao =  new ProjectImageBg();
				projectInfoDao.SaveProjectImageBg(conn,prjName,cropimgurl);
               String result="1";
				HtmlUtil.writerHtml(response, result);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
//					if (null != conn)
//					{
						if (null != dbUtil)
						{
							dbUtil.closeCon(conn);
						}
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(op.equals("update")){
			String PrjAlias=request.getParameter("PrjAlias");
			String PrjType=request.getParameter("PrjType");
			String PrjAddr=request.getParameter("PrjAddr");
			String JieGouDes=request.getParameter("JieGouDes");
			String JianceNeirong=request.getParameter("JianceNeirong");
			String starttime=request.getParameter("starttime");
			String endtime= request.getParameter("endtime");
			String longti=request.getParameter("jingdu");
			String lati=request.getParameter("weidu");
			String lxr1=request.getParameter("lxr1");
			String lxr2=request.getParameter("lxr2");
			 String property=ProjectInfo.JIEGOUDES+":"+JieGouDes+"|"+ProjectInfo.JIANCENEIRONG+":"+JianceNeirong+"|"+ProjectInfo.LXR1+":"+lxr1+"|"+ProjectInfo.LXR2+":"+lxr2;
			Connection conn = null;
			DbUtil dbUtil = null;
			try {
				dbUtil=new DbUtil();
				conn=dbUtil.getCon();
				
				ProjectInfo prjPoint = new ProjectInfo();
				prjPoint.setPrjAlais(PrjAlias);
				prjPoint.setPrjAddr(PrjAddr);
				prjPoint.setPrjType(PrjType);
				prjPoint.setEndTime(endtime);
				prjPoint.setStartTime(starttime);
				prjPoint.setLatitude(Double.parseDouble(lati));
				prjPoint.setLongitude(Double.parseDouble(longti));			
				prjPoint.setproperty(property);
				
				
			    prjPoint.setPrjName(prjName);
				ProjectInfoDao projectInfoDao = new ProjectInfoDao();
				String res=projectInfoDao.updateProjectInfo(conn,prjPoint);
				HtmlUtil.writerHtml(response, res);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
//					if (null != conn)
//					{
						if (null != dbUtil)
						{
							dbUtil.closeCon(conn);
						}
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
