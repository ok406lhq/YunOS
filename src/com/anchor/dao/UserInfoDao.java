package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.db.DBUtilsTemplate;
import com.anchor.model.ProjectInfo;
import com.anchor.model.User;
import com.anchor.util.Connest;
import com.anchor.util.DateUtil;
import com.anchor.util.HttpClientUtil;
public class UserInfoDao {
	public ResultSet m_rset = null;
	public PreparedStatement m_pstmt = null;
	public List<User> QueryUserInfoAll(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
//		List<User> user = new ArrayList<User>();
		
		String sql="select * from users order by id";
		
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		List<User> user = new ArrayList<User>();
	
		  while(m_rset.next()){
			  User user1=new User();
			  
			  user1.setid(m_rset.getDouble("id"));
			  user1.setUserID(m_rset.getString("userID"));
			  user1.setUserName(m_rset.getString("name"));
			  user1.setPassword(m_rset.getString("pwd"));
			  user1.setmobile(m_rset.getString("mobile"));
			  user1.seteTelNum(m_rset.getString("eTelNum"));
			  user1.setemail(m_rset.getString("email"));
			  user1.setrole(m_rset.getString("role"));
			  user1.setloginnum(m_rset.getInt("loginnum"));
			  user1.setloginIp(m_rset.getString("loginIp"));
			  user1.setlastlogindata(m_rset.getString("lastlogindata"));
			  user1.setadddate(m_rset.getString("adddate"));
			  user1.setposition(m_rset.getString("position"));
			  user1.setdepartment(m_rset.getString("department"));
			  user1.setaddress(m_rset.getString("address"));
			  user1.setdefPrj(m_rset.getString("defPrj"));
			  user1.setavatar(m_rset.getString("avatar"));
			  user.add(user1);
		  }
		return user;
	}
	public JSONArray QueryRoles(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		JSONArray jArray=new JSONArray();
		String sql="select * from role order by id desc";
		StringBuffer  sbf = new StringBuffer(sql);
		m_pstmt = conn.prepareStatement(sbf.toString());
		m_rset = m_pstmt.executeQuery();
		while(m_rset.next()){
			JSONObject rl=new JSONObject();
			rl.put("id", m_rset.getString("id"));
			rl.put("role", m_rset.getString("rolename"));
			jArray.add(rl);
		}
		return jArray;
	}
	public String UserInfoAdd(Connection conn, User user) throws Exception {
		// TODO Auto-generated method stub
		  Statement st;
		  String result="wrong";
		   String userID=user.getUserID();
		   String password=user.getPassword();
		   String mobile=user.getmobile();
		   String email=user.getemail();
		   String role=user.getrole();
		   String department=user.getdepartment();
		   String position=user.getposition();
		   String address=user.getaddress();
		   String adddate=DateUtil.getNowPlusTime();
		   String sqlString="insert into users(userID,name,pwd,mobile,email,role,loginnum,department,position,address,adddate) values  ";
		   sqlString+="(\"" + userID + "\",\"" + userID + "\",\"" + password + "\",\"" + mobile + "\",\"" + email + "\",\"" + role + "\",1,\"" + department + "\",\"" + position + "\",\"" + address + "\",\"" + adddate + "\")";
		
		   StringBuffer  sbf = new StringBuffer(sqlString);
			System.out.print(sqlString);
			  st = (Statement) conn.createStatement(); 
			int count = st.executeUpdate(sqlString);  // 执行插入操作的sql语句，并返回插入数据的个数   

			if(count>0)

			    {

				result="ok";

			    }         
		   return result;
		
	}
	public String UserInfoEdit(Connection conn, User user) throws Exception{
		// TODO Auto-generated method stub
		 String result="wrong";
		 String url;
		 String userId = URLEncoder.encode(user.getUserID(),"utf-8");
		 String userName = URLEncoder.encode(user.getUserName(),"utf-8");
		 String tel = URLEncoder.encode(user.geteTelNum(),"utf-8");
		 String email = URLEncoder.encode(user.getemail(),"utf-8");
		 String partMent = URLEncoder.encode(user.getdepartment(),"utf-8");
		 String position = URLEncoder.encode(user.getposition(),"utf-8");
		 String address = URLEncoder.encode(user.getaddress(),"utf-8");
		 String pwd = user.getPassword();
		 if(pwd == null || pwd.equals("")){
			 url = Connest.URL_USER_UserInfoEdit;
			 url = String.format(url, userId,userName,tel,email,partMent,position,address);
		 }else {
			 pwd = URLEncoder.encode(user.getPassword(),"utf-8");
			 url = Connest.URL_USER_UserInfoEdit_pwd;
			 url = String.format(url, userId,userName,tel,email,partMent,position,address,pwd);
		 }
		 String res=HttpClientUtil.doGet(url,"utf-8");
		 res=URLDecoder.decode(res, "utf-8");
		 org.json.JSONObject obj=new org.json.JSONObject(res);
		 String resCode=obj.getString("code");
		 if(!"00".equals(resCode)){
		 	 throw new Exception(obj.getString("msg"));
		 }else {
			 result = "ok";
		 }
//		 Statement st;
//		   Double id=user.getid();
//		   String userID=user.getUserID();
//		   String password=user.getPassword();
//		   String mobile=user.getmobile();
//		   String email=user.getemail();
//		   String role=user.getrole();
//		   String department=user.getdepartment();
//		   String position=user.getposition();
//		   String address=user.getaddress();
//		   String sqlString="Update users set userID=\"" + userID + "\",name=\"" + userID + "\",pwd=\"" + password + "\",mobile=\"" + mobile + "\",email=\"" + email + "\",role=\"" + role + "\",";
//		   sqlString+="department=\"" + department + "\",position=\"" + position + "\",address=\"" + address + "\",projects=\""+user.getProject()+"\" where id="+id+"";
//		   System.err.print(sqlString);
//		   st = (Statement) conn.createStatement(); 
//			int count = st.executeUpdate(sqlString); 
//			if(count>0)
//
//		    {
//
//			result="ok";;
//
//		    }         
	   return result;
	}
	public String UserInfoDel(Connection conn, String id) throws SQLException {
		// TODO Auto-generated method stub
		 String result="ok";
		 Statement st;
		  st = (Statement) conn.createStatement(); 
		  boolean count = st.execute("delete from users where id="+id+""); 
		  
		return result;
	}
	
	public String WarmSetAdd(Connection conn, User user, String act, String id) throws SQLException {
		// TODO Auto-generated method stub
		    Statement st;
		    String result="wrong";
		    String userName=user.getuserName();
	    	String prjName=user.getprjName();
	    	String Wtext=user.getWtext();
	    	String warningtype=user.getwarningtype();
	    	String warningLevel=user.getwarningLevel();
	    	String msgInterval=user.getmsgInterval();
	    	String sqlString="";
	    	if("add".equals(act)){
	    		 sqlString="insert into warningdef (prjName,userName,warningType,warningLevel,WText,msgInterval) values(\"" + prjName + "\",\"" + userName + "\",\"" + warningtype + "\",\"" + warningLevel + "\",\"" + Wtext + "\",\"" + msgInterval + "\")";
	    	}else if("edit".equals(act)){
	    		sqlString="update warningdef set prjName=\"" + prjName + "\",userName=\"" + userName + "\",Wtext=\"" + Wtext + "\",warningtype=\"" + warningtype + "\",warningLevel=\"" + warningLevel + "\",msgInterval=\"" + msgInterval + "\" where id="+id;
	    	}
	    	System.out.print(sqlString);
	    	 st = (Statement) conn.createStatement(); 
				int count = st.executeUpdate(sqlString); 
				if(count>0)

			    {

				result="ok";

			    }         
		   return result;
	    	
	}
	
	DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	public JSONObject queryUserIfoByID(Connection conn, String userID) throws Exception {
		/*String sql = "SELECT nCompanyID,sUserName,sLoginAccount,sPassWord,sWXOpenid,sWXNickname,sPartMent,sPosition,sSex,sAddress,sTel,sEMail,sRoler,sProjectIDS,nState,nLoginTimes,sLastLoginIP,dtLastLoginDT,dtCreateAt,dtModifyAt,nModifyBy FROM base_user_info t1 where ID = ?";
		List<Map<String, Object>> list = dbUtils.find(sql, userID);
		JSONObject WSSItem = null;
		for (Map<String, Object> map : list) {
			WSSItem = new JSONObject();
			WSSItem.put("ID", map.get("ID"));
			WSSItem.put("nCompanyID", map.get("nCompanyID"));
			WSSItem.put("sUserName", map.get("sUserName"));
			WSSItem.put("sLoginAccount", map.get("sLoginAccount"));
			WSSItem.put("sPassWord", map.get("sPassWord"));
			WSSItem.put("sWXOpenid", map.get("sWXOpenid"));
			WSSItem.put("sWXNickname", map.get("sWXNickname"));
			WSSItem.put("sPartMent", map.get("sPartMent"));
			WSSItem.put("sPosition", map.get("sPosition"));
			WSSItem.put("sSex", map.get("sSex"));
			WSSItem.put("sAddress", map.get("sAddress"));
			WSSItem.put("sTel", map.get("sTel"));
			WSSItem.put("sEMail", map.get("sEMail"));
			WSSItem.put("sRoler", map.get("sRoler"));
			WSSItem.put("sProjectIDS", map.get("sProjectIDS"));
			WSSItem.put("nState", map.get("nState"));
			WSSItem.put("nLoginTimes", map.get("nLoginTimes"));
			WSSItem.put("sLastLoginIP", map.get("sLastLoginIP"));
			WSSItem.put("dtLastLoginDT", map.get("dtLastLoginDT"));
			WSSItem.put("dtCreateAt", map.get("dtCreateAt"));
			WSSItem.put("dtModifyAt", map.get("dtModifyAt"));
			WSSItem.put("nModifyBy", map.get("nModifyBy"));
		}
		return WSSItem;*/
		
		// TODO Auto-generated method stub
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		String url=Connest.URL_USER_QueryUserIfonByID;
		String id = URLEncoder.encode(userID,"utf-8");
		url=String.format(url,id);
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
			jsonObject.put("userID", objItem.getString("UserID"));
			jsonObject.put("name", objItem.getString("name"));
			jsonObject.put("mobile", objItem.getString("mobile"));
			jsonObject.put("eTelNum", objItem.getString("eTelNum"));
			jsonObject.put("email", objItem.getString("email"));
			jsonObject.put("role", objItem.getString("role"));
			jsonObject.put("loginnum", objItem.getInt("loginnum"));
			jsonObject.put("loginIp", objItem.getString("loginIp"));
			jsonObject.put("lastlogindata", objItem.getString("lastlogindata"));
			jsonObject.put("adddate", objItem.getString("adddate"));
			jsonObject.put("department", objItem.getString("department"));
			jsonObject.put("position", objItem.getString("position"));
			jsonObject.put("address", objItem.getString("address"));
			jsonObject.put("defPrj", objItem.getString("defPrj"));
		}
		return jsonObject;
	}
	public List<User> QuerWarnListDao(Connection conn, int page, int pageSize) throws SQLException {
		
		List<User> warnlist=new ArrayList<User>();
		
		if(pageSize==0){
			pageSize=10;
		}
		if(page==0){
			page=1;
		}
		int startPage=(page-1)*pageSize;
		int endPage=page*pageSize;
		String sqlString="select w.id,w.prjName,w.userName,w.warningLevel,w.warningType,w.msgInterval, p.PrjAlias from warningdef w,prjinfo p where w.prjName=p.PrjName order by w.id desc limit  "+ startPage+","+pageSize;
		System.out.print(sqlString);
		StringBuffer  sbf = new StringBuffer(sqlString);
		m_pstmt=conn.prepareStatement(sbf.toString());
		m_rset=m_pstmt.executeQuery();
		while(m_rset.next()){
			User warninfo=new User();
			warninfo.setwarnID(m_rset.getInt("ID"));
			warninfo.setuserName(m_rset.getString("userName"));
			warninfo.setprjName(m_rset.getString("PrjAlias"));
			warninfo.setwarningLevel(m_rset.getString("warningLevel"));
			warninfo.setwarningtype(m_rset.getString("warningType"));
			warninfo.setmsgInterval(m_rset.getString("msgInterval"));
			//warninfo.setWtext(m_rset.getString("WText"));
			warnlist.add(warninfo);
			
		}
		
		return warnlist;
		// TODO Auto-generated method stub
		
	}
	public int QueryWarnTotalRecord(Connection conn) throws SQLException {
		String sql="select count(*) as count from warningdef";
		m_pstmt=conn.prepareStatement(sql);
		m_rset=m_pstmt.executeQuery();
	   int totalRecord = 0;
		while(m_rset.next()){
			totalRecord= m_rset.getInt("count");
		}
		// TODO Auto-generated method stub
		return totalRecord;
	}
	public User QueryWarnDataByIdDao(Connection conn, String id) throws SQLException {
		// TODO Auto-generated method stub
		JSONObject jsonObject=new JSONObject();
		User warninfo=new User();
		String sqlString="select * from warningdef where id="+id;
		m_pstmt=conn.prepareStatement(sqlString);
		m_rset=m_pstmt.executeQuery();
		if(m_rset.next()){
			
			warninfo.setwarnID(m_rset.getInt("ID"));
		
			warninfo.setuserName(m_rset.getString("userName"));
			warninfo.setprjName(m_rset.getString("prjName"));
			warninfo.setwarningLevel(m_rset.getString("warningLevel"));
			warninfo.setwarningtype(m_rset.getString("warningType"));
			warninfo.setInterval(m_rset.getString("msgInterval"));
			warninfo.setmsgInterval(m_rset.getString("msgInterval"));
			warninfo.setWtext(m_rset.getString("WText"));
		
		}
		return warninfo;
		
	}
	public String WarnDataDelById(Connection conn, String id) throws SQLException {
		// TODO Auto-generated method stub
		 String result="ok";
		 Statement st;
		  st = (Statement) conn.createStatement(); 
		  boolean count = st.execute("delete from warningdef where id="+id+""); 
		  
		return result;
	}
	public int UserInfoEdit(User user) throws Exception{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//sUserName,sPosition,sTel,sEMail,sPartMent,sAddress,dtCreateAt,ID,sPassWord
	
		 int result=0;
		 String url;
		 String userId = URLEncoder.encode(user.getUserID(),"utf-8");
		 String userName = URLEncoder.encode(user.getUserName(),"utf-8");
		 String tel = URLEncoder.encode(user.getmobile(),"utf-8");
		 String email = URLEncoder.encode(user.getemail(),"utf-8");
	
		 String position = URLEncoder.encode(user.getposition(),"utf-8");
		 String address = URLEncoder.encode(user.getaddress(),"utf-8");
		 String pwd = URLEncoder.encode(user.getPassword(),"utf-8");
		 
		 String partMent = URLEncoder.encode(user.getdepartment(),"utf-8");
		 
		 if(userName.equals("")) {
			 
			 
			 userName = null;
		 }
	 	 if(tel.equals("")) {
			 
			 
	 		tel = null;
		 }
	 	 if(email.equals("")) {
			 
			 
	 		email = null;
		 }

	 	 if(position.equals("")) {
			 
			 
	 		position = null;
		 }
	 	 
	 	 if(address.equals("")) {
			 
			 
	 		address = null;
		 }
	 	 if(pwd.equals("")) {
			 
			 
	 		pwd = null;
		 }
	 	
	 	if(partMent.equals("")) {
			 
			 
	 		partMent = null;
		 }
	 	
		 //http://172.10.3.45:80/BDSIMP/public/web/user_info_dao/UserInfoEdit/userID/130/sUserName/winner/sTel//sEMail//sPartMent//sPosition//sAddress/
		 
		 if(pwd == null || pwd.equals("")){
			 url = Connest.URL_USER_UserInfoEdit;
			 url = String.format(url, userId,userName,tel,email,partMent,position,address);
		 }else {
			 
			 url = Connest.URL_USER_UserInfoEdit_pwd;
			 url = String.format(url, userId,userName,tel,email,partMent,position,address,pwd);
		 }
		 String res=HttpClientUtil.doGet(url,"utf-8");
		 res=URLDecoder.decode(res, "utf-8");
		 org.json.JSONObject obj=new org.json.JSONObject(res);
		 String resCode=obj.getString("code");
		 if(!"00".equals(resCode)){
		 	 throw new Exception(obj.getString("msg"));
		 }else {
			 result = 1;
		 }
     
		 return result;
		
		
	}
	
}
