package com.anchor.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.service.LoadCellService;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class LoadCellServlet
 */
@WebServlet("/LoadCellServlet")
public class LoadCellServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoadCellService service = new LoadCellService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadCellServlet() {
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
