package com.anchor.service;

import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.anchor.dao.PointInfoDao;
import com.anchor.dao.PrismResultDao;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.dao.UssResultDao;
import com.anchor.model.UssResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;

public class PrismResultService {

	
	
	//查询项目信息
	public String QueryPrismResult(Connection con, String prjName, String sensorName,String starttime,String endtime,String ymw)
	{
		String json="";
		try{
			PrismResultDao PrismResultDao = new PrismResultDao();     
			json = PrismResultDao.queryPrismResultJson(con, prjName, sensorName,starttime,endtime,ymw);
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
