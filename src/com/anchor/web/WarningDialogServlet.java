package com.anchor.web;

import com.anchor.service.WarningInfoService;
import com.anchor.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class WarningDialogServlet extends HttpServlet {

    private WarningInfoService service = new WarningInfoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String project = (String) req.getSession().getAttribute("projects");
        String type = req.getParameter("type");
        if("select".equals(type)){
            Map<String, Object> map =null;
            if(StringUtils.isNotBlank(project)){
                map = service.getOneNewestWarning(project);
                if(map != null){
                    map.putAll(service.getCountWarning(project));
                }
            }
            HtmlUtil.writerJson(resp, map);
        }else if ("update".equals(type)){
            String ID =req.getParameter("id");
            if(StringUtils.isNotBlank(ID)){
                service.updateIsDialogById(ID);
            }
        }else if("updateAll".equals(type)){
            if(StringUtils.isNotBlank(project)){
                service.updateIsDialogAll(project);
            }
        }
    }
}
