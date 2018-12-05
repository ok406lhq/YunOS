package com.anchor.service;

import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.GNSSResultDao;
import com.anchor.dao.PointInfoDao;
import com.anchor.dao.ProjectInfoDao;
import com.anchor.dao.ReportInfoDao;
import com.anchor.dao.WarningInfoDao;
import com.anchor.model.GNSSResult;
import com.anchor.model.PointInfo;
import com.anchor.model.ProjectInfo;
import com.anchor.model.ReportInfo;
import com.anchor.model.ReportSummary;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;

public class ReportInfoService {
	
	//查询项目信息
	public String Query(Connection con, String prjName,
						String starttime,String endtime,String sReportType)
	{//todo.
		String json="";
		try{
			ReportInfoDao resDao = new ReportInfoDao();     
			List<ReportInfo> reports =  resDao.queryReports(con, prjName, starttime, endtime,sReportType);
			if (null != reports)
			{
				JSONArray array=new JSONArray();
				for( int i = 0 ; i < reports.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
					ReportInfo prjInfoItem = reports.get(i);
					JSONObject mapOfColValues=new JSONObject();
					//
					mapOfColValues.put(ReportInfo.SID, prjInfoItem.getID());
					mapOfColValues.put(ReportInfo.SNAME, prjInfoItem.getsname());
					mapOfColValues.put(ReportInfo.SPRJNAME, prjInfoItem.getsprjName());
					mapOfColValues.put(ReportInfo.SCREATETIME, prjInfoItem.getscreatetime());
					mapOfColValues.put(ReportInfo.SSTARTTIME, prjInfoItem.getsstarttime());
					mapOfColValues.put(ReportInfo.SENDTIME, prjInfoItem.getsendtime());
					mapOfColValues.put(ReportInfo.SAUTHOR, prjInfoItem.getsauthor());					
					mapOfColValues.put(ReportInfo.URLADDR, prjInfoItem.geturlAddr());
					mapOfColValues.put(ReportInfo.READTIMES, prjInfoItem.getreadTimes());
					mapOfColValues.put(ReportInfo.SREPORTTYPE, prjInfoItem.getsReportType());
					
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
}
