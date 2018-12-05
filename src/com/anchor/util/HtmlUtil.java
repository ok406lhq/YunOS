package com.anchor.util;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.anchor.util.json.JSONUtil;
  
public class HtmlUtil {
	
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,JSONUtil.toJSONString(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void ToJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,JSONUtil.toJSONString(object));
				System.out.println(JSONUtil.toJSONString(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
