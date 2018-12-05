package com.anchor.dal;

import com.anchor.db.DBUtilsTemplate;

import java.util.List;
import java.util.Map;

public class AuthorDao {

    private DBUtilsTemplate dbUtils = new DBUtilsTemplate();

    public List<Map<String, Object>> findProjectByIds(){
        List<Map<String, Object>> authorList = dbUtils.find("select * from base_user_info");
        return authorList;
    }
}
