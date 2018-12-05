package com.anchor.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import net.sf.json.JSONArray;

import com.anchor.model.ProjectInfo;
import com.anchor.util.Connest;
import com.anchor.util.DbUtil;
import com.anchor.util.HttpClientUtil;
import com.anchor.util.JsonUtil;

public class ProjectInfoDao {

    private DBUtilsTemplate dbUtilsTemplate=new DBUtilsTemplate();
    public ProjectInfoDao() {
        super();
    }

    public ResultSet m_rset = null;
    public PreparedStatement m_pstmt = null;

    /**
     * 查询项目信息--用list
     *
     * @param conn
     * @param
     * @return
     * @throws Exception
     */

    public List<ProjectInfo> queryPrjInfoList(Connection conn, String prjName) throws Exception {
        List<ProjectInfo> prjPointList = new ArrayList<ProjectInfo>();
        String url = Connest.URL_PROJECT_QueryPrjInfoList;
        String name = URLEncoder.encode(prjName, "utf-8");
        url = String.format(url, name);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
//		System.out.println("queryPrjInfoList:"+res);
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        org.json.JSONArray arrObjs = obj.getJSONArray("data");
        for (int i = 0; i < arrObjs.length(); i++) {
            org.json.JSONObject objItem = arrObjs.getJSONObject(i);
            ProjectInfo prjPoint = new ProjectInfo();
            prjPoint.setPrjID(objItem.getString("PrjID"));
            prjPoint.setPrjName(objItem.getString("PrjName"));
            prjPoint.setPrjAlais(objItem.getString("PrjAlais"));
            prjPoint.setPrjType(objItem.getString("PrjType"));
            prjPoint.setStartTime(objItem.getString("StartTime"));
            prjPoint.setEndTime(objItem.getString("EndTime"));
            prjPoint.setLatitude(objItem.getDouble("Latitude"));
            prjPoint.setLongitude(objItem.getDouble("Longitude"));
            prjPoint.setStatusVal(objItem.getString("StatusVal"));
            prjPoint.setStatusDes(objItem.getString("StatusDes"));
            prjPoint.setDescription(objItem.getString("Description"));
            //objItem.getString("Bgimg")
            prjPoint.setBgimg(objItem.getString("Bgimg"));
            System.out.println("Imgurl:" + objItem.getString("Bgimg"));
            prjPoint.setPrjAddr(objItem.getString("PrjAddr"));
            //prjPoint.setrenzhengma(objItem.getString("renzhengma"));
            prjPoint.setproperty(objItem.getString("property"));
            prjPoint.setLxr1(objItem.getString("Adminman"));
            prjPoint.setLxr2(objItem.getString("Testman"));
            prjPoint.setAddrCity(objItem.getString("sAddrCity"));
            prjPoint.setAddrRegions(objItem.getString("sAddrRegions"));
            prjPointList.add(prjPoint);

        }
        //		String sql = "select prjID,prjname,PrjAddr,PrjAlias,prjType,starttime,endtime,latitude,longitude,statusval,statusdes,description,bgimg,renzhengma,property from prjinfo ";
//		if (!prjName.equals(""))
//			sql += " where prjname = \"" + prjName + "\" ";
//		sql += "order by prjType desc";
//		StringBuffer sbf = new StringBuffer(sql);
//		System.err.println(sbf.toString());
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		List<ProjectInfo> prjPointList = new ArrayList<ProjectInfo>();
//		while (m_rset.next()) {
//			ProjectInfo prjPoint = new ProjectInfo();
//			prjPoint.setPrjID(m_rset.getString("prjID"));
//			prjPoint.setPrjName(m_rset.getString("prjname"));
//			prjPoint.setPrjAlais(m_rset.getString("PrjAlias"));
//			prjPoint.setPrjType(m_rset.getString("prjType"));
//			prjPoint.setStartTime(m_rset.getString("starttime"));
//			prjPoint.setEndTime(m_rset.getString("endtime"));
//			prjPoint.setLatitude(m_rset.getDouble("latitude"));
//			prjPoint.setLongitude(m_rset.getDouble("longitude"));
//			prjPoint.setStatusVal(m_rset.getString("statusval"));
//			prjPoint.setStatusDes(m_rset.getString("statusdes"));
//			prjPoint.setDescription(m_rset.getString("description"));
//			prjPoint.setBgimg(m_rset.getString("bgimg"));
//			prjPoint.setPrjAddr(m_rset.getString("PrjAddr"));
//			prjPoint.setrenzhengma(m_rset.getString("renzhengma"));
//			prjPoint.setproperty(m_rset.getString("property"));
//			prjPointList.add(prjPoint);
//		}
        return prjPointList;
    }

    /**
     * 查询某一个项目类型下的数据
     */

    public List<Map<String, Object>> getByProjectType(String projectType) {
        String sql = "select ID from base_project_info where nProjectType=?";
        List<Map<String, Object>> projectList = dbUtilsTemplate.find(sql,new Object[]{projectType});
        return projectList;
    }

    /**
     * 查询属于自己的项目信息
     *
     * @param conn
     * @param prjName
     * @return
     * @throws Exception
     */
    public List<ProjectInfo> queryMyPrjInfoList(Connection conn, String myproject) throws Exception {
        List<ProjectInfo> prjPointList = new ArrayList<ProjectInfo>();
        String url = Connest.URL_PROJECT_QueryMyPrjInfoList;

        String project = URLEncoder.encode(String.join(";", myproject), "utf-8");
        url = String.format(url, project);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        JSONObject obj = new JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }

        org.json.JSONArray arrObjs = obj.getJSONArray("data");

        for (int i = 0; i < arrObjs.length(); i++) {
            JSONObject objItem = arrObjs.getJSONObject(i);
            ProjectInfo prjPoint = new ProjectInfo();
            prjPoint.setPrjID(objItem.getString("PrjID"));
            prjPoint.setPrjName(objItem.getString("PrjName"));
            prjPoint.setPrjAlais(objItem.getString("PrjAlais"));
            prjPoint.setPrjType(objItem.getString("PrjType"));
            prjPoint.setStartTime(objItem.getString("StartTime"));
            prjPoint.setEndTime(objItem.getString("EndTime"));
            //System.out.println(objItem.getString("Latitude")+"------------");
            //经纬度为空赋值
            if(objItem.getString("Latitude")==null || objItem.getString("Latitude").equals("")) {
            	prjPoint.setLatitude(0);
            }else {
            	prjPoint.setLatitude(objItem.getDouble("Latitude"));
            }
            
            if(objItem.getString("Longitude")==null || objItem.getString("Longitude").equals("")) {
            	prjPoint.setLongitude(0);
            }else {
            	prjPoint.setLongitude(objItem.getDouble("Longitude"));
            }
            
            //prjPoint.setLongitude(objItem.getDouble("Longitude"));
            prjPoint.setStatusVal(objItem.getString("StatusVal"));
            prjPoint.setStatusDes(objItem.getString("StatusDes"));
            prjPoint.setDescription(objItem.getString("Description"));
            prjPoint.setBgimg(objItem.getString("Bgimg"));
            prjPoint.setPrjAddr(objItem.getString("PrjAddr"));
            //prjPoint.setrenzhengma(objItem.getString("renzhengma"));
            prjPoint.setproperty(objItem.getString("property"));
            if (objItem.has("sURLAddress")) {
            	try {
            		prjPoint.setUrlAddress(objItem.getString("sURLAddress"));
				} catch (Exception e) {
//					new RuntimeException("sURLAddress 字段未传值").printStackTrace();
				}
                
            }
            prjPointList.add(prjPoint);

        }
//		for (String index : myproject) {
//			String sql = "select prjID,prjname,PrjAddr,PrjAlias,prjType,starttime,endtime,latitude,longitude,statusval,statusdes,description,bgimg,renzhengma,property from prjinfo ";
//			if (!index.equals(""))
//				sql += " where prjname = \"" + index + "\" ";
//			sql += "order by starttime desc";
//			StringBuffer sbf = new StringBuffer(sql);
//			m_pstmt = conn.prepareStatement(sbf.toString());
//			m_rset = m_pstmt.executeQuery();
//			while (m_rset.next()) {
//				ProjectInfo prjPoint = new ProjectInfo();
//				prjPoint.setPrjID(m_rset.getString("prjID"));
//				prjPoint.setPrjName(m_rset.getString("prjname"));
//				prjPoint.setPrjAlais(m_rset.getString("PrjAlias"));
//				prjPoint.setPrjType(m_rset.getString("prjType"));
//				prjPoint.setStartTime(m_rset.getString("starttime"));
//				prjPoint.setEndTime(m_rset.getString("endtime"));
//				prjPoint.setLatitude(m_rset.getDouble("latitude"));
//				prjPoint.setLongitude(m_rset.getDouble("longitude"));
//				prjPoint.setStatusVal(m_rset.getString("statusval"));
//				prjPoint.setStatusDes(m_rset.getString("statusdes"));
//				prjPoint.setDescription(m_rset.getString("description"));
//				prjPoint.setBgimg(m_rset.getString("bgimg"));
//				prjPoint.setPrjAddr(m_rset.getString("PrjAddr"));
//				prjPoint.setrenzhengma(m_rset.getString("renzhengma"));
//				prjPoint.setproperty(m_rset.getString("property"));
//				prjPointList.add(prjPoint);
//			}
//		}
        return prjPointList;
    }

    /**
     * 查询项目信息--用list
     *
     * @param conn
     * @param model
     * @return
     * @throws Exception
     */
    public String queryPtInfoJson(Connection conn) throws Exception {
        String url = Connest.URL_PROJECT_QueryPtInfoJson;

        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        org.json.JSONArray arrObjs = obj.getJSONArray("data");
        // String sql = "select prjName, prjType, prjAddr from prjinfo order by
        // starttime desc";
//		String sql = "select * from prjinfo order by starttime desc";
//		StringBuffer sbf = new StringBuffer(sql);
//		m_pstmt = conn.prepareStatement(sbf.toString());
//		m_rset = m_pstmt.executeQuery();
//		JSONArray jsArray = JsonUtil.formatRsToJsonArray(m_rset);
//		System.out.print(jsArray.toString());
        return arrObjs.toString();
    }

    public ProjectInfo QueryProjectsInfoByprjName(Connection conn, String prjName) throws Exception {
        String url = Connest.URL_PROJECT_QueryProjectsInfoByprjName;
        String name = URLEncoder.encode(prjName, "utf-8");
        url = String.format(url, name);

        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        org.json.JSONArray arrObjs = obj.getJSONArray("data");
        org.json.JSONObject objItem = arrObjs.getJSONObject(0);
        ProjectInfo prjPoint = new ProjectInfo();
        prjPoint.setPrjID(objItem.getString("PrjID"));
        prjPoint.setPrjName(objItem.getString("PrjName"));
        prjPoint.setPrjType(objItem.getString("PrjType"));
        prjPoint.setPrjAddr(objItem.getString("PrjAddr"));
        prjPoint.setStartTime(objItem.getString("StartTime"));
        prjPoint.setEndTime(objItem.getString("EndTime"));
        prjPoint.setPrjAlais(objItem.getString("PrjAlais"));
        prjPoint.setLatitude(objItem.getDouble("Latitude"));
        prjPoint.setLongitude(objItem.getDouble("Longitude"));
        prjPoint.setStatusVal(objItem.getString("StatusVal"));
        prjPoint.setStatusDes(objItem.getString("StatusDes"));
        prjPoint.setDescription(objItem.getString("Description"));
        prjPoint.setBgimg(objItem.getString("Bgimg"));
        //prjPoint.setrenzhengma(objItem.getString("renzhengma"));
        prjPoint.setproperty(objItem.getString("property"));
//		String sql = "SELECT * FROM prjinfo WHERE  prjName=\"" + prjName + "\"";
//		ProjectInfo prjPoint = new ProjectInfo();
//		try {
//			m_pstmt = conn.prepareStatement(sql);
//			m_rset = m_pstmt.executeQuery();
//			while (m_rset.next()) {
//				prjPoint.setPrjID(m_rset.getString("prjID"));
//				prjPoint.setPrjName(m_rset.getString("PrjName"));
//				prjPoint.setPrjType(m_rset.getString("PrjType"));
//				prjPoint.setPrjAddr(m_rset.getString("PrjAddr"));
//				prjPoint.setStartTime(m_rset.getString("StartTime"));
//				prjPoint.setEndTime(m_rset.getString("EndTime"));
//				prjPoint.setPrjAlais(m_rset.getString("PrjAlias"));
//				prjPoint.setLatitude(m_rset.getDouble("Latitude"));
//				prjPoint.setLongitude(m_rset.getDouble("Longitude"));
//				prjPoint.setStatusVal(m_rset.getString("StatusVal"));
//				prjPoint.setStatusDes(m_rset.getString("StatusDes"));
//				prjPoint.setDescription(m_rset.getString("Description"));
//				prjPoint.setBgimg(m_rset.getString("Bgimg"));
//				prjPoint.setrenzhengma(m_rset.getString("renzhengma"));
//				prjPoint.setproperty(m_rset.getString("property"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        return prjPoint;

    }

    public String updateProjectInfo(Connection conn, ProjectInfo prjPoint) throws Exception {

        // TODO Auto-generated method stub
        String result = "";
        String url = Connest.URL_PROJECT_UpdateProjectInfo;
        String prjName = URLEncoder.encode(prjPoint.getPrjName(), "utf-8");
        String PrjAlias = URLEncoder.encode(prjPoint.getPrjAlais(), "utf-8");
        String PrjType = URLEncoder.encode(prjPoint.getPrjType(), "utf-8");
        String PrjAddr = URLEncoder.encode(prjPoint.getPrjAddr(), "utf-8");
        String starttime = URLEncoder.encode(prjPoint.getStartTime(), "utf-8");
        String endtime = URLEncoder.encode(prjPoint.getEndTime(), "utf-8");
        double lati = prjPoint.getLatitude();
        double longti = prjPoint.getLongitude();
        String property = URLEncoder.encode(prjPoint.getpropertyVal(), "utf-8");

        url = String.format(url, prjName, PrjAlias, PrjType, PrjAddr, starttime, endtime, lati, longti);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }
        result = obj.getJSONArray("data").getString(0);
//		String prjName = prjPoint.getPrjName();
//		String PrjAlias = prjPoint.getPrjAlais();
//		String PrjType = prjPoint.getPrjType();
//		String PrjAddr = prjPoint.getPrjAddr();
//		String starttime = prjPoint.getStartTime();
//		String endtime = prjPoint.getEndTime();
//		double lati = prjPoint.getLatitude();
//		double longti = prjPoint.getLongitude();
//		String property = prjPoint.getpropertyVal();
//
//		String sqlString = "update prjinfo set PrjAlias=\"" + PrjAlias + "\",PrjType=\"" + PrjType + "\",PrjAddr=\""
//				+ PrjAddr + "\",StartTime=\"" + starttime + "\",EndTime=\"" + endtime + "\",Latitude=\"" + lati
//				+ "\",Longitude=\"" + longti + "\",property=\"" + property + "\" where PrjName=\"" + prjName + "\"";
//		StringBuffer stringBuffer = new StringBuffer(sqlString);
//		try {
//			PreparedStatement pst = conn.prepareStatement(stringBuffer.toString());
//			int i = pst.executeUpdate();
//			result = i + "";
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			
//		}
        return result;
    }

    public String getuserproject(Connection conn, String username) throws Exception {
        String result = "";
        String url = Connest.URL_PROJECT_Getuserproject;
        String user = URLEncoder.encode(username, "utf-8");
        url = String.format(url, user);
        String res = HttpClientUtil.doGet(url, "utf-8");
        res = URLDecoder.decode(res, "utf-8");
        org.json.JSONObject obj = new org.json.JSONObject(res);
        String resCode = obj.getString("code");
        if (!"00".equals(resCode)) {
            throw new Exception(obj.getString("msg"));
        }

        org.json.JSONObject projects = obj.getJSONObject("data");
        result = projects.getString("projects");
        System.out.println("projects:" + projects);
//		String sql = "SELECT projects FROM users WHERE userID='" + username + "'";
//		String result = "";
//		try {
//			PreparedStatement pst = conn.prepareStatement(sql);
//			System.out.println(sql);
//			m_rset = pst.executeQuery();
//			m_rset.next();
//			result = m_rset.getString(1);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return result;
    }
}
