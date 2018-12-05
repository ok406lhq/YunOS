package com.anchor.web;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.anchor.service.WarningInfoService;
import org.json.JSONArray;
import org.json.JSONObject;

import com.anchor.util.Connest;
import com.anchor.util.DateUtil;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.HttpClientUtil;



/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Connection con = null;
		DbUtil dbUtil = null;
		 String result ="";
		 String userID;
		try{
			dbUtil=new DbUtil();
			con=dbUtil.getCon();
			HttpSession session=request.getSession(); 
			String code=request.getParameter("code").toLowerCase();
			String codeString="";
			Object codeTmp =session.getAttribute("verCode");
			if (codeTmp != null)
				codeString = codeTmp.toString();
			if(true)//if(code.equalsIgnoreCase(codeString))
			{ 
				String url=Connest.URL_Login;
				
				String username = URLEncoder.encode(request.getParameter("username"),"utf-8");
				String password = URLEncoder.encode(request.getParameter("password"),"utf-8");
				url=String.format(url,username,password);
				System.out.println("username:"+username+";password:"+password);
				System.out.println("url:"+url);
				String res=HttpClientUtil.doGet(url,"utf-8");
				
				res=URLDecoder.decode(res, "utf-8");
				System.out.println(res);
				JSONObject obj=new JSONObject(res);
				String resCode=obj.getString("code");
				if("00".equals(resCode)){
					JSONArray arrUsers=obj.getJSONArray("data");
					JSONObject objUser=arrUsers.getJSONObject(0);
					userID=objUser.getInt("UserID")+"";
					result="ok";
					 // HttpSession session = request.getSession(true);
					 // request.getSession().setAttribute("userID",userID);
					String[] roleArr=objUser.getString("role").split("\\|");
					session.setAttribute("userID", userID);
					session.setAttribute("username", username);
					session.setAttribute("role", roleArr);
					//将当前用户的项目信息保存到session中  yangqifang修改
					session.setAttribute("projects", objUser.getString("projects"));
					session.setMaxInactiveInterval(24*60*60*1000);

					String nowDateString=DateUtil.getNowPlusTime();
					//此方法应由后台执行
					//pStatement.executeUpdate("update users set loginnum=loginnum+1,lastlogindata=\""+ nowDateString + "\" where id="+userID);
					//SystemLogService.addSystemLogData("登录","登录系统",request); ///----------------保存登录行为到系统日志
					result=code.equals(codeString)?result:"codewrong";

					WarningInfoService service = new WarningInfoService();
					service.updateDialogAll(objUser.getString("projects"));
				}
				else
				{
					result="wrong";
				}

				
//				String sql = "select * from users where name=\""+ username + "\" and pwd=\""+ password + "\"";
//				StringBuffer sbf=new StringBuffer(sql);
//				PreparedStatement pStatement=con.prepareStatement(sbf.toString());
//				ResultSet rSet=pStatement.executeQuery();
//				if(rSet.next())
//				{
//					userID=rSet.getString("id");
//					result="ok";
//					 // HttpSession session = request.getSession(true);
//					 // request.getSession().setAttribute("userID",userID);
//					String[] roleArr=rSet.getString("role").split("\\|");
//					session.setAttribute("userID", userID);
//					session.setAttribute("username", username);
//					session.setAttribute("role", roleArr);
//					//将当前用户的项目信息保存到session中  yangqifang修改
//					session.setAttribute("projects", rSet.getString("projects"));
//					String nowDateString=DateUtil.getNowPlusTime();
//					pStatement.executeUpdate("update users set loginnum=loginnum+1,lastlogindata=\""+ nowDateString + "\" where id="+userID);
//					SystemLogService.addSystemLogData("登录","登录系统",request); ///----------------保存登录行为到系统日志
//				}
//				else
//				{
//					result="wrong";
//				}
				HtmlUtil.writerHtml(response, result);
			}
			else //if(code.equalsIg
			{
				HtmlUtil.writerHtml(response, "codewrong");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
//				if (null != con)
//				{
					if (null != dbUtil)
					{
						dbUtil.closeCon(con);
					}
//				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
