package com.anchor.service;

import com.anchor.dao.MoniteSiteDao;
import com.anchor.model.MoniteSite;
import com.anchor.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MoniteSiteService {

    //查询设备日志数量
    public int getCountMoniteSite(String project, String siteid){
        MoniteSiteDao dao = new MoniteSiteDao();
        if(StringUtils.isNotBlank(project)) {
           return dao.getMoniteSiteList(project, siteid);
        }
        return 0;
    }
    //分页查询设备日志
    public List<MoniteSite> getMoniteSiteList(String project, String siteid, int page, int pageSize) {
        MoniteSiteDao dao = new MoniteSiteDao();
        if(StringUtils.isNotBlank(project)){
           return dao.getMoniteSiteList(project,siteid,page,pageSize);
        }
        return null;
    }

    public JSONArray QueryMoniteData(String siteid, int page, int pageSize) {
        JSONArray json = new JSONArray();
        try {
            MoniteSiteDao monitesiteDao = new MoniteSiteDao();
            List<MoniteSite> monitesitelist = monitesiteDao.QueryMoniteSiteDataList(siteid, page, pageSize);
            for (int i = 0; i < monitesitelist.size(); i++) {
                MoniteSite monitesite = monitesitelist.get(i);
                JSONObject jsonObject = new JSONObject();


                String lastaction = monitesite.getsLastAction();
                String action = "";


                if (lastaction.equals("Heart")) {

                    action = "心跳包";

                } else if (lastaction.equals("DisConnect")) {

                    action = "离线";

                } else if (lastaction.equals("Register")) {

                    action = "注册";

                } else {

                    action = "数据";
                }


                jsonObject.put("nSiteID", monitesite.getnSiteID());
                jsonObject.put("sSendCommand", monitesite.getsSendCommand());
                jsonObject.put("nSendMinuteInterval", monitesite.getnSendMinuteInterval());
                jsonObject.put("dtSendDateTime", monitesite.getdtSendDateTime());
                jsonObject.put("dtLoginDateTime", monitesite.getdtLoginDateTime());
                jsonObject.put("sLastAction", action);
                jsonObject.put("dtLastDateTime", monitesite.getdtLastDateTime());


                jsonObject.put("sProjectName", monitesite.getsProjectName());
                jsonObject.put("sDtuName", monitesite.getsDtuName());
                json.add(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
