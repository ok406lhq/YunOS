package com.anchor.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;

public class JsonUtil {
	
	public static JSONArray formatRsToJsonArray(java.sql.ResultSet rs)throws Exception{
		ResultSetMetaData md= (ResultSetMetaData) rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				if (null != rs.getObject(i))
				{
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i).toString());
					//System.out.print(md.getColumnName(i) + ",");
					//System.out.print(rs.getObject(i) + "\n");
				}
			}
			array.add(mapOfColValues);
		}
		return array;
	}
	/**
	 * yangqifang
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static JSONArray xlhJson(java.sql.ResultSet rs) throws Exception {
		ResultSetMetaData md= (ResultSetMetaData) rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				if (null != rs.getObject(i))
				{
					mapOfColValues.put("a"+md.getColumnName(i), rs.getObject(i).toString());
					//System.out.print(md.getColumnName(i) + ",");
					//System.out.print(rs.getObject(i) + "\n");
				}
			}
			array.add(mapOfColValues);
		}
		System.out.println(array.toString());
		return array;
	}
}
