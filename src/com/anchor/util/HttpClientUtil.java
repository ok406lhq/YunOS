package com.anchor.util;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.Map;  
  
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;  
import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;  
import org.apache.commons.httpclient.HttpMethod;  
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams;  
//import org.apache.http.client.methods.HttpPost;

public class HttpClientUtil {
	/** 
     * httpClient的get请求方式 
     *  
     * @param url 
     * @param charset 
     * @return 
     * @throws Exception 
     */  
    public static String doGet(String url, String charset) throws Exception {  
  
        HttpClient client = new HttpClient();  
        GetMethod method = new GetMethod(url);  
  
        if (null == url || !url.startsWith("http")) {  
            throw new Exception("请求地址格式不对");  
        }  
        // 设置请求的编码方式  
        if (null != charset) {  
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);  
        } else {  
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + "utf-8");  
        }  
        int statusCode = client.executeMethod(method);  
  
        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态  
        	throw new Exception("Method failed: " + method.getStatusLine());  
        	//System.out.println("Method failed: " + method.getStatusLine());  
        }  
        // 返回响应消息  
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());  
        // 在返回响应消息使用编码(utf-8或gb2312)  
        String response = new String(responseBody, "utf-8");  
        //System.out.println("------------------response:" + response);  
        // 释放连接  
        method.releaseConnection();  
        return response;  
    }  
}
