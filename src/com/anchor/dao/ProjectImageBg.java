package com.anchor.dao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;

import java.sql.ResultSet;

import com.anchor.util.Connest;
import com.anchor.util.HttpClientUtil;

import java.sql.PreparedStatement;

public class ProjectImageBg {
	public ProjectImageBg() {
		super();
	}
	
	public void SaveProjectImageBg(Connection conn,String prjName,String imgUrl) throws Exception{
		String url = Connest.URL_PROJECT_SaveProjectImageBg;
		String name = URLEncoder.encode(prjName,"utf-8");
		String img = URLEncoder.encode(imgUrl,"utf-8");
		url = String.format(url, name,img);
		System.out.println("imgUrl:"+imgUrl);
		String res=HttpClientUtil.doGet(url,"utf-8");
		res=URLDecoder.decode(res, "utf-8");
		
		org.json.JSONObject obj=new org.json.JSONObject(res);
		String resCode=obj.getString("code");
		if(!"00".equals(resCode)){
			throw new Exception(obj.getString("msg"));
		}
//		String sql="update prjinfo set Bgimg=\""+ imgUrl + "\" where prjName=\""+prjName+  "\"";
//		System.out.print(sql);
//		StringBuffer sbf=new StringBuffer(sql);
//		PreparedStatement pStatement=conn.prepareStatement(sbf.toString());
//		pStatement.executeUpdate();
		
		
	}
}
