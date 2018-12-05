package com.anchor.service;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.anchor.dao.ProjectInfoDao;
import com.anchor.model.ProjectInfo;
import com.anchor.util.DbUtil;
import com.anchor.util.HtmlUtil;
import com.anchor.util.json.JSONUtil;
import org.apache.commons.lang.StringUtils;

public class ProjectInfoService {

    // 查询项目信息
    public String SummaryProjectStatus(String prjName) {
        // TODO Auto-generated method stub
        Connection con = null;
        DbUtil dbUtil = null;
        String json = "";
        try {
            dbUtil = new DbUtil();
            con = dbUtil.getCon();
            if (null != con) {
                ProjectInfoService prjInfoService = new ProjectInfoService();
                JSONArray summaryArray = prjInfoService.QueryProjectsInfo(con, prjName);
                json = summaryArray.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != con) {
                    if (null != dbUtil) {
                        dbUtil.closeCon(con);
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return json;
    }

    // 查询项目各种信息-组装成JSONArray
    public JSONArray QueryProjectsInfo(Connection con, String prjName) {
        JSONArray summaryArray = null;
        try {
            ProjectInfoDao projectInfoDao = new ProjectInfoDao();
            // json = m_projectInfoDao.queryPtInfoJson(con);
            List<ProjectInfo> prjInfos = projectInfoDao.queryPrjInfoList(con, prjName);
            if (null != prjInfos) {
                summaryArray = new JSONArray();
                JSONObject prjInfosItem = new JSONObject();
                JSONArray prjInfoArray = new JSONArray();
                int alert = 0;
                int alarm = 0;
                int action = 0;
                int normal = 0;
                for (int i = 0; i < prjInfos.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
                    ProjectInfo prjInfoItem = prjInfos.get(i);
                    JSONObject mapOfColValues = new JSONObject();
                    //
                    String statusVal = prjInfoItem.getStatusVal();
                    if (statusVal == null)
                        statusVal = "";
                    if (statusVal.equals("alert"))
                        alert++;
                    else if (statusVal.equals("alarm"))
                        alarm++;
                    else if (statusVal.equals("action"))
                        action++;
                    else
                        normal++;
                    mapOfColValues.put("prjID", prjInfoItem.getPrjID());
                    mapOfColValues.put(ProjectInfo.PRJNAME, prjInfoItem.getPrjName());
                    mapOfColValues.put(ProjectInfo.PRJALAIS, prjInfoItem.getPrjAlais());
                    mapOfColValues.put(ProjectInfo.PRJTYPE, prjInfoItem.getPrjType());
                    mapOfColValues.put(ProjectInfo.STARTTIME, prjInfoItem.getStartTime());
                    mapOfColValues.put(ProjectInfo.ENDTIME, prjInfoItem.getEndTime());
                    mapOfColValues.put(ProjectInfo.LATITUDE, prjInfoItem.getLatitude());
                    mapOfColValues.put(ProjectInfo.LONGITUDE, prjInfoItem.getLongitude());
                    mapOfColValues.put(ProjectInfo.STATUSVAL, statusVal);
                    mapOfColValues.put("Bgimg", prjInfoItem.getBgimg());
                    mapOfColValues.put(ProjectInfo.STATUSDES, prjInfoItem.getStatusDes());
                    mapOfColValues.put(ProjectInfo.BIAODUAN, prjInfoItem.getproperty(ProjectInfo.BIAODUAN));
                    mapOfColValues.put(ProjectInfo.JIEGOUDES, prjInfoItem.getproperty(ProjectInfo.JIEGOUDES));
                    mapOfColValues.put(ProjectInfo.JIANCENEIRONG, prjInfoItem.getproperty(ProjectInfo.JIANCENEIRONG));
                    mapOfColValues.put(ProjectInfo.LXR1, prjInfoItem.getproperty(ProjectInfo.LXR1));
                    mapOfColValues.put(ProjectInfo.LXR2, prjInfoItem.getproperty(ProjectInfo.LXR2));
                    mapOfColValues.put(ProjectInfo.PRJADDR, prjInfoItem.getproperty(ProjectInfo.PRJADDR));
                    prjInfoArray.add(mapOfColValues);
                }
                prjInfosItem.put("PrjInfo", prjInfoArray);
                summaryArray.add(prjInfosItem);
                //
                JSONObject prjStatusItem = new JSONObject();
                JSONArray prjStatusArray = new JSONArray();
                JSONObject statusItem = new JSONObject();
                statusItem.put("alert", alert);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("alarm", alarm);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("action", action);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("normal", normal);
                prjStatusArray.add(statusItem);

                prjStatusItem.put("PrjStatus", prjStatusArray);
                summaryArray.add(prjStatusItem);
                //
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return summaryArray;
    }

    ///
    // 查询单个项目各种信息-组装成JSONArray
    public JSONArray QueryProjectInfo(Connection con, String prjName) {
        JSONArray summaryArray = null;
        try {
            ProjectInfoDao projectInfoDao = new ProjectInfoDao();

            List<ProjectInfo> prjInfos = projectInfoDao.queryPrjInfoList(con, prjName);
            if (null != prjInfos) {
                summaryArray = new JSONArray();
                for (int i = 0; i < prjInfos.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
                    ProjectInfo prjInfoItem = prjInfos.get(i);
                    JSONObject mapOfColValues = new JSONObject();
                    //
                    String statusVal = prjInfoItem.getStatusVal();

                    mapOfColValues.put("prjID", prjInfoItem.getPrjID());
                    mapOfColValues.put(ProjectInfo.PRJNAME, prjInfoItem.getPrjName());
                    mapOfColValues.put(ProjectInfo.PRJALAIS, prjInfoItem.getPrjAlais());
                    mapOfColValues.put(ProjectInfo.PRJTYPE, prjInfoItem.getPrjType());
                    mapOfColValues.put(ProjectInfo.STARTTIME, prjInfoItem.getStartTime());
                    ///
					/*DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					String dateStr = fmt.format(new Date());*/
                    mapOfColValues.put(ProjectInfo.ENDTIME, prjInfoItem.getEndTime());
                    // mapOfColValues.put(ProjectInfo.ENDTIME, prjInfoItem.getEndTime());
                    mapOfColValues.put(ProjectInfo.LATITUDE, prjInfoItem.getLatitude());
                    mapOfColValues.put(ProjectInfo.LONGITUDE, prjInfoItem.getLongitude());
                    mapOfColValues.put(ProjectInfo.STATUSVAL, statusVal);
                    mapOfColValues.put(ProjectInfo.STATUSDES, prjInfoItem.getStatusDes());
                    mapOfColValues.put("Bgimg", prjInfoItem.getBgimg());
                    mapOfColValues.put(ProjectInfo.BIAODUAN, prjInfoItem.getproperty(ProjectInfo.BIAODUAN));
                    mapOfColValues.put(ProjectInfo.JIEGOUDES, prjInfoItem.getDescription());
                    mapOfColValues.put(ProjectInfo.JIANCENEIRONG, prjInfoItem.getpropertyVal());
                    mapOfColValues.put(ProjectInfo.LXR1, prjInfoItem.getLxr1());
                    mapOfColValues.put(ProjectInfo.LXR2, prjInfoItem.getLxr2());
                    mapOfColValues.put(ProjectInfo.PRJADDR, prjInfoItem.getPrjAddr());
                    mapOfColValues.put(ProjectInfo.PRJADDR, prjInfoItem.getPrjAddr());
                    mapOfColValues.put(ProjectInfo.ADDRCITY, prjInfoItem.getAddrCity());
                    mapOfColValues.put(ProjectInfo.ADDRREGIONS, prjInfoItem.getAddrRegions());
                    summaryArray.add(mapOfColValues);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return summaryArray;
    }

    public JSONObject QueryProjectByPrjName(String prjName, String realDir) {
        Connection con = null;
        DbUtil dbUtil = null;
        String json = "";
        JSONObject mapOfColValues = new JSONObject();
        try {
            dbUtil = new DbUtil();
            con = dbUtil.getCon();
//			if (null != con) {
            ProjectInfoDao projectInfoDao = new ProjectInfoDao();
            ProjectInfo prjInfoItem = projectInfoDao.QueryProjectsInfoByprjName(con, prjName);
            mapOfColValues.put("prjID", prjInfoItem.getPrjID());
            mapOfColValues.put(ProjectInfo.PRJNAME, prjInfoItem.getPrjName());
            mapOfColValues.put(ProjectInfo.PRJTYPE, prjInfoItem.getPrjType());
            mapOfColValues.put(ProjectInfo.PRJALAIS, prjInfoItem.getPrjAlais());
            mapOfColValues.put(ProjectInfo.STARTTIME, prjInfoItem.getStartTime());
            mapOfColValues.put(ProjectInfo.ENDTIME, prjInfoItem.getEndTime());
            mapOfColValues.put(ProjectInfo.LATITUDE, prjInfoItem.getLatitude());
            mapOfColValues.put(ProjectInfo.LONGITUDE, prjInfoItem.getLongitude());
            mapOfColValues.put(ProjectInfo.STATUSVAL, prjInfoItem.getStatusVal());
            mapOfColValues.put(ProjectInfo.STATUSDES, prjInfoItem.getStatusDes());
            mapOfColValues.put("Bgimg", prjInfoItem.getBgimg());
            mapOfColValues.put(ProjectInfo.BIAODUAN, prjInfoItem.getproperty(ProjectInfo.BIAODUAN));
            mapOfColValues.put(ProjectInfo.JIEGOUDES, prjInfoItem.getproperty(ProjectInfo.JIEGOUDES));
            mapOfColValues.put(ProjectInfo.LXR1, prjInfoItem.getproperty(ProjectInfo.LXR1));
            mapOfColValues.put(ProjectInfo.LXR2, prjInfoItem.getproperty(ProjectInfo.LXR2));
            System.out.println("Bgimg:" + realDir + prjInfoItem.getBgimg());
//			}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != con) {
                    if (null != dbUtil) {
                        dbUtil.closeCon(con);
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mapOfColValues;

    }

    /*
     * 通过prjname查询项目信息
     */
    public String getmyproject(String myproject) {
        Connection con = null;
        DbUtil dbUtil = null;
        String json = "";
        JSONArray summaryArray = null;
        try {
            dbUtil = new DbUtil();
            con = dbUtil.getCon();
            ProjectInfoDao projectInfoDao = new ProjectInfoDao();
            List<ProjectInfo> prjInfos = projectInfoDao.queryMyPrjInfoList(con, myproject);
            if (null != prjInfos) {
                summaryArray = new JSONArray();
                JSONObject prjInfosItem = new JSONObject();
                JSONArray prjInfoArray = new JSONArray();
                int alert = 0;//三级
                int alarm = 0;//一级
                int action = 0;//二级
                int normal = 0;//正常
                int fourLevelWarn = 0; //四级
                for (int i = 0; i < prjInfos.size(); i++) {// 内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
                    ProjectInfo prjInfoItem = prjInfos.get(i);
                    JSONObject mapOfColValues = new JSONObject();
                    //
                    String statusVal = prjInfoItem.getStatusVal();
                    if (statusVal == null)
                        statusVal = "";
                    if (statusVal.equals("三级预警"))
                        alert++;
                    else if (statusVal.equals("一级预警"))
                        alarm++;
                    else if (statusVal.equals("二级预警"))
                        action++;
                    else if (statusVal.equals("四级预警"))
                        fourLevelWarn++;
                    else
                        normal++;
                    mapOfColValues.put("prjID", prjInfoItem.getPrjID());
                    mapOfColValues.put(ProjectInfo.PRJNAME, prjInfoItem.getPrjName());
                    mapOfColValues.put(ProjectInfo.PRJALAIS, prjInfoItem.getPrjAlais());
                    mapOfColValues.put(ProjectInfo.PRJTYPE, prjInfoItem.getPrjType());
                    mapOfColValues.put(ProjectInfo.STARTTIME, prjInfoItem.getStartTime());
                    mapOfColValues.put(ProjectInfo.ENDTIME, prjInfoItem.getEndTime());
                    mapOfColValues.put(ProjectInfo.LATITUDE, prjInfoItem.getLatitude());
                    mapOfColValues.put(ProjectInfo.LONGITUDE, prjInfoItem.getLongitude());
                    mapOfColValues.put(ProjectInfo.STATUSVAL, statusVal);
                    mapOfColValues.put("Bgimg", prjInfoItem.getBgimg());
                    mapOfColValues.put(ProjectInfo.STATUSDES, prjInfoItem.getStatusDes());
                    mapOfColValues.put(ProjectInfo.BIAODUAN, prjInfoItem.getproperty(ProjectInfo.BIAODUAN));
                    mapOfColValues.put(ProjectInfo.JIEGOUDES, prjInfoItem.getDescription());
                    mapOfColValues.put(ProjectInfo.JIANCENEIRONG, prjInfoItem.getpropertyVal());
                    mapOfColValues.put(ProjectInfo.LXR1, prjInfoItem.getproperty(ProjectInfo.LXR1));
                    mapOfColValues.put(ProjectInfo.LXR2, prjInfoItem.getproperty(ProjectInfo.LXR2));
                    //mapOfColValues.put(ProjectInfo.PRJADDR, prjInfoItem.getproperty(ProjectInfo.PRJADDR));
                    mapOfColValues.put(ProjectInfo.PRJADDR, prjInfoItem.getPrjAddr());
                    mapOfColValues.put(ProjectInfo.SURLADDRESS, prjInfoItem.getUrlAddress());
                    prjInfoArray.add(mapOfColValues);
                }
                prjInfosItem.put("PrjInfo", prjInfoArray);
                summaryArray.add(prjInfosItem);
                JSONObject prjStatusItem = new JSONObject();
                JSONArray prjStatusArray = new JSONArray();
                JSONObject statusItem = new JSONObject();
                statusItem.put("alert", alert);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("alarm", alarm);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("action", action);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("normal", normal);
                prjStatusArray.add(statusItem);
                statusItem = new JSONObject();
                statusItem.put("fourLevelWarn", fourLevelWarn);
                prjStatusArray.add(statusItem);
                prjStatusItem.put("PrjStatus", prjStatusArray);
                summaryArray.add(prjStatusItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != con) {
                    if (null != dbUtil) {
                        dbUtil.closeCon(con);
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return summaryArray.toString();
    }

    /**
     * 通过用户名查出对应的项目
     *
     * @param username
     * @return
     */
    public String userproject(String username) {
        Connection con = null;
        DbUtil dbUtil = null;
        String json = "";
        try {
            dbUtil = new DbUtil();
            //con = dbUtil.getCon();
            ProjectInfoDao dao = new ProjectInfoDao();
            json = dao.getuserproject(con, username);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return json;
    }

    public List<String> getByProjectType(String projectType) {
        ProjectInfoDao dao = new ProjectInfoDao();
        List<Map<String, Object>> list= dao.getByProjectType(projectType);

        List<String> integerList = new ArrayList<>();
        for (Map<String, Object> map: list) {
            Iterator it = map.entrySet().iterator() ;
            while (it.hasNext())
            {
                Map.Entry entry = (Map.Entry) it.next() ;
                if(entry.getValue() !=null){
                    integerList.add(entry.getValue().toString());
                }
            }
        }
        return integerList;
    }
}
