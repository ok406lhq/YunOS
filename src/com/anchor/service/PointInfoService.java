package com.anchor.service;

import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.PointInfoDao;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;

public class PointInfoService {

	 
	
	private static final String ptInfos = null;
	public String queryPtAbnormal(Connection con, String prjName)
	{
		String json="";
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			PointInfoDao pointInfoDao = new PointInfoDao();    
			//json = m_projectInfoDao.queryPtInfoJson(con);			
			List<PointInfo> ptInfos = pointInfoDao.queryPtAbnormal(con, prjName);
			if (null != ptInfos)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < ptInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					PointInfo prjInfoItem = ptInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put("SensorName", prjInfoItem.getSensorName());
					mapOfColValues.put("staName", prjInfoItem.getStaName());
					mapOfColValues.put("prjName", prjInfoItem.getPrjName());
					mapOfColValues.put("sensorType", prjInfoItem.getSensorType());
					mapOfColValues.put("Latitude", prjInfoItem.getLatitude());
					mapOfColValues.put("Longitude", prjInfoItem.getLongitude());
					mapOfColValues.put("Status", prjInfoItem.getStatusVal());
					array.add(mapOfColValues);
				}
				json = array.toString();
				System.out.print(json);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return json;
	}
	//
	//查询项目信息
	public String QueryPointsInfo(Connection con, String prjName)
	{
		String json="";
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			JSONArray array = QueryPointsInfo2List(con, prjName);
			if (null != array)
			{
				json = array.toString();
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return json;
	}
	//
	//查询项目信息
	public JSONArray QueryPointsInfo2List(Connection con, String prjName)
	{
		JSONArray array = null;
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			//json = m_projectInfoDao.queryPtInfoJson(con);
			PointInfoDao pointInfoDao = new PointInfoDao();    
			List<PointInfo> ptInfos = pointInfoDao.queryPtInfoList(con, prjName);
		
			if (null != ptInfos)
			{
				array=new JSONArray();
				for( int i = 0 ; i < ptInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					PointInfo prjInfoItem = ptInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put("SensorName", prjInfoItem.getSensorName());
					mapOfColValues.put("staName", prjInfoItem.getStaName());
					mapOfColValues.put("prjName", prjInfoItem.getPrjName());
					mapOfColValues.put("sensorType", prjInfoItem.getSensorType());
					mapOfColValues.put("Latitude", prjInfoItem.getLatitude());
					mapOfColValues.put("Longitude", prjInfoItem.getLongitude());
					mapOfColValues.put("Status", prjInfoItem.getStatusVal());
					mapOfColValues.put("StatusDes", prjInfoItem.getCurStatus());
					mapOfColValues.put("location", prjInfoItem.getlocation());
					mapOfColValues.put("voltage", prjInfoItem.getvoltage());
					mapOfColValues.put("imgURL", prjInfoItem.getimgURL());
					mapOfColValues.put("imgX", prjInfoItem.getimgX());
					mapOfColValues.put("imgY", prjInfoItem.getimgY());
					mapOfColValues.put("ref1", prjInfoItem.getref1());
					mapOfColValues.put("ref2", prjInfoItem.getref2());
					mapOfColValues.put("ref3", prjInfoItem.getref3());
					mapOfColValues.put("groupname", prjInfoItem.getGroupName());
					mapOfColValues.put("SensorID", prjInfoItem.getSensorID());
					array.add(mapOfColValues);
				}
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return array;
	}
	
	//查询项目信息
	public JSONArray QueryPointsInfo2ListSimple(Connection con, String prjName)
	{
		JSONArray array = null;
		
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			//json = m_projectInfoDao.queryPtInfoJson(con);
			PointInfoDao pointInfoDao = new PointInfoDao();    
			List<PointInfo> ptInfos = pointInfoDao.queryPtInfoList(con, prjName);
			if (null != ptInfos)
			{
				array=new JSONArray();
				for( int i = 0 ; i < ptInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					PointInfo prjInfoItem = ptInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put("SensorName", prjInfoItem.getSensorName());
					mapOfColValues.put("staName", prjInfoItem.getStaName());
					mapOfColValues.put("prjName", prjInfoItem.getPrjName());
					mapOfColValues.put("sensorType", prjInfoItem.getSensorType());
					mapOfColValues.put("Latitude", prjInfoItem.getLatitude());
					mapOfColValues.put("Longitude", prjInfoItem.getLongitude());
					mapOfColValues.put("Status", prjInfoItem.getStatusVal());
					mapOfColValues.put("StatusDes", prjInfoItem.getCurStatus());
					mapOfColValues.put("location", prjInfoItem.getlocation());
					mapOfColValues.put("imgURL", prjInfoItem.getimgURL());
					mapOfColValues.put("imgX", prjInfoItem.getimgX());
					mapOfColValues.put("imgY", prjInfoItem.getimgY());
					mapOfColValues.put("ref1", prjInfoItem.getref1());
					mapOfColValues.put("ref2", prjInfoItem.getref2());
					mapOfColValues.put("ref3", prjInfoItem.getref3());
					mapOfColValues.put("SensorID", prjInfoItem.getSensorID());
					array.add(mapOfColValues);
				}
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return array;
	}
	public String QueryPointsAll(Connection con,String[] prj) {
		// TODO Auto-generated method stub
		String ptInfos = null;
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			//json = m_projectInfoDao.queryPtInfoJson(con);
			PointInfoDao pointInfoDao = new PointInfoDao();    
			 ptInfos = pointInfoDao.queryPtInfoJson(con,prj);
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return ptInfos;
	}
	public JSONObject QueryPointsSensorType(Connection con1, String sensorType) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=new JSONObject();
		try {
			PointInfoDao pointInfoDao = new PointInfoDao();   
			PointInfo pointInfo=new PointInfo();
			
			pointInfo=pointInfoDao.QueryPointBySensorType(con1,sensorType);
			jsonObject.put("alert", pointInfo.getalert());
			jsonObject.put("alarm", pointInfo.getalarm());
			jsonObject.put("action", pointInfo.getaction());
			jsonObject.put("notifyinterval", pointInfo.getnotifyinterval());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	
	public JSONArray QueryPointsInfoBySensorType(Connection con, String SensorType, String prjName)
	{
		JSONArray array = null;
		//m_dbUtil.SetDatabase(prjName + "_" + prjName);
		try{
			//json = m_projectInfoDao.queryPtInfoJson(con);
			PointInfoDao pointInfoDao = new PointInfoDao();    
			List<PointInfo> ptInfos = pointInfoDao.queryPtInfoBySensorTypeList(con, SensorType,prjName);
		
			if (null != ptInfos)
			{
				array=new JSONArray();
				for( int i = 0 ; i < ptInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					PointInfo prjInfoItem = ptInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put("SensorName", prjInfoItem.getSensorName());
					mapOfColValues.put("staName", prjInfoItem.getStaName());
					mapOfColValues.put("prjName", prjInfoItem.getPrjName());
					mapOfColValues.put("sensorType", prjInfoItem.getSensorType());
					mapOfColValues.put("Latitude", prjInfoItem.getLatitude());
					mapOfColValues.put("Longitude", prjInfoItem.getLongitude());
					mapOfColValues.put("Status", prjInfoItem.getStatusVal());
					mapOfColValues.put("StatusDes", prjInfoItem.getCurStatus());
					mapOfColValues.put("location", prjInfoItem.getlocation());
					mapOfColValues.put("imgURL", prjInfoItem.getimgURL());
					mapOfColValues.put("imgX", prjInfoItem.getimgX());
					mapOfColValues.put("imgY", prjInfoItem.getimgY());
					mapOfColValues.put("ref1", prjInfoItem.getref1());
					mapOfColValues.put("ref2", prjInfoItem.getref2());
					mapOfColValues.put("ref3", prjInfoItem.getref3());
					mapOfColValues.put("SensorID", prjInfoItem.getSensorID());
					array.add(mapOfColValues);
				}
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return array;
	}
	
	
	
}
