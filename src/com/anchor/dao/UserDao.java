package com.anchor.dao;

import java.util.List;
import java.util.Map;

import com.anchor.db.DBUtilsTemplate;


/** 
 * 项目名称：BDSIMP
 * 类描述：用户信息
 * 作者：张庭伟     时间：2018年6月29日下午7:44:50
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class UserDao {

	private DBUtilsTemplate dbUtils = new DBUtilsTemplate();
	/**
	 * 根据ID查询用户信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findUserObject(String param){
		String sql = "SELECT nCompanyID,sUserName,sLoginAccount,sPassWord,sWXOpenid,sWXNickname,sPartMent,sPosition,sSex,sAddress,sTel,sEMail,sRoler,sProjectIDS,nState,nLoginTimes,sLastLoginIP,dtLastLoginDT,dtCreateAt,dtModifyAt,nModifyBy FROM base_user_info t1 where ID = ?";
		List<Map<String, Object>> list = dbUtils.find(sql, param);
		return list;
	}
	
	/**
	 * 修改
	 * @param params
	 * @return
	 */
	public int updateUserInfo(String params[]){
		String sql = "UPDATE base_user_info SET sUserName = ?,sPosition = ?,sTel = ?,sEMail = ?,sPartMent = ?,sAddress = ?, dtModifyAt = ? WHERE ID = ?";
		int row = dbUtils.update(sql, params);
		System.out.println(row);
		if(row>0){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 查询所有用户信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findAllUserObject(){
		String sql = "SELECT ID,sUserName,sProjectIDS FROM base_user_info";
		List<Map<String, Object>> list = dbUtils.find(sql);
		return list;
	}
}
