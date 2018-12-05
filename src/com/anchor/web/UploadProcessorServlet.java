package com.anchor.web;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.regex.Matcher;  
import java.util.regex.Pattern; 

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.anchor.dao.PointInfoDao;
import com.anchor.dao.ProjectImageBg;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;



/**
 * Servlet implementation class UploadProcessorServlet
 */
public class UploadProcessorServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void destroy() {
		super.destroy(); 
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       doPost(request, response);
	}

	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn=null;
		DbUtil dbUtil=null;
		response.setContentType("text/html");   
		response.setCharacterEncoding("UTF-8");
		String realDir = request.getSession().getServletContext().getRealPath("");
	    String prjNameString=request.getParameter("prjName");
		String contextpath = request.getContextPath();
		String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextpath + "/";
		String timeString=request.getParameter("time");
		String typeString=request.getParameter("type");
       
		String sensorname=request.getParameter("sensorname");
		try {
		String filePath = "uploadfiles";
		String realPath = realDir+"\\"+filePath;
		
		//判断路径是否存在，不存在则创建
		File dir = new File(realPath);
		System.out.println("realPath:"+realPath);
		System.err.println(realPath);
		if(!dir.isDirectory())
		    dir.mkdir();
		
		if(ServletFileUpload.isMultipartContent(request)){

		    DiskFileItemFactory dff = new DiskFileItemFactory();
		    dff.setRepository(dir);
		    dff.setSizeThreshold(4096);
		    ServletFileUpload sfu = new ServletFileUpload(dff);
		    FileItemIterator fii = null;
		    fii = sfu.getItemIterator(request);
		    String title = "";   //图片标题
		    String url = "";    //图片地址
		    String fileName = "";
			String state="SUCCESS";
			String realFileName="";
			
		    while(fii.hasNext()){
		        FileItemStream fis = fii.next();

		        try{
		            if(!fis.isFormField() && fis.getName().length()>0){
		                fileName = fis.getName();
						Pattern reg=Pattern.compile("[.]jpg|png|jpeg|gif$");
						Matcher matcher=reg.matcher(fileName);
						if(!matcher.find()) {
							state = "文件类型不允许！";
							break;
						}
						String FileName=new Date().getTime()+"";
						if(typeString.equals("point")){
							FileName=timeString;
							
						}
						realFileName = FileName+fileName.substring(fileName.lastIndexOf("."),fileName.length());
						
		                url = realPath+"\\"+realFileName;

		                BufferedInputStream in = new BufferedInputStream(fis.openStream());//获得文件输入流
		                FileOutputStream a = new FileOutputStream(new File(url));
		                BufferedOutputStream output = new BufferedOutputStream(a);
		                Streams.copy(in, output, true);//开始把文件写到你指定的上传文件夹
		            }else{
		                String fname = fis.getFieldName();

		                if(fname.indexOf("pictitle")!=-1){
		                    BufferedInputStream in = new BufferedInputStream(fis.openStream());
		                    //byte c [] = new byte[10];
		                    byte[] c = new byte[1024];
		                    int n = 0;
		                    while((n=in.read(c))!=-1){
		                        title = new String(c,0,n);
		                        break;
		                    }
		                }
		            }

		        }catch(Exception e){
		            e.printStackTrace();
		        }
		    }
		 
         
		   // response.setStatus(200);
		   // String retxt =basePath+filePath+"/"+realFileName;
		    String retxt =filePath+"/"+realFileName;
		    System.out.print(retxt);
		   
		    if(typeString.equals("point")){
		    	dbUtil=new DbUtil();
				dbUtil.SetDatabase(Connest.DbName);
				conn = dbUtil.getCon();
				PointInfoDao pointInfoDao=new PointInfoDao();
				pointInfoDao.PointImgUrlSave(conn,retxt,sensorname);
				
				 SystemLogService.addSystemLogData("上传","上传点位："+sensorname+"的背景图片",request); ///----------------保存上传行为到系统日志
		     
		    }else{
		    	/*
		    	File f = new File(retxt);
		    	double newHeight = 1.0;
		    	double newWeight = 1.0; 
		    	String pathdir=filePath;
		    	File newFile = new File(pathdir,realFileName); 
		    	BufferedImage bi = ImageIO.read(f);
		    	Image itemp = bi.getScaledInstance (780,120,bi.SCALE_SMOOTH);
		    	*/
		    	   dbUtil=new DbUtil();
				   conn= dbUtil.getCon();
				
//				   if (null != conn){
					 
					   ProjectImageBg projectimgageBg=new ProjectImageBg();
					   projectimgageBg.SaveProjectImageBg(conn, prjNameString, realFileName);
//				   }
				   SystemLogService.addSystemLogData("上传","上传项目："+prjNameString+"的背景图片",request); ///----------------保存上传行为到系统日志
				   response.sendRedirect("./app/upload.jsp?prjName="+prjNameString);
		    }
		   
		  
		   response.getWriter().print(retxt);
		  
		  
		   // HtmlUtil.writerJson(response, "ok");
		}
		}catch(Exception ee) {
			ee.printStackTrace();
		}
		
	}
	public void init() throws ServletException {
		// Put your code here
	}

}
