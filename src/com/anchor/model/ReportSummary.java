package com.anchor.model;

import java.sql.Date;

/**
 * 项目信息类,
 * @author www.xcanchor.com
 *
 */
public class ReportSummary {
/*
	public final static String SID ="ID";  
	public final static String SDATETIME ="预警时间";  
	public final static String SMSG ="预警信息";  
	public final static String SSENSOR ="传感器";  
	public final static String STYPE ="类型";  
*/
	public final static String SPRJNAME ="PrjName";  
	public final static String SPRSALAIS ="PrjAlais";  
	public final static String SPRJTYPE ="PrjType";  
	public final static String SCURSTATUS ="CurStatus";  
	public final static String UNPROCSUM ="unprocSum";  
	public final static String ALARMCOUNT ="AlarmCount";  
	public final static String ACTIONCOUNT ="ActionCount";  
	public final static String REPORTURL ="ReportURL";  
	public final static String SDATETIME ="sDateTime";  
	public final static String SNAME ="sName";  
	public final static String URLADDR ="urlAddr";  
	public String sPrjName;	//项目名称
	public String sPrjAlais;	//项目别名
	public String sPrjType;  //项目类型
	public String curStatus;  //当前状态
	public double unPorcSum;    //未处理统计--
	public double alamCount;  //二级预警
	public double actionCount;  //一级预警
	public String reportURL;  //预警处理报告链接地址
	public String sdatetime;  //报警的时间
	public String sName;
	public String urlAddr;
	//

	public ReportSummary() {
		super();
	}
	
	public String getsdatetime() {
	    return this.sdatetime;
	}
	public void setsdatetime(String sdatetime) {
	    this.sdatetime=sdatetime;
	}
	public String getsPrjName() {
	    return this.sPrjName;
	}
	public void setsPrjName(String sPrjName) {
	    this.sPrjName=sPrjName;
	}
	public String getsPrjAlais() {
	    return this.sPrjAlais;
	}
	public void setsPrjAlais(String sPrjAlais) {
	    this.sPrjAlais=sPrjAlais;
	}
	public String getsPrjType() {
	    return this.sPrjType;
	}
	public void setsPrjType(String sPrjType) {
	    this.sPrjType=sPrjType;
	}
	public String getcurStatus() {
		if (this.curStatus == null)
			return " ";
		else
			return this.curStatus;
	}
	public void setcurStatus(String curStatus) {
	    this.curStatus=curStatus;
	}
	public double getunPorcSum() {
	    return this.unPorcSum;
	}
	public void setunPorcSum(double unPorcSum) {
	    this.unPorcSum=unPorcSum;
	}
	public double getalamCount() {
	    return this.alamCount;
	}
	public void setalamCount(double alamCount) {
	    this.alamCount=alamCount;
	}
	public double getactionCount() {
	    return this.actionCount;
	}
	public void setactionCount(double actionCount) {
	    this.actionCount=actionCount;
	}
	public String getreportURL() {
		if (this.reportURL == null)
			return " ";
		else
			return this.reportURL;
	}
	public void setreportURL(String reportURL) {
	    this.reportURL=reportURL;
	}

	public void setsName(String sName) {
		// TODO Auto-generated method stub
		 this.sName=sName;
		
	}
	public String getsName() {
	    return this.sName;
	}
	
	public String geturlAddr() {
	    return this.urlAddr;
	}
	
	public void seturlAddr(String urlAddr) {
		// TODO Auto-generated method stub
		 this.urlAddr=urlAddr;
		
	}
}
