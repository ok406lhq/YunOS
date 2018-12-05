package com.anchor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.model.UssResult;
import com.anchor.util.JsonUtil;

public class UssResultDao {

	public UssResultDao() {
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
	
	public List<UssResult> queryUSSData(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		ResultSet resSet = queryUssResultSet(conn, prjName, sensorName, starttime, endtime, ymw);
		if (null == resSet)
			return  null;
		int col = 1;
		List<UssResult> ussResultList = new ArrayList<UssResult>();
		while (m_rset.next()) {
			UssResult ussResult = new UssResult();
			ussResult.setsDatetime(m_rset.getString(col));
			ussResult.setdistance(m_rset.getDouble(++col));
			//ussResult.settemperature(m_rset.getDouble(++col));
			col =1;
			ussResultList.add(ussResult);
		}
		return ussResultList;
	}

	/**
	 * 查询项目信息--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public  String queryUssResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		ResultSet resSet = queryUssResultSet(conn, prjName, sensorName, starttime, endtime, ymw);
		if (null == resSet)
			return  "";
		JSONArray jsArray = JsonUtil.formatRsToJsonArray(resSet);
		return jsArray.toString();
	}
	
	public  ResultSet queryUssResultSet(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		if (null == ptInfo)return null;
			
		starttime=starttime+ " 00:00:00";
		endtime=endtime+" 23:59:59";
		String sql="";
		if(ymw==null || ymw.equals("hour") || ymw.equals("")){
			
			sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as date, avg(distance) as distance  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
			
		}else if(ymw.equals("day")){
			 sql="select DATE_FORMAT((sDateTime),'%Y-%m-%d') as date, avg(distance) as distance from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
				
		}
		else if(ymw.equals("month")){
			sql="select DATE_FORMAT((sDateTime),'%Y-%m') as date, avg(distance) as distance  from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
		}else if(ymw.equals("year")){
			sql="select DATE_FORMAT((sDateTime),'%Y') as date, avg(distance) as distance from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()+
			 " where  sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 4 group by date";
		}
		System.out.print(sql);
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
	
		return m_rset;
	}
}
