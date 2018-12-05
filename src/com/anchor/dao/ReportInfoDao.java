package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.model.ReportInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;

public class ReportInfoDao {
	private DBUtilsTemplate dbUtilsTemplate=new DBUtilsTemplate();
	public ReportInfoDao() {
		super();
	}
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	
	/**
	 * 查询数据--用list
	 * @param conn
	 * @param datadesc 
	 * @param showalertreport 
	 * @param reportform 
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public List<ReportInfo> queryReports(Connection conn, String prjName,
						String startTime, String endTime,String sReportType) throws Exception{
		//rqliao todo...请根据查询参数调整sql语句，如果参数有值，则需要加上where条件
		//String sql = "select id,sName,prjname,sauthor,sdatetime,sstarttime,sendtime,urladdr,readtimes from reports rpt1 where 1=1 ";
		List<ReportInfo> reportsList = new ArrayList<ReportInfo>();
		startTime=startTime.replaceAll("-", "");
		endTime=endTime.replaceAll("-", "");
		String url = Connest.URL_REPORT_QueryReports;
		String name = URLEncoder.encode(prjName,"utf-8");
		String sTime = URLEncoder.encode(startTime,"utf-8");
		String eTime = URLEncoder.encode(endTime,"utf-8");
		String type=URLEncoder.encode(sReportType,"utf-8");
		url = String.format(url,name,sTime,eTime,type);
		System.out.println("url:"+url);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if("04".equals(resCode)){
			return reportsList;
		}else if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			ReportInfo rpInfo = new ReportInfo();
			rpInfo.setID(objItem.getInt("ID"));
			rpInfo.setsname(objItem.getString("sReportName"));
			rpInfo.setsprjName(objItem.getString("nProjectID"));
			rpInfo.setsauthor(objItem.getString("nAuthorID"));
			rpInfo.setscreatetime(objItem.getString("dtCreatAt"));
			rpInfo.setsstarttime(objItem.getString("dtCreatAt"));
			rpInfo.setsendtime(objItem.getString("dtProcessAt"));
			rpInfo.seturlAddr(objItem.getString("sURLAddress"));
			rpInfo.setreadTimes(Integer.parseInt(objItem.getString("nReadTimes")));
			rpInfo.setsReportType(objItem.getString("sReportType"));
			reportsList.add(rpInfo);
		}
//		String sql = "select t1.id,t1.sName,t2.PrjAlias,t1.sauthor,t1.sdatetime,t1.sstarttime,t1.sendtime,t1.urladdr,t1.readtimes from reports t1, prjinfo t2 where t1.prjname = t2.prjname ";
//		sql+=" and t1.sdatetime>='"+startTime+"' and t1.sdatetime<='"+endTime+"'";
//		if (prjName != null)
//		{
//			if(!prjName.equals("")){
//				sql+=" and t1.prjname='"+prjName+"'";
//			}
//		}
//		if(!reportform.equals("")){
//			sql+=" and t1.sName like '%"+reportform+"%'";
//			sql+=" and  t1.sName like ?";
//		}
//		
//		
//		
//		if(!datadesc.equals("0")){
//			sql+=" order by t1.sdatetime desc";
//		}
//		//sql="select id,sName,prjname,sauthor,sdatetime,sstarttime,sendtime,urladdr,readtimes from reports where sName like ?";
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		
//		if (null != reportform)
//		{
//			if(!reportform.equals("")){
//				m_pstmt.setObject(1,"%"+reportform+"%");
//			}
//		}
//		//System.out.print(sql);
//		m_pstmt.execute();
//		// System.out.println(m_pstmt.toString());//打印sql
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<ReportInfo> reportsList = new ArrayList<ReportInfo>();
//		while (m_rset.next()) {
//			int sID = m_rset.getInt(col);
//			String sName = m_rset.getString(++col);
//			if (bAAAOnly)
//			{
//				String subStr = sName.substring(0, 3);
//				if (! sName.substring(0, 3).equals("AAA"))
//				{
//					col =1;
//					continue;
//				}
//			}
//			ReportInfo rpInfo = new ReportInfo();
//			rpInfo.setID(sID);
//			rpInfo.setsname(sName);
//			rpInfo.setsprjName(m_rset.getString(++col));
//			rpInfo.setsauthor(m_rset.getString(++col));
//			rpInfo.setscreatetime(m_rset.getString(++col));
//			rpInfo.setsstarttime(m_rset.getString(++col));
//			rpInfo.setsendtime(m_rset.getString(++col));
//			rpInfo.seturlAddr(m_rset.getString(++col));
//			rpInfo.setreadTimes(m_rset.getInt(++col));
//			col =1;
//			reportsList.add(rpInfo);
//		}
		return reportsList;
	}

	public int AddReportViewNums(Connection con, String id) throws Exception {
		// TODO Auto-generated method stub
		String url = Connest.URL_REPORT_UpdateReadTimes;
		String reportId = URLEncoder.encode(id,"utf-8");
		url = String.format(url,reportId);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(resCode.equals("00")){
			return 1;
		}
		return -1;
	}
	
	/**
	 * 结构信息页面的日常报表相关查询 日、周、月、年
	 * @param prjIds 结构名称，逗号分隔
	 * @return
	 */
	public String queryReportsForPrjlist(String prjIds,String reportType){
		String jsonStr="";
		String sql="SELECT * FROM warning_report_info WHERE nProjectID IN(?) and sReportType LIKE ? order by dtCreatAt desc LIMIT 3";
		List<Map<String, Object>> resultList = dbUtilsTemplate.find(sql,new Object[]{prjIds,"%"+reportType+"%"});
		if(resultList.size()>=0){
			jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(resultList,SerializerFeature.WriteDateUseDateFormat,
	                SerializerFeature.WriteMapNullValue);
		}
		return jsonStr;
	}
}
