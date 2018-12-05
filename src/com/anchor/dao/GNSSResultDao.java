package com.anchor.dao;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import net.sf.json.JSONArray;

import com.anchor.dal.GnssStaticDao;
import com.anchor.dal.ProjectDao;
import com.anchor.dal.SensorDao;
import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.HtmlUtil;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;

public class GNSSResultDao {

	public GNSSResultDao() {
		super();
	}
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	
	/**
	 * 查询数据--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	public List<GNSSResult> queryGNSSData(Connection conn, String prjName, 
			String sensorName) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		String sql = "select sdatetime, dnorth, deast, dup from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName()
					+ "order by sdatetime asc ";
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 1;
		List<GNSSResult> gnssResultList = new ArrayList<GNSSResult>();
		while (m_rset.next()) {
			GNSSResult gnssResult = new GNSSResult();
			gnssResult.setsDatetime(m_rset.getString(col));
			gnssResult.setdNorth(m_rset.getDouble(++col));
			gnssResult.setdEast(m_rset.getDouble(++col));
			gnssResult.setdUP(m_rset.getDouble(++col));
			gnssResult.setsigma(m_rset.getDouble(++col));
			col =1;
			gnssResultList.add(gnssResult);
		}
		return gnssResultList;
	}
	
	private ProjectDao projectDao = new ProjectDao();
	private SensorDao sensorDao = new SensorDao();
	private GnssStaticDao gnssStaticDao = new GnssStaticDao();
	/**
	 * 北斗数据初始化
	 * @param conn
	 * @param prjName
	 * @param sensorName
	 * @param starttime
	 * @param endtime
	 * @param ymw
	 * @param filter
	 * @return
	 */
	public List<GNSSResult> queryGNSSResultJson(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw, String filter){
		endtime = endtime+" 23:59:59";
		if(ymw.equals("all")) {
			starttime  = "2008-08-08 08:08:08";
		}
		List<GNSSResult> gnssResultList = new ArrayList<GNSSResult>();
		if (StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)){
			//获取参考值
			List<Map<String, Object>> sensorExt = sensorDao.findSensorAndExtById(sensorName);
			
			if (sensorExt != null && sensorExt.size() > 0){
				//GNSS数据
				List<Map<String, Object>> gnssStaticList = gnssStaticDao.findSensorGnssStaticData(sensorExt.get(0).get("nSiteID")+"", starttime, endtime);
				if (CollectionUtils.isNotEmpty(gnssStaticList)){
					//List<Map<String, Object>> listGnssResult = new ArrayList<>();
					
					for (int i = 0; i < gnssStaticList.size(); i++) {
						//Map<String, Object> gnssResult = new HashMap<>();
						GNSSResult gnssResult = new GNSSResult();
						try {
							//System.out.println(sensorExt+"------"+gnssStaticList.get(i).get("DateTime"));
							Map<String, Object> senserExtMap = getSensorExt(sensorExt, gnssStaticList.get(i).get("DateTime")+"");
							if(senserExtMap == null) {
								BigDecimal x = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("east")+""));
								BigDecimal y = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("north")+""));
								BigDecimal z = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("up")+""));
								Double s = Math.sqrt(Math.pow(x.doubleValue(), 2) + Math.pow(y.doubleValue(), 2));

								gnssResult.setsDatetime(gnssStaticList.get(i).get("DateTime")+"");
								gnssResult.setdNorth(Double.parseDouble(x.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdEast(Double.parseDouble(y.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdUP(Double.parseDouble(z.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setsigma(s);
								gnssResultList.add(gnssResult);
							}else {
								BigDecimal x = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("east")+"") - Double.parseDouble(senserExtMap.get("dRef1")+""));
								BigDecimal y = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("north")+"") - Double.parseDouble(senserExtMap.get("dRef2")+""));
								BigDecimal z = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("up")+"") - Double.parseDouble(senserExtMap.get("dRef3")+""));
								Double s = Math.sqrt(Math.pow(x.doubleValue(), 2) + Math.pow(y.doubleValue(), 2));

								gnssResult.setsDatetime(gnssStaticList.get(i).get("DateTime")+"");
								gnssResult.setdNorth(Double.parseDouble(x.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdEast(Double.parseDouble(y.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdUP(Double.parseDouble(z.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setsigma(s);
								gnssResultList.add(gnssResult);
							}
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			}else {
				//GNSS数据
				List<Map<String, Object>> gnssStaticList = gnssStaticDao.findSensorGnssStaticData(sensorExt.get(0).get("nSiteID")+"", starttime, endtime);
				if (CollectionUtils.isNotEmpty(gnssStaticList)){
					
					for (int i = 0; i < gnssStaticList.size(); i++) {
						//Map<String, Object> gnssResult = new HashMap<>();
						GNSSResult gnssResult = new GNSSResult();
						try {
							
								BigDecimal x = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("east")+""));
								BigDecimal y = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("north")+""));
								BigDecimal z = new BigDecimal(Double.parseDouble(gnssStaticList.get(i).get("up")+""));
								Double s = Math.sqrt(Math.pow(x.doubleValue(), 2) + Math.pow(y.doubleValue(), 2));

								gnssResult.setsDatetime(gnssStaticList.get(i).get("DateTime")+"");
								gnssResult.setdNorth(Double.parseDouble(x.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdEast(Double.parseDouble(y.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setdUP(Double.parseDouble(z.setScale(2, BigDecimal.ROUND_DOWN).toString()));
								gnssResult.setsigma(s);
								gnssResultList.add(gnssResult);
							
							
							
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			}
		}
		//测试数据
//		for (int i = 0; i < gnssResultList.size(); i++) {
//			System.out.println(gnssResultList.get(i).getdNorth()+"----"+gnssResultList.get(i).getdEast()+"----"+gnssResultList.get(i).getdUP()+"----"+gnssResultList.get(i).getSDatetime()+"----"+gnssResultList.get(i).getsigma());
//		}
		return gnssResultList;
	}
	
	//参考值取值比较
	public Map<String, Object> getSensorExt(List<Map<String, java.lang.Object>> sensorExt, String staticDate) throws ParseException {
		
		Map<String, Object> senserExtMap = sensorExt.get(0);
		if(senserExtMap != null) {
			if(senserExtMap.get("dtCreatDateTime") != null) {
				for (int i = 0; i < sensorExt.size(); i++)
				{
					if(
							DateUtils.parseDate(staticDate, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"}).getTime() <
									DateUtils.parseDate(senserExtMap.get("dtCreatDateTime")+"", new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"}).getTime()
							)
					{
						if (i < sensorExt.size()-1){
							senserExtMap = sensorExt.get(i+1);
						}
					}
				}
				return senserExtMap;
			}
		}
		
		return null;
	}

	/**
	 * 查询项目信息--用list
	 * 参数  ymw, 查询数据内容和格式--小时，天，周？
	 * @param conn
	 * @param filter 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param conn
	 * @param prjName
	 * @param sensorName
	 * @param starttime
	 * @param endtime
	 * @param ymw
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public List<GNSSResult> queryGNSSResultJson2(Connection conn, String prjName, 
			String sensorName,String starttime,String endtime,String ymw, String filter) throws Exception{
		PointInfoDao ptInfoDao = new PointInfoDao();
		PointInfo ptInfo = ptInfoDao.queryPtInfoBySensorName(conn, prjName, sensorName);
		if (null == ptInfo)
			return null;

		List<GNSSResult> gnssResultList = new ArrayList<GNSSResult>();
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String url = Connest.URL_GSNN_QueryGNSSResultJson;
		
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = URLEncoder.encode(ymw,"utf-8");
		String f = URLEncoder.encode(filter,"utf-8");
		url = String.format(url,name,sensor,sTime,eTime,y,f);
		System.out.println(url);
		
		String res=HttpClientUtil.doGet(url,"utf-8");
		
		res=URLDecoder.decode(res, "utf-8");
		System.out.println(res);
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			return gnssResultList;
//			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		
		for(int i = 0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			GNSSResult gnssResult = new GNSSResult();
			gnssResult.setsDatetime(objItem.getString("sDatetime"));
			gnssResult.setdNorth(Double.parseDouble(objItem.getString("dNorth")));
			gnssResult.setdEast(Double.parseDouble(objItem.getString("dEast")));
			gnssResult.setdUP(Double.parseDouble(objItem.getString("dUp")));
			gnssResult.setsigma(Double.parseDouble(objItem.getString("sigma")));
			gnssResultList.add(gnssResult);
		}
//		if (starttime != null)
//			starttime=starttime+ " 00:00:00";
//		else
//			starttime = "1980-02-02 22:22:22";
//		if (null != endtime)
//			endtime=endtime+" 23:59:59";
//		else
//			endtime = "2031-02-02 22:22:22";
//		String tableNameString="";
//		if (filter == null)
//			filter = "0";
//		if (ymw == null)
//			ymw = "";
//		if(filter.equals("1")){
//			tableNameString=ptInfo.getStaName() + "_" + ptInfo.getSensorName()+"filter1";
//		}else{
//			tableNameString=ptInfo.getStaName() + "_" + ptInfo.getSensorName();
//		}
//		String sql = "select sdatetime, dnorth - " + ptInfo.getref1() + " as north, deast - " + ptInfo.getref2() 
//					+ " as east, dup - " + ptInfo.getref3() + " as up , sigma from " + tableNameString;
//		if(ymw==""){
//			sql+= "  where sdateTime>\""+starttime+"\" and sdateTime<\""+endtime+"\" and mark < 10";
//		}else{
//			if(ymw.equals("day")){
//				sql+= "  where to_days(date_format(sdateTime,'%Y-%m-%d')) = to_days(now()) and mark < 10";
//			}
//			else if(ymw.equals("week")){
//				sql+= "  where YEARWEEK(date_format(sdateTime,'%Y-%m-%d')) = YEARWEEK(now()) and mark < 10";
//			}else if(ymw.equals("month")){
//				sql+= "  where date_format(sdateTime,'%Y%m') = date_format( CURDATE( ) , '%Y%m') and mark < 10";
//			}else if(ymw.equals("year")){
//				sql+= "  where YEAR(sdateTime) = YEAR(now()) and mark < 10";
//			}
//			
//		}
//		sql+="  order by sdatetime asc";
//	 
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		if (m_rset.wasNull())
//			return null;
//		int col = 1;
//		List<GNSSResult> gnssResultList = new ArrayList<GNSSResult>();
//		while (m_rset.next()) {
//			GNSSResult gnssResult = new GNSSResult();
//			gnssResult.setsDatetime(m_rset.getString(col));
//			gnssResult.setdNorth(m_rset.getDouble(++col));
//			gnssResult.setdEast(m_rset.getDouble(++col));
//			gnssResult.setdUP(m_rset.getDouble(++col));
//			gnssResult.setsigma(m_rset.getDouble(++col));
//			col =1;
//			gnssResultList.add(gnssResult);
//		}
//		//System.out.print(jsArray.toString());
		return gnssResultList;
	}
	
	/** 
     * 获取随机日期 
     * @param beginDate 起始日期，格式为：yyyy-MM-dd 
     * @param endDate 结束日期，格式为：yyyy-MM-dd 
     * @return 
     */  
    private static Date randomDate(String beginDate,String endDate){  
        try {  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
            Date start = format.parse(beginDate);  
            Date end = format.parse(endDate);  
              
            if(start.getTime() >= end.getTime()){  
                return null;  
            }  
              
            long date = random(start.getTime(),end.getTime());  
              
            return new Date(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    private static long random(long begin,long end){  
        long rtn = begin + (long)(Math.random() * (end - begin));  
        if(rtn == begin || rtn == end){  
            return random(begin,end);  
        }  
        return rtn;  
    } 
}
