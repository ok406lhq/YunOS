package com.anchor.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;

import com.anchor.util.Connest;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
       
        // TODO Auto-generated constructor stub
       
    }
    public void init() throws ServletException {
//    	Connest.jdbcName=this.getServletConfig().getInitParameter("driverClass");
//        Connest.dbUrl=this.getServletConfig().getInitParameter("url");
//        Connest.dbUserName=this.getServletConfig().getInitParameter("user");
//        Connest.dbPassword=this.getServletConfig().getInitParameter("password");
//        Connest.DbName=this.getServletConfig().getInitParameter("DbName");
//        Connest.DefaultPrjName=this.getServletConfig().getInitParameter("DefaultPrjName");
//        String port=this.getServletConfig().getInitParameter("servicePort");
//        if (port==""||port==null) {
//			Connest.UrlAndPort="http://"+this.getServletConfig().getInitParameter("serviceIP");
//		}else {
//			Connest.UrlAndPort="http://"+this.getServletConfig().getInitParameter("serviceIP")+":"+port;
//		}
//        System.out.println("设置成功!");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(request, response);
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 
    }
	

}
