package com.anchor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.anchor.dao.LoadCellDao;
import com.anchor.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @项目名称：BDSIMP 
 * @类名称：LoadCellService 
 * @类描述：
 * @作者：张庭伟         @创建时间：2018年9月3日下午4:19:52 
 * @修改人：                 @修改时间：
 * @备注： 
 * @version
 */
public class LoadCellService {

	LoadCellDao dao = new LoadCellDao();
	
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
			double car_weight = Double.parseDouble(String.valueOf(map.get("car_weight"))) - ref;
			double val =new BigDecimal(car_weight).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_1 = Double.parseDouble(String.valueOf(map.get("axis_weight_1"))) - ref2;
			double val1 =new BigDecimal(axis_weight_1).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_2 = Double.parseDouble(String.valueOf(map.get("axis_weight_2"))) - ref3;
			double val2 =new BigDecimal(axis_weight_2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_3 = Double.parseDouble(String.valueOf(map.get("axis_weight_3"))) - ref3;
			double val3 =new BigDecimal(axis_weight_3).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_4 = Double.parseDouble(String.valueOf(map.get("axis_weight_4"))) - ref3;
			double val4 =new BigDecimal(axis_weight_4).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_5 = Double.parseDouble(String.valueOf(map.get("axis_weight_5"))) - ref3;
			double val5 =new BigDecimal(axis_weight_5).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_6 = Double.parseDouble(String.valueOf(map.get("axis_weight_6"))) - ref3;
			double val6 =new BigDecimal(axis_weight_6).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_7 = Double.parseDouble(String.valueOf(map.get("axis_weight_7"))) - ref3;
			double val7 =new BigDecimal(axis_weight_7).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_8 = Double.parseDouble(String.valueOf(map.get("axis_weight_8"))) - ref3;
			double val8 =new BigDecimal(axis_weight_8).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_9 = Double.parseDouble(String.valueOf(map.get("axis_weight_9"))) - ref3;
			double val9 =new BigDecimal(axis_weight_9).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			double axis_weight_10 = Double.parseDouble(String.valueOf(map.get("axis_weight_10"))) - ref3;
			double val10 =new BigDecimal(axis_weight_10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			
			object.put("car_weight", val);
			object.put("axis_weight_1", val1);
			object.put("axis_weight_2", val2);
			object.put("axis_weight_3", val3);
			object.put("axis_weight_4", val4);
			object.put("axis_weight_5", val5);
			object.put("axis_weight_6", val6);
			object.put("axis_weight_7", val7);
			object.put("axis_weight_8", val8);
			object.put("axis_weight_9", val9);
			object.put("axis_weight_10", val10);

			object.put("dtCreateDT", DateUtil.getPlusTime(map.get("create_time")));
			array.add(object);
		}
		return array;
	}
}
