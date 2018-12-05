package com.anchor.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anchor.db.DBUtilsTemplate;

public class AuxiliaryDao {
	private DBUtilsTemplate dbUtilsTemplate=new DBUtilsTemplate();
	
	/**
	 * 传感器数据监测页面查询预警阈值数据
	 * @param prjId
	 * @param sensorId
	 * @param sensorType
	 * @return
	 */
	public String queryThresholdForBD(String prjId,String sensorId,String sensorType){
		String jsonStr="";
		String sql="(select t.dTopLevelValue,t.dMediumLevelValue,t.dLowLevelValue,t.dMicroLevelValue from base_warning_sensor_strategy t where t.nSensorID=?) ";
//				+ "UNION "
//				+ "(select t.dTopLevelValue,t.dMediumLevelValue,t.dLowLevelValue,t.dMicroLevelValue from base_warning_sensor_strategy t where t.nSensorID='0' AND t.nProjectID=? and t.sSensorType=?)";
//		List<Map<String, Object>> resultList = dbUtilsTemplate.find(sql,new Object[]{sensorId,prjId,sensorType});
		List<Map<String, Object>> resultList = dbUtilsTemplate.find(sql,new Object[]{sensorId});
		List<Map<String, Object>> processedList=new ArrayList<Map<String, Object>>();
		if(resultList.size()>0){
			Map<String, Object> map = resultList.get(0);//只取第一个，传感器的阈值优先级别高于传感器类型阈值
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				String tempStr = map.get(key).toString();
				String[] split = tempStr.split(";");
				Map<String,Object> tempMap=new HashMap<String, Object>();
				for (int i = 0; i < split.length; i++) {
					tempMap.put("value"+(i+1), split[i]);
				}
				processedList.add(tempMap);
			}
			
		}
		jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(processedList,SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteMapNullValue);
		return jsonStr;
	}
}
