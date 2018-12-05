package com.anchor.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.SystemLogDao;
import com.anchor.model.SystemLog;
import com.anchor.util.DateUtil;

public class SystemLogService {

	public static void addSystemLogData(String active, String descript, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		 HttpSession session = request.getSession();
		 String userID=(String)session.getAttribute("userID");
		 String username=(String)session.getAttribute("username");
		SystemLogDao systemLogDao=new SystemLogDao();
		SystemLog systemlog=new SystemLog();
		
		String nowDateString=DateUtil.getNowPlusTime();
		systemlog.setuserID(userID);
		systemlog.setusername(username);
		systemlog.setactive(active);
		systemlog.setdescript(descript);
	
		systemlog.setadddate(nowDateString);
		
		systemLogDao.SaveAddSystemLog(systemlog);
		
	}
	
	SystemLogDao systemLogDao=new SystemLogDao();
	
	public JSONArray QuerySystemData(String sProjectIDS, Integer userid,
			String starttime, String endtime, Integer page, Integer pageSize) throws Exception {
		JSONArray json=new JSONArray();
		List<Map<String, Object>> list = systemLogDao.QuerySystemLogoDataList(sProjectIDS,userid,starttime,endtime,page,pageSize);
		for (Map<String, Object> map : list) {
		   JSONObject jsonObject=new JSONObject();
		   jsonObject.put("id", map.get("ID"));
		   jsonObject.put("userid", map.get("nUserID"));
		   jsonObject.put("username", map.get("sUserName"));
		   jsonObject.put("active", map.get("sAction"));
		   jsonObject.put("descript", map.get("sDetail"));
		   jsonObject.put("adddate", DateUtil.getPlusTime(map.get("dtModifyAt")));
		   json.add(jsonObject);
	   }
	return json;
	}
 
}
