package com.anchor.web;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.anchor.util.Connest;
import com.anchor.util.HtmlUtil;

//import sun.org.mozilla.javascript.internal.ObjArray;



/**
 * Servlet implementation class img_crop_to_file
 */
public class img_crop_to_file extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public img_crop_to_file() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

response.setContentType("text/html");   
response.setCharacterEncoding("UTF-8");

int width=0;int height=0;
String basePath = request.getScheme() + "://";

try {
	String realDir = request.getSession().getServletContext().getRealPath("");
	 ServletContext sctx = getServletContext();  
     String path = sctx.getRealPath("./temp"); 
     String filePath = Connest.UploadFile;
		String realPath = realDir+"\\"+filePath;

		//判断路径是否存在，不存在则创建
File dir = new File(realPath);
if(!dir.isDirectory())
    dir.mkdir();

if(ServletFileUpload.isMultipartContent(request)){

    DiskFileItemFactory dff = new DiskFileItemFactory();
    dff.setRepository(dir);
    dff.setSizeThreshold(4096);
    ServletFileUpload sfu = new ServletFileUpload(dff);
    FileItemIterator fii = null;
    fii = sfu.getItemIterator(request);
    String title = "";  //图片标题
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
				
				realFileName = FileName+fileName.substring(fileName.lastIndexOf("."),fileName.length());
				
                url = realPath+realFileName;

                BufferedInputStream in = new BufferedInputStream(fis.openStream());
                FileOutputStream a = new FileOutputStream(new File(url));
                BufferedOutputStream output = new BufferedOutputStream(a);
                Streams.copy(in, output, true);
                BufferedImage buff = ImageIO.read(new File(url));
                width=buff.getWidth();
                height=buff.getHeight();
               
                
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
   String retxt ="./../"+filePath+realFileName;
    JSONObject jb=new JSONObject();
    jb.put("status", "success");
    jb.put("url", retxt);
    jb.put("width", width);
    jb.put("height", height);
    HtmlUtil.writerJson(response, jb);
    //String retxt =filePath+"/"+realFileName;
   
   
    
   
  

  
  
   // HtmlUtil.writerJson(response, "ok");
}
}catch(Exception ee) {
	ee.printStackTrace();
}

}
}
