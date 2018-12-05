package com.anchor.web;

import com.anchor.dao.CompareDataDao;
import com.anchor.service.SystemLogService;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet implementation class CompareDataServlet
 */
public class CompareDataServlet_bak extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareDataServlet_bak() {
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
		Connection con = null;
		DbUtil dbUtil = null;
		try{
			dbUtil=new DbUtil();
			dbUtil.SetDatabase(Connest.DbName);
			con = dbUtil.getCon();
			String prjName=request.getParameter("prjName");
			if("".equals(prjName) || null==prjName){
				prjName=Connest.DefaultPrjName;
			}
			//if(null!=con){
				String oper=request.getParameter("oper");
				if("compare".equals(oper)){ //如果是数据比对
					String sensorName=request.getParameter("sensorName");
					String sensorType=request.getParameter("sensorType");
					String start_time=request.getParameter("start_time");
					String end_time=request.getParameter("end_time");
					
					JSONObject jsonArray=new JSONObject();
					CompareDataDao compareDataDao=new CompareDataDao();
					jsonArray=compareDataDao.QueryCompareData(con,sensorName,prjName,sensorType,start_time,end_time);
					HtmlUtil.writerJson(response, jsonArray);
					SystemLogService.addSystemLogData("查询",sensorName+"数据对比,时间范围"+start_time+"至"+end_time,request); ///----------------保存查询行为到系统日志
				}
			//}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (null != con)
				{
					if (null != dbUtil)
					{
						dbUtil.closeCon(con);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
