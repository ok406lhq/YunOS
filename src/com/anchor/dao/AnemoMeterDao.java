package com.anchor.dao;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class AnemoMeterDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String,Object>> getAnemoMeterList(String nSensorID, String startTime, String endTime) {
        String sql = "SELECT id,(speed - IFNULL((SELECT dRef1 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS speed," +
                "(anemo - IFNULL((SELECT dRef2 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS anemo,create_time as createTime" +
                " FROM data_anemometer WHERE sensor_id = "+nSensorID;
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
