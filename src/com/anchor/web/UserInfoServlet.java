package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.anchor.model.User;
import com.anchor.dao.UserDao;
import com.anchor.dao.UserInfoDao;
import com.anchor.service.SystemLogService;
import com.anchor.service.UserInfoService;
import com.anchor.service.UserService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class UserInfo
 */
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service = new UserService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DbUtil dbUtil=null;
		Connection conn=null;
		String oper=request.getParameter("oper");
		try{
			//dbUtil=new DbUtil();
			
			//conn = dbUtil.getCon();
			String UserID=request.getParameter("username");
	    	String password=request.getParameter("password");
	    	String mobile=request.getParameter("mobile");
	    	String email=request.getParameter("email");
	    	String role=request.getParameter("role");
	    	String department=request.getParameter("department");
	    	String position=request.getParameter("position");
	    	String address=request.getParameter("address");
		    if("QueryAll".equals(oper)){ //查询所有用户操作.ou
		    	HttpSession session = request.getSession(true);
		    	String userID=(String)session.getAttribute("userID");
	    		UserService service = new UserService();
	    		JSONObject object = service.findUserObject(userID);
		    	JSONArray array = service.findAllUserObject((String)object.get("sProjectIDS"));
		    	HtmlUtil.writerJson(response, array);
		    }else if("role".equals(oper)){
		    	
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	JSONArray rolesString=userInfoDao.QueryRoles(conn);
		    	HtmlUtil.writerJson(response, rolesString);
		    	
		    }else if("userinfo".equals(oper)){
		    	HttpSession session = request.getSession(true);
		    	JSONObject  jsonObject=new JSONObject();
		    	if(null!=session){
		    		 String userID=(String)session.getAttribute("userID");
		    		 UserInfoDao userInfoDao=new UserInfoDao();
		    		 JSONObject userinfo= userInfoDao.queryUserIfoByID(conn,userID);
		    		 HtmlUtil.ToJson(response, userinfo);
		    		 SystemLogService.addSystemLogData("查询","查询ID"+userID+"用户信息",request); ///----------------保存查询行为到系统日志
		    	}
	    		  
	    		 
		   }else if("add".equals(oper)){
		    	
		    	User user=new User();
		    	user.setUserID(UserID);
		    	user.setPassword(password);
		    	user.setmobile(mobile);
		    	user.setemail(email);
		    	user.setrole(role);
		    	user.setdepartment(department);
		    	user.setposition(position);
		    	user.setaddress(address);
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	String msg=userInfoDao.UserInfoAdd(conn,user);
		    	HtmlUtil.writerHtml(response, msg);
		    	SystemLogService.addSystemLogData("添加","添加了一条用户信息",request); ///----------------保存添加行为到系统日志
		    }else if("edit".equals(oper)){
		    	String id=request.getParameter("userid");
//		    	String project=request.getParameter("prj");
		    	String eTelNum = request.getParameter("eTelNum");
		    	User user=new User();
		    	user.setid(Double.valueOf(id));
		    	user.setUserID(id);
		    	user.setUserName(UserID);
		    	user.setPassword(password);
		    	user.setmobile(mobile);
		    	user.setemail(email);
//		    	user.setrole(role);
		    	user.seteTelNum(eTelNum);
		    	user.setdepartment(department);
		    	user.setposition(position);
		    	user.setaddress(address);
//		    	user.setProject(project);
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	String msg=userInfoDao.UserInfoEdit(conn,user);
		    	//SystemLogService.addSystemLogData("编辑","编辑ID"+id+"的用户信息",request); ///----------------保存添加行为到系统日志
		    	HtmlUtil.writerHtml(response, msg);
		    }else if("del".equals(oper)){
		    	String id=request.getParameter("id");
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	String msg=userInfoDao.UserInfoDel(conn,id);
		    	HtmlUtil.writerHtml(response, msg);
		    	SystemLogService.addSystemLogData("删除","删除ID"+id+"的用户信息",request); ///----------------保存删除行为到系统日志
		    }else if("warnset".equals(oper)){
		    	request.setCharacterEncoding("UTF-8");
		    	String userName=request.getParameter("userName");
		    	String prjName=request.getParameter("prjName");
		    	String Wtext=request.getParameter("Wtext");
		    	String warningtype=request.getParameter("warningtype");
		    	String warningLevel=request.getParameter("warningLevel");
		    	  String msgInterval=request.getParameter("msgInterval");
		    	String act=request.getParameter("act");//区分下是增加还是修改
		    	String id=request.getParameter("id");
		    	User user=new User();
		    	user.setuserName(userName);
		    	user.setprjName(prjName);
		    	user.setwarningtype(warningtype);
		    	user.setWtext(Wtext);
		    	user.setwarningLevel(warningLevel);
		    	user.setmsgInterval(msgInterval);
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	String msg=userInfoDao.WarmSetAdd(conn,user,act,id);
		    	
		    	HtmlUtil.writerHtml(response, msg);
		    	SystemLogService.addSystemLogData("添加","添加了一条预警设置信息",request); ///----------------保存添加行为到系统日志
		    }else if("QueryWarnlist".equals(oper)){
		    	JSONObject result=new JSONObject();
		        JSONArray array=new JSONArray();
		    	 int page=Integer.valueOf(request.getParameter("page"));
		    	 int pageSize=Integer.valueOf(request.getParameter("pageSize"));
		       
		    	UserInfoDao userInfoDao=new UserInfoDao();
		    	int totalRecord;
		    	totalRecord=userInfoDao.QueryWarnTotalRecord(conn);
		    	UserInfoService userInfoService=new UserInfoService();
		    	
		    	array= userInfoService.QueryWarnList(conn,page,pageSize);
		    	result.put("totalRecord",totalRecord);
		    	result.put("data",array);
		    	HtmlUtil.writerJson(response, result);
		    	SystemLogService.addSystemLogData("查询","查询预警设置信息",request); ///----------------保存查询行为到系统日志
		    	
		    }else if("QueryWarnByID".equals(oper)){
		    	JSONObject jsonObject=new JSONObject();
		    	String id=request.getParameter("id");
		    	UserInfoService userInfoService=new UserInfoService();
		    	jsonObject=userInfoService.QueryWarnDataById(conn,id);
		    	HtmlUtil.writerJson(response, jsonObject);
		    	SystemLogService.addSystemLogData("查询","查询ID"+id+"预警设置信息",request); ///----------------保存查询行为到系统日志
			}else if("delwarn".equals(oper)){
				String id=request.getParameter("id");
				
				 UserInfoDao warnfoDao=new UserInfoDao();
				String msg= warnfoDao.WarnDataDelById(conn,id);
				HtmlUtil.writerHtml(response, msg);
				 SystemLogService.addSystemLogData("删除","删除了ID"+id+"预警设置信息",request); ///----------------保存删除行为到系统日志
			}

		
	}
	catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {
			if (null != conn)
			{
				if (null != dbUtil)
				{
					dbUtil.closeCon(conn);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//HtmlUtil.writerHtml(response, Points);
	}

}
