package com.anchor.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateParser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anchor.dal.GnssStaticDao;
import com.anchor.dal.SensorDao;
import com.anchor.dao.GNSSResultDao;
import com.anchor.model.GNSSResult;
import com.anchor.util.Calc;

public class GNSSResultService {
	private GnssStaticDao gnssStaticDao=new GnssStaticDao();
	private SensorDao sensorDao=new SensorDao();
	
	
	//查询北斗监测页面展示数据
	public String QueryGNSSResult(Connection con, String prjName, String sensorName,
			String starttime,String endtime,String ymw, String filter)
	{
		String json="";
		try{
			GNSSResultDao GNSSResultDao = new GNSSResultDao();     
			List<GNSSResult> results = GNSSResultDao.queryGNSSResultJson(con, prjName, sensorName,starttime,endtime,ymw,filter);
			//System.out.println(results.toString());
			com.alibaba.fastjson.JSONObject warpper=new com.alibaba.fastjson.JSONObject();
			
			if (results.size()>0)
			{
				NumberFormat nf = NumberFormat.getNumberInstance();  
	            nf.setMaximumFractionDigits(1);  
				List<Map<String, Object>> array=new ArrayList<Map<String, Object>>();
				for( int i = 0 ; i < results.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					GNSSResult prjInfoItem = results.get(i);
					Map<String, Object> mapOfColValues=new LinkedHashMap<>();
					BigDecimal dNorth=new BigDecimal(prjInfoItem.getdNorth()).setScale(2,BigDecimal.ROUND_DOWN);
					BigDecimal dEast=new BigDecimal(prjInfoItem.getdEast()).setScale(2,BigDecimal.ROUND_DOWN);
					BigDecimal dUP=new BigDecimal(prjInfoItem.getdUP()).setScale(2,BigDecimal.ROUND_DOWN);
					BigDecimal Sigma=new BigDecimal(prjInfoItem.getsigma()).setScale(2,BigDecimal.ROUND_DOWN);
					BigDecimal azimuth=new BigDecimal(Calc.azimuthAngle(0.00, 0.00, dNorth.doubleValue(), dEast.doubleValue())).setScale(2, BigDecimal.ROUND_DOWN);//计算方位角
					mapOfColValues.put(GNSSResult.RES_TIME, prjInfoItem.getSDatetime());
					mapOfColValues.put(GNSSResult.NORTH_DIS,dNorth);
					mapOfColValues.put(GNSSResult.EAST_DIS,dEast);
					mapOfColValues.put(GNSSResult.VERT_DIS,dUP);
					mapOfColValues.put(GNSSResult.SIGMA,Sigma);
					mapOfColValues.put("azimuth",azimuth);
					array.add(mapOfColValues);
				}
				List<Map<String, Object>> rateResults = getRateOfDisplacement(array);
				warpper.put("gnss",array);
				warpper.put("rate",rateResults);
				json =com.alibaba.fastjson.JSONObject.toJSONString(warpper,SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.WriteMapNullValue);
//				json = array.toString();
				//System.out.print(json);
			}else{
				return "[]";
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}finally{
		}
		return json;
	}
	
	
	//计算位移速率
	@SuppressWarnings("unused")
	private List<Map<String, Object>> getRateOfDisplacement(List<Map<String, Object>> array) throws ParseException{
		List<Map<String, Object>> tempList=new ArrayList<>();
		Map<String,Object> tempMap=new LinkedHashMap<>();
		int num=1;//时间相同的数据个数
		double x,y,z,xy;
		/* 第一天数据 */
		x=Double.parseDouble(array.get(0).get("dn").toString());
		y=Double.parseDouble(array.get(0).get("de").toString());
		z=Double.parseDouble(array.get(0).get("du").toString());
		xy=Double.parseDouble(array.get(0).get("Sigma").toString());
		
		
		//System.out.println(num+"---------"+array.get(0).get("dn").toString()+"=="+array.get(0).get("de").toString()+"=="+array.get(0).get("du").toString()+"=="+array.get(0).get("Sigma").toString());
		for (int i = 0; i < array.size(); i++) {
			if(i == 0) {
				x=Double.parseDouble(array.get(i).get("dn").toString());
				y=Double.parseDouble(array.get(i).get("de").toString());
				z=Double.parseDouble(array.get(i).get("du").toString());
				xy=Double.parseDouble(array.get(i).get("Sigma").toString());
			}
			//最后一天数据统计出来除以数量得到平均值
			if(i==array.size()-1){
				String strDate=array.get(i).get("st").toString().substring(0, 10)+" 00:00:00";
				tempMap.put("sDatetime", strDate);
//				tempMap.put("dNorth", x/num);
//				tempMap.put("dEast", y/num);
//				tempMap.put("dUp", z/num);
//				tempMap.put("sigma", xy/num);
				tempMap.put("dNorth",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("dn").toString())-x)).setScale(2, BigDecimal.ROUND_DOWN));				
				//System.out.println(Double.parseDouble(array.get(i-1).get("dn").toString())+"--------"+x);
				tempMap.put("dEast",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("de").toString())-y)).setScale(2, BigDecimal.ROUND_DOWN));
				tempMap.put("dUp",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("du").toString())-z)).setScale(2, BigDecimal.ROUND_DOWN));
				tempMap.put("sigma",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("Sigma").toString())-xy)).setScale(2, BigDecimal.ROUND_DOWN));
				tempList.add(tempMap);
				break;
			}
			
			
			String curDateStr=array.get(i).get("st").toString().substring(0, 10);//起始时间
			String nextDateStr=array.get(i+1).get("st").toString().substring(0, 10);//下一条时间
			
			if(curDateStr.equals(nextDateStr)){//时间相等，同一天
//				x+=Double.parseDouble(array.get(i+1).get("dn").toString());
//				y+=Double.parseDouble(array.get(i+1).get("de").toString());
//				z+=Double.parseDouble(array.get(i+1).get("du").toString());
//				xy+=Double.parseDouble(array.get(i+1).get("Sigma").toString());
				num++;
				//System.out.println(num+"---------"+array.get(i).get("dn").toString()+"=="+array.get(i).get("de").toString()+"=="+array.get(i).get("du").toString()+"=="+array.get(i).get("Sigma").toString());
			}else{//时间不相等
				
				String strDate=array.get(i).get("st").toString().substring(0, 10)+" 00:00:00";
				
				tempMap.put("sDatetime", strDate);
//				tempMap.put("dNorth", x/num);
//				tempMap.put("dEast", y/num);
//				tempMap.put("dUp", z/num);
//				tempMap.put("sigma", xy/num);
				
				
				tempMap.put("dNorth",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("dn").toString())-x)).setScale(2, BigDecimal.ROUND_DOWN));				
				//System.out.println(Double.parseDouble(array.get(i-1).get("dn").toString())+"--------"+x);
				tempMap.put("dEast",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("de").toString())-y)).setScale(2, BigDecimal.ROUND_DOWN));
				tempMap.put("dUp",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("du").toString())-z)).setScale(2, BigDecimal.ROUND_DOWN));
				tempMap.put("sigma",new BigDecimal(Math.abs(Double.parseDouble(array.get(i).get("Sigma").toString())-xy)).setScale(2, BigDecimal.ROUND_DOWN));
				
				
//				new BigDecimal(Math.abs(Double.parseDouble(array.get(i-1).get("dn").toString())-x)).setScale(2, BigDecimal.ROUND_DOWN);
//				new BigDecimal(Math.abs(Double.parseDouble(array.get(i-1).get("de").toString())-y)).setScale(2, BigDecimal.ROUND_DOWN);
//				new BigDecimal(Math.abs(Double.parseDouble(array.get(i-1).get("du").toString())-z)).setScale(2, BigDecimal.ROUND_DOWN);
//				new BigDecimal(Math.abs(Double.parseDouble(array.get(i-1).get("Sigma").toString())-xy)).setScale(2, BigDecimal.ROUND_DOWN);
				
				tempList.add(tempMap);
				
				//重置
				x=Double.parseDouble(array.get(i+1).get("dn").toString());
				y=Double.parseDouble(array.get(i+1).get("de").toString());
				z=Double.parseDouble(array.get(i+1).get("du").toString());
				xy=Double.parseDouble(array.get(i+1).get("Sigma").toString());
				num=1;
				tempMap=new LinkedHashMap<>();
			}
			
		}
		
		List<Map<String, Object>> rateList=new ArrayList<>();
		
//		for (int i = 0; i < tempList.size(); i++) {
//			System.out.println(tempList.get(i).get("dNorth"));
//			Map<String,Object> rateMap=new LinkedHashMap<>();
//			if(i==0){
//				rateMap.put("sDatetime", tempList.get(i).get("sDatetime"));
//				rateMap.put("dNorth", "0.00");
//				rateMap.put("dEast", "0.00");
//				rateMap.put("dUp", "0.00");
//				rateMap.put("sigma", "0.00");
//				rateList.add(rateMap);
//			}else{
//				rateMap.put("sDatetime", tempList.get(i).get("sDatetime"));
//				System.out.println((double)tempList.get(i).get("dNorth")+"--"+(double)tempList.get(i-1).get("dNorth"));
//				rateMap.put("dNorth",new BigDecimal(Math.abs((double)tempList.get(i).get("dNorth")-(double)tempList.get(i-1).get("dNorth"))).setScale(2, BigDecimal.ROUND_DOWN));
//				rateMap.put("dEast",new BigDecimal(Math.abs((double)tempList.get(i).get("dEast")-(double)tempList.get(i-1).get("dEast"))).setScale(2, BigDecimal.ROUND_DOWN));
//				rateMap.put("dUp",new BigDecimal(Math.abs((double)tempList.get(i).get("dUp")-(double)tempList.get(i-1).get("dUp"))).setScale(2, BigDecimal.ROUND_DOWN));
//				rateMap.put("sigma",new BigDecimal(Math.abs((double)tempList.get(i).get("sigma")-(double)tempList.get(i-1).get("sigma"))).setScale(2, BigDecimal.ROUND_DOWN));
//				rateList.add(rateMap);
//			}
//		}
		return tempList;
	}

}
