package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.PointInfoDao;
import com.anchor.model.PointInfo;
import com.anchor.service.PointInfoService;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.HttpRequest;

/**
 * Servlet implementation class PointAll
 */
public class PointAll extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PointAll() {
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
		// TODO Auto-generated method stub
		DbUtil dbUtil = null;
		Connection con1 = null;
		String Points = "";
		String oper = request.getParameter("oper");
		try {
			dbUtil = new DbUtil();
			dbUtil.SetDatabase(Connest.DbName);
			con1 = dbUtil.getCon();
//			if (null != con1) {
				if (oper == null)
					oper = "all";
				// ptsStatus
				if (oper.equals("all")) {
					//获取当前用户所对应的项目信息  SESSION中获得 yangqifang修改
					String project = (String) request.getSession().getAttribute("projects");
					if (project != null) {
						String[] projects = project.split(";");
						PointInfoService pointInfoService = new PointInfoService();
						Points = pointInfoService.QueryPointsAll(con1,projects);
					}
					HtmlUtil.writerHtml(response, Points);
				} else if (oper.equals("SensorType")) {
					String SensorType = request.getParameter("SensorType");

					PointInfoService pointInfoService = new PointInfoService();
					JSONObject jsonObject = new JSONObject();
					jsonObject = pointInfoService.QueryPointsSensorType(con1, SensorType);
					Points = jsonObject.toString();
					HtmlUtil.writerHtml(response, Points);

				} else if (oper.equals("ImgUrlBySensorName")) {
					String SensorName = request.getParameter("SensorName");

					PointInfoDao pointInfoBap = new PointInfoDao();
					org.json.JSONArray jsonObject = new org.json.JSONArray();
					jsonObject = pointInfoBap.QueryPointsSensorNameToImgUr(con1, SensorName);
					Points = jsonObject.toString();
					HtmlUtil.writerHtml(response, Points);
				}

				else if (oper.equals("SensorName")) {
					String SensorName = request.getParameter("SensorName");

					PointInfoDao pointInfoBap = new PointInfoDao();
					org.json.JSONArray jsonObject = new org.json.JSONArray();
					jsonObject = pointInfoBap.QueryPointsSensorName(con1, SensorName);
					Points = jsonObject.toString();
					HtmlUtil.writerHtml(response, Points);
				} else if (oper.equals("GroupName")) {
					String SensorName = request.getParameter("groupname");

					PointInfoDao pointInfoBap = new PointInfoDao();
					org.json.JSONArray jsonObject = new org.json.JSONArray();
					jsonObject = pointInfoBap.QueryPointsSensorName(con1, SensorName);
					Points = jsonObject.toString();
					HtmlUtil.writerHtml(response, Points);
				} else if (oper.equals("prjName")) {
					String prjName = request.getParameter("prjName");
					JSONArray jsonObject = new JSONArray();
					PointInfoService pointInfoBap = new PointInfoService();
					jsonObject = pointInfoBap.QueryPointsInfo2List(con1, prjName);
					HtmlUtil.writerJson(response, jsonObject);

				} else if (oper.equals("SensorTypePoints")) {
					String SensorType = request.getParameter("SensorType");
					String PrjName = request.getParameter("PrjName");
					JSONArray jsonObject = new JSONArray();
					PointInfoService pointInfoService = new PointInfoService();
					jsonObject = pointInfoService.QueryPointsInfoBySensorType(con1, SensorType, PrjName);
					HtmlUtil.writerJson(response, jsonObject);
				}

				else if (oper.equals("edit")) {

					String SensorName = request.getParameter("SensorName");
					String alert = request.getParameter("alert");
					String alarm = request.getParameter("alarm");
					String action = request.getParameter("action");
					String north = request.getParameter("north");
					String east = request.getParameter("east");
					String up = request.getParameter("up");
					String location = request.getParameter("location");
					String sensor_all = request.getParameter("sensor_all");
					String notifyInterval = request.getParameter("notifyInterval");
					String SensorType = request.getParameter("SensorType");
					String act = request.getParameter("act");
					//HttpRequest.Get(Connest.UrlAndPort+"/SETAAA", "SensorName="+SensorName+"&Alert="+alert+"&Alarm="+alarm+"&Action="+action);
					//wtyang20171223
					HttpRequest.Get(Connest.UrlAndPort+"/SETAAA", "SensorName="+SensorName+"&Alert="+alert+"&Alarm="+alarm+"&Action="+action+"&notifyInterval="+notifyInterval);
					if (act.equals("SensorType")) {
						PointInfo pointInfo = new PointInfo();

						if (alert != null)
							pointInfo.setalert(Double.valueOf(alert));
						if (alarm != null)
							pointInfo.setalarm(Double.valueOf(alarm));
						if (action != null)
							pointInfo.setaction(Double.valueOf(action));
						if (notifyInterval != null)
							pointInfo.setnotifyinterval(notifyInterval);
						if (SensorType != null)
							pointInfo.setSensorType(SensorType);
						PointInfoDao pointInfoBap = new PointInfoDao();
						String msg = pointInfoBap.editPointBySensorType(con1, pointInfo);
						HtmlUtil.writerHtml(response, msg);
						SystemLogService.addSystemLogData("编辑", "编辑点位类型" + SensorType + "的预警值", request); /// ----------------编辑登录行为到系统日志
					} else if ("SensorName".equals(act)) {
						PointInfo pointInfo = new PointInfo();
						pointInfo.setSensorName(SensorName);
						// pointInfo.setalert(Double.valueOf(alert));
						if (alarm != null)
							pointInfo.setalarm(Double.valueOf(alarm));
						if (action != null)
							pointInfo.setaction(Double.valueOf(action));
						if (north != null)
							pointInfo.setnorth(Double.valueOf(north));
						if (east != null)
							pointInfo.seteast(Double.valueOf(east));
						if (up != null)
							pointInfo.setup(Double.valueOf(up));
						if (location != null)
							pointInfo.setlocation(location);
						if (notifyInterval != null)
							pointInfo.setnotifyinterval(notifyInterval);
						if (SensorType != null)
							pointInfo.setSensorType(SensorType);
						if (alert!=null)
							pointInfo.setalert(Double.valueOf(alert));
						PointInfoDao pointInfoBap = new PointInfoDao();
						String msg = pointInfoBap.editPointBySensorName(con1, pointInfo);
						HtmlUtil.writerHtml(response, msg);
						SystemLogService.addSystemLogData("编辑", "编辑点位" + SensorName + "的预警值", request); /// ----------------编辑登录行为到系统日志
					}

				} else if ("UpdateDescript".equals(oper)) {
					String description = request.getParameter("description");
					String groupname = request.getParameter("groupname");
					String prjName = request.getParameter("prjName");

					String sqlString = "update ptinfo set Demo='" + description + "' where groupname=\"" + groupname
							+ "\"";

					PreparedStatement pStatement = con1.prepareStatement(sqlString);

					int i = pStatement.executeUpdate();
					HtmlUtil.writerHtml(response, i + "");
					SystemLogService.addSystemLogData("编辑", "编辑点位" + groupname + "的描叙内容", request); /// ----------------编辑登录行为到系统日志
				} else if ("saveChartY".equals(oper)) {
					String SensorName = request.getParameter("SensorName");
					String isY = request.getParameter("isY");
					String chartY = request.getParameter("chartY");
					PointInfoDao pointInfoBap = new PointInfoDao();
					int msg = pointInfoBap.editPointChartY(con1, SensorName, isY, chartY);
					HtmlUtil.writerHtml(response, msg + "");

				}

//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != con1) {
					if (null != dbUtil) {
						dbUtil.closeCon(con1);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		HtmlUtil.writerHtml(response, Points);
	}

}
