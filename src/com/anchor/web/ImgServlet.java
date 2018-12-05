package com.anchor.web;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class ImgServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String x=req.getParameter("x");
		String y=req.getParameter("y");
		String w=req.getParameter("w");
		String h=req.getParameter("h");
		String img=req.getParameter("img");
		System.out.println(img);
		String file="";
		//System.out.println(img.substring(img.lastIndexOf("/")+1,img.length()));
		//System.out.println(req.getSession().getServletContext().getRealPath("/"));
		String s="upload/"+img.substring(img.lastIndexOf("/")+1,img.length());
		System.out.println(s);
		try {
			file=cutImg(new File(req.getSession().getServletContext().getRealPath("/")+s),Integer.valueOf(x),Integer.valueOf(y),Integer.valueOf(w),Integer.valueOf(h));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();  
		System.out.println(file.substring(file.indexOf("u"),file.length()));
		out.println(file.substring(file.indexOf("u"),file.length()));  
        out.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 req.setCharacterEncoding("utf-8");  
	        resp.setContentType("text/html;charset=utf-8");  
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload sfu = new ServletFileUpload(factory);  
	
		try {
			 List<FileItem> items = sfu.parseRequest(req);
			for (int i = 0; i < items.size(); i++) {  
	             FileItem item = items.get(i);  
	             if(!item.isFormField()){  
	                 ServletContext sctx = getServletContext();  
	                 String path = sctx.getRealPath("/upload");  
	                // System.out.println(path);  
	                 //获得文件名  
	                 String fileName = item.getName();  
	           
	                // System.out.println(fileName);  
	                 fileName = fileName.substring(fileName.lastIndexOf("/")+1);  
	                 File file = new File(path+"\\"+fileName);  
	                 if(!file.exists()){  
	               	  System.out.println(fileName+"KKKKKKKKKKKKKKKKKK");
	                     item.write(file);
	                     String str="./../upload\\"+fileName;
	                     System.out.println(str);
	                     req.setAttribute("img_path", str.replaceAll("\\\\", "/"));
	                     req.getRequestDispatcher("./app/img_success.jsp").forward(req, resp);
	                 }  
	             }  
	         } 
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	@SuppressWarnings("unchecked")
	public  String cutImg(File file,Integer x,Integer y,Integer w,Integer h) throws Exception{
		 // 取得图片读入器
      Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
      ImageReader reader = (ImageReader)readers.next();
      // 取得图片读入流
      InputStream source=new FileInputStream(file);
      ImageInputStream iis = ImageIO.createImageInputStream(source);
      reader.setInput(iis, true);
      // 图片参数
      ImageReadParam param = reader.getDefaultReadParam();
      Rectangle rect = new Rectangle(x,y,w,h);
      param.setSourceRegion(rect);
      BufferedImage bi = reader.read(0,param);             
      ImageIO.write(bi, "jpg",file);
		return file.getAbsolutePath();
	}
}