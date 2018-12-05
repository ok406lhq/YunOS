package com.anchor.web;

import com.anchor.service.CrackMeterService;
import com.anchor.util.HtmlUtil;
import net.sf.json.JSONArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/crackMeterServlet")
public class CrackMeterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sensorName = request.getParameter("sensorName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        CrackMeterService service = new CrackMeterService();
        JSONArray array = service.getCrackMeterList(sensorName,startTime,endTime);
        HtmlUtil.writerJson(response, array);
    }
}
