package com.anchor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;

public class AnalysisDao {
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	
	  public List<GNSSResult> AnalysisResultDao(Connection conn, String prjName, String sensorName,
				String starttime,String endtime) throws Exception{
		
		  PointInfoDao ptInfoDao = new PointInfoDao();
			PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
			String sql = "select sdatetime, dnorth - " + ptInfo.getref1() + " as north, deast - " + ptInfo.getref2() 
					+ " as east, dup - " + ptInfo.getref3() + " as up from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName();
			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\"";
			sql+="  order by sdatetime asc";
			
			
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
			
				col =1;
				gnssResultList.add(gnssResult);
			}
			return gnssResultList;
	
		  
	  }
	  
	  
	  public List<GNSSResult> AnalysisFilterResultDao(Connection conn, String prjName, String sensorName,
				String starttime,String endtime) throws Exception{
		  
		  PointInfoDao ptInfoDao = new PointInfoDao();
		  
			PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
			
			String tableNameString=ptInfo.getStaName() + "_" + ptInfo.getSensorName()+"filter1";
			
			tableNameString="rove_rovefilter1";// 测试固定表名。
			
			String sql = "select sdatetime, dnorth - " + ptInfo.getref1() + " as north, deast - " + ptInfo.getref2() 
					+ " as east, dup - " + ptInfo.getref3() + " as up from " + tableNameString;
			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\"";
			sql+="  order by sdatetime asc";
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
			
				col =1;
				gnssResultList.add(gnssResult);
			}
			return gnssResultList;
	
		  
	  }

}
