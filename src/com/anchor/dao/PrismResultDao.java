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

public class PrismResultDao {

	public PrismResultDao() {
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
	public  String queryPrismResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		
		if (null == ptInfo)return "";
			
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String url = Connest.URL_PRISM_QueryPrismResultJson;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = URLEncoder.encode(ymw,"utf-8");
		url = String.format(url,name,sensor,sTime,eTime,y);
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
//		
//		String sql = "select sdatetime, round((northing - " + ptInfo.getref1() + "),2) as north, round((easting - " + ptInfo.getref2() 
//					+ "),2) as east, round((rl - " + ptInfo.getref3() + "),2) as rl from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName();
//	
//		if(ymw==null || ymw.equals("")){
//			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\"";
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
//		System.out.print(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//	
//		JSONArray jsArray = JsonUtil.xlhJson(m_rset);
//		//System.out.print(jsArray.toString());
		return arrObjs.toString();
	}
}
