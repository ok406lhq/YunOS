package com.anchor.service;

import com.anchor.dao.ThmeterDao;
import com.anchor.util.DateUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThmeterService {
	public JSONArray getThmeterList(String nSensorID, String startTime, String endTime) {
		if (StringUtils.isNotBlank(nSensorID)) {
			ThmeterDao dao = new ThmeterDao();
			startTime = StringUtils.isNotBlank(startTime) ? startTime + " 00:00:00" : startTime;
			endTime = StringUtils.isNotBlank(endTime) ? endTime + " 23:59:59" : endTime;
			List<Map<String,Object>> list = dao.getThmeterList(nSensorID, startTime, endTime);
			if (list != null && !list.isEmpty()) {
				JSONArray array = new JSONArray();
				for (Map<String, Object> map : list) {
					net.sf.json.JSONObject object = new net.sf.json.JSONObject();
					object.put("createTime",DateUtil.dateTypeToString((Date) map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));
					object.put("temp",map.get("temp"));
					object.put("hum",map.get("hum"));
					array.add(object);
				}
				return array;
			}
		}
		return null;
	}
}
