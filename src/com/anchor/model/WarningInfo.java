package com.anchor.model;

import java.sql.Date;

/**
 * 项目信息类,
 * @author www.xcanchor.com
 *
 */
public class WarningInfo {
/*
	public final static String SID ="ID";  
	public final static String SDATETIME ="预警时间";  
	public final static String SMSG ="预警信息";  
	public final static String SSENSOR ="传感器";  
	public final static String STYPE ="类型";  
*/
	public final static String SID ="ID";  
	public final static String SDATETIME ="waringTime";  
	public final static String SMSG ="waringMsg";  
	public final static String SSENSOR ="wSensor";  
	public final static String STYPE ="waringType";  
	
	public int ID;	//报警序号
	public String sdatetime;	//报警时间
	public String project;  //项目名称
	public String sensor;  //预警设备名
	public String active;    //预警级别
	public String msg;  //预警详细描述
	public int confirm; //预警确认
	public int handle; //预警接受
	public String response;  //预警回馈
	public String reportURL;  //预警处理报告链接地址
	public String PrjAlias;
	//

	public WarningInfo() {
		super();
	}
	
	public int getConfirm() {
		return confirm;
	}
	
	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	
	public int getHandle() {
		return handle;
	}

	public void setHandle(int handle) {
		this.handle = handle;
	}

	public int getID() {
	    return this.ID;
	}
	public void setID(int ID) {
	    this.ID=ID;
	}
	public String getsdatetime() {
	    return this.sdatetime;
	}
	public void setsdatetime(String sdatetime) {
	    this.sdatetime=sdatetime;
	}
	public String getproject() {
	    return this.project;
	}
	public void setproject(String project) {
	    this.project=project;
	}
	public String getsensor() {
	    return this.sensor;
	}
	public void setsensor(String sensor) {
	    this.sensor=sensor;
	}
	public String getactive() {
	    return this.active;
	}
	public void setactive(String active) {
	    this.active=active;
	}
	public String getmsg() {
	    return this.msg;
	}
	public void setmsg(String msg) {
	    this.msg=msg;
	}
	public String getresponse() {
	    return this.response;
	}
	public void setresponse(String response) {
	    this.response=response;
	}
	public String getreportURL() {
	    return this.reportURL;
	}
	public void setreportURL(String reportURL) {
	    this.reportURL=reportURL;
	}

	public void setPrjAlias(String PrjAlias) {
		// TODO Auto-generated method stub
		 this.PrjAlias=PrjAlias;
	}

	public String getPrjAlias() {
		// TODO Auto-generated method stub
		return this.PrjAlias;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WarningInfo [ID=" + ID + ", sdatetime=" + sdatetime + ", project=" + project + ", sensor=" + sensor
				+ ", active=" + active + ", msg=" + msg + ", confirm=" + confirm + ", handle=" + handle + ", response="
				+ response + ", reportURL=" + reportURL + ", PrjAlias=" + PrjAlias + "]";
	}
	
	
}
