package com.anchor.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anchor.dao.WarningUserSetDao;
import com.anchor.service.SystemLogService;
import com.anchor.service.WarningUserSetService;
import com.anchor.util.DateUtil;
import com.anchor.util.HtmlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 项目名称：BDSIMP
 * 类描述：预警人设置
 * 作者：张庭伟     时间：2018年6月28日上午9:49:35
 * 编辑：                时间：
 * 备注：
 * @version
 */
@WebServlet("/WarningUserSetServlet")
public class WarningUserSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private WarningUserSetService service = new WarningUserSetService();
	WarningUserSetDao dao = new WarningUserSetDao();
    public WarningUserSetServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if(type.equals("C")){//页面初始化
			
			String project = (String) request.getSession().getAttribute("projects");

			String proid = request.getParameter("proid");
			int page = Integer.valueOf(request.getParameter("page"));
			Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
			//Integer arrPage[] = {(page-1)*pageSize,pageSize};
			
			JSONArray array = service.findByPageWarningUserSetAll(project,proid,(page-1)*pageSize,pageSize);
			JSONObject totalRecords = service.findByPageWarningUserSetAll(project,proid);
			JSONArray arrProject = service.findByProjectWarningUserSetAll(project);
			JSONArray arrSensor = service.findBySensorWarningUserSetAll();
			JSONObject object = new JSONObject();
			object.put("array", array);
			object.put("arrProject", arrProject);
			object.put("arrSensor", arrSensor);
			object.put("totalRecords", totalRecords);
			HtmlUtil.writerJson(response, object);
		}else if(type.equals("Q")){
			String proid = request.getParameter("proid");
			Integer page = Integer.valueOf(request.getParameter("page"));
			Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
			
			JSONArray array = service.findByProSenWarningUserSetAll(proid,(page-1)*pageSize,pageSize);
			JSONObject totalRecords = dao.findByProSenWarningUserSetAllCount(proid);
			JSONObject object = new JSONObject();
			object.put("array", array);
			object.put("totalRecords", totalRecords);
			HtmlUtil.writerJson(response, object);
		}else if(type.equals("add")){
			JSONArray array = service.findUserSetAll();
			HtmlUtil.writerJson(response, array);
		}else if(type.equals("save")){
			String userID = request.getParameter("userID");
			String proID = request.getParameter("proID");
			String warningtype = request.getParameter("warningtype");
			String msgInterval = request.getParameter("msgInterval");
			String warningLevel = request.getParameter("warningLevel");
			String proIDs[] = proID.split(",");
			String warningLevels[] = warningLevel.split(",");
			try {
				String dtCreateAt = DateUtil.getNowPlusTime();
				JSONObject row = null;
				for(int i = 0; i<proIDs.length; i++) {
					String select[] = {userID,proIDs[i]};
					List<Map<String, Object>> list = dao.findUserSetAll(select);
					if(list.size()>0) {
						continue;//判断用户项目设置是否存在
					}
					String params[] = {userID,proIDs[i],warningtype,msgInterval,warningLevels[warningLevels.length-1],dtCreateAt};
					row = service.addWarningUserSet(params);
					SystemLogService.addSystemLogData("添加","预警人添加",request);///----------------保存修改行为到系统日志
					HtmlUtil.writerJson(response, row);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(type.equals("update")){
			String ID = request.getParameter("ID");
			String userID = request.getParameter("userID");
			String proID = request.getParameter("proID");
			String warningtype = request.getParameter("warningtype");
			String msgInterval = request.getParameter("msgInterval");
			String warningLevel = request.getParameter("warningLevel");
			String proIDs[] = proID.split(",");
			String warningLevels[] = warningLevel.split(",");
			try {
				String dtModifyAt = DateUtil.getNowPlusTime();
				JSONObject row = null;
				for(int i = 0; i<proIDs.length; i++) {
					String params[] = {userID,proIDs[i],warningLevels[warningLevels.length-1],msgInterval,warningtype,dtModifyAt,ID};
					row = service.updateWarningUserSet(params);
				}
				SystemLogService.addSystemLogData("修改","预警人修改",request);///----------------保存修改行为到系统日志
				HtmlUtil.writerJson(response, row);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("delete")){
			String ID = request.getParameter("ID");
			JSONObject row = service.deleteByIDWarningUserSet(ID);
			if(row != null){
				try {
					SystemLogService.addSystemLogData("删除","预警人删除",request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}///----------------保存修改行为到系统日志
				HtmlUtil.writerJson(response, row);
			}else{
				HtmlUtil.writerJson(response, row);
			}
		}else if(type.equals("deleteAll")){
			String chk_value = request.getParameter("chk_value");
			String[] id = chk_value.split(",");
			JSONObject row = null;
			for (int i = 0; i < id.length; i++) {
				row = service.deleteByIDWarningUserSet(id[i]);
			}
			if(row != null){
				try {
					SystemLogService.addSystemLogData("删除","预警人删除",request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HtmlUtil.writerJson(response, row);
			}else{
				HtmlUtil.writerJson(response, row);
			}
		}
			
	}

}
