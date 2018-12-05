package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;

public class PointInfoDao {

	public PointInfoDao() {
		super();
	}

	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;

	/**
	 * 查询当前不在正常工作的设备
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public List<PointInfo> queryPtAbnormal(Connection conn, String prjName) throws Exception {
		String sql = "select SensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal from ptinfo where status <> \"Online\" ";
		if (prjName != "")
			sql += " and prjName = \"" + prjName + "\" ";
		sql += " order by sensorName desc";
		StringBuffer sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 1;
		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
		while (m_rset.next()) {
			PointInfo ptInfo = new PointInfo();
			ptInfo.setSensorName(m_rset.getString(col));
			ptInfo.setStaName(m_rset.getString(++col));
			ptInfo.setPrjName(m_rset.getString(++col));
			ptInfo.setSensorType(m_rset.getString(++col));
			ptInfo.setLatitude(m_rset.getDouble(++col));
			ptInfo.setLongitude(m_rset.getDouble(++col));
			ptInfo.setStatusVal(m_rset.getString(++col));
			col = 1;
			prjPointList.add(ptInfo);
		}
		return prjPointList;
	}

	/**
	 * 查询点位信息--
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PointInfo queryPtInfoBySensorName(Connection conn, String prjName, String sensorName) throws Exception {
		String url = Connest.URL_POINT_QueryPtInfoBySensorName;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		url = String.format(url,name,sensor);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		org.json.JSONObject objItem=arrObjs.getJSONObject(0);
		PointInfo ptInfo = new PointInfo();
		ptInfo.setSensorName(objItem.getString("SensorName"));
		ptInfo.setStaName(objItem.getString("StaName"));
		ptInfo.setPrjName(objItem.getString("PrjName"));
		ptInfo.setSensorType(objItem.getString("SensorType"));
		//经纬度
		try{
			ptInfo.setLatitude(Double.parseDouble(objItem.getString("Latitude")));
			ptInfo.setLongitude(Double.parseDouble(objItem.getString("Longitude")));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setLatitude(0.0);
			ptInfo.setLongitude(0.0);
		}
		
		ptInfo.setStatusVal(objItem.getString("StatusVal"));
		ptInfo.setCurStatus(objItem.getString("CurStatus"));
		//电压
		try{
			ptInfo.setvoltage(objItem.getString("voltage"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setvoltage("0.0");
		}
		
		try{
			ptInfo.setlocation(objItem.getString("location"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setlocation("位置");
		}

		try{
			ptInfo.setimgURL(objItem.getString("imgURL"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setlocation("图片地址");
		}
		try{
			ptInfo.setref1(objItem.getDouble("ref1"));
			ptInfo.setref2(objItem.getDouble("ref2"));
			ptInfo.setref3(objItem.getDouble("ref3"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setref1(0);
			ptInfo.setref2(0);
			ptInfo.setref3(0);
		}
		
		//		String sql = "select SensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,ref1,ref2,ref3 from ptinfo "
//				+ " where prjName = \"" + prjName + "\" and sensorName = \"" + sensorName + "\" ";
//		StringBuffer sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		if (m_rset.next()) {
//			PointInfo ptInfo = new PointInfo();
//			ptInfo.setSensorName(m_rset.getString(col));
//			ptInfo.setStaName(m_rset.getString(++col));
//			ptInfo.setPrjName(m_rset.getString(++col));
//			ptInfo.setSensorType(m_rset.getString(++col));
//			ptInfo.setLatitude(m_rset.getDouble(++col));
//			ptInfo.setLongitude(m_rset.getDouble(++col));
//			ptInfo.setStatusVal(m_rset.getString(++col));
//			ptInfo.setCurStatus(m_rset.getString(++col));
//			ptInfo.setvoltage(m_rset.getString(++col));
//			ptInfo.setlocation(m_rset.getString(++col));
//			ptInfo.setimgURL(m_rset.getString(++col));
//			ptInfo.setref1(m_rset.getDouble(++col));
//			ptInfo.setref2(m_rset.getDouble(++col));
//			ptInfo.setref3(m_rset.getDouble(++col));
//			col = 1;
//			return ptInfo;
//		}
		return ptInfo;
	}

	/**
	 * 依据点位名称查询点位信息（无需传入项目名称）
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PointInfo queryPtInfoOnlyBySensorName(Connection conn, String sensorName) throws Exception {
		String url = Connest.URL_POINT_QueryPtInfoOnlyBySensorName;
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		url = String.format(url,sensor);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		org.json.JSONObject objItem=arrObjs.getJSONObject(0);
		PointInfo ptInfo = new PointInfo();
		ptInfo.setSensorName(objItem.getString("SensorName"));
		ptInfo.setStaName(objItem.getString("StaName"));
		ptInfo.setPrjName(objItem.getString("PrjName"));
		ptInfo.setSensorType(objItem.getString("SensorType"));
		//经纬度
		try{
			ptInfo.setLatitude(Double.parseDouble(objItem.getString("Latitude")));
			ptInfo.setLongitude(Double.parseDouble(objItem.getString("Longitude")));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setLatitude(0.0);
			ptInfo.setLongitude(0.0);
		}
		
		ptInfo.setStatusVal(objItem.getString("StatusVal"));
		ptInfo.setCurStatus(objItem.getString("CurStatus"));
		//电压
		try{
			ptInfo.setvoltage(objItem.getString("voltage"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setvoltage("0.0");
		}
		
		try{
			ptInfo.setlocation(objItem.getString("location"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setlocation("位置");
		}

		try{
			ptInfo.setimgURL(objItem.getString("imgURL"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setlocation("图片地址");
		}
		try{
			ptInfo.setref1(objItem.getDouble("ref1"));
			ptInfo.setref2(objItem.getDouble("ref2"));
			ptInfo.setref3(objItem.getDouble("ref3"));
		}catch(Exception er){
			//由于服务器的数据库中没有填充数据，容易返回空指针
			ptInfo.setref1(0.0);
			ptInfo.setref2(0.0);
			ptInfo.setref3(0.0);
		}
		
		//		String sql = "select SensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,ref1,ref2,ref3 from ptinfo "
//				+ " where  sensorName = \"" + sensorName + "\" ";
//		StringBuffer sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		if (m_rset.next()) {
//			PointInfo ptInfo = new PointInfo();
//			ptInfo.setSensorName(m_rset.getString(col));
//			ptInfo.setStaName(m_rset.getString(++col));
//			ptInfo.setPrjName(m_rset.getString(++col));
//			ptInfo.setSensorType(m_rset.getString(++col));
//			ptInfo.setLatitude(m_rset.getDouble(++col));
//			ptInfo.setLongitude(m_rset.getDouble(++col));
//			ptInfo.setStatusVal(m_rset.getString(++col));
//			ptInfo.setCurStatus(m_rset.getString(++col));
//			ptInfo.setvoltage(m_rset.getString(++col));
//			ptInfo.setlocation(m_rset.getString(++col));
//			ptInfo.setimgURL(m_rset.getString(++col));
//			ptInfo.setref1(m_rset.getDouble(++col));
//			ptInfo.setref2(m_rset.getDouble(++col));
//			ptInfo.setref3(m_rset.getDouble(++col));
//			col = 1;
//			return ptInfo;
//		}
		return ptInfo;
	}

	/**
	 * 查询项目信息--用list
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public List<PointInfo> queryPtInfoList(Connection conn, String prjName) throws Exception {
		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
		String url = Connest.URL_POINT_QueryPtInfoList;
		String name = URLEncoder.encode(prjName,"utf-8");
		url = String.format(url, name);
		String res = HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		System.out.println("queryPtInfoList:"+res);
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			return prjPointList;
			//throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			PointInfo ptInfo = new PointInfo();
			ptInfo.setSensorName(objItem.getString("SensorName"));
			ptInfo.setStaName(objItem.getString("StaName"));
			ptInfo.setPrjName(objItem.getString("PrjName"));
			ptInfo.setSensorType(objItem.getString("SensorType"));
//			//null
//			ptInfo.setLatitude(objItem.getDouble("Latitude"));
//			//null
//			ptInfo.setLongitude(objItem.getDouble("Longitude"));
			ptInfo.setStatusVal(objItem.getString("StatusVal"));
			ptInfo.setCurStatus(objItem.getString("CurStatus"));
			//null objItem.getString("voltage")
			ptInfo.setvoltage("15");
			//null objItem.getString("location")
			ptInfo.setlocation("位置");
			//null objItem.getString("imgURL")
			ptInfo.setimgURL("https://ws1.sinaimg.cn/large/610dc034ly1fp9qm6nv50j20u00miacg.jpg");
			ptInfo.setimgX(objItem.getString("imgX"));
			ptInfo.setimgY(objItem.getString("imgY"));
			//"" objItem.getDouble("ref1")
			ptInfo.setref1(0.0);
			ptInfo.setref2(0.0);
			ptInfo.setref3(0.0);
			//null 
			//ptInfo.setGroupName(objItem.getString("groupName"));
			ptInfo.setSensorID(objItem.getString("SensorID"));
			prjPointList.add(ptInfo);
		}
//		String sql = "select SensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,imgX,imgY,ref1,ref2,ref3,groupname from ptinfo "
//				+ " where prjName = \"" + prjName + "\" group by groupname order by sensorType desc";
//
//		StringBuffer sbf = new StringBuffer(sql);
//		System.out.println(sbf.toString());
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
//		while (m_rset.next()) {
//			PointInfo ptInfo = new PointInfo();
//			ptInfo.setSensorName(m_rset.getString(col));
//			ptInfo.setStaName(m_rset.getString(++col));
//			ptInfo.setPrjName(m_rset.getString(++col));
//			ptInfo.setSensorType(m_rset.getString(++col));
//			ptInfo.setLatitude(m_rset.getDouble(++col));
//			ptInfo.setLongitude(m_rset.getDouble(++col));
//			ptInfo.setStatusVal(m_rset.getString(++col));
//			ptInfo.setCurStatus(m_rset.getString(++col));
//			ptInfo.setvoltage(m_rset.getString(++col));
//			ptInfo.setlocation(m_rset.getString(++col));
//			ptInfo.setimgURL(m_rset.getString(++col));
//			ptInfo.setimgX(m_rset.getString(++col));
//			ptInfo.setimgY(m_rset.getString(++col));
//			ptInfo.setref1(m_rset.getDouble(++col));
//			ptInfo.setref2(m_rset.getDouble(++col));
//			ptInfo.setref3(m_rset.getDouble(++col));
//			ptInfo.setGroupName(m_rset.getString(++col));
//			col = 1;
//			prjPointList.add(ptInfo);
//		}
		return prjPointList;
	}
						    
	public List<PointInfo> queryPtInfoBySensorTypeList(Connection conn, String sensorType, String PrjName)
			throws Exception {
		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
		String url = Connest.URL_POINT_QueryPtInfoBySensorTypeList;
		String type = URLEncoder.encode(sensorType,"utf-8");
		String name = URLEncoder.encode(PrjName,"utf-8");
		System.out.println("sensorType:"+type);
		System.out.println("PrjName:"+name);
		
		url = String.format(url,name,type);
		String res = HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			PointInfo ptInfo = new PointInfo();
			ptInfo.setSensorName(objItem.getString("SensorName"));
			ptInfo.setStaName(objItem.getString("StaName"));
			ptInfo.setPrjName(objItem.getString("PrjName"));
			ptInfo.setSensorType(objItem.getString("SensorType"));
			try{
				ptInfo.setLatitude(objItem.getDouble("Latitude"));
				ptInfo.setLongitude(objItem.getDouble("Longitude"));
			}catch(Exception exception){
				ptInfo.setLatitude(0.0);
				ptInfo.setLongitude(0.0);
			}
			
			ptInfo.setStatusVal(objItem.getString("StatusVal"));
			ptInfo.setCurStatus(objItem.getString("CurStatus"));
			
			try{
				ptInfo.setvoltage(objItem.getString("voltage"));
				ptInfo.setlocation(objItem.getString("location"));
				ptInfo.setimgURL(objItem.getString("imgURL"));
			}catch(Exception exception){
				ptInfo.setvoltage("voltage");
				ptInfo.setlocation("location");
				ptInfo.setimgURL("imgURL");
			}
			
			ptInfo.setimgX(objItem.getString("imgX"));
			ptInfo.setimgY(objItem.getString("imgY"));
			
			try{
				ptInfo.setref1(objItem.getDouble("ref1"));
				ptInfo.setref2(objItem.getDouble("ref2"));
				ptInfo.setref3(objItem.getDouble("ref3"));
			}catch(Exception exception){
				ptInfo.setref1(0.0);
				ptInfo.setref2(0.0);
				ptInfo.setref3(0.0);
			}
			ptInfo.setSensorID(objItem.getString("SensorID"));
			prjPointList.add(ptInfo);
		}
		// String sql = "select
		// SensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,imgX,imgY,ref1,ref2,ref3
		// from ptinfo "
		// + " where SensorType = \"" + sensorType + "\" and prjname= \"" + PrjName +
		// "\" order by sensorType desc";
//		String sql = "select groupname,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,imgX,imgY,ref1,ref2,ref3 from ptinfo "
//				+ " where SensorType = \"" + sensorType + "\" and prjname= \"" + PrjName
//				+ "\" group by groupname order by sensorType desc";
//
//		StringBuffer sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
//		while (m_rset.next()) {
//			PointInfo ptInfo = new PointInfo();
//			ptInfo.setSensorName(m_rset.getString(col));
//			System.out.print(m_rset.getString(col));
//			ptInfo.setStaName(m_rset.getString(++col));
//			ptInfo.setPrjName(m_rset.getString(++col));
//			ptInfo.setSensorType(m_rset.getString(++col));
//			ptInfo.setLatitude(m_rset.getDouble(++col));
//			ptInfo.setLongitude(m_rset.getDouble(++col));
//			ptInfo.setStatusVal(m_rset.getString(++col));
//			ptInfo.setCurStatus(m_rset.getString(++col));
//			ptInfo.setvoltage(m_rset.getString(++col));
//			ptInfo.setlocation(m_rset.getString(++col));
//			ptInfo.setimgURL(m_rset.getString(++col));
//			ptInfo.setimgX(m_rset.getString(++col));
//			ptInfo.setimgY(m_rset.getString(++col));
//			ptInfo.setref1(m_rset.getDouble(++col));
//			ptInfo.setref2(m_rset.getDouble(++col));
//			ptInfo.setref3(m_rset.getDouble(++col));
//			col = 1;
//			prjPointList.add(ptInfo);
//		}
		return prjPointList;
	}

	public List<PointInfo> queryPtInfoBySensorType(Connection conn, String sensorType, String PrjName)
			throws Exception {

		String sql = "select sensorName,StaName,PrjName,SensorType,Latitude,Longitude,StatusVal,CurStatus,voltage,location,imgURL,imgX,imgY,ref1,ref2,ref3 from ptinfo "
				+ " where SensorType = \"" + sensorType + "\" and prjname= \"" + PrjName + "\" ";

		StringBuffer sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 1;
		List<PointInfo> prjPointList = new ArrayList<PointInfo>();
		while (m_rset.next()) {
			PointInfo ptInfo = new PointInfo();
			ptInfo.setSensorName(m_rset.getString(col));

			ptInfo.setStaName(m_rset.getString(++col));
			ptInfo.setPrjName(m_rset.getString(++col));
			ptInfo.setSensorType(m_rset.getString(++col));
			ptInfo.setLatitude(m_rset.getDouble(++col));
			ptInfo.setLongitude(m_rset.getDouble(++col));
			ptInfo.setStatusVal(m_rset.getString(++col));
			ptInfo.setCurStatus(m_rset.getString(++col));
			ptInfo.setvoltage(m_rset.getString(++col));
			ptInfo.setlocation(m_rset.getString(++col));
			ptInfo.setimgURL(m_rset.getString(++col));
			ptInfo.setimgX(m_rset.getString(++col));
			ptInfo.setimgY(m_rset.getString(++col));
			ptInfo.setref1(m_rset.getDouble(++col));
			ptInfo.setref2(m_rset.getDouble(++col));
			ptInfo.setref3(m_rset.getDouble(++col));
			col = 1;
			prjPointList.add(ptInfo);
		}
		return prjPointList;
	}

	/**
	 * 查询项目信息--用list
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryPtInfoJson(Connection conn, String[] prj) throws Exception {
		String url = Connest.URL_POINT_QueryPtInfoJson;
		String project = URLEncoder.encode(String.join(",",prj),"utf-8");
		url=String.format(url,project);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		System.out.println("arrObjs:"+arrObjs.toString());
		// String sql = "select prjName, prjType, prjAddr from prjinfo order by
		// starttime desc";
//		String where = "WHERE prjname in(";
//		for (String index : prj) {
//			where += "\'" + index + "\'";
//			if (index != prj[prj.length - 1]) {
//				where += ",";
//			}
//		}
//		where+=")";
//		String sql = "select * from ptinfo "+where+" order by sensorName desc";
//		StringBuffer sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		 System.out.print("jsArray:"+jsArray.toString()+",prj:"+prj.length);
		return arrObjs.toString();
	}

	public String updatePointImageXY(Connection conn, String imgX, String imgY, String sensorname) throws Exception {
		// TODO Auto-generated method stub
		String url = Connest.URL_POINT_UpdatePointImageXY;
		String x = URLEncoder.encode(imgX,"utf-8");
		String y = URLEncoder.encode(imgY,"utf-8");
		String sensor = URLEncoder.encode(sensorname,"utf-8");
		url = String.format(url, x,y,sensor);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		return obj.getJSONArray("data").getString(0);
		
//		String sqlString = "update ptinfo set imgX=\"" + imgX + "\",imgY=\"" + imgY + "\" where SensorName=\""
//				+ sensorname + "\"";
//		System.out.print(sqlString);
//		int rSet;
//		m_pstmt = conn.prepareStatement(sqlString);
//		rSet = m_pstmt.executeUpdate(sqlString);
//
//		if (rSet == 1) {
//			return "ok";
//		} else {
//			return "wrong";
//		}
	}

	public PointInfo QueryPointBySensorType(Connection con1, String sensorType) throws Exception {
		// TODO Auto-generated method stub
		PointInfo PointInfo = new PointInfo();
		String url = Connest.URL_POINT_QueryPointBySensorType;
		String type = URLEncoder.encode(sensorType,"utf-8");
		url = String.format(url, type);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		org.json.JSONObject objItem=arrObjs.getJSONObject(0);
		try{
			PointInfo.setalert(objItem.getDouble("alert"));
			PointInfo.setalarm(objItem.getDouble("alarm"));
			PointInfo.setaction(objItem.getDouble("action"));
			PointInfo.setnotifyinterval(objItem.getString("notifyinterval"));
		}catch(Exception exception){
			PointInfo.setalert(0.0);
			PointInfo.setalarm(5.0);
			PointInfo.setaction(0.0);
			PointInfo.setnotifyinterval("1 Weeks");
		}
		
//		String sqlString = "select alert, alarm, action, notifyinterval  from ptinfo where SensorType = \"" + sensorType
//				+ "\" order by action limit 1";
//
//		StringBuffer sbf = new StringBuffer(sqlString);
//		m_pstmt = con1.prepareStatement(sbf.toString());
//		PointInfo PointInfo = new PointInfo();
//		m_rset = m_pstmt.executeQuery();
//		if (m_rset.next()) {
//
//			PointInfo.setalert(m_rset.getDouble("alert"));
//			PointInfo.setalarm(m_rset.getDouble("alarm"));
//			PointInfo.setaction(m_rset.getDouble("action"));
//			PointInfo.setnotifyinterval(m_rset.getString("notifyinterval"));
//
//		}
		return PointInfo;
	}

	public List<PointInfo> QueryPointsByGroupName(Connection con1, String groupName) throws Exception {
		// TODO Auto-generated method stub
		List<PointInfo> PointInfos = new ArrayList<PointInfo>();
		String url = Connest.URL_POINT_QueryPointsByGroupName;
		String name = URLEncoder.encode(groupName,"utf-8");
		url = String.format(url, name);
		System.out.println("url111:"+url);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			PointInfo ptInfo = new PointInfo();
			ptInfo.setSensorName(objItem.getString("SensorName"));
			ptInfo.setStaName(objItem.getString("StaName"));
			try{
				ptInfo.setGroupName(objItem.getString("groupName"));
				ptInfo.setup(objItem.getDouble("up"));
			}catch(Exception e){
				ptInfo.setGroupName("GroupName");
				ptInfo.setup(0.0);
			}
			PointInfos.add(ptInfo);
		}
//		String sqlString = "select staname, sensorname, up,groupname  from ptinfo where groupname = \"" + groupName
//				+ "\" ";
//
//		StringBuffer sbf = new StringBuffer(sqlString);
//		m_pstmt = con1.prepareStatement(sbf.toString());
//		List<PointInfo> PointInfos = new ArrayList<PointInfo>();
//		m_rset = m_pstmt.executeQuery();
//		while (m_rset.next()) {
//			PointInfo ptInfo = new PointInfo();
//			String sensorName = m_rset.getString("sensorname");
//			sensorName = sensorName.replace("-", "_");
//			ptInfo.setSensorName(sensorName);
//
//			String staName = m_rset.getString("staName");
//			staName = staName.replace("-", "_");
//			ptInfo.setStaName(staName);
//			ptInfo.setGroupName(m_rset.getString("groupname"));
//			ptInfo.setup(m_rset.getDouble("up"));
//			PointInfos.add(ptInfo);
//		}
		return PointInfos;
	}
					  
	public org.json.JSONArray QueryPointsSensorName(Connection con1, String sensorName) throws Exception {
		// TODO Auto-generated method stub
		String url = Connest.URL_POINT_QueryPointsSensorName;
		String name = URLEncoder.encode(sensorName,"utf-8");
		url = String.format(url, name);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
//		JSONArray jsArray = new JSONArray();
//		// String sqlString="select * from ptinfo where SensorName = \"" + sensorName +
//		// "\" ";
//		String sqlString = "select *  from ptinfo where groupname = \"" + sensorName + "\" ";
//
//		StringBuffer sbf = new StringBuffer(sqlString);
//		m_pstmt = con1.prepareStatement(sbf.toString());
//
//		m_rset = m_pstmt.executeQuery();
//
//		jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		 System.out.print(jsArray.toString());

		return obj.getJSONArray("data");
	}

	public org.json.JSONArray QueryPointsSensorNameToImgUr(Connection con1, String sensorName) throws Exception {
		// TODO Auto-generated method stub
		String url = Connest.URL_POINT_QueryPointsSensorNameToImgUr;
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		url = String.format(url, sensor);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		
//		JSONArray jsArray = new JSONArray();
//		// String sqlString="select * from ptinfo where SensorName = \"" + sensorName +
//		// "\" ";
//		String sqlString = "select *  from ptinfo where groupname = \"" + sensorName + "\" ";
//
//		StringBuffer sbf = new StringBuffer(sqlString);
//		m_pstmt = con1.prepareStatement(sbf.toString());
//
//		m_rset = m_pstmt.executeQuery();
//
//		jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		// System.out.print(jsArray.toString());

		return obj.getJSONArray("data");
	}

	public String editPointBySensorType(Connection con1, PointInfo pointInfo) throws SQLException {
		String p = "0";
		// TODO Auto-generated method stub

		double alert = pointInfo.getalert();
		double alarm = pointInfo.getalarm();
		double action = pointInfo.getaction();
		String SensorType = pointInfo.getSensorType();
		String notifyInterval = pointInfo.getnotifyinterval();
		String sqlString = "update  ptinfo set alert=" + alert + ",alarm=" + alarm + ",action=" + action
				+ ",notifyinterval=\"" + notifyInterval + "\" where SensorType = \"" + SensorType + "\" ";
		System.out.print(sqlString);
		StringBuffer sbf = new StringBuffer(sqlString);
		m_pstmt = con1.prepareStatement(sbf.toString());
		int rst = 0;
		rst = m_pstmt.executeUpdate();
		// return rst+"";
		return "1";
	}

	public String editPointBySensorName(Connection con1, PointInfo pointInfo) throws SQLException {
		// TODO Auto-generated method stub

		double alert = pointInfo.getalert();
		double alarm = pointInfo.getalarm();
		double action = pointInfo.getaction();
		double north = pointInfo.getnorth();
		double east = pointInfo.geteast();
		double up = pointInfo.getup();
		String SensorType = pointInfo.getSensorType();
		String location = pointInfo.getlocation();
		String notifyInterval = pointInfo.getnotifyinterval();
		String SensorName = pointInfo.getSensorName();
		String sqlString = "update  ptinfo set north=" + north + ",east=" + east + ",up=" + up + ", alert=" + alert
				+ ",alarm=" + alarm + ",action=" + action + ",location=\"" + location + "\",notifyinterval=\""
				+ notifyInterval + "\" where SensorName = \"" + SensorName + "\" ";
		System.out.print(sqlString);
		StringBuffer sbf = new StringBuffer(sqlString);
		m_pstmt = con1.prepareStatement(sbf.toString());
		int rst = 0;
		rst = m_pstmt.executeUpdate();

		return rst + "";

	}

	public void PointImgUrlSave(Connection conn, String retxt, String sensorname) throws SQLException {
		// TODO Auto-generated method stub
		String sqlString = "update ptinfo set imgURL=\"" + retxt + "\" where sensorname=\"" + sensorname + "\"";
		StringBuffer sbf = new StringBuffer(sqlString);
		m_pstmt = conn.prepareStatement(sbf.toString());
		int rst = 0;
		rst = m_pstmt.executeUpdate();

	}

	public JSONArray QueryPointsByPrjName(Connection con1, String prjName) {
		// TODO Auto-generated method stub
		return null;
	}

	public int editPointChartY(Connection con1, String sensorName, String isY, String chartY) throws SQLException {
		String sqlString = "update ptinfo set isY=" + isY + " , chartY=" + chartY + " where groupname=\"" + sensorName
				+ "\"";
		System.out.print(sqlString);

		StringBuffer sbf = new StringBuffer(sqlString);
		try {
			m_pstmt = con1.prepareStatement(sbf.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rst = 0;
		rst = m_pstmt.executeUpdate();
		return rst;
	}
}
