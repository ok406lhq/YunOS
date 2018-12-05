package com.anchor.dao;

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;

/**
 * 
 * @项目名称：BDSIMP 
 * @类名称：LogoDao 
 * @类描述：
 * @作者：张庭伟         @创建时间：2018年9月11日下午4:50:37 
 * @修改人：                 @修改时间：
 * @备注： 
 * @version
 */
public class LogoDao {

	DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> initializeData(String param){
		String sql = "SELECT t1.iLoginLogo,t1.sWebName FROM base_company_info t1 INNER JOIN base_user_info t2 on t1.ID = t2.nCompanyID WHERE t2.ID = ?";
		List<Map<String, Object>> list = dbUtils.find(sql,param);
		return list;
	}
}
