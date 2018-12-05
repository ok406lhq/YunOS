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

import com.anchor.db.DBUtilsTemplate;
import org.json.JSONObject;

import net.sf.json.JSONArray;

import com.anchor.model.ReportSummary;
import com.anchor.model.WarningInfo;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;
//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class WarningInfoDao {
	

	public WarningInfoDao() {
		super();
	}
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	private DBUtilsTemplate dbUtils = new DBUtilsTemplate();



	public Map<String,Object> getOneNewestWarning(String nProjectID){
		String[] projects = nProjectID.split(",");
		StringBuilder sql =new StringBuilder("select a.* from warning_sensor_info a,base_project_info b,base_sensor_info c where" +
				" a.nProjectID=b.ID AND a.nSensorID=c.nSensorID and a.isConfirm ='0' and a.isHandle ='0' and isDialog ='0'" +
				" and a.iIsToWeb ='1' and a.nProjectID in(");
		for (int i = 0; i < projects.length; i++) {
			sql.append(projects[i]);
			if(i != projects.length-1){
				sql.append(",");
			}
		}
		sql.append(") order by a.dtCreateAt desc limit 1");
		return dbUtils.findFirst(sql.toString());
	}

	public Map<String,Object> getCountWarning(String nProjectID){
		String[] projects = nProjectID.split(",");
		StringBuilder sql =new StringBuilder("select count(*) as total from warning_sensor_info a,base_project_info b,base_sensor_info c where " +
				" a.nProjectID=b.ID and a.nSensorID=c.nSensorID and a.isConfirm ='0' and a.isHandle ='0' and isDialog ='0'" +
				" and a.iIsToWeb ='1' and a.nProjectID in(");
		for (int i = 0; i < projects.length; i++) {
			sql.append(projects[i]);
			if(i != projects.length-1){
				sql.append(",");
			}
		}
		sql.append(")");
		return dbUtils.findFirst(sql.toString());
	}

	public int updateIsDialogById(String ID){
		String sql = "update warning_sensor_info set isDialog = 1 where ID =?";
		return dbUtils.update(sql,ID);
	}
	public int updateIsDialogAll(String project) {
		String[] projects = project.split(",");
		StringBuilder sql = new StringBuilder("update warning_sensor_info a,base_project_info b,base_sensor_info c set isDialog = 1 where " +
				" a.nProjectID=b.ID AND a.nSensorID=c.nSensorID and a.isConfirm ='0' and a.isHandle ='0' and isDialog ='0'" +
				" and a.iIsToWeb ='1' and a.nProjectID in(");
		for (int i = 0; i < projects.length; i++) {
			sql.append(projects[i]);
			if(i != projects.length-1){
				sql.append(",");
			}
		}
		sql.append(")");
		return dbUtils.update(sql.toString());
	}

	public int updateDialogAll(String project) {
		String[] projects = project.split(",");
		StringBuilder sql = new StringBuilder("update warning_sensor_info a,base_project_info b,base_sensor_info c set isDialog = 0 where " +
				" a.nProjectID=b.ID AND a.nSensorID=c.nSensorID and a.isConfirm ='0' and a.isHandle ='0' and isDialog ='1'" +
				" and a.iIsToWeb ='1' and a.nProjectID in(");
		for (int i = 0; i < projects.length; i++) {
			sql.append(projects[i]);
			if(i != projects.length-1){
				sql.append(",");
			}
		}
		sql.append(")");
		return dbUtils.update(sql.toString());
	}
	DBUtilsTemplate dao = new DBUtilsTemplate();
	
	/**
	 * 查詢全部
	 * @param sProjectIDS
	 * @param project
	 * @param activeval
	 * @param starttime
	 * @param endtime
	 * @param structure_type
	 * @param structure_name
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> findAll(String sProjectIDS,String project,String activeval, String starttime, String endtime, String structure_type, String structure_name, String page, String pageSize){
		//String arr[] = {activeval,starttime,endtime,structure_type,structure_name,page,pageSize};
		StringBuffer buffer = new StringBuffer();
		StringBuffer bufferStr = new StringBuffer();
		StringBuffer bufferLimit = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("a.nProjectID="+spid[i]);
		}
		if(!activeval.equals("All")) {
			bufferStr.append(" AND a.sAlertLevel = '"+activeval+"'");
		}
		if(!structure_type.equals("All")) {
			bufferStr.append(" AND b.nProjectType = '"+structure_type+"'");
		}
		if(!structure_name.equals("All")) {
			bufferStr.append(" AND a.nProjectID = "+structure_name);
		}
		bufferStr.append(" AND a.dtCreateAt BETWEEN '"+starttime+" 00:00:01' AND '"+endtime+" 23:59:59' ");
		int fpage = (Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
		int epageSize = Integer.parseInt(pageSize);
		bufferLimit.append("LIMIT "+fpage+","+epageSize);
		String sql = "SELECT a.ID,a.dtCreateAt,c.sSiteName,sAlertLevel,b.sProjectName,b.sProjectAbbr,a.sAlertContent,a.isConfirm,a.isHandle,a.sProcessInfo FROM warning_sensor_info a,base_project_info b,base_sensor_info c WHERE a.iIsToWeb=1 AND a.nProjectID=b.ID AND a.nSensorID=c.nSensorID "+bufferStr.toString()+"AND ("+buffer.toString()+") ORDER BY a.dtCreateAt DESC "+bufferLimit.toString();
		List<Map<String, Object>> list2 = dao.find(sql);
		return list2;
	}
	/**
	 * 统计数量
	 * @param activeval
	 * @param starttime
	 * @param endtime
	 * @param structure_type
	 * @param structure_name
	 * @return
	 */
	public List<Map<String, Object>> findAllCount(String sProjectIDS,String project,String activeval, String starttime, String endtime, String structure_type, String structure_name){
		StringBuffer buffer = new StringBuffer();
		StringBuffer bufferStr = new StringBuffer();
		String spid[] = sProjectIDS.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("a.nProjectID="+spid[i]);
		}
		if(!activeval.equals("All")) {
			bufferStr.append(" AND a.sAlertLevel = '"+activeval+"'");
		}
		if(!structure_type.equals("All")) {
			bufferStr.append(" AND b.nProjectType = '"+structure_type+"'");
		}
		if(!structure_name.equals("All")) {
			bufferStr.append(" AND a.nProjectID = "+structure_name);
		}
		bufferStr.append(" AND a.dtCreateAt BETWEEN '"+starttime+" 00:00:01' AND '"+endtime+" 23:59:59' ");
		
		String sql = "SELECT count(a.ID) as 'totalRecord' FROM warning_sensor_info a,base_project_info b,base_sensor_info c WHERE a.iIsToWeb=1 AND a.nProjectID=b.ID AND a.nSensorID=c.nSensorID "+bufferStr.toString()+"AND ("+buffer.toString()+") ORDER BY a.ID DESC";
		List<Map<String, Object>> list2 = dao.find(sql);
		return list2;
	}
	
	/**
	 * 修改
	 * @param con
	 * @param id
	 * @param project
	 * @return
	 */
	public int UpdateWarningConfirmInfo(Connection con, String id){
		String url= Connest.URL_WARNING_UpdateWarningConfirmInfo;
		try {
			String d = URLEncoder.encode(id,"utf-8");
			url = String.format(url,d);
			String res=HttpClientUtil.doGet(url,"utf-8");
			res=URLDecoder.decode(res, "utf-8");
			org.json.JSONObject obj=new org.json.JSONObject(res);
			String resCode=obj.getString("code");
			if(!"00".equals(resCode)){
				throw new Exception(obj.getString("msg"));
			}
			return obj.getInt("data");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 查询项目信息--用list
	 * @param conn
	 * @param pageSize 
	 * @param page 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	public List<WarningInfo> queryLogListByActive(Connection conn, String active,String structure_type,String structure_name,String starttime,String endtime, int page, int pageSize,String project) throws Exception{
//		starttime=starttime+ " 00:00:00";
//		endtime=endtime+ " 23:59:59";

		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		if(pageSize==0){
			pageSize=15;
		}
		if(page==0){
			page=1;
		}
		int startPage=(page-1)*pageSize;
		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
		String url= Connest.URL_WARNING_QueryLogListByActive;
		String prj = URLEncoder.encode(project,"utf-8");
		String a = URLEncoder.encode(active,"utf-8");
		String stype = URLEncoder.encode(structure_type,"utf-8");
		String sname = URLEncoder.encode(structure_name,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		if(structure_type.equals("") && structure_name.equals("")){
			url= Connest.URL_WARNING_QueryLogListByActiveNull;
			url=String.format(url,a,sTime,eTime,page,pageSize,prj);
		}else if(structure_name.equals("")){
			url= Connest.URL_WARNING_QueryLogListByActiveType;
			url=String.format(url,a,stype,sTime,eTime,page,pageSize,prj);
		}else if(structure_type.equals("")){
			url= Connest.URL_WARNING_QueryLogListByActiveName;
			url=String.format(url,a,sname,sTime,eTime,page,pageSize,prj);
		}else{
			url=String.format(url,a,stype,sname,sTime,eTime,page,pageSize,prj);
		}
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		
		System.out.println("*************"+res);
		
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if("04".equals(resCode)){
			return warningList;
		}else if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			WarningInfo warningItem = new WarningInfo();
			warningItem.setID(Integer.parseInt(objItem.getString("ID")));
			warningItem.setsdatetime(objItem.getString("sdatetime"));
			warningItem.setproject(objItem.getString("project"));
			warningItem.setsensor(objItem.getString("sensor"));
			warningItem.setactive(objItem.getString("active"));
			//System.out.println(objItem.getString("sAlertLevel")+"--------------------------------");
			warningItem.setmsg(objItem.getString("msg"));
			warningItem.setConfirm(Integer.parseInt(objItem.getString("isConfirm")));
			warningItem.setHandle(Integer.parseInt(objItem.getString("isHandle")));
			warningItem.setresponse(objItem.getString("response"));
			//没有返回该字段
			//warningItem.setreportURL(objItem.getString(""));
			warningItem.setPrjAlias(objItem.getString("PrjAlias"));
			warningList.add(warningItem);
		}
//		String sql = "select  s.id,s.sdatetime,s.project,s.sensor,s.active,s.msg,s.response,s.reportURL,p.PrjAlias from syslog s,prjinfo p where 1 = 1 ";
//		if (!active.equals("All")){//wtyang查询所有信息
//			sql += " and s.active = \""+ active + "\" ";
//		}
//		else
//		{
//			//sql += " and ( active = \"LowerPower\" or active = \"Alert\" or active = \"Action\" or active = \"Alarm\" or active = \"Offline\") ";
//			
//		}
//			
//		sql+=" and s.sdatetime> \""+ starttime + "\" and s.sdatetime<\""+ endtime + "\" ";
//		sql+=" and s.project=p.PrjName AND s.active!='CONN' AND s.active!='DISCONN'";
//		sql += " order by s.sdatetime desc limit  "+ startPage+","+pageSize;
//		System.out.print("sql:"+sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
//		while (m_rset.next()) {
//			WarningInfo warningItem = new WarningInfo();
//			warningItem.setID((int) m_rset.getDouble(col));
//			warningItem.setsdatetime(m_rset.getString(++col));
//			warningItem.setproject(m_rset.getString(++col));
//			warningItem.setsensor(m_rset.getString(++col));
//			warningItem.setactive(m_rset.getString(++col));
//			warningItem.setmsg(m_rset.getString(++col));
//			warningItem.setresponse(m_rset.getString(++col));
//			warningItem.setreportURL(m_rset.getString(++col));
//			warningItem.setPrjAlias(m_rset.getString(++col));
//			col =1;
//			warningList.add(warningItem);
//		}
//		m_rset.close();
//		m_pstmt.close();
		System.out.println("++++++++++++++++++++++++++++"+warningList);
		return warningList;
	}
	
	public ReportSummary FindReportItem(List<ReportSummary> summary, String prjName)
	{
		ReportSummary reportItem = null;
		for( int i = 0 ; i < summary.size() ; i++)
		{
			reportItem = summary.get(i);
			if (reportItem.getsPrjName().equals(prjName))
				return reportItem;
		}
		return null;
	}
	//查询所有监测简报
	/*
	public List<ReportSummary> QueryJCJB(Connection conn) throws Exception{
		//String sql = "select project, count(project) as alertCount from syslog where (active = \'alarm\' or active = \'action\') group by project";
		String sql = "select project from syslog group by project";
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		int col = 0;
		List<ReportSummary> reportList = new ArrayList<ReportSummary>();
		while (m_rset.next()) {
			String prjName = m_rset.getString(1);
			if (prjName.equals("System" ))
				continue;
			ReportSummary reportItem = new ReportSummary();
			
			reportItem.setsPrjName(m_rset.getString(1));
			reportList.add(reportItem);
		}
		m_rset.close();
		m_pstmt.close();
		//
		sql = "select project, count(project) as alarmCount from syslog where active = \'action\' group by project";
		sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		col = 0;
		while (m_rset.next()) {
			ReportSummary reportItem = FindReportItem(reportList, m_rset.getString(++col));
			if (null != reportItem)
				reportItem.setalamCount(m_rset.getDouble(++col));
			col =0;
		}
		m_rset.close();
		m_pstmt.close();
		//
		sql = "select project, count(project) as unprocCount from syslog where ( active = \'alarm\' or active = \'action\') and response = \'\' group by project";
		sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		col = 0;
		while (m_rset.next()) {
			ReportSummary reportItem = FindReportItem(reportList, m_rset.getString(++col));
			if (null != reportItem)
				reportItem.setunPorcSum(m_rset.getDouble(++col));
			col =0;
		}
		m_rset.close();
		m_pstmt.close();
		//
		//
		sql = "select project, sdatetime, msg from syslog where ( active = \'alarm\' or active = \'action\') order by sdatetime desc";
		sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		col = 0;
		while (m_rset.next()) {
			ReportSummary reportItem = FindReportItem(reportList, m_rset.getString(++col));
			reportItem.setsdatetime(m_rset.getString(++col));
			col =0;
		}
		m_rset.close();
		m_pstmt.close();
		//
		return reportList;
	}
	*/
	
	public List<ReportSummary> QueryJCJB(Connection conn,String[] prjdice) throws Exception{
		List<ReportSummary> reportList = new ArrayList<ReportSummary>();
		String url = Connest.URL_WARNING_QueryJCJB;
		String prjdices = URLEncoder.encode(String.join(";",prjdice),"utf-8");
		url=String.format(url,prjdices);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		JSONObject obj=new JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		
		for(int i=0;i<arrObjs.length();i++){
			JSONObject objItem=arrObjs.getJSONObject(i);
			ReportSummary reportItem = new ReportSummary();
			reportItem.setsPrjName(objItem.getString("sPrjName"));
			reportItem.setsPrjAlais(objItem.getString("sPrjAlais"));
			//该字段为空
			//reportItem.setcurStatus(objItem.getString("curStatus"));
			String unPorcSum = objItem.getString("unPorcSum");
			String alamCount = objItem.getString("alamCount");
			if(!unPorcSum.equals("")){
				reportItem.setunPorcSum(Double.parseDouble(unPorcSum));
			}else {
				reportItem.setunPorcSum(0.0);
			}
			if(!alamCount.equals("")){
				reportItem.setalamCount(Double.parseDouble(alamCount));
			}else {
				reportItem.setalamCount(0.0);
			}
			reportItem.setsName(objItem.getString("sName"));
			reportItem.seturlAddr(objItem.getString("urlAddr"));
			reportList.add(reportItem);
		}
//		//String sql = "select project, count(project) as alertCount from syslog where (active = \'alarm\' or active = \'action\') group by project";
//		String sql = "select prjName, prjAlias,StatusVal from prjInfo";
//		//后面家入权限修改的问题
//		String where=" WHERE prjName IN (";
//		for(String index:prjdice) {
//			where+="\'"+index+"\'";
//			if (index!=prjdice[prjdice.length-1]) {
//				where+=",";
//			}
//		}
//		where+=") group by prjName";
//		sql=sql+where;
//		StringBuffer  sbf = new StringBuffer(sql);
//		System.out.println(sbf);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 0;
//		List<ReportSummary> reportList = new ArrayList<ReportSummary>();
//		while (m_rset.next()) {
//		
//			ReportSummary reportItem = new ReportSummary();
//			String prjName = m_rset.getString(1);
//			reportItem.setsPrjName(prjName);
//			reportItem.setsPrjAlais(m_rset.getString(2));
//			reportList.add(reportItem);
//			
//			reportItem.setcurStatus(m_rset.getString(3));
//			///reportList.add(reportItem);
//		  String sql_rString="select urlAddr, sName from reports where prjName=\""+prjName+"\" order by sdatetime desc limit 1 ";
//		  StringBuffer  sbf_r = new StringBuffer(sql_rString);
//		  PreparedStatement p_pstmt = conn.prepareStatement(sbf_r.toString());
//		  ResultSet p_rset = p_pstmt.executeQuery();
//		  if(p_rset.next()){
//		  reportItem.setsName(p_rset.getString("sName"));
//		  reportItem.seturlAddr(p_rset.getString("urlAddr"));
//		  }
//		}
//		m_rset.close();
//		m_pstmt.close();
//		//
//		sql = "select prjname, count(*) as alarmCount from warninglog where warninglevel = \'alarm\' group by prjname";
//		sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		col = 0;
//		while (m_rset.next()) {
//			String prjName = m_rset.getString(++col);
//			ReportSummary reportItem = FindReportItem(reportList, prjName);
//			if (null != reportItem)
//				reportItem.setalamCount(m_rset.getDouble(++col));
//			col =0;
//		}
//		m_rset.close();
//		m_pstmt.close();
//		//
//		sql = "select count(*) as unproc from warninglog where readFlag = \'\' or readFlag is null group by prjname";
//		sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		col = 0;
//		while (m_rset.next()) {
//			ReportSummary reportItem = FindReportItem(reportList, m_rset.getString(++col));
//			if (null != reportItem)
//				reportItem.setunPorcSum(m_rset.getDouble(++col));
//			col =0;
//		}
//		m_rset.close();
//		m_pstmt.close();
//		//
		return reportList;
	}
	//查询所有未处理的警告信息
	public List<WarningInfo> queryWaringInfoList(Connection conn,String project) throws Exception{
		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
		String url = Connest.URL_WARNING_QueryWaringInfoList;
		String prj = URLEncoder.encode(project,"utf-8");
		url = String.format(url, prj);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i=0;i<arrObjs.length();i++){
			org.json.JSONObject objItem=arrObjs.getJSONObject(i);
			WarningInfo warningItem = new WarningInfo();
			warningItem.setID(Integer.parseInt(objItem.getString("ID")));
			warningItem.setsdatetime(objItem.getString("sdatetime"));
			warningItem.setproject(objItem.getString("project"));
			warningItem.setsensor(objItem.getString("sensor"));
			warningItem.setactive(objItem.getString("active"));
			warningItem.setmsg(objItem.getString("msg"));
			warningItem.setresponse(objItem.getString("response"));
			warningItem.setPrjAlias(objItem.getString("PrjAlias"));
			//未返回此字段
//			warningItem.setreportURL(objItem.getString(""));
			warningList.add(warningItem);
		}
		//String sql = "select project from syslog group by project where response = \"\" and active = \"Action\"";
//		String sql = "select id,sdatetime,project,sensor,active,msg,response,reportURL from syslog where (response is null or reportURL is null)" +
//					" and (active = \"Action\" or active = \"Alert\" or active = \"Alarm\" ) order by active desc";
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
//		while (m_rset.next()) {
//			WarningInfo warningItem = new WarningInfo();
//			warningItem.setID((int) m_rset.getDouble(col));
//			warningItem.setsdatetime(m_rset.getString(++col));
//			warningItem.setproject(m_rset.getString(++col));
//			warningItem.setsensor(m_rset.getString(++col));
//			warningItem.setactive(m_rset.getString(++col));
//			warningItem.setmsg(m_rset.getString(++col));
//			warningItem.setresponse(m_rset.getString(++col));
//			warningItem.setreportURL(m_rset.getString(++col));
//			col =1;
//			warningList.add(warningItem);
//		}
//		m_rset.close();
//		m_pstmt.close();
		return warningList;
	}

	/**
	 * 查询警告信息--用list
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryPtInfoJson(Connection conn) throws Exception{
		//String sql = "select prjName, prjType, prjAddr from prjinfo order by starttime desc";
		String sql = "select id,sdatetime,project,sensor,active,msg,response,reportURL from syslog order by starttime desc";
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
		//System.out.print(jsArray.toString());
		m_rset.close();
		m_pstmt.close();
		return jsArray.toString();
	}
	/**
	 * 查询条件下数据数量
	 * @param conn
	 * @param active,starttime,endtime
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public int QuerywarningInfoTotalRecord(Connection con, String active,
			String starttime, String endtime) throws Exception {
		// TODO Auto-generated method stub
		int totalRecord=0;
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String url=Connest.URL_WARNING_QueryWarningInfoTotalRecord;
		String a = URLEncoder.encode(active,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		url=String.format(url,a,sTime,eTime);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		totalRecord = obj.getInt("count");
//		System.out.println("starttime:"+starttime);
//		System.out.println("endtime:"+endtime);
//		String sql = "select count(*) as totalrecord from syslog where 1 = 1 ";
//		if (!active.equals("All")){//wtyang查询警告信息
//			sql += " and active = \""+ active + "\" ";
//		}
//			
//		sql+=" and sdatetime> \""+ starttime + "\" and sdatetime<\""+ endtime + "\" ";
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = con.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		if(m_rset.next()){
//			totalRecord=m_rset.getInt("totalrecord");
//		}
		return totalRecord;
	}

	public int QuerySensorInfoTotalRecord(Connection con, String sensor,
			String starttime, String endtime) throws Exception {
		int totalRecord=0;

		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String url=Connest.URL_WARNING_QuerySensorInfoTotalRecord;
		String s = URLEncoder.encode(sensor,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		url=String.format(url,s,sTime,eTime);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		totalRecord = obj.getInt("data");
		
//		String sql = "select count(*) as totalrecord from syslog where 1 = 1 ";
//		if (!sensor.equals("All")){//wtyang查询警告信息
//			sql += " and sensor = \""+ sensor + "\" ";
//		}
//			
//		sql+=" and sdatetime> \""+ starttime + "\" and sdatetime<\""+ endtime + "\" ";
//		sql+=" order by sdatetime desc";
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = con.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		if(m_rset.next()){
//			totalRecord=m_rset.getInt("totalrecord");
//		}
		return totalRecord;
	}

	public List<WarningInfo> queryLogListBysensor(Connection con,
			String sensor, String starttime, String endtime, int page,
			int pageSize,List<String> ids) throws Exception  {
//		starttime=starttime+ " 00:00:00";
//		endtime=endtime+ " 23:59:59";

		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
		if(pageSize==0){
			pageSize=10;
		}
		if(page==0){
			page=1;
		}
		//判断是否是选择全部
//		if(sensor.equals("All")){
//			for(int i = 0;i<ids.size();i++){
//				warningList.addAll(queryLogList(con, ids.get(i),starttime,endtime,page,pageSize));
//			}
//			return warningList;
//		}else{
//			return queryLogList(con, sensor,starttime,endtime,page,pageSize);
//		}
		return queryLogList(con, sensor,starttime,endtime,page,pageSize);
		
//		int startPage=(page-1)*pageSize;
//		String sql = "select  s.id,s.sdatetime,s.project,s.sensor,s.active,s.msg,s.response,s.reportURL,p.PrjAlias from syslog s,prjinfo p where 1 = 1 ";
//		if (!sensor.equals("All")){//wtyang查询所有信息
//			sql += " and s.sensor = \""+ sensor + "\" ";
//		}
//		else
//		{
//			//sql += " and ( active = \"LowerPower\" or active = \"Alert\" or active = \"Action\" or active = \"Alarm\" or active = \"Offline\") ";
//			
//		}
//			
//		sql+=" and s.sdatetime> \""+ starttime + "\" and s.sdatetime<\""+ endtime + "\" ";
//		sql+=" and s.project=p.PrjName";
//		sql += " order by s.sdatetime desc limit  "+ startPage+","+pageSize;
//		System.out.println(sql);
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt = con.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		int col = 1;
//		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
//		while (m_rset.next()) {
//			WarningInfo warningItem = new WarningInfo();
//			warningItem.setID((int) m_rset.getDouble(col));
//			warningItem.setsdatetime(m_rset.getString(++col));
//			warningItem.setproject(m_rset.getString(++col));
//			warningItem.setsensor(m_rset.getString(++col));
//			warningItem.setactive(m_rset.getString(++col));
//			warningItem.setmsg(m_rset.getString(++col));
//			warningItem.setresponse(m_rset.getString(++col));
//			warningItem.setreportURL(m_rset.getString(++col));
//			warningItem.setPrjAlias(m_rset.getString(++col));
//			col =1;
//			warningList.add(warningItem);
//		}
//		m_rset.close();
//		m_pstmt.close();
//	
		
	}
	
	public List<WarningInfo> queryLogList(Connection con,
			String sensor, String starttime, String endtime, int page,
			int pageSize) throws Exception  {
		List<WarningInfo> warningList = new ArrayList<WarningInfo>();
		String url = Connest.URL_WARNING_QueryLogListBysensor;
		String s = URLEncoder.encode(sensor,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		url=String.format(url,s,sTime,eTime);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			//throw new Exception(obj.getString("msg"));
			return warningList;
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		for(int i = 0;i<arrObjs.length();i++){
			org.json.JSONObject objItem = arrObjs.getJSONObject(i);
			WarningInfo warningItem = new WarningInfo();
			warningItem.setID(Integer.parseInt(objItem.getString("ID")));
			warningItem.setsdatetime(objItem.getString("sdatetime"));
			warningItem.setproject(objItem.getString("project"));
			warningItem.setsensor(objItem.getString("sensor"));
			warningItem.setactive(objItem.getString("active"));
			warningItem.setmsg(objItem.getString("msg"));
			warningItem.setresponse(objItem.getString("response"));
			//缺少该字段
//			warningItem.setreportURL(objItem.getString(""));
			warningItem.setPrjAlias(objItem.getString("PrjAlias"));
			warningList.add(warningItem);
		}
		return warningList;
	}
	
	/**
	 * 根据项目ID查询警告信息总记录数
	 * @param projects 项目id(支持多个，逗号隔开)
	 * @return 
	 */
	public String queryWarningInfoCount(String projects) throws Exception {
		String url = Connest.URL_WARNING_QueryWaringcount;
		String prj = URLEncoder.encode(projects,"utf-8");
		url = String.format(url, prj);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		return obj.getJSONArray("data").getJSONObject(0).toString();
	}
	

	/**
	 * 根据组合条件查询警告信息总记录数
	 * @param projects 项目id(支持多个，逗号隔开)
	 * @param active   预警级别
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws Exception
	 */
	public String queryWarningInfoCount(String projects,String active,String structure_type,String structure_name,String starttime,String endtime) throws Exception {
		String url = Connest.URL_WARNING_QueryWaringcountByActive;
		starttime=starttime.replaceAll("-", "");
		endtime=endtime.replaceAll("-", "");
		String prj = URLEncoder.encode(projects,"utf-8");
		String a = URLEncoder.encode(active,"utf-8");
		String type = URLEncoder.encode(structure_type,"utf-8");
		String name = URLEncoder.encode(structure_name,"utf-8");
		String sTime = URLEncoder.encode(starttime,"utf-8");
		String eTime = URLEncoder.encode(endtime,"utf-8");
		if(structure_type.equals("") && structure_name.equals("")){
			url= Connest.URL_WARNING_QueryWaringcountByActiveNull;
			url = String.format(url,prj,a,sTime,eTime);
		}else if(structure_name.equals("")){
			url= Connest.URL_WARNING_QueryWaringcountByActiveType;
			url = String.format(url,prj,a,type,sTime,eTime);
		}else if(structure_type.equals("")){
			url= Connest.URL_WARNING_QueryWaringcountByActiveName;
			url = String.format(url,prj,a,name,sTime,eTime);
		}else{
			url = String.format(url,prj,a,type,name,sTime,eTime);
		}
		//url = String.format(url,prj,a,type,name,sTime,eTime);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		return String.valueOf(obj.getInt("data"));
	}


}
