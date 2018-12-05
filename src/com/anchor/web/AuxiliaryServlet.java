package com.anchor.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import net.sf.json.JSONObject;

import com.anchor.dao.AuxiliaryDao;
import com.anchor.dao.PointInfoDao;
import com.anchor.dao.WarningInfoDao;
import com.anchor.service.SystemLogService;
import com.anchor.service.WarningInfoService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.StringUtil;

/**
 * Servlet implementation class WarningInfoServlet
 */
public class AuxiliaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuxiliaryDao auxiliaryDao= new AuxiliaryDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuxiliaryServlet() {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String method=request.getParameter("method");//调用的方法
        String result=null;
        if("queryThresholdForBD".equals(method)){
        	String prjId=request.getParameter("prjId");
        	String sensorId=request.getParameter("sensorId");
        	String sensorType=request.getParameter("sensorType");
        	result=auxiliaryDao.queryThresholdForBD(prjId, sensorId, sensorType);
        }
        response.getWriter().write(result);
    }

}
