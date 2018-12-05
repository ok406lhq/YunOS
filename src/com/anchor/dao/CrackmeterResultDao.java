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

public class CrackmeterResultDao {

	public CrackmeterResultDao() {
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
	public  String queryCMResult(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
		
		if (starttime != null)
			starttime=starttime+ " 00:00:00";
		else
			starttime = "1980-02-02 11:11:11";
		if (endtime != null)
			endtime=endtime+" 23:59:59";
		else
			endtime = "2020-02-02 11:11:11";
			
		String sql="";
		 
	
		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
		
			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as date1, min(offset) as displacement,FORMAT(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
			
		}else if(ymw.equals("day")){
			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as date1, min(offset) as displacement,FORMAT(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
				
		}
		else if(ymw.equals("month")){
			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as date1, min(offset) as displacement,FORMAT(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
		}else if(ymw.equals("year")){
			sql="select DATE_FORMAT((sDateTime),'%Y') as date1, min(offset) as displacement,FORMAT(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
		}
		
		//System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
	
		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
		//System.out.print(jsArray.toString());
		return jsArray.toString();
	}
	
	public List<GNSSResult> queryCMData(Connection conn, String prjName, 
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
	public  String querycrackmeterResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
		
		if (starttime != null)
			starttime=starttime.replaceAll("-", "");
		else
			starttime = "19800202";
		if (endtime != null)
			endtime=endtime.replaceAll("-", "");
		else
			endtime = "20200202";
		String url = Connest.URL_CRACKMETER_QuerycrackmeterResultJson;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = URLEncoder.encode(ymw,"utf-8");
		url = String.format(url,"2","2","20180101","20180323",y);
		System.out.println("url:"+url);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
//		String sql="";
//		 
//	
//		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
//		
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as date1, round(min(offset),4) as displacement,round(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
//			
//		}else if(ymw.equals("day")){
//			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as date1, round(min(offset),4) as displacement,round(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
//				
//		}
//		else if(ymw.equals("month")){
//			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as date1, round(min(offset),4) as displacement,round(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
//		}else if(ymw.equals("year")){
//			sql="select DATE_FORMAT((sDateTime),'%Y') as date1, round(min(offset),4) as displacement,round(avg(temperture),1) as temperature  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
//			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date1";
//		}
//		
//		//System.out.print(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//	
//		JSONArray jsArray = JsonUtil.xlhJson(m_rset);
//		//System.out.print(jsArray.toString());
//		System.err.println("jsArray.toString():"+jsArray.toString());
		return arrObjs.toString();
	}
}
