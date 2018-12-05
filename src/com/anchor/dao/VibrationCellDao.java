package com.anchor.dao;

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;

/**
 * 
 * @项目名称：BDSIMP 
 * @类名称：VibrationCellDao 
 * @类描述：
 * @作者：张庭伟         @创建时间：2018年9月2日下午6:03:02 
 * @修改人：                 @修改时间：
 * @备注： 
 * @version
 */
public class VibrationCellDao {

DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	
	/**
	 * 通过项目ID查找所属站点
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> stationAll(String param[]){
		String sql = "SELECT nSensorID,sSiteName FROM base_sensor_info t1 WHERE t1.nProjectID = ? and sSensorType= ?";
		List<Map<String, Object>> list = dbUtils.find(sql,param);
		return list;
	}
	
	/**
	 * 数据初始化
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> initializeData(String params[]){
		String sql = "SELECT * from data_cableforcemeter WHERE create_time BETWEEN ? AND ? AND sensor_id = ?";
		List<Map<String, Object>> list = dbUtils.find(sql,params);
		return list;
	}
	
	/**
	 * 参考值
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> referenceData(String params){
		String sql = "SELECT dRef1,dRef2,dRef3 from base_sensor_ref WHERE nSensorID = ?";
		List<Map<String, Object>> list = dbUtils.find(sql,params);
		return list;
	}
}
