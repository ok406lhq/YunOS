package com.anchor.service;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;


import com.anchor.dao.PointInfoDao;
import com.anchor.dao.TiltmeterResultDao;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.dao.UssResultDao;
import com.anchor.model.TiltmeterResult;
import com.anchor.model.UssResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;

public class TiltmeterResultService {

	
	
	//查询项目信息
	public String QueryTiltmeterResult(Connection con, String prjName, String sensorName,String starttime,String endtime,String ymw)
	{
		String json="";
		try{
			TiltmeterResultDao tiltmeterResultDao = new TiltmeterResultDao();     
			//json = PrismResultDao.queryPrismResultJson(con, prjName, sensorName,starttime,endtime,ymw);
			JSONObject obj = tiltmeterResultDao.queryTiltmeterResult(con, prjName, sensorName,starttime,endtime,ymw);
//			if (null == obj)
//				return json;
//			if(obj.getString("code").equals("00")){
//				JSONArray jsonArray = obj.getJSONArray("data");
//				NumberFormat nf = NumberFormat.getNumberInstance();  
//				nf.setMaximumFractionDigits(3);  
//				JSONArray array=new JSONArray();
//				for( int i = 0 ; i < jsonArray.length() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
//					JSONObject prjInfoItem = jsonArray.getJSONObject(i);
//					JSONObject mapOfColValues =new JSONObject();
//					mapOfColValues.put(TiltmeterResult.RES_TIME, prjInfoItem.getString("sDateTime"));
//					mapOfColValues.put(TiltmeterResult.X_DIS, nf.format(prjInfoItem.getString("x")));
//					mapOfColValues.put(TiltmeterResult.Y_DIS, nf.format(prjInfoItem.getString("y")));
//					array.put(mapOfColValues);
//				}
//			}
//			
//			json = array.toString();
			return obj.toString();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject prompt = new JSONObject().append("code", "05").append("msg", "未知异常，请联系管理人员！");
			return prompt.toString();
		}
		
	}
}
