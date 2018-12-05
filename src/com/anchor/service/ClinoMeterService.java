package com.anchor.service;

import com.anchor.dao.ClinoMeterDao;
import com.anchor.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ClinoMeterService {

    public JSONArray getTiltmeteRecordList(String nSensorID, String startTime, String endTime) {
        if (StringUtils.isNotBlank(nSensorID)) {
            ClinoMeterDao dao = new ClinoMeterDao();
            startTime = StringUtils.isNotBlank(startTime) ? startTime + " 00:00:00" : startTime;
            endTime = StringUtils.isNotBlank(endTime) ? endTime + " 23:59:59" : endTime;
            List<Map<String,Object>> list = dao.getTiltmeteList(nSensorID, startTime, endTime);
            if (list != null && !list.isEmpty()) {
                JSONArray array = new JSONArray();
                for (Map<String, Object> map : list) {
                    JSONObject object = new JSONObject();
                    object.put("createTime",DateUtil.dateTypeToString((Date) map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));
                    object.put("x",new BigDecimal(String.valueOf(map.get("x"))).setScale(3,BigDecimal.ROUND_DOWN));
                    object.put("y",new BigDecimal(String.valueOf(map.get("y"))).setScale(3,BigDecimal.ROUND_DOWN));
                    array.add(object);
                }
                return array;
            }
        }
        return null;
    }
}
