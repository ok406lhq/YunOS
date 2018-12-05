package com.anchor.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.model.User;
import com.anchor.service.SystemLogService;
import com.anchor.service.UserService;
import com.anchor.util.DateUtil;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONObject;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService service = new UserService();
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type.equals("QID")){
			String ID = request.getParameter("ID");
			JSONObject object = service.findUserObject(ID);
			HtmlUtil.writerJson(response, object);
		}else if(type.equals("U")){
			String ID = request.getParameter("ID");
			String sUserName = request.getParameter("sUserName");
			String sPosition = request.getParameter("sPosition");
			String sTel = request.getParameter("sTel");
			String sEMail = request.getParameter("sEMail");
			String sPartMent = request.getParameter("sPartMent");  
			String sAddress = request.getParameter("sAddress");
			String sPassWord = request.getParameter("sPassWord");
		   
			String dtCreateAt;
			try {
				dtCreateAt = DateUtil.getNowPlusTime();
			
				
				User user = new User(ID, sUserName, sPassWord, sPartMent, sTel, sEMail, dtCreateAt, sPosition,sAddress);
				
				//System.out.println(sUserName+","+sPosition+","+sTel+","+sEMail+","+sPartMent+","+sAddress+","+dtCreateAt+","+ID+"---"+params.length);
				JSONObject row = service.updateUserInfo(user);
				
				
				
				if(row != null){
					SystemLogService.addSystemLogData("修改","用户信息修改",request); ///----------------保存修改行为到系统日志
					HtmlUtil.writerJson(response, row);
				}else{
					HtmlUtil.writerJson(response, row);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	}

}
