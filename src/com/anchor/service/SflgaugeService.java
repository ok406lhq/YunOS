package com.anchor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.anchor.dao.SflgaugeDao;
import com.anchor.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 项目名称：BDSIMP 类名称：sflgaugeService 
 * 类描述：静力水准仪
 * 作者：张庭伟         创建时间：2018年7月16日上午10:00:27 
 * 修改人：                 修改时间：
 * 备注： 
 * @version
 */
public class SflgaugeService {

	SflgaugeDao dao = new SflgaugeDao();
	
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
		}
		for (Map<String, Object> map : list) {
			object = new JSONObject();
			double dL = Double.parseDouble(String.valueOf(map.get("dL"))) - ref;
			double value =new BigDecimal(dL).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			object.put("dL", value);
			object.put("dtCreateDT", DateUtil.getPlusTime(map.get("dtCreateDT")));
			array.add(object);
		}
		return array;
	}
}
