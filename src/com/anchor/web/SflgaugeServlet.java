package com.anchor.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.SflgaugeService;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONArray;
/**
 * 
 * 项目名称：BDSIMP 类名称：sflgauge 
 * 类描述：静力水准仪
 * 作者：张庭伟         创建时间：2018年7月16日上午9:29:53 
 * 修改人：                 修改时间：
 * 备注： 
 * @version
 */
@WebServlet("/SflgaugeServlet")
public class SflgaugeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private SflgaugeService service = new SflgaugeService();
	
    public SflgaugeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type.equals("stationAll")) {
			String prjId = request.getParameter("prjId");
			String sensorType = request.getParameter("sensorType");
			String arr[] = {prjId,sensorType};
			JSONArray jsonArray = service.stationAll(arr);
			HtmlUtil.writerJson(response, jsonArray);
		}else if(type.equals("initializeData")) {
			
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String sensorID = request.getParameter("sensorID");
			String tempTime = endtime+" 23:59:59";
			String params[] = {starttime,tempTime,sensorID};
//			System.out.println("我进来了！！！");
			JSONArray jsonArray = null;
			try {
				jsonArray = service.initializeData(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			HtmlUtil.writerJson(response, jsonArray);
		}
	}

}
