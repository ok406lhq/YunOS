package com.anchor.dao;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;
import java.util.List;
import java.util.Map;

public class ClinoMeterDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String,Object>> getTiltmeteList(String nSensorID, String startTime, String endTime) {
        String sql = "SELECT ID,(x - IFNULL((SELECT dRef1 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS x," +
                "(y - IFNULL((SELECT dRef2 FROM base_sensor_ref WHERE nSensorID = "+nSensorID+"),0)) AS y,create_time as createTime" +
                " FROM data_clinometer WHERE sensor_id = "+nSensorID;
        if(StringUtils.isNotBlank(startTime)){
            sql += " AND create_time >= '"+startTime+"'";
        }
        if(StringUtils.isNotBlank(endTime)){
            sql += " AND create_time <= '"+endTime+"'";
        }
        sql += " order by sensor_id asc";
        System.out.println("sql="+sql);
        return dbUtils.find(sql);
    }
}
