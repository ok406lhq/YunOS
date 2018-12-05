package com.anchor.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Spring;

import com.alibaba.fastjson.JSON;
import com.anchor.service.ProjectInfoService;
import com.anchor.util.HtmlUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 获取用户所对应的项目
 *
 * @author yang-qifang
 */
public class myprojects extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public myprojects() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String project = (String) request.getSession().getAttribute("projects");
        ProjectInfoService prjInfoService = new ProjectInfoService();
        try {
            String projectName = request.getParameter("prjName");
            if (projectName != "" && projectName != null && !"undefined".equals(projectName)) {
                List<String> integerList = prjInfoService.getByProjectType(projectName);
                project = compareArray(project, integerList);
                System.out.println(JSON.toJSONString(project));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = prjInfoService.getmyproject(project);
        System.out.println("json:" + json);
        HtmlUtil.writerHtml(response, json);
    }

    private String compareArray(String myproject, List<String> integerList) {
        List<String> m = Arrays.asList(myproject.split(","));
        List<String> same = new ArrayList<>();
        for (String i : integerList) {
            if (m.contains(i)) {
                same.add(i);
            }
        }
        return StringUtils.join(same, ",");
    }
}
