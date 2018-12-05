package com.anchor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.anchor.dao.LogoDao;
import com.anchor.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @项目名称：BDSIMP 
 * @类名称：LogoService 
 * @类描述：
 * @作者：张庭伟         @创建时间：2018年9月11日下午4:50:13 
 * @修改人：                 @修改时间：
 * @备注： 
 * @version
 */
public class LogoService {

	LogoDao dao = new LogoDao();
	
	public JSONArray initializeData(String param) throws Exception {
		JSONObject object = null;
		JSONArray array = new JSONArray();
		List<Map<String, Object>> list = dao.initializeData(param);
		for (Map<String, Object> map : list) {
			object = new JSONObject();
			object.put("iLoginLogo", map.get("iLoginLogo"));
			object.put("sWebName", map.get("sWebName"));
			array.add(object);
		}
		return array;
	}
}
