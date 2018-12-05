package com.anchor.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.anchor.dao.UserDao;
import com.anchor.dao.UserInfoDao;
import com.anchor.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 项目名称：BDSIMP
 * 类描述：用户信息
 * 作者：张庭伟     时间：2018年6月29日下午7:49:52
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class UserService {

	UserDao dao = new UserDao();
	
	public JSONObject findUserObject(String param){
		List<Map<String, Object>> list = dao.findUserObject(param);
		JSONObject WSSItem = null;
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			WSSItem.put("nCompanyID", map.get("nCompanyID"));
			WSSItem.put("sUserName", map.get("sUserName"));
			WSSItem.put("sLoginAccount", map.get("sLoginAccount"));
			WSSItem.put("sPassWord", map.get("sPassWord"));
			WSSItem.put("sWXOpenid", map.get("sWXOpenid"));
			WSSItem.put("sWXNickname", map.get("sWXNickname"));
			WSSItem.put("sPartMent", map.get("sPartMent"));
			WSSItem.put("sPosition", map.get("sPosition"));
			WSSItem.put("sSex", map.get("sSex"));
			WSSItem.put("sAddress", map.get("sAddress"));
			WSSItem.put("sTel", map.get("sTel"));
			WSSItem.put("sEMail", map.get("sEMail"));
			WSSItem.put("sRoler", map.get("sRoler"));
			WSSItem.put("sProjectIDS", map.get("sProjectIDS"));
			WSSItem.put("nState", map.get("nState"));
			WSSItem.put("nLoginTimes", map.get("nLoginTimes"));
			WSSItem.put("sLastLoginIP", map.get("sLastLoginIP"));
			WSSItem.put("dtLastLoginDT", map.get("dtLastLoginDT"));
			WSSItem.put("dtCreateAt", map.get("dtCreateAt"));
			WSSItem.put("dtModifyAt", map.get("dtModifyAt"));
			WSSItem.put("nModifyBy", map.get("nModifyBy"));
		}
		return WSSItem;
	}
	
	public JSONObject updateUserInfo(User user){
		JSONObject WSSItem = null;
		UserInfoDao daoinfo = new UserInfoDao();
	
		int row = 0;
		try {
			row = daoinfo.UserInfoEdit(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(row>0){
			WSSItem = new JSONObject();
			WSSItem.put("msg", "success");
			return WSSItem;
		}
		return WSSItem;
	}
	
	
	public JSONArray findAllUserObject(String sProjectIDS){
		
		
		List<Map<String, Object>> list = dao.findAllUserObject();
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			if(sProjectIDS.indexOf((String)map.get("sProjectIDS")) > -1) {
				WSSItem.put("ID", map.get("ID"));
				WSSItem.put("sUserName", map.get("sUserName"));
				WSSItem.put("sProjectIDS", map.get("sProjectIDS"));
				array.add(WSSItem);
			}
		}
		return array;
	}
}
