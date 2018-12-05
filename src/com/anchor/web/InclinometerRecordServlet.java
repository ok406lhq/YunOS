package com.anchor.web;

import com.anchor.service.InclinometerRecordService;
import com.anchor.util.HtmlUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/inclinometerRecordServlet")
public class InclinometerRecordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sensorName = request.getParameter("sensorName");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            InclinometerRecordService service = new InclinometerRecordService();
            JSONArray array = service.getInclinometerRecordList(sensorName,startTime,endTime);
            HtmlUtil.writerJson(response, array);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
