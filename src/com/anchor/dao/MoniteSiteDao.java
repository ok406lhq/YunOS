package com.anchor.dao;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.MoniteSite;
import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MoniteSiteDao {
    public PreparedStatement m_pstmt = null;
    public ResultSet m_rset = null;
    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public int QueryMoniteSiteTotalRecord(String siteid) throws Exception {
        // TODO Auto-generated method stub
        int totalRecord = 0;

        String url = Connest.URL_SYSTEM_LOG_QueryMoniteSiteTotalRecord;


        url = String.format(url, siteid);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        totalRecord = obj.getInt("data");
//		
//		String sql="select count(*) as count from user_active where 1=1 ";
//		sql+= " and adddate>'"+starttime+"' and adddate<'"+endtime+"'";
//		if(!userid.equals("all")){
//			sql+=" and userid='"+userid+"'";
//		}
//		m_pstmt=con.prepareStatement(sql);
//		m_rset=m_pstmt.executeQuery();
//	   int totalRecord = 0;
//		while(m_rset.next()){
//			totalRecord= m_rset.getInt("count");
//		}
        // TODO Auto-generated method stub
        return totalRecord;
    }


    //新Java接口
    public int getMoniteSiteList(String project, String siteid) {
        String[] projects = project.split(",");
        StringBuilder sql = new StringBuilder("SELECT m.*, p.sProjectName,sDtuName  FROM monite_site_info  m LEFT JOIN  base_site_info s  ON m.nSiteID =s.nSiteID" +
                " LEFT JOIN  base_project_info p  ON s.nProjectID =p.ID" +
                " WHERE m.nSiteID in(SELECT nSiteID FROM base_site_info where nProjectID in( ");
        for (int i = 0; i < projects.length; i++) {
            sql.append(projects[i]);
            if(i != projects.length-1){
                sql.append(",");
            }
        }
        sql.append("))");
        if (StringUtils.isNotBlank(siteid)) {
            sql.append( " and m.nSiteID ='" + siteid + "'");
        }
        return dbUtils.find(MoniteSite.class,sql.toString()).size();
    }

    //新Java接口
    public List<MoniteSite> getMoniteSiteList(String project, String siteid, int page, int pageSize) {
        String[] projects = project.split(",");
        StringBuilder sql = new StringBuilder("SELECT m.*, p.sProjectAbbr,sDtuName  FROM monite_site_info  m LEFT JOIN  base_site_info s  ON m.nSiteID =s.nSiteID" +
                " LEFT JOIN  base_project_info p  ON s.nProjectID =p.ID" +
                " WHERE m.nSiteID in(SELECT nSiteID FROM base_site_info where nProjectID in( ");
        for (int i = 0; i < projects.length; i++) {
            sql.append(projects[i]);
            if(i != projects.length-1){
                sql.append(",");
            }
        }
        sql.append("))");
        if (StringUtils.isNotBlank(siteid)) {
            sql.append( " and m.nSiteID ='" + siteid + "'");
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        if (page == 0) {
            page = 1;
        }
        int startPage=(page-1)*pageSize;
        sql.append( " ORDER BY ID DESC LIMIT "+ startPage+","+pageSize);
        return dbUtils.find(MoniteSite.class,sql.toString());
    }

    public List<Map<String, Object>> getCurrentUserAllSiteId(String project) {
        String[] projects = project.split(",");
        StringBuilder sql = new StringBuilder("select nSiteID,sDtuName from base_site_info where nProjectID in(");
        for (int i = 0; i < projects.length; i++) {
            sql.append(projects[i]);
            if(i != projects.length-1){
                sql.append(",");
            }
        }
        sql.append(")");
        return dbUtils.find(sql.toString());
    }

    public List<Map<String,Object>> getCurrentUserProject(String project) {
        String[] projects = project.split(",");
        StringBuilder sql = new StringBuilder("select ID,sProjectName,sProjectAbbr from base_project_info where ID in(");
        for (int i = 0; i < projects.length; i++) {
            sql.append(projects[i]);
            if(i != projects.length-1){
                sql.append(",");
            }
        }
        sql.append(")");
        return dbUtils.find(sql.toString());
    }


    //MoniteSite   monitesite   老php接口
    public List<MoniteSite> QueryMoniteSiteDataList(
            String siteid, int page,
            int pageSize) throws Exception {
        // TODO Auto-generated method stub
        List<MoniteSite> monitesitelist = new ArrayList<MoniteSite>();
        String url = Connest.URL_SYSTEM_LOG_QueryMoniteSiteDataList;

//		String sTime = URLEncoder.encode(starttime,"utf-8");
//		String eTime = URLEncoder.encode(endtime,"utf-8");
        url = String.format(url, siteid, page, pageSize);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        org.json.JSONArray arrObjs = obj.getJSONArray("data");
        for (int i = 0; i < arrObjs.length(); i++) {
            org.json.JSONObject objItem = arrObjs.getJSONObject(i);


            MoniteSite monitesite = new MoniteSite();
            monitesite.setnSiteID(objItem.getString("nSiteID"));
            monitesite.setsSendCommand(objItem.getString("sSendCommand"));
            monitesite.setnSendMinuteInterval(objItem.getString("nSendMinuteInterval"));
            monitesite.setdtSendDateTime(objItem.getString("dtSendDateTime"));
            monitesite.setdtLoginDateTime(objItem.getString("dtLoginDateTime"));
            monitesite.setsLastAction(objItem.getString("sLastAction"));
            monitesite.setdtLastDateTime(objItem.getString("dtLastDateTime"));

            monitesite.setsProjectName(objItem.getString("sProjectName"));
            monitesite.setsDtuName(objItem.getString("sDtuName"));

            monitesitelist.add(monitesite);
        }
//		if(pageSize==0){
//			pageSize=10;
//		}
//		if(page==0){
//			page=1;
//		}
//		int startPage=(page-1)*pageSize;
//		starttime=starttime+ " 00:00:00";
//		endtime =endtime+ " 23:59:59";
//		String sql="select * from user_active where 1=1";
//		sql+= " and adddate>'"+starttime+"' and adddate<'"+endtime+"'";
//		if(!userid.equals("all")){
//			sql+=" and userid='"+userid+"'";
//		}
//		sql+= " order by id desc limit  "+ startPage+","+pageSize;
//		StringBuffer  sbf = new StringBuffer(sql);
//		m_pstmt=con.prepareStatement(sbf.toString());
//		m_rset=m_pstmt.executeQuery();
//		while (m_rset.next()) {
//			 SystemLog systemLog=new SystemLog();
//			 systemLog.setid(m_rset.getString("id"));
//			 systemLog.setuserID(m_rset.getString("userid"));
//			 systemLog.setusername(m_rset.getString("username"));
//			 systemLog.setactive(m_rset.getString("active"));
//			 systemLog.setdescript(m_rset.getString("descript"));
//			 systemLog.setadddate(m_rset.getString("adddate"));
//			 
//			 systemLoglist.add(systemLog);
//		}
        return monitesitelist;
    }

    public String MoniteSiteDelted(Connection con, String idsString) throws SQLException {
        // TODO Auto-generated method stub
        String result = "ok";

        if (null != idsString) {

            String[] ids = idsString.split(",");
            for (int i = 0; i < ids.length; i++) {
                Statement st;
                st = (Statement) con.createStatement();
                boolean count = st.execute("delete from user_active where id=" + ids[i] + "");
            }
        }

        return result;
    }

}
