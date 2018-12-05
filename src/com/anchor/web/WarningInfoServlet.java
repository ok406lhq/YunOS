package com.anchor.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import net.sf.json.JSONObject;

import com.anchor.dao.PointInfoDao;
import com.anchor.dao.WarningInfoDao;
import com.anchor.service.SystemLogService;
import com.anchor.service.UserService;
import com.anchor.service.WarningInfoService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;

/**
 * Servlet implementation class WarningInfoServlet
 */
public class WarningInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WarningInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	/*根据类型返回数据
	 	qType:
	 	  G   返回已经产生预警，但是还没有完全处理的记录，用于显示在首页《边坡状态》位置，表示出有几个预警
	 	  Q   根据active参数返回指定类型的日志信息，包括登录，离线，掉线等，还可以扩展时间范围，点位名等参数查询
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String project = (String) request.getSession().getAttribute("projects");
        String[] prjdice=null;
        
        if (project!=null) {
            prjdice=project.split(";");
        }
        Connection con = null;
        DbUtil dbUtil = null;
        try{
            dbUtil=new DbUtil();
            con = dbUtil.getCon();
//			if (null != con)
//			{
            String qType = request.getParameter("qType");
            if (qType == null)
                qType = "";
            WarningInfoService warningInfoService =  new WarningInfoService();
            //String active = "CONN";
            String prjInfo = "";
            if (qType.equals("Q")){//根据事件类型查询日志信息

                String activeval = URLDecoder.decode(request.getParameter("activeval"), "utf-8");
                String starttime=request.getParameter("starttime");
                String endtime=request.getParameter("endtime");
                String structure_type= URLDecoder.decode(request.getParameter("structure_type"), "utf-8");
                String structure_name= URLDecoder.decode(request.getParameter("structure_name"), "utf-8");
                
                String page=request.getParameter("page");
                String pageSize=request.getParameter("pageSize");
                String totalRecord = warningInfoService.findAllCount(project, project, activeval, starttime, endtime, structure_type, structure_name);
                prjInfo = warningInfoService.findAll(project, project,activeval,starttime,endtime,structure_type,structure_name,page,pageSize);
                JSONObject object=new JSONObject();
                object.put("totalRecord", totalRecord);
                object.put("datalist",prjInfo);
                HtmlUtil.writerJson(response, object);
                //SystemLogService.addSystemLogData("查询","查询预警信息 时间范围："+starttime+"到"+endtime,request); ///----------------保存查询行为到系统日志
                
            }else if(qType.equals("Confirm")){
                String id = request.getParameter("id");
                int row = warningInfoService.UpdateWarningConfirmInfo(con, id);
                prjInfo = String.valueOf(row);
                JSONObject object=new JSONObject();
                if(row > 0){
                    object.put("msg", "success");
                }
                HtmlUtil.writerJson(response, object);
                SystemLogService.addSystemLogData("修改","确认预警反馈",request); ///----------------保存查询行为到系统日志

            }else if(qType.equals("S")){

                List<String> sensorIDs = new ArrayList<>();
                String sensor = request.getParameter("sensor");
                String starttime=request.getParameter("starttime");
                String endtime=request.getParameter("endtime");
                String[] projects = project.split(";");
                int page=Integer.valueOf(request.getParameter("page"));
                int pageSize=Integer.valueOf(request.getParameter("pageSize"));
                PointInfoDao pointInfoDao = new PointInfoDao();
                if(sensor.equals("All")){
                    String ptInfos = pointInfoDao.queryPtInfoJson(con,projects);
                    JSONArray array = new JSONArray(ptInfos);
                    for(int i = 0;i<array.length();i++){
                        org.json.JSONObject objItem=array.getJSONObject(i);
                        sensorIDs.add(objItem.getString("SensorID"));
                    }
                }
                WarningInfoDao warningInfoDao = new WarningInfoDao();
                int totalRecord; //查询数据数量
                totalRecord=warningInfoDao.QuerySensorInfoTotalRecord(con, sensor,starttime,endtime);
                prjInfo = warningInfoService.QuerySensorLogInfo(con, sensor,starttime,endtime,page,pageSize,sensorIDs);
                JSONObject object=new JSONObject();
                object.put("totalRecord", totalRecord);
                object.put("datalist",prjInfo);
                HtmlUtil.writerJson(response, object);
                SystemLogService.addSystemLogData("查询","查询预警信息 时间范围："+starttime+"到"+endtime,request); ///----------------保存查询行为到系统日志
            

            }else if (qType.equals("G")){//"G" --查询警告信息用于主页上显
                prjInfo = warningInfoService.QueryWarningInfo(con,project);
            }else if(qType.equals("C")){//"S" --总记录数  警告信息用于主页上显
                prjInfo = warningInfoService.queryWarningInfoCount(project);
            }else{//"JCJB"，查询监测简报信息
                if (prjdice!=null) {
                    prjInfo = warningInfoService.QueryJCJB(con,prjdice);
                }
                SystemLogService.addSystemLogData("查询","查询监测简报信息",request); ///----------------保存查询行为到系统日志
            }
            if (prjInfo == "")
            {
                //将前端js的error变量赋值。重新加载后，会显示错误信息
                request.setAttribute("error","用户名或密码错误");
                //服务器跳转，重新装载页面。
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }else{
                if(!qType.equals("Q")){
                    System.out.println("json监测:"+prjInfo);
                    HtmlUtil.writerHtml(response, prjInfo);

                }
            }
//			}
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
//					if (null != con)
//					{
                if (null != dbUtil)
                {
                    dbUtil.closeCon(con);
                }
//					}
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
