package com.anchor.dao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.anchor.model.GNSSResult;
import com.anchor.model.IPIResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;
import com.mysql.jdbc.Statement;

public class IPIResultDao {

	public IPIResultDao() {
		super();
	}

	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;

	/**
	 * 查询项目信息--用list 参数 ymw, 查询数据内容和格式--小时，天，周？
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<IPIResult> queryIPIResultList(Connection conn, String prjName, List<PointInfo> ptInfos,
			String starttime, String endtime, String ymw,String sensorName) throws Exception {
//		starttime = starttime + " 00:00:00";
//		endtime = endtime + " 23:59:59";
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		List<IPIResult> ipiResultList = new ArrayList<IPIResult>();
		String url = Connest.URL_IPI_QueryIPIResultList;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sensor = URLEncoder.encode(sensorName,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		String y = URLEncoder.encode(ymw,"utf-8");
		url = String.format(url, name,sensor,sTime,eTime,y,"0");
		System.out.println("url:"+url);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i = 0;i<=arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			IPIResult ipiResult = new IPIResult();

			ipiResult.setsDatetime(objItem.getString("sDatetime"));
			ipiResult.setdisX(Double.parseDouble(objItem.getString("disX")));
			ipiResult.setdisY(Double.parseDouble(objItem.getString("disY")));
			ipiResultList.add(ipiResult);
		}
//		for (int i = 0; i < ptInfos.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
//			PointInfo ptInfo = ptInfos.get(i);
//
//			String sql = "select sdatetime, disx, disy from " + ptInfo.getStaName() + "_" + ptInfo.getSensorName();
//			if (!starttime.isEmpty() && !endtime.isEmpty())
//				sql += "  where sdateTime>\"" + starttime + "\" and sdateTime<\"" + endtime + "\"";
//			sql += "  order by sdatetime asc";
//
//			//
//			m_pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			m_rset = m_pstmt.executeQuery();
//			int rowCount = 0;
//
//			try {
//				m_rset.last();
//				rowCount = m_rset.getRow();
//
//				int step = rowCount / 5;
//				int resIdx = 0;
//				//for (int j = 1; j <= rowCount; j += step) {
//				for (int j = 0; j < 5; j ++) {
//					m_rset.absolute(j*step+1);
//					int col = 1;
//					IPIResult ipiResult = null;
//					if (i == 0) {
//						ipiResult = new IPIResult();
//						ipiResultList.add(ipiResult);
//					} else {
//						ipiResult = ipiResultList.get(resIdx);
//					}
//					ipiResult.setsDatetime(m_rset.getString(col));
//					ipiResult.setsensorname(ptInfo.getSensorName());
//					ipiResult.setsensorY(ptInfo.getup());
//					ipiResult.setdisX(ptInfo.getup(), m_rset.getDouble(++col));
//					ipiResult.setdisY(ptInfo.getup(), m_rset.getDouble(++col));
//					resIdx++;
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} // each sensor

		return ipiResultList;
	}
}
