package com.anchor.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.anchor.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.WarningInfoDao;
import com.anchor.model.ReportSummary;
import com.anchor.model.WarningInfo;
import com.anchor.util.DateUtil;
import org.apache.commons.lang.StringUtils;

public class WarningInfoService {

	WarningInfoDao dao = new WarningInfoDao();
	
	public String findAll(String sProjectIDS,String project,String activeval, String starttime, String endtime, String structure_type, String structure_name, String page, String pageSize) throws Exception{
		List<Map<String, Object>> list = dao.findAll(sProjectIDS,project,activeval,starttime,endtime,structure_type,structure_name,page,pageSize);
		String json="";
		JSONObject WSSItem = null;
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			WSSItem.put("waringTime",DateUtil.getPlusTime(map.get("dtCreateAt")));
			WSSItem.put("wSensor", map.get("sSiteName"));
			WSSItem.put("sAlertLevel", map.get("sAlertLevel"));
			WSSItem.put("project", map.get("sProjectName"));
			WSSItem.put("PrjAlias", map.get("sProjectAbbr"));
			WSSItem.put("waringMsg", map.get("sAlertContent"));
			WSSItem.put("confirm", map.get("isConfirm"));
			WSSItem.put("handle", map.get("isHandle"));
			WSSItem.put("response", map.get("sProcessInfo"));
			array.add(WSSItem);
		}
		json = array.toString();
		return json;
	}
	
	public String findAllCount(String sProjectIDS,String project, String activeval, String starttime, String endtime, String structure_type, String structure_name) throws Exception{
		List<Map<String, Object>> list = dao.findAllCount(sProjectIDS,project,activeval,starttime,endtime,structure_type,structure_name);
		String json="";
		JSONObject WSSItem = null;
		//JSONArray array = new JSONArray();
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("totalRecord", map.get("totalRecord"));
		}
		json = WSSItem.getString("totalRecord");
		return json;
	}
	
	/**
	 * 修改信息
	 * @param con
	 * @param id
	 * @param project
	 * @return
	 */
	public int UpdateWarningConfirmInfo(Connection con, String id){
		WarningInfoDao warningInfoDao = new WarningInfoDao();
		int row = warningInfoDao.UpdateWarningConfirmInfo(con,id);
		if(row > 0){
			return row;
		}
		return 0;
	}
	     
	
	//查询项目信息
	public String QueryLogInfo(Connection con, String active,String structure_type,String structure_name,String starttime,String endtime, int page, int pageSize,String project)
	{
		String json="";
		try{
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			//json = m_projectInfoDao.queryPtInfoJson(con);
			List<WarningInfo> warningInfos = warningInfoDao.queryLogListByActive(con, active,structure_type,structure_name,starttime,endtime,page,pageSize,project);
			if (null != warningInfos)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < warningInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					WarningInfo prjInfoItem = warningInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(WarningInfo.SID, prjInfoItem.getID());
					mapOfColValues.put(WarningInfo.SDATETIME, prjInfoItem.getsdatetime());
					mapOfColValues.put(WarningInfo.SSENSOR, prjInfoItem.getsensor());
					mapOfColValues.put("sAlertLevel", prjInfoItem.getactive());
					mapOfColValues.put("project", prjInfoItem.getproject());
					mapOfColValues.put("PrjAlias", prjInfoItem.getPrjAlias());
					mapOfColValues.put(WarningInfo.SMSG, prjInfoItem.getmsg());
					mapOfColValues.put("confirm", prjInfoItem.getConfirm());
					mapOfColValues.put("handle", prjInfoItem.getHandle());
					mapOfColValues.put("response", prjInfoItem.getresponse());
					array.add(mapOfColValues);
				}
				json = array.toString();
System.out.print("---------------------------:"+json);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	//
	//查询警告信息概况
	public String QueryWarningInfo(Connection con,String project)
	{
		String json="";
		try{
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			List<WarningInfo> warningInfos = warningInfoDao.queryWaringInfoList(con,project);
			if (null != warningInfos)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < warningInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					WarningInfo prjInfoItem = warningInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(WarningInfo.SID, prjInfoItem.getID());
					mapOfColValues.put(WarningInfo.SDATETIME, prjInfoItem.getsdatetime());
					mapOfColValues.put(WarningInfo.STYPE, prjInfoItem.getactive());
					mapOfColValues.put(WarningInfo.SSENSOR, prjInfoItem.getsensor());
					mapOfColValues.put(WarningInfo.SMSG, prjInfoItem.getmsg());
					mapOfColValues.put("PrjAlias", prjInfoItem.getPrjAlias());
					array.add(mapOfColValues);
				}
				json = array.toString();
				System.out.print(json);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

	//查询警告信息概况
	public String QueryJCJB(Connection con,String[] prjdict)
	{//todo.
		String json="";
		try{
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			List<ReportSummary> warningInfos = warningInfoDao.QueryJCJB(con,prjdict);
			if (null != warningInfos)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < warningInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					ReportSummary prjInfoItem = warningInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(ReportSummary.SPRJNAME, prjInfoItem.getsPrjName());
					mapOfColValues.put(ReportSummary.SPRSALAIS, prjInfoItem.getsPrjAlais());
					mapOfColValues.put(ReportSummary.SPRJTYPE, prjInfoItem.getsPrjType());
					mapOfColValues.put(ReportSummary.SCURSTATUS, prjInfoItem.getcurStatus());
					mapOfColValues.put(ReportSummary.UNPROCSUM, prjInfoItem.getunPorcSum());
					mapOfColValues.put(ReportSummary.ALARMCOUNT, prjInfoItem.getalamCount());
					mapOfColValues.put(ReportSummary.ACTIONCOUNT, prjInfoItem.getactionCount());
					mapOfColValues.put(ReportSummary.REPORTURL, prjInfoItem.getreportURL());
					mapOfColValues.put(ReportSummary.SDATETIME, prjInfoItem.getsdatetime());
					mapOfColValues.put(ReportSummary.SNAME, prjInfoItem.getsName());
					mapOfColValues.put(ReportSummary.URLADDR, prjInfoItem.geturlAddr());
					array.add(mapOfColValues);
				}
				json = array.toString();
				System.out.print(json);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}
	public String QuerySensorLogInfo(Connection con, String sensor,
			String starttime, String endtime, int page, int pageSize,List<String> ids) {
		// TODO Auto-generated method stub
		String json="";
		try{
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			//json = m_projectInfoDao.queryPtInfoJson(con);
			//warning_sensor_info a,base_project_info
			List<WarningInfo> warningInfos = warningInfoDao.queryLogListBysensor(con, sensor,starttime,endtime,page,pageSize,ids);
			if (null != warningInfos)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < warningInfos.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					WarningInfo prjInfoItem = warningInfos.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(WarningInfo.SID, prjInfoItem.getID());
					mapOfColValues.put(WarningInfo.SDATETIME, prjInfoItem.getsdatetime());
					mapOfColValues.put(WarningInfo.SSENSOR, prjInfoItem.getsensor());
					mapOfColValues.put(WarningInfo.SSENSOR, prjInfoItem.getsensor());
					mapOfColValues.put("project", prjInfoItem.getproject());
					mapOfColValues.put("PrjAlias", prjInfoItem.getPrjAlias());
					mapOfColValues.put("response", prjInfoItem.getresponse());
					mapOfColValues.put(WarningInfo.SMSG, prjInfoItem.getmsg());
					array.add(mapOfColValues);
				}
				json = array.toString();
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	

	/**
	 * 根据项目ID查询警告信息总记录数
	 * @param projects 项目id(支持多个，逗号隔开)
	 * @return 
	 */
	public String queryWarningInfoCount(String projects){
		String count="";
		try {
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			count = warningInfoDao.queryWarningInfoCount(projects);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return count;
	}
	public Map<String, Object> getOneNewestWarning(String nProjectID) {
        WarningInfoDao warningInfoDao = new WarningInfoDao();
        return warningInfoDao.getOneNewestWarning(nProjectID);
    }
	public Map<String, Object> getCountWarning(String nProjectID) {
        WarningInfoDao warningInfoDao = new WarningInfoDao();
        return warningInfoDao.getCountWarning(nProjectID);
    }

    public int updateIsDialogById(String ID){
        WarningInfoDao warningInfoDao = new WarningInfoDao();
        return warningInfoDao.updateIsDialogById(ID);
    }

    public int updateIsDialogAll(String project) {
		WarningInfoDao warningInfoDao = new WarningInfoDao();
		return warningInfoDao.updateIsDialogAll(project);
    }

    public int updateDialogAll(String project) {
		if(StringUtils.isNotBlank(project)){
			WarningInfoDao warningInfoDao = new WarningInfoDao();
			return warningInfoDao.updateDialogAll(project);
		}
        return 0;
    }
}