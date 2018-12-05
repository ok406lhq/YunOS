package com.anchor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.anchor.dao.TemperatureMeterDao;
import com.anchor.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @项目名称：BDSIMP 
 * @类名称：TemperatureMeterService 
 * @类描述：
 * @作者：张庭伟         @创建时间：2018年9月2日下午4:28:53 
 * @修改人：                 @修改时间：
 * @备注： 
 * @version
 */
public class TemperatureMeterService {

	TemperatureMeterDao dao = new TemperatureMeterDao();
	
	/**
	 *  站点全部
	 * @param param
	 * @return
	 */
	public JSONArray stationAll(String param[]){
		List<Map<String, Object>> list = dao.stationAll(param);
		JSONObject object = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			object = new JSONObject();
			object.put("nSensorID", map.get("nSensorID"));
			object.put("sSiteName", map.get("sSiteName"));
			array.add(object);
		}
		return array;
	}
	
	/**
	 *  初始化数据
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public JSONArray initializeData(String params[]) throws Exception {
		JSONObject object = null;
		JSONArray array = new JSONArray();
		JSONObject refObj = null;
		double ref = 0.0;
		double ref2 = 0.0;
		double ref3 = 0.0;
		List<Map<String, Object>> list = dao.initializeData(params);
		List<Map<String, Object>> refObjList = dao.referenceData(params[params.length-1]);
		for (Map<String, Object> map : refObjList) {
			refObj = new JSONObject();
			refObj.put("dRef1", map.get("dRef1"));
			refObj.put("dRef2", map.get("dRef2"));
			refObj.put("dRef3", map.get("dRef3"));
		}
		if(refObj != null) {
			ref = Double.parseDouble(String.valueOf(refObj.get("dRef1")));
			ref2 = Double.parseDouble(String.valueOf(refObj.get("dRef2")));
			ref3 = Double.parseDouble(String.valueOf(refObj.get("dRef3")));
		}
		for (Map<String, Object> map : list) {
			object = new JSONObject();
//			double dStrain = Double.parseDouble(String.valueOf(map.get("dStrain"))) - ref;
//			double val1 =new BigDecimal(dStrain).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//			double dPressure = Double.parseDouble(String.valueOf(map.get("dPressure"))) - ref2;
//			double val2 =new BigDecimal(dPressure).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			double displacement = Double.parseDouble(String.valueOf(map.get("temp"))) - ref3;
			double val3 =new BigDecimal(displacement).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//			object.put("dStrain", val1);
//			object.put("dPressure", val2);
			object.put("displacement", val3);
//			object.put("dTemp", map.get("dTemp"));
			object.put("dtCreateDT", DateUtil.getPlusTime(map.get("create_time")));
			array.add(object);
		}
		return array;
	}
}
