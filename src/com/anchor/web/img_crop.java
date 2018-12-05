package com.anchor.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.util.Connest;
import com.anchor.util.HtmlUtil;
import com.anchor.util.ImgUtils;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class imgcrop
 */
public class img_crop extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  String filePath = Connest.UploadFile; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public img_crop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String imgUrl=request.getParameter("imgUrl");
	
		String imgInitW=request.getParameter("imgInitW");
		String imgInitH=request.getParameter("imgInitH");
		// resized sizes
		String imgW=request.getParameter("imgW");
		String imgH=request.getParameter("imgH");
		// offsets
		String imgY1=request.getParameter("imgY1");
		String imgX1=request.getParameter("imgX1");
		// crop box
		String cropW=request.getParameter("cropW");
		String cropH=request.getParameter("cropH");
		// rotation angle
		String rotation=request.getParameter("rotation");
		
		
		
		String realDir = request.getSession().getServletContext().getRealPath("");
	
		
		String realimgUrl=filePath+imgUrl.substring(imgUrl.lastIndexOf("/"),imgUrl.length());
		String FileName="T"+new Date().getTime();
		String lastFileName ="/"+filePath+ FileName+imgUrl.substring(imgUrl.lastIndexOf("."),imgUrl.length());
		 String suffix=imgUrl.substring(imgUrl.lastIndexOf("."),imgUrl.length());
		System.out.print(suffix);
		File file = new File(realDir+"/"+realimgUrl);
		  File outFile = new File(realDir+lastFileName);  
		
		 BufferedImage bufferedImage2 = ImgUtils.shearImage(ImageIO.read(file),Integer.parseInt(imgW), Integer.parseInt(imgH), Integer.parseInt(imgX1),Integer.parseInt(imgY1),Integer.parseInt(cropW), Integer.parseInt(cropH), Integer.parseInt(rotation));  
	     ImageIO.write(bufferedImage2, "jpg", outFile); 
	     JSONObject resultJsonObject=new JSONObject();
	     resultJsonObject.put("status", "success");
	     resultJsonObject.put("url", "./../"+filePath+FileName+suffix);
	     HtmlUtil.writerJson(response, resultJsonObject);
	     if(file.exists()){
	    	 file.delete();
	     }
	}

}
