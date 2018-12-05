/**
 * 
 */
package com.anchor.service;

import java.util.List;
import java.util.Map;

import com.anchor.dao.warningStrategyDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 项目名称：BDSIMP
 * 类描述：测点预警值业务逻辑
 * 作者：张庭伟     时间：2018年6月26日下午4:05:44
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class WarningStrategyService {

	warningStrategyDao dao = new warningStrategyDao();
	
	
	public int findWarningStrategyVerify(String []params){
		List<Map<String, Object>> list = dao.findWarningStrategyVerify(params);
		if(list.size()>0) {
			return 1;
		}
		return 0;
	}
	
	
	public JSONArray findWarningStrategyPro(String sProjectIDS){
		List<Map<String, Object>> list = dao.findWarningStrategyPro(sProjectIDS);
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			//WSSItem.put("nProjectID", map.get("nProjectID"));
			WSSItem.put("sProjectAbbr", map.get("sProjectAbbr"));
			WSSItem.put("nProjectType", map.get("nProjectType"));
			array.add(WSSItem);
		}
		return array;
	}
	
	
	/**
	 * 查询全部
	 * @return
	 */
	public JSONArray findWarningStrategyAll(String sProjectIDS,String page,String pageSize,String nProjectID){
		List<Map<String, Object>> list = dao.findWarningStrategyAll(sProjectIDS,page,pageSize,nProjectID);
		String json = "";
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			WSSItem.put("nProjectID", map.get("nProjectID"));
			WSSItem.put("nSensorID", map.get("nSensorID"));
			WSSItem.put("sSensorType", map.get("sSensorType"));
			WSSItem.put("sSensorTypeName", map.get("sSensorTypeName"));
			WSSItem.put("dTopLevelValue", map.get("dTopLevelValue"));
			WSSItem.put("dMediumLevelValue", map.get("dMediumLevelValue"));
			WSSItem.put("dLowLevelValue", map.get("dLowLevelValue"));
			WSSItem.put("dMicroLevelValue", map.get("dMicroLevelValue"));
			WSSItem.put("iWarningInterval", map.get("iWarningInterval"));
			WSSItem.put("dtCreateDT", map.get("dtCreateDT"));
			WSSItem.put("dtModifyDT", map.get("dtModifyDT"));
			WSSItem.put("nModifyBy", map.get("nModifyBy"));
			WSSItem.put("sProjectAbbr", map.get("sProjectAbbr"));
			WSSItem.put("nProjectType", map.get("nProjectType"));
			array.add(WSSItem);
		}
		return array;
	}
	
	
	public JSONArray findWarningStrategyCount(String sProjectIDS,String page,String pageSize,String nProjectID){
		List<Map<String, Object>> list = dao.findWarningStrategyCount(sProjectIDS,page,pageSize,nProjectID);
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("totalRecord", map.get("totalRecord"));
			array.add(WSSItem);
		}
		return array;
	}
	/**
	 * 通过ID修改一、二、三、四级预警阀值
	 * @return
	 */
	public JSONObject UpdateWarningStrategy(String params[]){
		JSONObject WSSItem = new JSONObject();
		int row = dao.UpdateWarningStrategy(params);
		if(row>0) {
			WSSItem.put("msg", "success");
		}else {
			WSSItem.put("msg", "error");
		}
		return WSSItem;
	}
	
	/**
	 * 通过ID修改一、二、三、四级预警阀值
	 * @return
	 */
	public JSONObject UpdateBatchWarningStrategy(String params[]){
		JSONObject WSSItem = new JSONObject();
		int row = dao.UpdateBatchWarningStrategy(params);
		if(row>0) {
			WSSItem.put("msg", "success");
		}else {
			WSSItem.put("msg", "error");
		}
		return WSSItem;
	}
	
	/**
	 * 根据项目ID和传感器类型查询子站点
	 * @return
	 */
	public JSONArray findByPidStypeStrategy(String[] params){
		List<Map<String, Object>> list = dao.findByPidStypeStrategy(params);
		String json = "";
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			WSSItem.put("nProjectID", map.get("nProjectID"));
			WSSItem.put("nSensorID", map.get("nSensorID"));
			WSSItem.put("sSensorType", map.get("sSensorType"));
			WSSItem.put("sSensorTypeName", map.get("sSensorTypeName"));
			WSSItem.put("dTopLevelValue", map.get("dTopLevelValue"));
			WSSItem.put("dMediumLevelValue", map.get("dMediumLevelValue"));
			WSSItem.put("dLowLevelValue", map.get("dLowLevelValue"));
			WSSItem.put("dMicroLevelValue", map.get("dMicroLevelValue"));
			WSSItem.put("iWarningInterval", map.get("iWarningInterval"));
			WSSItem.put("dtCreateDT", map.get("dtCreateDT"));
			WSSItem.put("dtModifyDT", map.get("dtModifyDT"));
			WSSItem.put("nModifyBy", map.get("nModifyBy"));
			WSSItem.put("sSiteName", map.get("sSiteName"));
			array.add(WSSItem);
		}
		return array;
	}
	
	/**
	 * 添加预警值
	 * @param params
	 * @return
	 */
	public JSONObject addWarningStrategy(String[] params, String[] params2){
		
		List<Map<String, Object>> list = dao.findWarningStrategyVerify(params2);
		JSONObject object = new JSONObject();
		if(list.size() > 0) {
			object.put("msg", "cunzai");
		}else {
			int row = dao.addWarningStrategy(params);
			if(row>0){
				object.put("msg", "success");
			}else{
				object.put("msg", "cunzai");
			}
		}
		return object;
	}
	
	
	/**
	 * 根据项目ID和传感器类型查询子站点
	 * @return
	 */
	public JSONArray findByProidSentypeStrategy(String[] params){
		List<Map<String, Object>> list = dao.findByProidSentypeStrategy(params);
		String json = "";
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("nProjectID", map.get("nProjectID"));
			WSSItem.put("nSensorID", map.get("nSensorID"));
			WSSItem.put("sSensorName", map.get("sSensorName"));
			WSSItem.put("sSensorType", map.get("sSensorType"));
			array.add(WSSItem);
		}
		return array;
	}
	
	/**
	 * 根据项目ID和传感器类型查询子站点
	 * @return
	 */
	public JSONArray findByProidSentypeStrategyVali(String[] params){
		List<Map<String, Object>> list = dao.findByProidSentypeStrategyVali(params);
		String json = "";
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			array.add(WSSItem);
		}
		return array;
	}
	
	/**
	 * 删除父类以及子类
	 * @param params
	 * @return
	 */
	public JSONObject deleteAllStrategy(String params[]){
		int row = dao.deleteAllStrategy(params);
		JSONObject object = new JSONObject();
		if(row>0){
			object.put("msg", "success");
		}else{
			object.put("msg", "error");
		}
		return object;
		
	}
	
	/**
	 * 删除子类
	 * @param params
	 * @return
	 */
	public JSONObject deleteStrategy(String params){
		int row = dao.deleteStrategy(params);
		JSONObject object = new JSONObject();
		if(row>0){
			object.put("msg", "success");
		}else{
			object.put("msg", "error");
		}
		return object;
		
	}
}
