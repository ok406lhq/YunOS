package com.anchor.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.anchor.service.LogoService;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class LogoServlet
 */
@WebServlet("/LogoServlet")
public class LogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	LogoService service = new LogoService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String type = request.getParameter("type");
		if(type.equals("logo")) {
			String userID = (String) session.getAttribute("userID");
			try {
				JSONArray array = service.initializeData(userID);
				HtmlUtil.writerJson(response, array);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
