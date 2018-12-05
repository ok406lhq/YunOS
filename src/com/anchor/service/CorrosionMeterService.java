package com.anchor.service;

import com.anchor.dao.CorrosionMeterDao;
import com.anchor.dao.ThmeterDao;
import com.anchor.util.DateUtil;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CorrosionMeterService {

    public JSONArray getThmeterList(String nSensorID, String startTime, String endTime) {
        if (StringUtils.isNotBlank(nSensorID)) {
            CorrosionMeterDao dao = new CorrosionMeterDao();
            startTime = StringUtils.isNotBlank(startTime) ? startTime + " 00:00:00" : startTime;
            endTime = StringUtils.isNotBlank(endTime) ? endTime + " 23:59:59" : endTime;
            List<Map<String,Object>> list = dao.getCorrosionMeterList(nSensorID, startTime, endTime);
            if (list != null && !list.isEmpty()) {
                JSONArray array = new JSONArray();
                for (Map<String, Object> map : list) {
                    net.sf.json.JSONObject object = new net.sf.json.JSONObject();
                    object.put("createTime",DateUtil.dateTypeToString((Date) map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));
                    object.put("corrosionRate",map.get("corrosionRate"));
                    object.put("corrosionCurrent",map.get("corrosionCurrent"));
                    array.add(object);
                }
                return array;
            }
        }
        return null;
    }
}
