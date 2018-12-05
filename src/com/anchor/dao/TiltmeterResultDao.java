package com.anchor.dao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import net.sf.json.JSONArray;

import com.anchor.model.TiltmeterResult;
import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;
import com.anchor.util.StringUtil;

public class TiltmeterResultDao {

	public TiltmeterResultDao() {
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
	
	public List<TiltmeterResult> queryTiltmeterData(Connection conn, String prjName, 
			String sensorName) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		String sql = "select sdatetime, x, y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()
					+ "order by sdatetime asc ";
		System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 1;
		List<TiltmeterResult> gnssResultList = new ArrayList<TiltmeterResult>();
		while (m_rset.next()) {
			TiltmeterResult gnssResult = new TiltmeterResult();
			gnssResult.setsDatetime(m_rset.getString(col));
			gnssResult.setX(m_rset.getDouble(++col));
			gnssResult.setY(m_rset.getDouble(++col));
			col =1;
			gnssResultList.add(gnssResult);
		}
		return gnssResultList;
	}

	public  org.json.JSONObject queryTiltmeterResult(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
//		PointInfoDao ptInfoDao = new PointInfoDao();
//		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
//		if (null == ptInfo)return null;
			
//		starttime=starttime+ " 00:00:00";
//		endtime=endtime+" 23:59:59";
//		
//		String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as sdatetime1, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
//					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName();
//	
//		if(ymw==null || ymw.equals("")){
//			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by sdatetime1" ;
//			
//		}else{
//			if(ymw.equals("day")){
//				sql+= "  where to_days(date_format(sdateTime,'%Y-%m-%d')) = to_days(now())";
//			}
//			else if(ymw.equals("week")){
//				sql+= "  where YEARWEEK(date_format(sdateTime,'%Y-%m-%d')) = YEARWEEK(now())";
//			}else if(ymw.equals("month")){
//				sql+= "  where date_format(sdateTime,'%Y%m') = date_format( CURDATE( ) , '%Y%m')";
//			}else if(ymw.equals("year")){
//				sql+= "  where YEAR(sdateTime) = YEAR(now())";
//			}
//			
//		}
//		sql+="  order by sdatetime asc";
//		//System.out.print(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
		String url = Connest.URL_TILTMETER_QueryTiltmeterResult;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		starttime=starttime.replace("-", "");
		endtime=endtime.replace("-", "");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = StringUtil.checkNotNull(ymw)?URLEncoder.encode(ymw,"utf-8"):null;
		url = String.format(url,name,sensor,sTime,eTime,y);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		JSONObject obj=new JSONObject(res);
		String resCode=obj.getString("code");
		if(!resCode.equals("00")){
			return obj;
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		List<TiltmeterResult> tiltmeterResultList = new ArrayList<TiltmeterResult>();
		for(int i = 0;i<arrObjs.length();i++){
			JSONObject objItem=arrObjs.getJSONObject(i);
			TiltmeterResult tiltmeterResult = new TiltmeterResult();
			tiltmeterResult.setSt(objItem.getString("sDatetime"));
			double pointX=objItem.getDouble("dx")*(180/Math.PI);
			double pointY=objItem.getDouble("dy")*(180/Math.PI);
			tiltmeterResult.setX(((double)((int)(pointX*1000))/100));//计算角度数
			tiltmeterResult.setY(((double)((int)(pointY*1000))/100));
			tiltmeterResultList.add(tiltmeterResult);
		}
		obj.remove("data");
		obj.append("data", new org.json.JSONArray(tiltmeterResultList));
//		int col = 1;
//		
//		while (m_rset.next()) {
//			TiltmeterResult gnssResult = new TiltmeterResult();
//			gnssResult.setsDatetime(m_rset.getString(col));
//			gnssResult.setX(m_rset.getDouble(++col));
//			gnssResult.setY(m_rset.getDouble(++col));
//			col =1;
//			tiltmeterResultList.add(gnssResult);
//		}
		return obj;
		
	}
	/**
	 * 查询项目信息--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public  String queryPrismResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
			
		starttime=starttime+ " 00:00:00";
		endtime=endtime+" 23:59:59";
		
		String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as sdatetime1, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName();
	
		if(ymw==null || ymw.equals("")){
			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by sdatetime1" ;
			
		}else{
			if(ymw.equals("day")){
				sql+= "  where to_days(date_format(sdateTime,'%Y-%m-%d')) = to_days(now())";
			}
			else if(ymw.equals("week")){
				sql+= "  where YEARWEEK(date_format(sdateTime,'%Y-%m-%d')) = YEARWEEK(now())";
			}else if(ymw.equals("month")){
				sql+= "  where date_format(sdateTime,'%Y%m') = date_format( CURDATE( ) , '%Y%m')";
			}else if(ymw.equals("year")){
				sql+= "  where YEAR(sdateTime) = YEAR(now())";
			}
			
		}
		sql+="  order by sdatetime asc";
		System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
	
		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
		//System.out.print(jsArray.toString());
		return jsArray.toString();
		/*
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
		if (starttime == null)
			starttime = "2000-01-01";
		if (endtime == null)
			endtime = "2050-01-01";
		starttime=starttime+ " 00:00:00";
		endtime=endtime+" 23:59:59";
		String sql="";
		 
	
		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
		
			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as st, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by st";
			
		}else if(ymw.equals("day")){
			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as st, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by st";
				
		}
		else if(ymw.equals("month")){
			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as st, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by st";
		}else if(ymw.equals("year")){
			sql="select DATE_FORMAT((sDateTime),'%Y') as st, min(x) - " + ptInfo.getref1() + " as x, min(y) - " + ptInfo.getref2() 
					+ " as y from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by st";
		}
		
		//System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
	
		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
		//System.out.print(jsArray.toString());
		return jsArray.toString();
		*/
	}
}
