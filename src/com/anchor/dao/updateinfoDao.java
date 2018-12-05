package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sound.midi.SysexMessage;

import org.json.JSONObject;

import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;

public class updateinfoDao {
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;

	public boolean updateinfo(Connection con, String id, String info,String userId) throws Exception {
		String url = Connest.URL_UPDATE_updateinfo;
		String prjID = URLEncoder.encode(id,"utf-8");
		String inf = URLEncoder.encode(info,"utf-8");
		String uId = URLEncoder.encode(userId,"utf-8");
		url = String.format(url, uId,prjID,inf);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
		org.json.JSONArray arrObjs=obj.getJSONArray("data");
		org.json.JSONObject jsonObject = (JSONObject) arrObjs.get(0);
		String result = jsonObject.getString("result");
		if(result.equals("true")){
			return true;
		}else{
			return false;
		}
//		String sql = "UPDATE syslog SET response=\"" + info + "\" WHERE Id=" + id;
//		System.err.println(sql);
//		try {
//			Statement st = con.createStatement();
//			int s = st.executeUpdate(sql);
//			if (s > 0) {
//				String sql2 = "SELECT COUNT(id),project FROM syslog WHERE active NOT IN ( 'DISCONN', 'CONN' ) AND ( ISNULL(response) OR response='') "
//						+ "AND project = ( SELECT project FROM syslog WHERE Id = " + id + " )";
//				System.err.println("sql2:"+sql2);
//				m_pstmt = con.prepareStatement(sql2);
//				m_rset = null;
//				m_rset = m_pstmt.executeQuery();
//				m_rset.next();
//				System.err.println(m_rset.getInt(1));
//				if (m_rset.getInt(1)==0) {
//					String sql3="SELECT project FROM syslog WHERE Id ="+id;
//					System.err.println("sql3:"+sql3);
//					PreparedStatement ps=con.prepareStatement(sql3);
//					ResultSet rs=ps.executeQuery();
//					rs.next();
//					String sql4="UPDATE prjinfo SET StatusVal='' WHERE PrjName='"+rs.getString(1)+"'";
//					System.err.println("sql4:"+sql4);
//					int flag=st.executeUpdate(sql4);
//				}
//				return true;
//			} else {
//				return false;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
}
