package com.anchor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest {
	public static String Get(String url, String param) {
		url += "?"+param;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			System.out.println("url:"+url);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line=in.readLine())!=null) {
				result+=line;
			}
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 测试HTTP请求方法
	 * @param args
	 */
	public static void main(String[] args) {
		//http://118.190.143.70:25030/ExportReport?prjName=gs&startTime=2016-12-12&endTime=2017-11-21
		String string=HttpRequest.Get("http://118.190.143.70:25032/ExportReport", "prjName=gs&startTime=2016-12-12&endTime=2017-11-21");
		System.err.println(string);
	}
}
