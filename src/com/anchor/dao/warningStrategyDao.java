/**
 * 
 */
package com.anchor.dao;

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.WarningStrategy;

/** 
 * 项目名称：BDSIMP
 * 类描述：测点预警值
 * 作者：张庭伟     时间：2018年6月26日下午3:31:33
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class warningStrategyDao {
	
	WarningStrategy ws = null;
	private DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	
	/**
	 * 验证
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findWarningStrategyVerify(String []params){
		String sql = "select nProjectID from base_warning_sensor_strategy where nProjectID = ? AND nSensorID = ? AND sSensorType = ?";
		List<Map<String, Object>> list = dbUtils.find(sql,params);
		return list;
	}
	
	
	
	/**
	 * 根据用户查询结构物
	 * @param sProjectIDS
	 * @return
	 */
	public List<Map<String, Object>> findWarningStrategyPro(String sProjectIDS){
		StringBuffer buffer = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("t1.ID="+spid[i]);
		}
		String sql = "SELECT t1.ID,t1.sProjectAbbr,t1.nProjectType FROM base_project_info t1 WHERE ("+buffer.toString()+") GROUP BY t1.sProjectAbbr order by dtCreateAt desc";
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	
	
	/**
	 * 分页查询
	 * @return
	 */
	public List<Map<String, Object>> findWarningStrategyAll(String sProjectIDS,String page,String pageSize,String nProjectID) {
		
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		StringBuffer bufferLimit = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("t1.nProjectID="+spid[i]);
		}
		int page1 = Integer.parseInt(page);
		int pageSize1 = Integer.parseInt(pageSize);
		int page2 = (page1-1)*pageSize1;
		String sql = null;
		if(nProjectID.equals("All")) {
			bufferLimit.append(" limit "+page2+","+pageSize1);
			sql = "SELECT t1.ID,t1.nProjectID,nSensorID,sSensorType,sSensorTypeName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,iWarningInterval,dtCreateDT,dtModifyDT,t1.nModifyBy,t2.sProjectAbbr,t2.nProjectType FROM base_warning_sensor_strategy t1 INNER JOIN base_project_info t2 on t1.nProjectID = t2.ID WHERE nSensorID = 0 AND ("+buffer.toString()+") order by dtCreateDT desc "+bufferLimit.toString();
		}else {
			buffer2.append("AND t1.nProjectID="+nProjectID);
			bufferLimit.append(" LIMIT "+page2+","+pageSize1);
			sql = "SELECT t1.ID,t1.nProjectID,nSensorID,sSensorType,sSensorTypeName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,iWarningInterval,dtCreateDT,dtModifyDT,t1.nModifyBy,t2.sProjectAbbr,t2.nProjectType FROM base_warning_sensor_strategy t1 INNER JOIN base_project_info t2 on t1.nProjectID = t2.ID WHERE nSensorID = 0 AND ("+buffer.toString()+") "+buffer2.toString()+" order by dtCreateDT desc "+bufferLimit.toString();
		}
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	/**
	 * 统计数量
	 * @param sProjectIDS
	 * @param page
	 * @param pageSize
	 * @param nProjectID
	 * @return
	 */
    public List<Map<String, Object>> findWarningStrategyCount(String sProjectIDS,String page,String pageSize,String nProjectID) {
		
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer2 = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("t1.nProjectID="+spid[i]);
		}
		String sql = null;
		if(nProjectID.equals("All")) {
			sql = "SELECT count(t1.ID) as 'totalRecord' FROM base_warning_sensor_strategy t1 INNER JOIN base_project_info t2 on t1.nProjectID = t2.ID WHERE nSensorID = 0 AND ("+buffer.toString()+") order by dtCreateDT desc";
		}else {
			buffer2.append("AND t1.nProjectID="+nProjectID);
			sql = "SELECT count(t1.ID) as 'totalRecord' FROM base_warning_sensor_strategy t1 INNER JOIN base_project_info t2 on t1.nProjectID = t2.ID WHERE nSensorID = 0 AND ("+buffer.toString()+") "+buffer2.toString()+" order by dtCreateDT desc";
		}
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
	
	/**
	 * 通过ID修改一、二、三、四级预警阀值
	 * @return
	 */
	public int UpdateWarningStrategy(String params[]){
		String sql = "UPDATE base_warning_sensor_strategy SET dTopLevelValue = ?,dMediumLevelValue = ?,dLowLevelValue = ?,dMicroLevelValue = ?, dtModifyDT = ? WHERE ID = ? and sSensorType = ?";
		int row = dbUtils.update(sql, params);
		if(row>0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 通过ID修改一、二、三、四级预警阀值
	 * @return
	 */
	public int UpdateBatchWarningStrategy(String params[]){
		String sql = "UPDATE base_warning_sensor_strategy SET dTopLevelValue = ?,dMediumLevelValue = ?,dLowLevelValue = ?,dMicroLevelValue = ?, dtModifyDT = ? WHERE nProjectID = ? and sSensorType = ?";
		int row = dbUtils.update(sql, params);
		if(row>0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 根据项目ID和传感器类型查询子站点
	 * @return
	 */
	public List<Map<String, Object>> findByPidStypeStrategy(String[] params){
		String sql = "SELECT t1.ID,t1.nProjectID,t1.nSensorID,t1.sSensorType,t1.sSensorTypeName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,iWarningInterval,dtCreateDT,dtModifyDT,t1.nModifyBy,t2.sSiteName FROM base_warning_sensor_strategy t1 INNER JOIN base_sensor_info t2 on t1.nSensorID = t2.nSensorID WHERE t1.nProjectID = ? AND t1.sSensorType = ? AND t1.nSensorID <> ? order by dtCreateDT desc";
		List<Map<String, Object>> list = dbUtils.find(sql, params);
		return list;
	}
	
	/**
	 * 添加预警值
	 * @param params
	 * @return
	 */
	public int addWarningStrategy(String[] params){
		String sql = "INSERT INTO base_warning_sensor_strategy(nProjectID,nSensorID,sSensorType,sSensorTypeName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int row = dbUtils.insert(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
	
	
	/**
	 * 根据项目ID和传感器类型查询子站点
	 * @return
	 */
	public List<Map<String, Object>> findByProidSentypeStrategy(String[] params){
		String sql = "SELECT nProjectID,nSensorID,sSensorName,sSensorType FROM base_sensor_info WHERE nProjectID = ? AND sSensorType = ? order by dtCreateDT desc";
		List<Map<String, Object>> list = dbUtils.find(sql, params);
		return list;
	}
	
	
	/**
	 * 根据项目ID和传感器类型查询是否存在
	 * @return
	 */
	public List<Map<String, Object>> findByProidSentypeStrategyVali(String[] params){
		String sql = "SELECT ID FROM base_warning_sensor_strategy WHERE nProjectID = ? AND sSensorType = ? and nSensorID = ?";
		List<Map<String, Object>> list = dbUtils.find(sql, params);
		return list;
	}
	
	/**
	 * 删除父类以及下面的子类
	 * @param params
	 * @return
	 */
	public int deleteAllStrategy(String[] params){
		String sql = "DELETE FROM base_warning_sensor_strategy WHERE nProjectID = ? AND sSensorType = ?";
		int row = dbUtils.update(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
	/**
	 * 删除子类
	 * @param params
	 * @return
	 */
	public int deleteStrategy(String params){
		String sql = "DELETE FROM base_warning_sensor_strategy WHERE ID = ?";
		int row = dbUtils.update(sql, params);
		if(row > 0){
			return 1;
		}
		return 0;
	}
	
}
