package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class GnssStaticDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String, Object>> findSensorGnssStaticData(String siteId, String startTime, String endTime){
        List<Map<String, Object>> projectList = dbUtils.find("SELECT DateTime,X as north,Y as east, Z as up FROM sensor_gnss_static_result0 WHERE SITEID=? AND DateTime>=? AND DateTime<=?  order by DateTime asc", new String[]{siteId, startTime, endTime});
        return projectList;
    }

}
