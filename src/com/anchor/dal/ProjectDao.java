package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class ProjectDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String, Object>> findProjectByIds(String projectIds){
    	StringBuffer buffer = new StringBuffer();
		String spid[] = projectIds.split(",");
		for (int i = 0; i < spid.length; i++) {
			if(i>0) {
				buffer.append(" or ");
			}
			buffer.append("ID ="+spid[i]);
		}
		
        List<Map<String, Object>> projectList = dbUtils.find("select * from base_project_info where ("+buffer.toString()+")");
        return projectList;
    }
}
