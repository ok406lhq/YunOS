package com.anchor.service;

import com.anchor.dao.DismeterRecordDao;
import com.anchor.dao.StrainGaugeDao;
import com.anchor.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StrainGaugeService {


    public JSONArray getStrainGaugeList(String nSensorID, String startTime, String endTime) {
        if (StringUtils.isNotBlank(nSensorID)) {
            StrainGaugeDao dao = new StrainGaugeDao();
            startTime = StringUtils.isNotBlank(startTime) ? startTime + " 00:00:00" : startTime;
            endTime = StringUtils.isNotBlank(endTime) ? endTime + " 23:59:59" : endTime;
            List<Map<String,Object>> list = dao.getStrainGaugeList(nSensorID, startTime, endTime);
            if (list != null && !list.isEmpty()) {
                JSONArray array = new JSONArray();
                for (Map<String, Object> map : list) {
                    JSONObject object = new JSONObject();
                    object.put("createTime",DateUtil.dateTypeToString((Date) map.get("createTime"),"yyyy-MM-dd HH:mm:ss"));
                    object.put("strain",map.get("strain"));
                    array.add(object);
                }
                return array;
            }
        }
        return null;
    }
}
