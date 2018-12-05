package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class ReportDao2 {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

// select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress  from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC

    
    public List<Map<String, Object>> findReportData(String projectsIds, String reportType, String projectId, String startTime, String endTime  ){
    	List<Map<String, Object>> reportList = null;
    	
    	StringBuffer buffer = new StringBuffer();
		String spid[] = projectsIds.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("b.nProjectID ="+spid[i]);
		}
    	
    	if(!"".equals(projectId.trim())&&projectId!=null) {
    		if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
    			//当结构物不等于空且时间不等于空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId, startTime, endTime});
    		}
    		else {
    			//当结构物不等于空时间等于空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId});
    		}
    		
    	} else if("".equals(projectId.trim())||projectId==null){
    		if("".equals(startTime.trim())||"00:00:00".equals(startTime.trim())) {
    			//当结构物等于空且时间也为空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType});
     			
    		}else {
    			//当结构物为空时间不为空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=?  and (b.dtCreatAt>=? and b.dtCreatAt<=?) and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,startTime, endTime});
    		}
    	}
    	
        return reportList;
    }
    
    /**s
     * 分页
     **/
    
    
    public List<Map<String, Object>> findReportDataPage(String projectsIds, String reportType, String projectId, String startTime, String endTime,Integer page,Integer limit  ){
    	List<Map<String, Object>> reportList = null;
    	
    	StringBuffer buffer = new StringBuffer();
		String spid[] = projectsIds.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("b.nProjectID ="+spid[i]);
		}
    	
    	if(!"".equals(projectId.trim())&&projectId!=null) {
    		if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
    			//当结构物不等于空且时间不等于空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType,projectId, startTime, endTime});
    		}
    		else {
    			//当结构物不等于空时间等于空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql,
    	        		new String[]{reportType,projectId});
    		}
    		
    	} else if("".equals(projectId.trim())||projectId==null){
    		if("".equals(startTime.trim())||"00:00:00".equals(startTime.trim())) {
    			//当结构物等于空且时间也为空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType});
     			
    		}else {
    			//当结构物为空时间不为空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=?  and (b.dtCreatAt>=? and b.dtCreatAt<=?) and ("+buffer.toString()+") ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType,startTime, endTime});
    		}
    	}
    	
        return reportList;
    }
    
//##############################################################################################3
//******************************************************************************************88888
/**
 * 预警报告
 */
    public List<Map<String, Object>> findReportWarning(String projectsIds,String reportType, String projectId, String warningLevel,String startTime, String endTime){
    	
    	StringBuffer buffer = new StringBuffer();
		String spid[] = projectsIds.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("nProjectID ="+spid[i]);
		}
    	
    	
    	List<Map<String, Object>> reportList = null;
    	String sql ="select b.sWarningLevel,b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType='"+reportType.trim()+"'";
//    	ORDER BY b.dtCreatAt DESC
    	
    	if(!"".equals(projectId)&&null!=projectId) {
    		sql += " and nProjectID = '"+projectId.trim()+"'";
    	}
    	
    	if(!"".equals(warningLevel)&&null!=warningLevel) {
    		sql += " and sWarningLevel = '"+warningLevel.trim()+"'";
    	}
    	
    	if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
    		
    		sql += " and (b.dtCreatAt>= '"+startTime.trim()+"' and b.dtCreatAt<='"+endTime.trim()+"')";
    	}
         
    	 sql +="and ("+buffer.toString()+")";
         sql += " ORDER BY b.dtCreatAt DESC";
         
         reportList = dbUtils.find(sql);
    	
        return reportList;
    }   
    
    
    /**
     * 预警报告分页
     */
        public List<Map<String, Object>> findReportWarningPage(String projectsIds, String reportType, String projectId, String warningLevel,String startTime, String endTime,Integer page,Integer limit){
        	StringBuffer buffer = new StringBuffer();
    		String spid[] = projectsIds.split(",");
    		for (int i = 0; i < spid.length; i++) {
    			if(i>0) {
    				buffer.append(" or ");
    			}
    			buffer.append("nProjectID ="+spid[i]);
    		}
        	
        	List<Map<String, Object>> reportList = null;
        	String sql ="select b.sWarningLevel,b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType='"+reportType.trim()+"'";
//        	ORDER BY b.dtCreatAt DESC
       
        	if(!"".equals(projectId)&&null!=projectId) {
        		sql += " and nProjectID = '"+projectId.trim()+"'";
        	}
        	
        	if(!"".equals(warningLevel)&&null!=warningLevel) {
        		sql += " and sWarningLevel = '"+warningLevel.trim()+"'";
        	}
        	
        	if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
        		
        		sql += " and (b.dtCreatAt>= '"+startTime.trim()+"' and b.dtCreatAt<='"+endTime.trim()+"')";
        	}
            
        	sql +="and ("+buffer.toString()+")";
        	
             sql += " ORDER BY b.dtCreatAt DESC";
             
 			if(page != null && limit != null) {
				sql += " limit "+((page-1)*limit)+","+limit;
			}
             
 			
             reportList = dbUtils.find(sql);
        	
            return reportList;
        }   
    

}
