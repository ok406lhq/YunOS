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
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.service.PointInfoService;
import com.anchor.service.ProjectInfoService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.StringUtil;
//import com.sun.org.apache.xpath.internal.functions.Function;

/**
 * Servlet implementation class ProjectInfoServlet
 */
public class PointInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PointInfoServlet() {
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
	 * 功能：查询指定项目名,指定的ptName测点基础信息，以json形式返回 -wtyang2017/07/06 如果测点名为空，则返回所有测点的详细信息。
	 */
	// sample
	// [{"ref3":0.001,"Status":"Online","staName":"USS1","prjName":"CEDD","ref2":5.076,"StatusDes":"2017-03-21
	// 09:58:22,USS1 distance(5.126 m).
	// ","sensorType":"USS","ref1":0,"SensorName":"USS1","Latitude":811539.359,"Longitude":806009.89},
	// {"ref3":328.907,"Status":"Online","staName":"KIPI4","prjName":"CEDD","ref2":-2.682,"StatusDes":"2017-04-02
	// 23:19:01 KS-IPI4-2-4M X(-35.246);Y(15.8)(mm)
	// ","sensorType":"Inclinometer","ref1":-1.081,"SensorName":"KS-IPI4-2-4M","Latitude":0,"Longitude":0},
	// {"ref3":328.907,"Status":"Online","staName":"KIPI4","prjName":"CEDD","ref2":-1.373,"StatusDes":"2017-04-02
	// 23:19:01 KS-IPI4-3-6M X(-28.616);Y(21.1)(mm)
	// ","sensorType":"Inclinometer","ref1":-0.951,"SensorName":"KS-IPI4-3-6M","Latitude":0,"Longitude":0},{"ref3":328.907,"Status":"Online","staName":"KIPI4","prjName":"CEDD","ref2":-1.75,"StatusDes":"2017-04-02
	// 23:19:01 KS-IPI4-4-9M X(-28.895);Y(24.5)(mm)
	// ","sensorType":"Inclinometer","ref1":-1.042,"SensorName":"KS-IPI4-4-9M","Latitude":0,"Longitude":0}]

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
				String ptName = request.getParameter("ptName"); // 返回是否是查询点位数据界面
			String op = request.getParameter("op");
			String prjNameString = request.getParameter("prjName");

			String json = "";
			if (ptName == null) {

				if (op.equals("byprjName")) {

					json = SummaryPtInfo(prjNameString, ptName);
				} else if (op.equals("prjNameAndPrjInfo")) {

					json = SummaryPtInfos(prjNameString);
				} else {
					json = SummaryPtInfos(prjNameString);
				}

			} else {// 查询指定点

				json = SummaryPtInfo(prjNameString, ptName);
			}
			HtmlUtil.writerHtml(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}

	// 汇总一个项目中的基本信息以及各点位的状态
	protected String SummaryPtInfo(String prjName, String ptName) {
		// TODO Auto-generated method stub
		Connection con1 = null;
		DbUtil dbUtil = null;
		String json = "";
		JSONArray summaryArray = null;
		try {
			dbUtil = new DbUtil();
			dbUtil.SetDatabase(Connest.DbName);
			summaryArray = new JSONArray();
			con1 = dbUtil.getCon();
//			if (null != con1) {// prjInfo
				PointInfoService ptInfoService = new PointInfoService();
				JSONObject prjInfoItem = new JSONObject();
				JSONArray prjInfoArray = ptInfoService.QueryPointsInfo2ListSimple(con1, prjName);
				prjInfoItem.put("ptInfo", prjInfoArray);
				summaryArray.add(prjInfoItem);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (null != con1) {
					if (null != dbUtil) {
						dbUtil.closeCon(con1);
					}
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		json = summaryArray.toString();
		System.out.print(json);
		return json;
	}

	// 汇总一个项目中的基本信息以及各点位的状态
	protected String SummaryPtInfos(String prjName) {
		// TODO Auto-generated method stub
		Connection con1 = null;
		DbUtil dbUtil = null;
		String json = "";
		JSONArray summaryArray = null;
		try {
			dbUtil = new DbUtil();
			summaryArray = new JSONArray();
			con1 = dbUtil.getCon();
//			if (null != con1) {
				// prjInfo
				ProjectInfoService prjInfoService = new ProjectInfoService();
				JSONObject prjInfoItem = new JSONObject();
				JSONArray prjInfoArray = prjInfoService.QueryProjectInfo(con1, prjName);
				prjInfoItem.put("PrjInfo", prjInfoArray);
				summaryArray.add(prjInfoItem);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (null != con1) {
					if (null != dbUtil) {
						dbUtil.closeCon(con1);
					}
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		////////////////
		try {
			dbUtil.SetDatabase(Connest.DbName);
			con1 = dbUtil.getCon();
//			if (null != con1) {
				// ptsStatus
				JSONObject ptStatusItem = new JSONObject();
				PointInfoService pointInfoService = new PointInfoService();
				JSONArray prjStatusArray = pointInfoService.QueryPointsInfo2List(con1, prjName);
				ptStatusItem.put("ptStatus", prjStatusArray);
				summaryArray.add(ptStatusItem);
				// ptStatuSummary
				JSONObject ptStatusSummy = new JSONObject();
				int normal = 0;
				int offline = 0;
				int alarm = 0;
				int action = 0;// 二级预警
				int alert = 0;// 三级预警
			    int fourLevelWarn = 0; //四级预警
				
				for (int i = 0; i < prjStatusArray.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					JSONObject jObj = (JSONObject) prjStatusArray.get(i);
					String obStatus = "";
					if (jObj.get("Status") != null) {
						obStatus = jObj.get("Status").toString();
						obStatus = obStatus.toLowerCase();
					}
					System.err.println("obStatus:" + obStatus);
					if (obStatus.equals("离线"))
						offline++;
					else if (obStatus.equals("一级预警"))
						alarm++;
					else if (obStatus.equals("二级预警"))
						action++;
					else if (obStatus.equals("三级预警"))
						alert++;
					else if (obStatus.equals("四级预警"))
						fourLevelWarn++;
					else {
						normal++;
					}
				}
				JSONArray ptStatus = new JSONArray();
				JSONObject normalItem = new JSONObject();
				normalItem.put("normal", normal);
				ptStatus.add(normalItem);
				JSONObject offlineItem = new JSONObject();
				offlineItem.put("offline", offline);
				ptStatus.add(offlineItem);
				JSONObject alarmItem = new JSONObject();
				alarmItem.put("alarm", alarm);
				ptStatus.add(alarmItem);
				JSONObject actionItem = new JSONObject();
				actionItem.put("action", action);
				ptStatus.add(actionItem);
				// 三级预警
				JSONObject alertItem = new JSONObject();
				alertItem.put("alert", alert);
				ptStatus.add(alertItem);
				// 四级预警
				JSONObject fourLevelWarnItem = new JSONObject();
			    fourLevelWarnItem.put("fourLevelWarn", fourLevelWarn);
				ptStatus.add(fourLevelWarnItem);
				//
				ptStatusSummy.put("ptSummary", ptStatus);
				summaryArray.add(ptStatusSummy);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (null != con1) {
					if (null != dbUtil) {
						dbUtil.closeCon(con1);
					}
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		json = summaryArray.toString();
		System.err.println(json);
		return json;
	}

	// 汇总查询项目中的点位信息（少数据量的简单查询）
	protected String SummaryPtInfoSensors(String prjName, String ptName) {
		Connection con1 = null;
		DbUtil dbUtil = null;
		String json = "";
		JSONArray summaryArray = null;
		try {
			dbUtil.SetDatabase(Connest.DbName);
			con1 = dbUtil.getCon();
//			if (null != con1) {
				// ptsStatus
				JSONObject prjStatusItem = new JSONObject();
				PointInfoService pointInfoService = new PointInfoService();
				JSONArray prjStatusArray = pointInfoService.QueryPointsInfo2ListSimple(con1, prjName);
				prjStatusItem.put("ptSensors", prjStatusArray);
				summaryArray.add(prjStatusItem);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (null != con1) {
					if (null != dbUtil) {
						dbUtil.closeCon(con1);
					}
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		json = summaryArray.toString();
		return json;
	}
}
