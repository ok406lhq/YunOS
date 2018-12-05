package com.anchor.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.SystemLogService;
import com.anchor.service.WarningStrategyService;
import com.anchor.service.WarningUserSetService;
import com.anchor.util.DateUtil;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 项目名称：BDSIMP
 * 类描述：测点预警值
 * 作者：张庭伟     时间：2018年6月26日下午4:46:21
 * 编辑：                时间：
 * 备注：
 * @version
 */
@WebServlet("/WarningStrategyServlet")
public class WarningStrategyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WarningStrategyService service = new WarningStrategyService();
	WarningUserSetService setService = new WarningUserSetService();
	
    public WarningStrategyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String project = (String) request.getSession().getAttribute("projects");
		
		
		if(type.equals("C")){//页面初始化
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			String nProjectID = request.getParameter("nProjectID");
			JSONArray array = service.findWarningStrategyAll(project,page,pageSize,nProjectID);
			JSONArray array2=service.findWarningStrategyCount(project, page, pageSize, nProjectID);
			int totalRecord = (int) array2.getJSONObject(0).get("totalRecord");
			JSONObject object = new JSONObject();
			object.put("array", array);
			object.put("totalRecord", totalRecord);
			//System.out.println(totalRecord+"----------");
			HtmlUtil.writerJson(response, object);
			
		}else if(type.equals("QC")) {
			JSONArray proArray = service.findWarningStrategyPro(project);
			HtmlUtil.writerJson(response, proArray);
			
			
		}else if(type.equals("U")){//预警阀值修改
			String ID = request.getParameter("ID");
			String batch = request.getParameter("batch");
			String proid = request.getParameter("proid");
			String senType = request.getParameter("senType");
			String dTopLevelValue = request.getParameter("dTopLevelValue");
			String dMediumLevelValue = request.getParameter("dMediumLevelValue");
			String dLowLevelValue = request.getParameter("dLowLevelValue");
			String dMicroLevelValue = request.getParameter("dMicroLevelValue");
			String dtModifyDT = null;
			try {
				dtModifyDT = DateUtil.getNowPlusTime();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONObject row = null;
//			if(batch.equals("all")) {
//				String arrParam[] = {dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtModifyDT,proid,senType}; 
//				row = service.UpdateBatchWarningStrategy(arrParam);
//			}else {
				String arrParam[] = {dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtModifyDT,ID,senType}; 
				row = service.UpdateWarningStrategy(arrParam);
//			}
			if(row != null){
				try {
					SystemLogService.addSystemLogData("修改","预警阈值修改",request);///----------------保存修改行为到系统日志
				} catch (Exception e) {
					e.printStackTrace();
				} 
				HtmlUtil.writerJson(response, row);
			}else{
				HtmlUtil.writerJson(response, row);
			}
		}else if(type.equals("S")){//子类查询
			String nProjectID = request.getParameter("nProjectID");
			String sSensorType = request.getParameter("sSensorType");
			String nSensorID = "0";
			String arrParam[] = {nProjectID,sSensorType,nSensorID}; 
			JSONArray array = service.findByPidStypeStrategy(arrParam);
			HtmlUtil.writerJson(response, array);
		}else if(type.equals("pro")){
			JSONArray array = setService.findByProjectWarningUserSetAll(project);
			HtmlUtil.writerJson(response, array);
		}else if(type.equals("add")){//添加
			String dTopLevelValue = request.getParameter("dTopLevelValue");
			String dMediumLevelValue = request.getParameter("dMediumLevelValue");
			String dLowLevelValue = request.getParameter("dLowLevelValue");
			String dMicroLevelValue = request.getParameter("dMicroLevelValue");
			String proselect = request.getParameter("proselect");//ID
			String senselect = request.getParameter("senselect");//GNSS
			String senselectName = "";
			if(senselect.equals("GNSS")) {
				senselectName = "北斗";
			}else if(senselect.equals("RainGauge")) {
				senselectName = "雨量";
			}
			String dtCreateDT = null;
			try {
				dtCreateDT = DateUtil.getNowPlusTime();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			JSONObject row = null;
//			if(proselect.equals("All")){
//				JSONArray array = setService.findByProjectWarningUserSetAll((String)userObject.get("sProjectIDS"));
//				for (int i = 0; i < array.size(); i++) {
//					String arr[] = {array.getJSONObject(i).getString("ID"),senselect,"0"};
//					JSONArray jsonArray = service.findByProidSentypeStrategyVali(arr);
//					if(jsonArray.size()>0){
//						continue;
//					}else {
//						String params[] = {array.getJSONObject(i).getString("ID"),"0",senselect,senselectName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT}; 
//						row = service.addWarningStrategy(params);
//					}
//				}
//			}else{
//				String arr[] = {proselect,senselect,"0"};
//				JSONArray jsonArray = service.findByProidSentypeStrategyVali(arr);
//				if(jsonArray.size()>0){
//					JSONObject object = new JSONObject();
//					object.put("msg", "cunzai");
//					HtmlUtil.writerJson(response, object);
//					return;
//				}else {
//					String params[] = {proselect,"0",senselect,senselectName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT}; 
//					row = service.addWarningStrategy(params);
//				}
//			}
//			String arr[] = {proselect,senselect};
//			JSONArray jsonArray = service.findByProidSentypeStrategy(arr);
//			for (int i = 0; i < jsonArray.size(); i++) {
//				String params[] = {proselect,jsonArray.getJSONObject(i).getString("nSensorID"),senselect,senselectName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT}; 
//				row = service.addWarningStrategy(params);
//			}
			String params[] = {proselect,"0",senselect,senselectName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT}; 
			String params2[] = {proselect,"0",senselect};
			row = service.addWarningStrategy(params,params2);
			if(row!=null){
				try {
					SystemLogService.addSystemLogData("添加","预警阈值添加",request);
				} catch (Exception e) {
					e.printStackTrace();
				}///----------------保存修改行为到系统日志
				HtmlUtil.writerJson(response, row);
			}else{
				HtmlUtil.writerJson(response, row);
			}
			
		}else if(type.equals("site")){
			String nprojectID = request.getParameter("nprojectID");
			String sSensorType = request.getParameter("sSensorType");
			String arr[] = {nprojectID,sSensorType};
			JSONArray array = setService.findBySensorWarningUserSetAll(arr);
			HtmlUtil.writerJson(response, array);
			
		}else if(type.equals("addchild")){
			String dTopLevelValue = request.getParameter("dTopLevelValue");
			String dMediumLevelValue = request.getParameter("dMediumLevelValue");
			String dLowLevelValue = request.getParameter("dLowLevelValue");
			String dMicroLevelValue = request.getParameter("dMicroLevelValue");
			String proselect = request.getParameter("proselect");//ID
			String senselect = request.getParameter("senselect");//GNSS
			String siteselect = request.getParameter("siteselect");//nSensorID
			String senselectName = "";
			if(senselect.equals("GNSS")) {
				senselectName = "北斗";
			}else if(senselect.equals("RainGauge")) {
				senselectName = "雨量";
			}
			String dtCreateDT = null;
			try {
				dtCreateDT = DateUtil.getNowPlusTime();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String arr[] = {proselect,senselect,siteselect};
			JSONArray jsonArray = service.findByProidSentypeStrategyVali(arr);
			JSONObject row = null;
			if(jsonArray.size()>0){
				JSONObject object = new JSONObject();
				object.put("msg", "cunzai");
				HtmlUtil.writerJson(response, object);
				return;
			}else{
				String params[] = {proselect,siteselect,senselect,senselectName,dTopLevelValue,dMediumLevelValue,dLowLevelValue,dMicroLevelValue,dtCreateDT}; 
				String params2[] = {proselect,siteselect,senselect};
				row = service.addWarningStrategy(params,params2);
			}
			if(row!=null){
				try {
					SystemLogService.addSystemLogData("添加","预警阈值添加",request);
				} catch (Exception e) {
					e.printStackTrace();
				}///----------------保存修改行为到系统日志
				HtmlUtil.writerJson(response, row);
			}else{
				HtmlUtil.writerJson(response, row);
			}
		}else if(type.equals("deleteAll")){
			String nProjectID = request.getParameter("nProjectID");
			String sSensorType = request.getParameter("sSensorType");
			String params[] = {nProjectID,sSensorType};
			JSONObject object = service.deleteAllStrategy(params);
			HtmlUtil.writerJson(response, object);
		}else if(type.equals("delete")){
			String id = request.getParameter("id");
			JSONObject object = service.deleteStrategy(id);
			HtmlUtil.writerJson(response, object);
		}
		
		
	}

}
