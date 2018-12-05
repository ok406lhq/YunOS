package com.anchor.web;

import com.anchor.dao.MoniteSiteDao;
import com.anchor.model.MoniteSite;
import com.anchor.service.MoniteSiteService;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class SystemlogoServlet
 */
public class MoniteSiteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoniteSiteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String oper = request.getParameter("oper");
        HttpSession session=request.getSession();
        String project = (String)session.getAttribute("projects");
        Connection con = null;
        DbUtil dbUtil = null;
        JSONObject result = new JSONObject();
        try {
            if ("Query".equals(oper)) {
                String[] role = (String[]) session.getAttribute("role");
                List<MoniteSite> list = new ArrayList<>();
                int totalRecord =0;
                if(Arrays.asList(role).contains("view")||Arrays.asList(role).contains("admin")){ //如果有查看的权限
                    int page = Integer.valueOf(request.getParameter("page"));
                    int pageSize = Integer.valueOf(request.getParameter("pageSize"));
                    MoniteSiteService monitesiteService = new MoniteSiteService();
                    String projectid = request.getParameter("projectid");
                    if(StringUtils.isBlank(projectid)){
                        projectid =project;
                    }
                    list = monitesiteService.getMoniteSiteList(projectid, request.getParameter("siteid"), page, pageSize);
                    totalRecord = monitesiteService.getCountMoniteSite(projectid, request.getParameter("siteid"));
                }
                result.put("totalRecord",totalRecord);
                result.put("data", list);
                HtmlUtil.writerJson(response, result);

            } else if ("del".equals(oper)) {

                String idsString = request.getParameter("ids");
                MoniteSiteDao monitesitedao = new MoniteSiteDao();
                String res = monitesitedao.MoniteSiteDelted(con, idsString);

                HtmlUtil.writerHtml(response, res);

            }else if("select".equals(oper)){
                MoniteSiteDao monitesitedao = new MoniteSiteDao();
                String projectid = request.getParameter("projectid");
                if(StringUtils.isBlank(projectid)){
                    projectid =project;
                }
                List<Map<String, Object>> list = monitesitedao.getCurrentUserAllSiteId(projectid);
                HtmlUtil.writerJson(response, list);
            }else if("selectProject".equals(oper)){
                MoniteSiteDao monitesitedao = new MoniteSiteDao();
                List<Map<String, Object>> list = monitesitedao.getCurrentUserProject(project);
                HtmlUtil.writerJson(response, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
//				if (null != con)
//				{
                if (null != dbUtil) {
                    dbUtil.closeCon(con);
                }
//				}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
