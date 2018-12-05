package com.anchor.dao;
/**
 * 
 * 项目名称：BDSIMP 类名称：SflgaugeDao 
 * 类描述：静力水准仪Dao
 * 作者：张庭伟         创建时间：2018年7月11日下午4:39:07 
 * 修改人：                 修改时间：
 * 备注： 
 * @version
 */

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;

public class SflgaugeDao {

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
		String sql = "SELECT * from sensor_sflgauge_record WHERE dtCreateDT BETWEEN ? AND ? AND nSensorID = ?";
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
