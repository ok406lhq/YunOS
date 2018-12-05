package com.anchor.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.SystemLogService;

import java.util.zip.*;
/**
 * Servlet implementation class Download
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String urlString = request.getParameter("url");
		urlString=new String(urlString.getBytes("iso-8859-1"),"utf-8");
		// String path = this.getServletContext().getRealPath("");
		String path = "http://172.20.39.14/picserver/export";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
		if("firefox".equals(getExplorerType(request))){
			response.setHeader("Content-Disposition", "attachment; filename=" +new String(urlString.getBytes("GB2312"),"ISO-8859-1"));
		}else{
			response.setHeader("Content-Disposition", "attachment; filename=" +URLEncoder.encode(urlString,"UTF-8").replaceAll("%2B","\\+"));
		}
		/// ----------------保存下载行为到系统日志********/
		try {
			SystemLogService.addSystemLogData("下载", "下载文件" + urlString, request);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		urlString = path + "/" + URLEncoder.encode(urlString,"UTF-8");
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] e = new byte[8192];

			int bytesRead;
			while (-1 != (bytesRead = bis.read(e, 0, e.length))) {
				bos.write(e, 0, bytesRead);
			}
		} catch (IOException arg24) {
			throw arg24;
		} finally {
			if (bis != null) {
				bis.close();
			}

			if (bos != null) {
				bos.close();
			}

		}
//		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
//		File[] files = new File[1];
//		/*这里可以根据前台进行赋值,我这样做事为了快速做测试而已 */
//		//urlString=path+"/up/ak14.pdf";
//		urlString=path+"/"+urlString;
//		
//		
//		files[0] = new File(urlString);
//		
//		for(File f:files){
//			zipFile(f, "", zos);
//		}
//	
//		zos.flush();
//		zos.close();		
		
	}

	private  String getExplorerType(HttpServletRequest request) {
		String agent = request.getHeader("USER-AGENT");
		if (agent != null && agent.toLowerCase().indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent != null && agent.toLowerCase().indexOf("msie") > 0) {
			return "ie";
		} else if (agent != null && agent.toLowerCase().indexOf("chrome") > 0) {
			return "chrome";
		} else if (agent != null && agent.toLowerCase().indexOf("opera") > 0) {
			return "opera";
		} else if (agent != null && agent.toLowerCase().indexOf("safari") > 0) {
			return "safari";
		}
		return "others";
	}

		/**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}
	
	private void zipFile(File subs, String baseName, ZipOutputStream zos)throws IOException {
		if(subs.exists()){
			
			if(subs.isFile()){
				zos.putNextEntry(new ZipEntry(baseName + subs.getName()));
				FileInputStream fis = new FileInputStream(subs);
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
				fis.close();
			}else{
				//如果是目录。递归查找里面的文件
				String dirName = baseName + subs.getName() + "/";
				zos.putNextEntry(new ZipEntry(dirName));
				File[] sub = subs.listFiles();
				for (File f : sub) {
					zipFile(f, dirName, zos);
				}
			}
		}
	
	}

	/**
	 * 获取zip文件名
	 * @return
	 */
	private String getZipFilename() {
		Date date = new Date();
		String s = date.getTime() + ".zip";
		return s;
	}

}
