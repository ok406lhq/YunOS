package com.anchor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;

public class RainResultDao {

	public RainResultDao() {
		super();
	}
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	
	
	/**
	 * 通过项目ID查找所属站点
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> stationAll(String param[]){
		String sql = "SELECT nSensorID,sSiteName FROM base_sensor_info t1 WHERE t1.nProjectID = ? and sSensorType= ?";
		List<Map<String, Object>> list = dbUtils.find(sql,param);
		return list;
	}
	
	
	/**
	 * 查询数据--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	public List<GNSSResult> queryGNSSData(Connection conn, String prjName, 
			String sensorName) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		String sql = "select sdatetime, dnorth, deast, dup from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()
					+ "order by sdatetime asc ";
		System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 1;
		List<GNSSResult> gnssResultList = new ArrayList<GNSSResult>();
		while (m_rset.next()) {
			GNSSResult gnssResult = new GNSSResult();
			gnssResult.setsDatetime(m_rset.getString(col));
			gnssResult.setdNorth(m_rset.getDouble(++col));
			gnssResult.setdEast(m_rset.getDouble(++col));
			gnssResult.setdUP(m_rset.getDouble(++col));
			gnssResult.setsigma(m_rset.getDouble(++col));
			col =1;
			gnssResultList.add(gnssResult);
		}
		return gnssResultList;
	}

	/**
	 * 张庭伟
	 */
	DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	/**
	 * 数据初始化
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryRainResultJson(
			String sensorName,String starttime,String endtime,String ymw) {
		String sql = "SELECT create_time AS 'dtCreateDT',rain AS 'dRealRain',sensor_id AS 'nSensorID',temperature AS 'temperature' FROM data_raingauge WHERE sensor_id = '"+sensorName+"' AND create_time > '"+starttime+"' AND create_time < '"+endtime+"' ORDER BY create_time ASC"; 
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
//		switch (ymw) {
//		case "hour":
//			dateFormat = "%Y-%m-%d %H";
//			break;
//		case "day":
//			dateFormat = "%Y-%m-%d";
//			break;
//		case "week":
//			dateFormat = "%Y-%m-%d %H";
//			break;
//		case "month":
//			dateFormat = "%Y-%m";
//			break;
//		case "year":
//			dateFormat = "%Y";
//			break;
//		default:
//			dateFormat = "%Y-%m-%d %H";
//			break;
//		}
//		String sql = "SELECT DATE_FORMAT(create_time,'"+dateFormat+"') AS 'dtCreateDT',sum(rain) AS 'dRealRain',sensor_id AS 'nSensorID',SUM(temperature) AS 'temperature'\r\n" + 
//				" FROM data_raingauge\r\n" + 
//				" WHERE sensor_id = "+sensorName+" AND create_time BETWEEN '"+starttime+"' AND '"+endtime+"'\r\n" + 
//				" group by DATE_FORMAT(create_time,'"+dateFormat+"')\r\n" + 
//				" ORDER BY create_time ASC";
//		
		
//		JSONObject object = null;
//		JSONArray array = new JSONArray();
//		for (Map<String, Object> map : list) {
//			object = new JSONObject();
//		    object.put("dtCreateDT", map.get("dtCreateDT"));
//			object.put("dRealRain", map.get("dRealRain"));
//			object.put("nSensorID", map.get("nSensorID"));
//			object.put("temperature", map.get("temperature"));
//			array.add(object);
//		}
		
	}
	
	
//	/**
//	 * 查询项目信息--用list
//	 * @param conn
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	public  String queryRainResultJson2(Connection conn, String prjName, 
//			String sensorName,String starttime,String endtime,String ymw) throws Exception{
//		PointInfoDao ptInfoDao = new PointInfoDao();
//		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
//		
//		if (null == ptInfo)return "";
//			
//		starttime=starttime.replaceAll("-", "");
//		endtime=endtime.replaceAll("-", "");
//		String url = Connest.URL_RAIN_QueryRainResultSum;
////		String name = URLEncoder.encode(prjName,"utf-8");
//		String sensor = URLEncoder.encode(sensorName,"utf-8");
//		String sTime = URLEncoder.encode(starttime,"utf-8");
//		String eTime = URLEncoder.encode(endtime,"utf-8");
//		String y = null!=ymw?URLEncoder.encode(ymw,"utf-8"):null;
//		url = String.format(url,sensor,sTime,eTime,y);
//		System.out.println("url:"+url);
//		String res=HttpClientUtil.doGet(url,"utf-8");
//		res=URLDecoder.decode(res, "utf-8");
//		org.json.JSONObject obj=new org.json.JSONObject(res);
//		String resCode=obj.getString("code");
//		if(!"00".equals(resCode)){
//			throw new Exception(obj.getString("msg"));
//		}
//		org.json.JSONArray arrObjs=obj.getJSONArray("data");
//		starttime=starttime+ " 00:00:00";
//		endtime=endtime+" 23:59:59";
//		String sql="";
//		 
//	
//		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
//		
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as date, max(hyetal) as hyetal,round(avg(temperature),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//			
//		}else if(ymw.equals("day")){
//			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as date, max(hyetal) as hyetal,round(avg(temperature),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//				
//		}
//		else if(ymw.equals("month")){
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as date, max(hyetal) as hyetal,round(avg(temperature),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//		}else if(ymw.equals("year")){
//			sql="select DATE_FORMAT((sDateTime),'%Y') as date, max(hyetal) as hyetal,round(avg(temperature),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//		}
//		
//		System.out.print(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//	
//		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		//System.out.print(jsArray.toString());
//		return arrObjs.toString();
//	}
}
