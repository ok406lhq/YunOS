package com.anchor.service;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.model.IPIItem;
import com.anchor.dao.GNSSResultDao;
import com.anchor.dao.IPIResultDao;
import com.anchor.dao.PointInfoDao;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.model.GNSSResult;
import com.anchor.model.IPIResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.model.ReportSummary;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;

public class IPIResultService {

	
	
	//查询项目信息
	public String QueryIPIResult(Connection con, String prjName, String sensorName,
			String starttime,String endtime,String ymw)
	{
		String json="";
		try{
			PointInfoDao ptInfoDao = new PointInfoDao();
			List<PointInfo> ptInfos = ptInfoDao.QueryPointsByGroupName(con, sensorName);
			if (null == ptInfos)
				return json;

			JSONArray array=new JSONArray();
			JSONObject ptInfosItem = new JSONObject();
			JSONArray ptInfoArray = new JSONArray();
			for (int i = 0 ; i < ptInfos.size(); i ++){
				PointInfo ptInfo = ptInfos.get(i);
				JSONObject mapOfValues=new JSONObject();
				mapOfValues.put("XY_"+(i), ptInfo.getSensorName());
				mapOfValues.put("depth", ptInfo.getup());
				ptInfoArray.add(mapOfValues);
			}
			
			
			array.add(ptInfosItem);
			
			IPIResultDao IPIResultdao = new IPIResultDao();
			//
			List<IPIResult> results = IPIResultdao.queryIPIResultList(con, prjName, ptInfos,starttime,endtime,ymw,sensorName);
			
			if (null != results)
			{
				NumberFormat nf = NumberFormat.getNumberInstance();  
	            nf.setMaximumFractionDigits(2);  
                   
				JSONObject ptDatasItem = new JSONObject();
	            JSONArray dataArray=new JSONArray();
	            for( int i = 0 ; i < results.size() ; i++) {
	            	IPIResult ipiRes = results.get(i);
	            	JSONArray itemArray=new JSONArray();
	            	
	            	for (int j = 0 ; j < ipiRes.IpiItems.size(); j ++)
					{
	            		PointInfo ptInfo = ptInfos.get(j);
	            		
	            		IPIItem ipiItem = ipiRes.IpiItems.get(j);
	            		JSONObject mapOfColValues=new JSONObject();
	            		mapOfColValues.put("x", nf.format(ipiItem.getdisX()));
	            		mapOfColValues.put("y", nf.format(ipiItem.getdisY()));
	            		mapOfColValues.put("depth", ptInfo.getup());
	            		mapOfColValues.put("adddate", ipiRes.getSDatetime());
	            		mapOfColValues.put("sensorname", ptInfo.getGroupName());
	            		itemArray.add(mapOfColValues);
	            	}
	            	dataArray.add(itemArray);
	            }
	          /*
				for( int i = 0 ; i < results.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					IPIResult ipiRes = results.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(IPIResult.RES_TIME, ipiRes.getSDatetime());
					for (int j = 0 ; j < ipiRes.IpiItems.size(); j ++)
					{
						IPIItem ipiItem = ipiRes.IpiItems.get(j);
						mapOfColValues.put(IPIResult.DIS_X1 + j, nf.format(ipiItem.getdisX()));
						mapOfColValues.put(IPIResult.DIS_Y1 + j, nf.format(ipiItem.getdisY()));
					}
					dataArray.add(mapOfColValues);
				}
				*/
	            ptDatasItem.put("ptData", dataArray);
	            array.add(ptDatasItem);
				json = dataArray.toString();
				
			}
			//System.out.print(json);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		return json;
	}
}
