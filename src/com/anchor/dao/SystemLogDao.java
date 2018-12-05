package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.SystemLog;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HttpClientUtil;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class SystemLogDao {
	public  PreparedStatement  m_pstmt=null;
	public ResultSet m_rset = null;
     public  int SaveAddSystemLog( SystemLog systemlog) throws Exception{
    	 int count = 0;
    	 String url = Connest.URL_SYSTEM_LOG_SaveAddSystemLog;
    	 String userID = URLEncoder.encode(systemlog.getuserID(),"utf-8");
    	 String username= URLEncoder.encode(systemlog.getusername(),"utf-8");
    	 String active= URLEncoder.encode(systemlog.getactive(),"utf-8");
    	 String descript= URLEncoder.encode(systemlog.getdescript(),"utf-8");
    	 String adddate= URLEncoder.encode(systemlog.getadddate(),"utf-8");
    	 url = String.format(url,userID,username,active,descript,adddate);
    	 String res=HttpClientUtil.doGet(url,"utf-8");
 		res=URLDecoder.decode(res, "utf-8");
 		org.json.JSONObject obj=new org.json.JSONObject(res);
 		String resCode=obj.getString("code");
 		if(!"00".equals(resCode)){
 			throw new Exception(obj.getString("msg"));
 		}
 		org.json.JSONObject objItem=obj.getJSONObject("data");
 		count = objItem.getInt("num");
//    	 Statement st;
//    	 int count = 0;
//    	 String userID=systemlog.getuserID();
//    	 String username=systemlog.getusername();
//    	 String active=systemlog.getactive();
//    	 String descript=systemlog.getdescript();
//    	 String adddate=systemlog.getadddate();
//    	 String sqlString="insert into user_active(userid,username,active,descript,adddate) values  ";
//		   sqlString+="(" + userID + ",\"" + username + "\",\"" + active + "\",\"" + descript + "\",\"" + adddate + "\")";
//		   DbUtil dbUtil=null;
//			Connection conn=null;
//			try{
//				dbUtil=new DbUtil();
//				
//				conn = dbUtil.getCon();
//						if (null != conn)
//						{
//							 StringBuffer  sbf = new StringBuffer(sqlString);
//							 st = (Statement) conn.createStatement(); 
//							 count = st.executeUpdate(sqlString);
//						}
//				}catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					try {
//						if (null != conn)
//						{
//							if (null != dbUtil)
//							{
//								dbUtil.closeCon(conn);
//							}
//						}
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				 return count;
			}

     /**
       *   统计数量
      * @param sProjectIDS
      * @param userid
      * @param starttime
      * @param endtime
      * @return
      * @throws Exception
      */
	public int QuerySystemLogoTotalRecord(String sProjectIDS,Integer userid, String starttime, String endtime) throws Exception {
		String str = "";
		Object params[];
		if(userid > 0) {
			Object param[] = {sProjectIDS,starttime,endtime,userid};
			str = " and t1.nUserID = ? ";
			params = param;
		}else {
			Object param[] = {sProjectIDS,starttime,endtime};
			params = param;
		}
		String sql = "SELECT t1.ID,t1.nUserID,t1.sAction,t1.sDetail,t1.dtModifyAt,t2.sUserName,t2.sProjectIDS FROM log_base_useraction t1 INNER JOIN base_user_info t2 ON t1.nUserID = t2.ID  WHERE ? LIKE CONCAT('%',t2.sProjectIDS,'%') and t1.dtModifyAt between ? and ? "
				+ str +"ORDER BY t1.ID DESC";
		List<Map<String, Object>> list = dbUtils.find(sql, params);
		return list.size();
	}
	
	DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	/**
	 * 系统日志列表
	 * @param sProjectIDS
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> QuerySystemLogoDataList(String sProjectIDS,Integer userid, String starttime, String endtime, Integer page,
			Integer pageSize) {
		String str = "";
		Object params[];
		if(userid > 0) {
			Object param[] = {sProjectIDS,starttime,endtime,userid,page,pageSize};
			str = " and t1.nUserID = ? ";
			params = param;
		}else {
			Object param[] = {sProjectIDS,starttime,endtime,page,pageSize};
			params = param;
		}

		String sql = "SELECT t1.ID,t1.nUserID,t1.sAction,t1.sDetail,t1.dtModifyAt,t2.sUserName,t2.sProjectIDS FROM log_base_useraction t1 INNER JOIN base_user_info t2 ON t1.nUserID = t2.ID  WHERE ? LIKE CONCAT('%',t2.sProjectIDS,'%') and t1.dtModifyAt between ? and ? "
				+ str +"ORDER BY t1.ID DESC LIMIT ?,?";
		List<Map<String, Object>> list = dbUtils.find(sql, params);
		return list;
		
	}

	public String SystemLogDelted(Connection con, String idsString) throws SQLException {
		// TODO Auto-generated method stub
		 String result="ok";
		
		if(null!=idsString){
			
			String[] ids=idsString.split(",");
			for (int i = 0; i < ids.length; i++) {
				 Statement st;
				  st = (Statement) con.createStatement(); 
				  boolean count= st.execute("delete from user_active where id="+ids[i]+""); 
			}
		}
		
		return result;
	}	 
    
}
