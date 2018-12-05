package com.anchor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.anchor.model.PointInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DatarelatedDao {
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;

	/*
	 * 数据关联
	 */
	public JSONObject getdata(Connection con, String[] starttime, String[] endtime, String sensorType, String prjName)
			throws Exception {
		JSONObject ResultArrs = new JSONObject();
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoOnlyBySensorName(con, prjName);
		if (sensorType != null) {
			for (int i = 0; i < 2; i++) {
				String condition = " where DATE_FORMAT((sDateTime),'%Y-%m-%d')>=\"" + starttime[i]
						+ "\" and DATE_FORMAT((sDateTime),'%Y-%m-%d') <=\"" + endtime[i]
						+ "\" and mark < 10 group by  DateTime";
				if (sensorType.equals("Prism")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,easting as easting, northing  as northing, rl as rl from "
							+ "trts_" + prjName + " " + condition;
					StringBuffer sbf = new StringBuffer(sql);
					System.err.println(sbf.toString());
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray eastingjsonArray = new JSONArray();
					JSONArray northingjsonArray = new JSONArray();
					JSONArray rljsonArray = new JSONArray();
					int index = 0;
					while (m_rset.next()) {
						JSONObject eastingObject = new JSONObject();
						JSONObject northingObject = new JSONObject();
						JSONObject rlObject = new JSONObject();
						eastingObject.put(0, m_rset.getString("DateTime"));
						// eastingObject.put(0, String.valueOf(index));
						eastingObject.put(1, m_rset.getDouble("easting") - ptInfo.getref2());
						northingObject.put(0, m_rset.getString("DateTime"));
						// northingObject.put(0, String.valueOf(index));
						northingObject.put(1, m_rset.getDouble("northing") - ptInfo.getref1());
						rlObject.put(0, m_rset.getString("DateTime"));
						// rlObject.put(0, index);
						rlObject.put(1, m_rset.getDouble("rl") - ptInfo.getref3());
						eastingjsonArray.add(eastingObject);
						northingjsonArray.add(northingObject);
						rljsonArray.add(rlObject);
						index++;
					}
					ResultArrs.put(prjName + "时间段"+(i + 1) + "-easting", eastingjsonArray);
					ResultArrs.put(prjName + "时间段"+(i + 1) + "-northing", northingjsonArray);
					ResultArrs.put(prjName + "时间段"+(i + 1) + "-rl", rljsonArray);
				} else if (sensorType.equals("GNSS")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,dnorth - "
							+ ptInfo.getref1() + " as dnorth, deast - " + ptInfo.getref2() + " as deast, dup - "
							+ ptInfo.getref3() + " as dup from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()
							+ condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray dnorthjsonArray = new JSONArray();
					JSONArray deastjsonArray = new JSONArray();
					JSONArray dupjsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject dnorthObject = new JSONObject();
						JSONObject deastObject = new JSONObject();
						JSONObject dupObject = new JSONObject();
						dnorthObject.put(0, m_rset.getString("DateTime"));
						dnorthObject.put(1, m_rset.getDouble("dnorth"));
						deastObject.put(0, m_rset.getString("DateTime"));
						deastObject.put(1, m_rset.getDouble("deast"));
						dupObject.put(0, m_rset.getString("DateTime"));
						dupObject.put(1, m_rset.getDouble("dup"));
						dnorthjsonArray.add(dnorthObject);
						deastjsonArray.add(deastObject);
						dupjsonArray.add(dupObject);
					}
					ResultArrs.put(prjName +  "时间段"+(i + 1)  + "_dnorth/", dnorthjsonArray);
					ResultArrs.put(prjName +  "时间段"+(i + 1)  + "_deast/", deastjsonArray);
					ResultArrs.put(prjName +  "时间段"+(i + 1) + "_dup/", dupjsonArray);
				} else if (sensorType.equals("RainGauge")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,max(hyetal) as hyetal  from "
							+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray hyetaljsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject hyetalObject = new JSONObject();
						hyetalObject.put(0, m_rset.getString("DateTime"));
						hyetalObject.put(1, m_rset.getDouble("hyetal"));
						hyetaljsonArray.add(hyetalObject);
					}

					ResultArrs.put(prjName + ":" + (i + 1) + "/", hyetaljsonArray);
				} else if (sensorType.equals("Crackmeter")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,avg(offset) as displacement  from "
							+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray displacementjsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject displacementObject = new JSONObject();
						displacementObject.put(0, m_rset.getString("DateTime"));
						displacementObject.put(1, m_rset.getDouble("displacement"));
						displacementjsonArray.add(displacementObject);
					}

					ResultArrs.put(prjName +  "时间段"+(i + 1) , displacementjsonArray);
				} else if (sensorType.equals("JLSZ")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,avg(offset) as displacement  from "
							+ "JLSZ_" + ptInfo.getSensorName() + condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray displacementjsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject displacementObject = new JSONObject();
						displacementObject.put(0, m_rset.getString("DateTime"));
						displacementObject.put(1, m_rset.getDouble("displacement"));
						displacementjsonArray.add(displacementObject);
					}
					ResultArrs.put(prjName + ":" + (i + 1), displacementjsonArray);
				} else if (sensorType.equals("Piezometer")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,max(wheight) as wheight  from "
							+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray wheightjsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject wheightObject = new JSONObject();
						wheightObject.put(0, m_rset.getString("DateTime"));
						wheightObject.put(1, m_rset.getDouble("wheight"));
						wheightjsonArray.add(wheightObject);
					}

					ResultArrs.put(prjName +  "时间段"+(i + 1) , wheightjsonArray);
				} else if (sensorType.equals("Inclinometer")) {
					String tableName = ptInfo.getStaName() + "_" + ptInfo.getSensorName().replace("-", "_");
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,x as Xcoord  from "
							+ tableName + condition;
					System.out.print(sql);
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray XcoordjsonArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject XcoordObject = new JSONObject();
						XcoordObject.put(0, m_rset.getString("DateTime"));
						XcoordObject.put(1, m_rset.getDouble("Xcoord"));
						XcoordjsonArray.add(XcoordObject);
					}

					ResultArrs.put(prjName +  "时间段"+(i + 1) , XcoordjsonArray);
				} else if (sensorType.equals("Tiltmeter")) {
					String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,avg(x) as easting,avg(y) as northing from "
							+ "1208_" + ptInfo.getSensorName() + condition;
					StringBuffer sbf = new StringBuffer(sql);
					m_pstmt = con.prepareStatement(sbf.toString());
					m_rset = m_pstmt.executeQuery();
					JSONArray xArray = new JSONArray();
					JSONArray yArray = new JSONArray();
					while (m_rset.next()) {
						JSONObject xobjice = new JSONObject();
						JSONObject yobjice = new JSONObject();
						xobjice.put(0, m_rset.getString("DateTime"));
						xobjice.put(1, m_rset.getDouble("easting") - ptInfo.getref2());
						yobjice.put(0, m_rset.getString("DateTime"));
						yobjice.put(1, m_rset.getDouble("northing") - ptInfo.getref1());
						xArray.add(xobjice);
						yArray.add(yobjice);
					}
					// ResultArrs.put(sensorname + "_dnorth/" + sensorTypeStrings[i],
					// dnorthjsonArray);
					ResultArrs.put(prjName +  "时间段"+(i + 1)  + "_x/", xArray);
					ResultArrs.put(prjName + "时间段"+(i + 1) + "_y/", yArray);
				}
			}
		}
		return ResultArrs;
	}
}
