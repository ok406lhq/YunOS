package com.anchor.dao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;

public class LoadcellResultDao {

	public LoadcellResultDao() {
		super();
	}
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	
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
	 * 查询项目信息--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public  String queryLoadcellResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String url = Connest.URL_LOADCELL_QueryLoadcellResultJson;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = URLEncoder.encode(ymw,"utf-8");
		url = String.format(url, name,sensor,sTime,eTime,y);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
//		starttime=starttime+ " 00:00:00";
//		endtime=endtime+" 23:59:59";
//		String sql="";
//		 
//	
//		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
//		
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as date, min(pressure) as pressure,round(avg(temperature),2) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//			
//		}else if(ymw.equals("day")){
//			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as date, min(pressure) as pressure,round(avg(temperature),2) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//				
//		}
//		else if(ymw.equals("month")){
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as date, min(pressure) as pressure,round(avg(temperature),2) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//		}else if(ymw.equals("year")){
//			sql="select DATE_FORMAT((sDateTime),'%Y') as date, min(pressure) as pressure,round(avg(temperature),2) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
//		}
//		
//		//System.out.print(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//	
//		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		//System.out.print(jsArray.toString());
		return arrObjs.toString();
	}
}
