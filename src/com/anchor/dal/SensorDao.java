package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class SensorDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String, Object>> findSensorByProjectId(String projectId){
        List<Map<String, Object>> projectList = dbUtils.find("select nSensorID, sSiteName, sSensorName, sSensorType, sSensorCompanyType, ISRUN, sStatusValue from base_sensor_info where nProjectID=? order by sSiteName asc", new String[]{projectId});
        return projectList;
    }
    public List<Map<String, Object>> findSensorByProjectIdAndType(String projectId, String sensorType){
        List<Map<String, Object>> projectList = dbUtils.find("select nSensorID, sSiteName, sSensorName, sSensorType, sSensorCompanyType, ISRUN, sStatusValue from base_sensor_info where nProjectID=? and sSensorType=? order by sSiteName asc", new String[]{projectId, sensorType});
        return projectList;
    }
    public List<Map<String, Object>> findSensorAndExtById(String sensorId){
        List<Map<String, Object>> sensorExt = dbUtils.find("SELECT s.nSiteID,s.nSensorID,r.dRef1,r.dRef2,r.dRef3,r.dtCreatDateTime FROM base_sensor_info s LEFT JOIN base_sensor_ref r ON r.nSensorID = s.nSensorID WHERE s.nSensorID=? ORDER BY r.dtCreatDateTime DESC", new String[]{sensorId});
        return sensorExt;
    }


}
