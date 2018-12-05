package com.anchor.service;

import com.anchor.dao.InclinometerDao;
import com.anchor.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InclinometerRecordService {

    public JSONArray getInclinometerRecordList(String nSensorID, String startTime, String endTime) {
        if (StringUtils.isNotBlank(nSensorID)) {
            InclinometerDao dao = new InclinometerDao();
            startTime = StringUtils.isNotBlank(startTime) ? startTime + " 00:00:00" : startTime;
            endTime = StringUtils.isNotBlank(endTime) ? endTime + " 23:59:59" : endTime;
            List<Map<String,Object>> list = dao.getInclinometerRecordList(nSensorID, startTime, endTime);
            if (list != null && !list.isEmpty()) {
                JSONArray array = new JSONArray();
                for (Map<String, Object> map : list) {
                    JSONObject object = new JSONObject();
                    object.put("createTime",DateUtil.dateTypeToString((Date) map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));
                    object.put("disX",new BigDecimal(String.valueOf(map.get("disX"))).setScale(3,BigDecimal.ROUND_DOWN));
                    object.put("disY",new BigDecimal(String.valueOf(map.get("disY"))).setScale(3,BigDecimal.ROUND_DOWN));
                    array.add(object);
                }
                return array;
            }
        }
        return null;
    }
}
