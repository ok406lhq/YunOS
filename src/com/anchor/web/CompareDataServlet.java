package com.anchor.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dal.GnssStaticDao;
import com.anchor.dal.ProjectDao;
import com.anchor.dal.RainStaticDao;
import com.anchor.dal.SensorDao;
import com.anchor.util.HtmlUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class CompareDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CompareDataServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	private final String GET_PROJECT="getProject";
	private final String GET_SENSOR="getSensor";
	private final String GET_SENSORT_YPE="getSensorType";
	private final String GET_GNSS_COMPARE_DATA="getGnssCompareData";
	private final String GET_RAIN_COMPARE_DATA="getRainCompareData";
	private ProjectDao projectDao = new ProjectDao();
	private SensorDao sensorDao = new SensorDao();
	private GnssStaticDao gnssStaticDao = new GnssStaticDao();
	private RainStaticDao rainStaticDao = new RainStaticDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter("requestType");
		switch (requestType){
			case GET_PROJECT:
				getProject(request, response);
				break;
			case GET_SENSORT_YPE:
				getSensorType(request, response);
				break;
			case GET_SENSOR:
				getSensor(request, response);
				break;
			case GET_GNSS_COMPARE_DATA:
				getGnssCompareData(request, response);
//				SystemLogService.addSystemLogData("查询",sensorName+"数据对比,时间范围"+start_time+"至"+end_time,request); ///----------------保存查询行为到系统日志
				break;
			case GET_RAIN_COMPARE_DATA:
				getRainCompareData(request, response);
//				SystemLogService.addSystemLogData("查询",sensorName+"数据对比,时间范围"+start_time+"至"+end_time,request); ///----------------保存查询行为到系统日志
				break;
		}
	}

	private void getProject(HttpServletRequest request, HttpServletResponse response){
		String projectsIds = (String) request.getSession().getAttribute("projects");
		List<Map<String, Object>> projectList = projectDao.findProjectByIds(projectsIds);
		HtmlUtil.writerJson(response, projectList);
	}
	private void getSensorType(HttpServletRequest request, HttpServletResponse response){
		String projectId = request.getParameter("projectId");
		List<Map<String, Object>> allSensor = sensorDao.findSensorByProjectId(projectId);
		Set<String> listType = new HashSet<>();
		if (allSensor != null && allSensor.size() > 0)
		{
			for (Map<String, Object> map : allSensor) {
				if (map.get("sSensorType") != null)
					listType.add(map.get("sSensorType")+"");
			}
		}
		HtmlUtil.writerJson(response, listType);
	}
	private void getSensor(HttpServletRequest request, HttpServletResponse response){
		String projectId = request.getParameter("projectId");
		String sensorType = request.getParameter("sensorType");
		List<Map<String, Object>> sensorList = sensorDao.findSensorByProjectIdAndType(projectId, sensorType);
		HtmlUtil.writerJson(response, sensorList);
	}

	private void getRainCompareData(HttpServletRequest request, HttpServletResponse response){
		String sensorId = request.getParameter("sensorId");
		String startTime = request.getParameter("startTime")+" 00:00:00";
		String endTime = request.getParameter("endTime")+" 23:59:59";
		//		String sensorId = "89";
		//		String startTime = "2018-06-20 00:00:00";
		//		String endTime = "2018-06-27 23:59:59";
		List<Map<String, Object>> jsonRainResult = new ArrayList<>();
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			List<Map<String, Object>> sensorResult = rainStaticDao.findSensorRainStaticData(sensorId, startTime, endTime);
			for (Map<String, Object> result : sensorResult) {
				Map<String, Object> raingaugeResult= new HashMap<>();
				raingaugeResult.put("nSensorID", result.get("nSensorID"));
				raingaugeResult.put("dRealRain", result.get("dRealRain"));
				raingaugeResult.put("dtCreateDT", result.get("dtCreateDT"));
				jsonRainResult.add(raingaugeResult);
			}
		}
		HtmlUtil.writerJson(response, jsonRainResult);
	}
	
	private void getGnssCompareData(HttpServletRequest request, HttpServletResponse response){
		String sensorId = request.getParameter("sensorId");
		String startTime = request.getParameter("startTime")+" 00:00:00";
		String endTime = request.getParameter("endTime")+" 23:59:59";
//		String sensorId = "89";
//		String startTime = "2018-06-20 00:00:00";
//		String endTime = "2018-06-27 23:59:59";
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			List<Map<String, Object>> sensorExt = sensorDao.findSensorAndExtById(sensorId);
			if (sensorExt != null && sensorExt.size() > 0){
				List<Map<String, Object>> gnssStaticList = gnssStaticDao.findSensorGnssStaticData(sensorExt.get(0).get("nSiteID")+"", startTime, endTime);
				if (CollectionUtils.isNotEmpty(gnssStaticList)){
					List<Map<String, Object>> listGnssResult = new ArrayList<>();
					for (int i = 0; i < gnssStaticList.size(); i++) {
						Map<String, Object> gnssResult = new HashMap<>();
						try {
							Map<String, Object> senserExtMap = getSensorExt(sensorExt, gnssStaticList.get(i).get("DateTime")+"");

							BigDecimal x = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("east")+"") - Double.parseDouble(senserExtMap.get("dRef1")+""));
							BigDecimal y = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("north")+"") - Double.parseDouble(senserExtMap.get("dRef2")+""));
							BigDecimal z = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("up")+"") - Double.parseDouble(senserExtMap.get("dRef3")+""));
							Double s = Math.sqrt(Math.pow(x.doubleValue(), 2) + Math.pow(y.doubleValue(), 2));

							gnssResult.put("dNorth", x.setScale(2, BigDecimal.ROUND_DOWN));
							gnssResult.put("dEast", y.setScale(2, BigDecimal.ROUND_DOWN));
							gnssResult.put("dUp", z.setScale(2, BigDecimal.ROUND_DOWN));
							gnssResult.put("sigma", s);
							gnssResult.put("sDatetime", gnssStaticList.get(i).get("DateTime"));
							listGnssResult.add(gnssResult);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
					HtmlUtil.writerJson(response, listGnssResult);
				}
			}
		}
	}

	public Map<String, Object> getSensorExt(List<Map<String, java.lang.Object>> sensorExt, String staticDate) throws ParseException {

		Map<String, Object> senserExtMap = sensorExt.get(0);
		for (int i = 0; i < sensorExt.size(); i++)
		{
			if(
					DateUtils.parseDate(staticDate, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"}).getTime() <
							DateUtils.parseDate(senserExtMap.get("dtCreatDateTime")+"", new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"}).getTime()
					)
			{
				if (i < sensorExt.size()-1){
					senserExtMap = sensorExt.get(i+1);
				}
			}
		}
		return senserExtMap;
	}

}
