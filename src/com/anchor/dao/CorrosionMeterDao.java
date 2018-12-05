package com.anchor.dao;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class CorrosionMeterDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String,Object>> getCorrosionMeterList(String nSensorID, String startTime, String endTime) {
        String sql = "SELECT id,(corrosion_rate - IFNULL((SELECT dRef1 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS corrosionRate," +
                "(corrosion_current - IFNULL((SELECT dRef2 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS corrosionCurrent,create_time as createTime" +
                " FROM data_corrosionmeter WHERE sensor_id = "+nSensorID;
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
