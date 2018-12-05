package com.anchor.dao;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class ThmeterDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String,Object>> getThmeterList(String nSensorID, String startTime, String endTime) {
        String sql = "SELECT id,(temp - IFNULL((SELECT dRef1 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS temp," +
                "(hum - IFNULL((SELECT dRef2 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS hum,create_time as createTime" +
                " FROM data_thmeter WHERE sensor_id = "+nSensorID;
        if(StringUtils.isNotBlank(startTime)){
            sql += " AND create_time >= '"+startTime+"'";
        }
        if(StringUtils.isNotBlank(endTime)){
            sql += " AND create_time <= '"+endTime+"'";
        }
        sql += " order by create_time asc";
        System.out.println("sql="+sql);
        return dbUtils.find(sql);
    }
}
