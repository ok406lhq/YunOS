package com.anchor.service;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.UserInfoDao;

import com.anchor.model.ProjectInfo;
import com.anchor.model.User;
public class UserInfoService {

	public JSONArray QueryUserAll(Connection conn) {
		// TODO Auto-generated method stub
		JSONArray json=new JSONArray();
		try {
			UserInfoDao userInfoDao=new UserInfoDao();
			List<User> userinfosList=userInfoDao.QueryUserInfoAll(conn);
			for (int i = 0; i < userinfosList.size(); i++) {
				User user = userinfosList.get(i);
				JSONObject mapOfColValues=new JSONObject();
				mapOfColValues.put("id", user.getid());
				mapOfColValues.put("userID", user.getUserID());
				mapOfColValues.put("UserName", user.getUserName());
				mapOfColValues.put("Password", user.getPassword());
				mapOfColValues.put("mobile", user.getmobile());
				mapOfColValues.put("eTelNum", user.geteTelNum());
				mapOfColValues.put("email", user.getemail());
				mapOfColValues.put("role", user.getrole());
				mapOfColValues.put("loginnum", user.getloginnum());
				mapOfColValues.put("loginIp", user.getloginIp());
				mapOfColValues.put("lastlogindata", user.getlastlogindata());
				mapOfColValues.put("adddate", user.getadddate());
				mapOfColValues.put("position", user.getposition());
				mapOfColValues.put("department", user.getdepartment());
				mapOfColValues.put("address", user.getaddress());
				mapOfColValues.put("defPrj", user.getdefPrj());
				mapOfColValues.put("avatar", user.getavatar());
				json.add(mapOfColValues);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}

	public JSONArray QueryWarnList(Connection conn, int page, int pageSize) {
		// TODO Auto-generated method stub
		JSONArray array = null;
		UserInfoDao userInfoDao=new UserInfoDao();
		try {
			List<User> warninfo=userInfoDao.QuerWarnListDao(conn, page, pageSize);
			if (null != warninfo){
				array=new JSONArray();
				for(int i=0; i<warninfo.size();i++){
					User warnlist=warninfo.get(i);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("ID", warnlist.getwarnID());
				jsonObject.put("userName", warnlist.getuserName());
				jsonObject.put("prjName", warnlist.getprjName());
				jsonObject.put("warningLevel", warnlist.getwarningLevel());
				jsonObject.put("msgInterval", warnlist.getmsgInterval());
				jsonObject.put("warningType", warnlist.getwarningtype());
				//jsonObject.put("WText", warnlist.getWtext());
				array.add(jsonObject);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
		
	}

	public JSONObject QueryWarnDataById(Connection conn, String id) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject=new JSONObject();
		UserInfoDao userInfoDao=new UserInfoDao();
		try {
			User warnData=new User();
			warnData=userInfoDao.QueryWarnDataByIdDao(conn,id);
			if(null!=warnData){
				jsonObject.put("ID", warnData.getwarnID());
				jsonObject.put("userName", warnData.getuserName());
				jsonObject.put("prjName", warnData.getprjName());
				jsonObject.put("warningLevel", warnData.getwarningLevel());
				jsonObject.put("msgInterval", warnData.getmsgInterval());
				jsonObject.put("Interval", warnData.getInterval());
				jsonObject.put("warningType", warnData.getwarningtype());
				jsonObject.put("WText", warnData.getWtext());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}

}
