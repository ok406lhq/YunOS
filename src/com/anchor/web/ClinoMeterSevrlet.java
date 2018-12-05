package com.anchor.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.anchor.service.ClinoMeterService;
import com.anchor.util.HtmlUtil;
import net.sf.json.JSONArray;


/**
 * Servlet implementation class GNSSResultServlet
 */
@WebServlet("/clinoMeterSevrlet")
public class ClinoMeterSevrlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClinoMeterSevrlet() {
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
	 * 功能：查询指定点名(prjName)的测点数据，以json形式返回 -wtyang2017/07/06
	 */
	//[{"sdatetime":"2017-03-10 17:59:50","east":"-1.0747380000539124","north":"2.9663230000296608","up":"-2.6221179999993183"},{"sdatetime":"2017-03-10 18:59:50","east":"-13.44877500005532","north":"12.487308999989182","up":"-10.624877000000197"},{"sdatetime":"2017-03-10 19:59:50","east":"-6.326885999995284","north":"9.406378000043333","up":"1.7491040000004432"},{"sdatetime":"2017-03-10 20:59:50","east":"-9.760560999973677","north":"8.952799000078812","up":"2.8898489999992307"},{"sdatetime":"2017-03-10 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String sensorName = request.getParameter("sensorName");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			ClinoMeterService service = new ClinoMeterService();
			JSONArray array = service.getTiltmeteRecordList(sensorName,startTime,endTime);
			HtmlUtil.writerJson(response, array);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
