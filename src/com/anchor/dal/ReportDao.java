package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class ReportDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

// select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress  from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC

    
    public List<Map<String, Object>> findReportData(String reportType, String projectId, String authorId, String startTime, String endTime  ){
    	List<Map<String, Object>> reportList = null;
    	
    	
    	if(!"".equals(authorId.trim())&&authorId!=null) {
    		if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
    			//当创建人不等于空且时间不等于空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId,authorId, startTime, endTime});
    		}
    		else {
    			//当创建人不等于空时间等于空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId,authorId});
    		}
    		
    	} else if("".equals(authorId.trim())||authorId==null){
    		if("".equals(startTime.trim())||"00:00:00".equals(startTime.trim())) {
    			//当创建人等于空且时间也为空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId});
     			
    		}else {
    			//当创建人为空时间不为空
    			reportList = dbUtils.find("select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC", 
    	        		new String[]{reportType,projectId,startTime, endTime});
    		}
    	}
    	
        return reportList;
    }
    
    /**s
     * 分页
     **/
    
    
    public List<Map<String, Object>> findReportDataPage(String reportType, String projectId, String authorId, String startTime, String endTime,Integer page,Integer limit  ){
    	List<Map<String, Object>> reportList = null;
    	
    	
    	if(!"".equals(authorId.trim())&&authorId!=null) {
    		if(!"".equals(startTime.trim())&&!"00:00:00".equals(startTime.trim())) {
    			//当创建人不等于空且时间不等于空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType,projectId,authorId, startTime, endTime});
    		}
    		else {
    			//当创建人不等于空时间等于空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and b.nAuthorID=? ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql,
    	        		new String[]{reportType,projectId,authorId});
    		}
    		
    	} else if("".equals(authorId.trim())||authorId==null){
    		if("".equals(startTime.trim())||"00:00:00".equals(startTime.trim())) {
    			//当创建人等于空且时间也为空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType,projectId});
     			
    		}else {
    			//当创建人为空时间不为空
    			String sql = "select b.sReportName,b.dtCreatAt,a.sUserName,b.sURLAddress from base_user_info a,warning_report_info b  where a.ID=b.nAuthorID and b.sReportType=? and b.nProjectID=? and (b.dtCreatAt>=? and b.dtCreatAt<=?) ORDER BY b.dtCreatAt DESC";
    			if(page != null && limit != null) {
    				sql += " limit "+((page-1)*limit)+","+limit;
    			}
    			reportList = dbUtils.find(sql, 
    	        		new String[]{reportType,projectId,startTime, endTime});
    		}
    	}
    	
        return reportList;
    }

}
