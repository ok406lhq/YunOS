package com.anchor.util;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.*;


public class DbUtil extends HttpServlet{
/*
	private String dbUrl="jdbc:mysql://localhost:3306/anchor_lztest?useUnicode=true&characterEncoding=utf-8";
	private String dbUserName="root";
	private String dbPassword="root";

	private String jdbcName="com.mysql.jdbc.Driver";
	*/
	
	private String dbUrl=Connest.dbUrl+"?useUnicode=true&characterEncoding=utf-8";
	private String dbUserName=Connest.dbUserName;

	private String dbPassword=Connest.dbPassword;

	private String jdbcName=Connest.jdbcName;
	
	
	public String SetDatabase(String dbName)
	{
		dbUrl="jdbc:mysql://192.168.1.111:3306/" + dbName;
		dbUrl="jdbc:mysql://127.0.0.1:3306/" + dbName;
		return dbUrl;
	}
	/**
	 * ��ȡ���ݿ�����
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception{
//		Class.forName(jdbcName);
//		Connection con=DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return null;
	}
	
	/**
	 * �ر����ݿ�����
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception{
		if(con!=null){
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			//dbUtil.getCon();
			System.out.println("数据库连接错误！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
