package com.anchor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import sun.management.counter.Variability;

import com.anchor.model.PointInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CompareDataDao {
	public CompareDataDao() {
		super();
	}

	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;

	public JSONObject QueryCompareData(Connection con, String sensorName, String prjName, String sensorType,
			String start_time, String end_time) throws Exception {
		// TODO Auto-generated method stub
		String[] SensorTypeArr = { "GNSS", "USS", "Prism", "Inclinometer", "RainGauge", "Piezometer", "LoadCell",
				"LRDM" };
		PointInfoDao ptInfoDao = new PointInfoDao();
		String[] sensorNameStrings = sensorName.split(",");
		String[] sensorTypeStrings = sensorType.split(",");
		JSONObject ResultArrs = new JSONObject();
		for (int i = 0; i < sensorNameStrings.length; i++) {
			String sensorname = sensorNameStrings[i];
			PointInfo ptInfo = ptInfoDao.queryPtInfoOnlyBySensorName(con, sensorname);
			// PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(con, prjName,
			// sensorname);
			if (null == ptInfo)
				continue;
			String condition = " where DATE_FORMAT((sDateTime),'%Y-%m-%d')>=\"" + start_time
					+ "\" and DATE_FORMAT((sDateTime),'%Y-%m-%d') <=\"" + end_time
					+ "\" and mark < 10 group by  DateTime";
			//////// GNSS////////////////////////////////////////////////////////////GNSS///////////////////
			if (sensorTypeStrings[i].equals("GNSS")) { // GNSS
				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,dnorth - " + ptInfo.getref1()
						+ " as dnorth, deast - " + ptInfo.getref2() + " as deast, dup - " + ptInfo.getref3()
						+ " as dup from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
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
				ResultArrs.put(sensorname + "_dnorth/" + sensorTypeStrings[i], dnorthjsonArray);
				ResultArrs.put(sensorname + "_deast/" + sensorTypeStrings[i], deastjsonArray);
				ResultArrs.put(sensorname + "_dup/" + sensorTypeStrings[i], dupjsonArray);
				////// USS类型///////////////////////////////////////////////////////USS类型//////////
			} else if (sensorTypeStrings[i].equals("USS")) { // USS类型

				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,max(distance) as distance  from "
						+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
				StringBuffer sbf = new StringBuffer(sql);
				m_pstmt = con.prepareStatement(sbf.toString());
				m_rset = m_pstmt.executeQuery();
				JSONArray ussjsonArray = new JSONArray();
				while (m_rset.next()) {
					JSONObject ussObject = new JSONObject();
					ussObject.put(0, m_rset.getString("DateTime"));
					ussObject.put(1, m_rset.getDouble("distance"));
					ussjsonArray.add(ussObject);
				}

				ResultArrs.put(sensorname + "/" + sensorTypeStrings[i], ussjsonArray);
				/////////////////////////////////// Prism//////////////////////////////////////////////
			} else if (sensorTypeStrings[i].equals("Prism")) {
				// String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime
				// ,easting - " + ptInfo.getref1() + " as easting, northing - " +
				// ptInfo.getref2()
				// + " as northing, rl - " + ptInfo.getref3() + " as rl from " +
				// ptInfo.getStaName() + "_" + ptInfo.getSensorName()+ condition;
				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,easting as easting, northing  as northing, rl as rl from "
						+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
				StringBuffer sbf = new StringBuffer(sql);
				m_pstmt = con.prepareStatement(sbf.toString());
				m_rset = m_pstmt.executeQuery();
				JSONArray eastingjsonArray = new JSONArray();
				JSONArray northingjsonArray = new JSONArray();
				JSONArray rljsonArray = new JSONArray();
				while (m_rset.next()) {
					JSONObject eastingObject = new JSONObject();
					JSONObject northingObject = new JSONObject();
					JSONObject rlObject = new JSONObject();
					eastingObject.put(0, m_rset.getString("DateTime"));
					eastingObject.put(1, m_rset.getDouble("easting") - ptInfo.getref2());
					northingObject.put(0, m_rset.getString("DateTime"));
					northingObject.put(1, m_rset.getDouble("northing") - ptInfo.getref1());
					rlObject.put(0, m_rset.getString("DateTime"));
					rlObject.put(1, m_rset.getDouble("rl") - ptInfo.getref3());
					eastingjsonArray.add(eastingObject);
					northingjsonArray.add(northingObject);
					rljsonArray.add(rlObject);
				}
				ResultArrs.put(sensorname + "-easting/" + sensorTypeStrings[i], eastingjsonArray);
				ResultArrs.put(sensorname + "-northing/" + sensorTypeStrings[i], northingjsonArray);
				ResultArrs.put(sensorname + "-rl/" + sensorTypeStrings[i], rljsonArray);

				////////////////////////// Inclinometer//////////////////////////////////
			} else if (sensorTypeStrings[i].equals("Inclinometer")) {
				String tableName = ptInfo.getStaName() + "_" + ptInfo.getSensorName().replace("-", "_");
				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,x as Xcoord  from " + tableName
						+ condition;
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

				ResultArrs.put(sensorname + "/" + sensorTypeStrings[i], XcoordjsonArray);
				////// RainGauge///////////////////////////RainGauge//////////////////////////////////////RainGauge///////////
			} else if (sensorTypeStrings[i].equals("RainGauge")) {
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

				ResultArrs.put(sensorname + "/" + sensorTypeStrings[i], hyetaljsonArray);
				////// Piezometer///////////////////////////////////////Piezometer//////////////////Piezometer//////////////////////////
			} else if (sensorTypeStrings[i].equals("Piezometer")) {
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

				ResultArrs.put(sensorname + "/" + sensorTypeStrings[i], wheightjsonArray);
				////////// LoadCell/////////////////////////////////////////////////////////////////LoadCell////////////
			} else if (sensorTypeStrings[i].equals("LoadCell")) {
				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,max(pressure) as pressure  from "
						+ ptInfo.getStaName() + "_" + ptInfo.getSensorName() + condition;
				StringBuffer sbf = new StringBuffer(sql);
				m_pstmt = con.prepareStatement(sbf.toString());
				m_rset = m_pstmt.executeQuery();
				JSONArray pressurejsonArray = new JSONArray();
				while (m_rset.next()) {
					JSONObject pressureObject = new JSONObject();
					pressureObject.put(0, m_rset.getString("DateTime"));
					pressureObject.put(1, m_rset.getDouble("pressure"));
					pressurejsonArray.add(pressureObject);
				}

				ResultArrs.put(sensorname + "/" + sensorTypeStrings[i], pressurejsonArray);
				////////// LRDM/////////////////////////////////////////////////////////////LRDM///////////////////
			} else if (sensorTypeStrings[i].equals("LRDM")) {
				String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime ,max(displacement)- "
						+ ptInfo.getref1() + "  as displacement  from " + ptInfo.getStaName() + "_"
						+ ptInfo.getSensorName() + condition;
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

				ResultArrs.put(sensorname, displacementjsonArray);
			} else if (sensorTypeStrings[i].equals("Crackmeter")) {
				// String sql = "select DATE_FORMAT((sDateTime),'%Y-%m-%d %H') as DateTime
				// ,max(offset)- " + ptInfo.getref1() + " as displacement from " +
				// ptInfo.getStaName() + "_" + ptInfo.getSensorName()+ condition;
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

				ResultArrs.put(sensorname, displacementjsonArray);
			} else if (sensorTypeStrings[i].equals("JLSZ")) {
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
				ResultArrs.put(sensorname, displacementjsonArray);
			}else if (sensorTypeStrings[i].equals("Tiltmeter")) {
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
				//ResultArrs.put(sensorname + "_dnorth/" + sensorTypeStrings[i], dnorthjsonArray);
				ResultArrs.put(sensorname+"_x/" + sensorTypeStrings[i], xArray);
				ResultArrs.put(sensorname+"_y/" + sensorTypeStrings[i], yArray);
			}
		}

		return ResultArrs;
	}
}
