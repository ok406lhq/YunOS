package com.anchor.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.RainResultDao;


public class RainResultService {

	RainResultDao rainResultDao = new RainResultDao(); 
	
	
	/**
	 *  站点全部
	 * @param param
	 * @return
	 */
	public JSONArray stationAll(String param[]){
		List<Map<String, Object>> list = rainResultDao.stationAll(param);
		JSONObject object = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			object = new JSONObject();
			object.put("nSensorID", map.get("nSensorID"));
			object.put("sSiteName", map.get("sSiteName"));
			array.add(object);
		}
		return array;
	}
	
	//查询项目信息
	public JSONArray initializeData(String sensorName,String starttime,String endtime,String ymw)
	{
		String json="";
		JSONObject object = null;
		List<String> dataTime = new ArrayList<>();
		List<Double> temp = null;
		List<List<Double>> dataSet = new ArrayList<>();
		List<Map<String, Object>> list = rainResultDao.queryRainResultJson(sensorName,starttime,endtime,ymw);
		JSONArray array = new JSONArray();
		
		for (Map<String, Object> map : list) {
			dataTime.add(map.get("dtCreateDT").toString());
			temp = new ArrayList<>();
			temp.add(Double.valueOf(map.get("dRealRain").toString()));
			dataSet.add(temp);
		}
		Map<String, Object> mrate = null;
		String format = "";
        switch (ymw) {
            case "hour":
            	format = ":00:00";
            	mrate = rainSum(dataSet, dataTime, 13, format);
                break;
            case "day":
            	format = " 00:00:00";
            	mrate = rainSum(dataSet, dataTime, 10, format);
                break;
            case "month":
            	format = "-00 00:00:00";
            	mrate = rainSum(dataSet, dataTime, 7, format);
                break;
            case "year":
            	format = "-00-00 00:00:00";
            	mrate = rainSum(dataSet, dataTime, 4, format);
                break;
        }
		
        @SuppressWarnings("unchecked")
		List<String> timeResult = (List<String>) mrate.get("timeResult");
        @SuppressWarnings("unchecked")
        List<List<Double>> dataResult = (List<List<Double>>) mrate.get("dataResult");
        
        for(int i=0; i<timeResult.size(); i++) {
        	object = new JSONObject();
		    object.put("dtCreateDT", timeResult.get(i));
		    object.put("dRealRain", dataResult.get(i).get(0));
		    System.out.println(dataResult.get(i).get(0));
		    object.put("nSensorID", list.get(0).get("nSensorID"));
		    array.add(object);
        }
		return array;
	}
	
	
	 /**
     * 雨量统计
     * @param dataSet 数据集
     * @param dataTS 时间
     * @param num 统计条件
     * @return
     */
    public static Map<String, Object> rainSum(List<List<Double>> dataSet, List<String> dataTS, int num, String format){
    	Map<String, Object> map = new HashMap<>();
        List<String> dataTime = new ArrayList<>();//得到统计集合
        for (String str : dataTS) {
            dataTime.add(str.substring(0, num));
        }
        List<String> timeResult = new ArrayList<String>();
        List<List<List<Double>>> dataTempResult = new ArrayList<List<List<Double>>>();
        String time = null;
        List<List<Double>> data = null;
        if(dataTime.get(dataTime.size()-1).equals(dataTime.get(0))) {
        	timeResult.add(dataTime.get(0) + format);
        	data = new ArrayList<List<Double>>();
        	for (List<Double> dset : dataSet) {
                data.add(dset);
            }
        	dataTempResult.add(data);
        }else {
        	for (int i = 0; i < dataTime.size(); i++) {
                data = new ArrayList<List<Double>>();
                time = dataTime.get(i) + format;
                data.add(dataSet.get(i));
                for (int j = i + 1; j < dataTime.size() + 1; j++) {
                    if (j < dataTime.size()) {
                        if (dataTime.get(i).equals(dataTime.get(j))) {
                            data.add(dataSet.get(j));
                        } else {
                            i = j - 1;
                            break;
                        }
                    } else {
                        if (dataTime.get(i - 1).equals(dataTime.get(i))) {
                            data.add(dataSet.get(i));
                        } else {
                            i = j - 1;
                            break;
                        }
                    }
                }
                dataTempResult.add(data);
                timeResult.add(time);
            }
        }
        
        //时间结果集
//		for (String list : timeResult) {
//			System.out.println(list);
//		}
        BigDecimal bg = null;
        List<Double> dataTemp = null;
        List<List<Double>> dataResult = new ArrayList<List<Double>>();
        for (int i = 0; i < dataTempResult.size(); i++) {
            List<Double> end = dataTempResult.get(i).get(dataTempResult.get(i).size() - 1);
            List<Double> start = dataTempResult.get(i).get(0);
            dataTemp = new ArrayList<Double>();
            for (int j = 0; j < start.size(); j++) {
            	if(j > 0) {
            		break;
            	}
                Double dataNum = end.get(j) - start.get(j);
                //System.out.println(end.get(j) +"-"+ start.get(j));
                bg = new BigDecimal(dataNum).setScale(2, BigDecimal.ROUND_HALF_UP);
                dataTemp.add(Math.abs(bg.doubleValue()));
            }
            dataResult.add(dataTemp);
        }
        map.put("timeResult", timeResult);
        map.put("dataResult", dataResult);
        return map;
    }
}
