package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class RainStaticDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String, Object>> findSensorRainStaticData(String sensorId, String startTime, String endTime){
        List<Map<String, Object>> projectList = dbUtils.find("SELECT nSensorID,dRealRain,dtCreateDT FROM sensor_raingauge_record WHERE nSensorID=? AND  dtCreateDT > ? AND dtCreateDT < ? ORDER  BY dtCreateDT ASC", new String[]{sensorId, startTime, endTime});
        return projectList;
    }

}
