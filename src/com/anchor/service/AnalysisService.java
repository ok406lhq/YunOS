package com.anchor.service;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.AnalysisDao;

import com.anchor.model.GNSSResult;

public class AnalysisService {
    public JSONArray QueryAnalysisResult(Connection con, String prjName, String sensorName,
			String starttime,String endtime){
    	
    	
    	     JSONArray array=new JSONArray();
    		try{
    			AnalysisDao AnalysisResultDao = new AnalysisDao();     
    			List<GNSSResult> results = AnalysisResultDao.AnalysisResultDao(con, prjName, sensorName,starttime,endtime);
    			if (null != results)
    			{
    				NumberFormat nf = NumberFormat.getNumberInstance();  
    	            nf.setMaximumFractionDigits(1);  
    				
    				for( int i = 0 ; i < results.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
    					GNSSResult prjInfoItem = results.get(i);
    					JSONObject mapOfColValues=new JSONObject();
    					//
    					mapOfColValues.put(GNSSResult.RES_TIME, prjInfoItem.getSDatetime());
    					mapOfColValues.put(GNSSResult.NORTH_DIS, nf.format(prjInfoItem.getdNorth()));
    					mapOfColValues.put(GNSSResult.EAST_DIS, nf.format(prjInfoItem.getdEast()));
    					mapOfColValues.put(GNSSResult.VERT_DIS, nf.format(prjInfoItem.getdUP()));
    					array.add(mapOfColValues);
    				}
    				
    				//System.out.print(json);
    			}
    			//System.out.print(json);
    		}
    		catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}finally{
    		}
    		return array;
    	}
		
    
    public JSONArray QueryAnalysisFilterResult(Connection con, String prjName, String sensorName,
			String starttime,String endtime){
    	
    	
    	     JSONArray array=new JSONArray();
    		try{
    			AnalysisDao AnalysisResultDao = new AnalysisDao();     
    			List<GNSSResult> results = AnalysisResultDao.AnalysisFilterResultDao(con, prjName, sensorName,starttime,endtime);
    			if (null != results)
    			{
    				NumberFormat nf = NumberFormat.getNumberInstance();  
    	            nf.setMaximumFractionDigits(1);  
    				
    				for( int i = 0 ; i < results.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
    					GNSSResult prjInfoItem = results.get(i);
    					JSONObject mapOfColValues=new JSONObject();
    					//
    					mapOfColValues.put(GNSSResult.RES_TIME, prjInfoItem.getSDatetime());
    					mapOfColValues.put(GNSSResult.NORTH_DIS, nf.format(prjInfoItem.getdNorth()));
    					mapOfColValues.put(GNSSResult.EAST_DIS, nf.format(prjInfoItem.getdEast()));
    					mapOfColValues.put(GNSSResult.VERT_DIS, nf.format(prjInfoItem.getdUP()));
    					array.add(mapOfColValues);
    				}
    				
    				//System.out.print(json);
    			}
    			//System.out.print(json);
    		}
    		catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}finally{
    		}
    		return array;
    	}
}	
  

