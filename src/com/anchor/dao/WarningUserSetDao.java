/**
 * 
 */
package com.anchor.dao;

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.WarningStrategy;

import net.sf.json.JSONObject;

/** 
 * 项目名称：BDSIMP
 * 类描述：接警人设置
 * 作者：张庭伟     时间：2018年6月28日上午9:40:26
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class WarningUserSetDao {

	WarningStrategy ws = null;
	private DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	
	/**
	 * 分页查询数据
	 * @return
	 */
	public List<Map<String, Object>> findByPageWarningUserSetAll(String sProjectIDS,String proid,Integer page,Integer pageSize){
		Object object[] = {proid,page,pageSize};
		StringBuffer buffer = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("t1.nProjectID="+spid[i]);
		}
		
		String sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE ("+buffer.toString()+") and t1.nProjectID = ? order by t1.dtCreateAt desc LIMIT ?,?";
		List<Map<String, Object>> list = null;
		if(proid.equals("All")){
			Object object2[] = {page,pageSize};
			sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE ("+buffer.toString()+") order by t1.dtCreateAt desc LIMIT ?,?";
			list = dbUtils.find(sql,object2);
		}else {
			list = dbUtils.find(sql,object);
		}
		return list;
	}
	
	/**
	 * 总记录
	 * @return
	 */
	public List<Map<String, Object>> findByPageWarningUserSetAll(String ProjectIDS,String proid){
		StringBuffer buffer = new StringBuffer();
		String spid[] = ProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("t1.nProjectID="+spid[i]);
		}
		
		String sql = "SELECT count(t1.ID) as 'total'  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE ("+buffer.toString()+") and t1.nProjectID = ?";
		List<Map<String, Object>> list = null;

		if(proid.equals("All")){
			sql = "SELECT count(t1.ID) as 'total'  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE ("+buffer.toString()+") ";
			list = dbUtils.find(sql);
		}else {
			list = dbUtils.find(sql,proid);
		}
		return list;
	}
	
	/**
	 * 结构物分组查询
	 * @return
	 */
	public List<Map<String, Object>> findByProjectWarningUserSetAll(){
		String sql = "SELECT t1.ID,t1.nProjectType,t1.sProjectAbbr FROM base_project_info t1";
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	
	/**
	 * 监测点分组查询
	 * @return
	 */
	public List<Map<String, Object>> findBySensorWarningUserSetAll(){
		String sql = "SELECT t1.nSensorID,t1.sSiteName,t1.sSensorName,t1.nProjectID FROM base_sensor_info t1";
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	
	/**
	 *   通过项目ID和传感器类型查询站点
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findBySensorWarningUserSetAll(String params[]){
		String sql = "SELECT t1.nSensorID,t1.sSiteName,t1.sSensorName,t1.nProjectID FROM base_sensor_info t1 where nProjectID = ? and sSensorType = ?";
		List<Map<String, Object>> list = dbUtils.find(sql,params);
		return list;
	}
	
	/**
	 * 根据结构物或监测点查询数据
	 * @return
	 */
	public List<Map<String, Object>> findByProSenWarningUserSetAll(String proid,Integer page,Integer pageSize){
		Object object[] = {proid,page,pageSize};
		String sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE t1.nProjectID = ? order by dtCreateAt desc LIMIT ?,?";
		List<Map<String, Object>> list = null;
		if(proid.equals("All")){
			Object object2[] = {page,pageSize};
			sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID order by dtCreateAt desc LIMIT ?,?";
			list = dbUtils.find(sql,object2);
		}else {
			list = dbUtils.find(sql,object);
		}
		return list;
	}
	
	/**
	 * 根据结构物或监测点查询数据
	 * @return
	 */
	public JSONObject findByProSenWarningUserSetAllCount(String proid){
		String json = "";
		String sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID WHERE t1.nProjectID = ? order by dtCreateAt desc";
		List<Map<String, Object>> list = null;
		if(proid.equals("All")){
			sql = "SELECT t1.ID,t1.nUserID,t1.nProjectID,sAlertLevel,nMsgHourInterval,sSendChannel,dtLastWarningDT,t1.dtCreateAt,t1.dtModifyAt,nModyfyBy,t3.nProjectType,t3.sProjectAbbr,t4.sEMail,t4.sUserName,t4.sTel  FROM base_userwarning_set t1 INNER JOIN base_project_info t3 ON t1.nProjectID = t3.ID INNER JOIN base_user_info t4 ON t1.nUserID = t4.ID order by dtCreateAt desc";
			list = dbUtils.find(sql);
		}else {
			list = dbUtils.find(sql,proid);
		}
		JSONObject object = new JSONObject();
		object.put("total", list.size());
		return object;
	}
	
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<Map<String, Object>> findUserSetAll(){
		String sql = "SELECT t1.ID, t1.sUserName FROM base_user_info t1 order by dtCreateAt desc";
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<Map<String, Object>> findUserSetAll(String params[]){
		String sql = "SELECT t1.ID, t1.nUserID FROM base_userwarning_set t1 where nUserID = ? and nProjectID = ? order by dtCreateAt desc";
		List<Map<String, Object>> list = dbUtils.find(sql,params);
		return list;
	}
	
	
	/**
	 * 添加预警关联
	 * @param params
	 * @return
	 */
	public int addWarningUserSet(String[] params){
		String sql = "INSERT INTO base_userwarning_set(nUserID,nProjectID,sSendChannel,nMsgHourInterval,sAlertLevel,dtCreateAt) VALUES (?, ?, ?, ?, ?, ?)";
		int row = dbUtils.insert(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 编辑预警关联
	 * @param params
	 * @return
	 */
	public int updateWarningUserSet(String[] params){
		String sql = "UPDATE base_userwarning_set SET nUserID = ?,nProjectID = ?,sAlertLevel = ?,nMsgHourInterval = ?,sSendChannel = ?,dtModifyAt = ? WHERE ID = ?";
		int row = dbUtils.update(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 编辑预警关联
	 * @param params
	 * @return
	 */
	public int deleteByIDWarningUserSet(String params){
		String sql = "DELETE FROM base_userwarning_set WHERE ID = ?";
		int row = dbUtils.update(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
}
