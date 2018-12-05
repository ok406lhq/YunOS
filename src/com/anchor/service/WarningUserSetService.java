/**
 * 
 */
package com.anchor.service;

import java.util.List;
import java.util.Map;

import com.anchor.dao.WarningUserSetDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 项目名称：BDSIMP
 * 类描述：预警人设置业务逻辑
 * 作者：张庭伟     时间：2018年6月28日上午9:43:41
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class WarningUserSetService {

	WarningUserSetDao dao = new WarningUserSetDao();
	
	/**
	 * 删除预警关联
	 * @param params
	 * @return
	 */
	public JSONObject deleteByIDWarningUserSet(String params){
		int row = dao.deleteByIDWarningUserSet(params);
		JSONObject object = new JSONObject();
		if(row>0){
			object.put("msg", "success");
		}else{
			object.put("msg", "error");
		}
		return object;
		
	}
	
	/**
	 * 编辑预警关联
	 * @param params
	 * @return
	 */
	public JSONObject updateWarningUserSet(String[] params){
		int row = dao.updateWarningUserSet(params);
		JSONObject object = new JSONObject();
		if(row>0){
			object.put("msg", "success");
		}else{
			object.put("msg", "error");
		}
		return object;
		
	}
	
	
	/**
	 * 添加预警关联
	 * @param params
	 * @return
	 */
	public JSONObject addWarningUserSet(String[] params){
		int row = dao.addWarningUserSet(params);
		JSONObject object = new JSONObject();
		if(row>0){
			object.put("msg", "success");
		}else{
			object.put("msg", "error");
		}
		return object;
		
	}
	
	
	/**
	 * 查找全部用户
	 * @return
	 */
	public JSONArray findUserSetAll(){
		List<Map<String, Object>> list = dao.findUserSetAll();
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("ID", map.get("ID"));
			PWUSItem.put("sUserName", map.get("sUserName"));
			array.add(PWUSItem);
		}
		return array;
	}
	
	
	
	/**
	 * 查询全部
	 * @return
	 */
	public JSONArray findByPageWarningUserSetAll(String sProjectIDS,String proid,Integer page,Integer pageSize){
		List<Map<String, Object>> list = dao.findByPageWarningUserSetAll(sProjectIDS,proid,page,pageSize);
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("ID", map.get("ID"));
			PWUSItem.put("nUserID", map.get("nUserID"));
			PWUSItem.put("nProjectID", map.get("nProjectID"));
			PWUSItem.put("sAlertLevel", map.get("sAlertLevel"));
			PWUSItem.put("nMsgHourInterval", map.get("nMsgHourInterval"));
			PWUSItem.put("sSendChannel", map.get("sSendChannel"));
			PWUSItem.put("dtLastWarningDT", map.get("dtLastWarningDT"));
			PWUSItem.put("dtCreateAt", map.get("dtCreateAt"));
			PWUSItem.put("dtModifyAt", map.get("dtModifyAt"));
			PWUSItem.put("IDnModyfyBy", map.get("nModyfyBy"));
			PWUSItem.put("nProjectType", map.get("nProjectType"));
			PWUSItem.put("sProjectAbbr", map.get("sProjectAbbr"));
			PWUSItem.put("sEMail", map.get("sEMail"));
			PWUSItem.put("sUserName", map.get("sUserName"));
			PWUSItem.put("sTel", map.get("sTel"));
			array.add(PWUSItem);
		}
		return array;
	}
	
	/**
	 * 总记录
	 * @return
	 */
	public JSONObject findByPageWarningUserSetAll(String ProjectIDS,String proid){
		List<Map<String, Object>> list = dao.findByPageWarningUserSetAll(ProjectIDS,proid);
		JSONObject PWUSItem = null;
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("total", map.get("total"));
		}
		return PWUSItem;
	}
	
	/**
	 * 结构物分组查询
	 * @return
	 */
	public JSONArray findByProjectWarningUserSetAll(String sProjectIDS){
		List<Map<String, Object>> list = dao.findByProjectWarningUserSetAll();
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			Object object = map.get("ID");
			String str = object+"";
			if(sProjectIDS.indexOf(str) > -1) {
				PWUSItem = new JSONObject();
				PWUSItem.put("ID", map.get("ID"));
				PWUSItem.put("nProjectType", map.get("nProjectType"));
				PWUSItem.put("sProjectAbbr", map.get("sProjectAbbr"));
				array.add(PWUSItem);
			}
		}
		return array;
	}
	
	
	/**
	 * 监测点分组查询
	 * @return
	 */
	public JSONArray findBySensorWarningUserSetAll(){
		List<Map<String, Object>> list = dao.findBySensorWarningUserSetAll();
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("nSensorID", map.get("nSensorID"));
			PWUSItem.put("sSiteName", map.get("sSiteName"));
			PWUSItem.put("sSensorName", map.get("sSensorName"));
			PWUSItem.put("nProjectID", map.get("nProjectID"));
			array.add(PWUSItem);
		}
		return array;
	}
	
	public JSONArray findBySensorWarningUserSetAll(String params[]){
		List<Map<String, Object>> list = dao.findBySensorWarningUserSetAll(params);
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("nSensorID", map.get("nSensorID"));
			PWUSItem.put("sSiteName", map.get("sSiteName"));
			PWUSItem.put("sSensorName", map.get("sSensorName"));
			PWUSItem.put("nProjectID", map.get("nProjectID"));
			array.add(PWUSItem);
		}
		return array;
	}
	
	/**
	 * 根据结构物或监测点查询数据
	 * @return
	 */
	public JSONArray findByProSenWarningUserSetAll(String proid,Integer page,Integer pageSize){
		List<Map<String, Object>> list = dao.findByProSenWarningUserSetAll(proid,page,pageSize);
		JSONObject PWUSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			PWUSItem = new JSONObject();
			PWUSItem.put("total", map.get("total"));
			PWUSItem.put("ID", map.get("ID"));
			PWUSItem.put("nUserID", map.get("nUserID"));
			PWUSItem.put("nProjectID", map.get("nProjectID"));
			PWUSItem.put("sAlertLevel", map.get("sAlertLevel"));
			PWUSItem.put("nMsgHourInterval", map.get("nMsgHourInterval"));
			PWUSItem.put("sSendChannel", map.get("sSendChannel"));
			PWUSItem.put("dtLastWarningDT", map.get("dtLastWarningDT"));
			PWUSItem.put("dtCreateAt", map.get("dtCreateAt"));
			PWUSItem.put("dtModifyAt", map.get("dtModifyAt"));
			PWUSItem.put("IDnModyfyBy", map.get("nModyfyBy"));
			PWUSItem.put("nSensorID", map.get("nSensorID"));
			PWUSItem.put("sSiteName", map.get("sSiteName"));
			PWUSItem.put("sSensorName", map.get("sSensorName"));
			PWUSItem.put("nProjectType", map.get("nProjectType"));
			PWUSItem.put("sProjectAbbr", map.get("sProjectAbbr"));
			PWUSItem.put("sEMail", map.get("sEMail"));
			PWUSItem.put("sUserName", map.get("sUserName"));
			PWUSItem.put("sTel", map.get("sTel"));
			array.add(PWUSItem);
		}
		return array;
	}
	
}
