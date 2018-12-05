package com.anchor.dao;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class CrackMeterDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String,Object>> getCrackMeterList(String nSensorID, String startTime, String endTime) {
        String sql = "SELECT id,(crack_dis - IFNULL((SELECT dRef1 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS crackDis,create_time as createTime" +
                " FROM data_crackmeter WHERE sensor_id = "+nSensorID;
        if(StringUtils.isNotBlank(startTime)){
            sql += " AND create_time >= '"+startTime+"'";
        }
        if(StringUtils.isNotBlank(endTime)){
            sql += " AND create_time <= '"+endTime+"'";
        }
        sql += " order by create_time asc";
        return dbUtils.find(sql);
    }
}
