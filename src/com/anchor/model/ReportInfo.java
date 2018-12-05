package com.anchor.model;

import java.sql.Date;

/**
 * 项目信息类,
 * @author www.xcanchor.com
 *
 */
public class ReportInfo {
/*
	public final static String SID ="ID";  
	public final static String SDATETIME ="预警时间";  
	public final static String SMSG ="预警信息";  
	public final static String SSENSOR ="传感器";  
	public final static String STYPE ="类型";  
*/
	public final static String SID ="ID";  
	public final static String SNAME ="sname";  
	public final static String SPRJNAME ="sprjname";  
	public final static String SCREATETIME ="screatetime";  
	public final static String SSTARTTIME ="sstarttime";  
	public final static String SENDTIME ="sendtime"; 
	public final static String SAUTHOR ="author"; 
	public final static String URLADDR ="url";  
	public final static String READTIMES ="read";  
	public final static String SREPORTTYPE ="sReportType";  
	
	public int ID;	//报告序号
	public String sname;	//报告名称
	public String sprjName;	//项目名称
	public String sauthor;	//创建时间
	public String screatetime;	//创建时间
	public String sstarttime;	//报告内容开始时间
	public String sendtime;	//报告内容结束时间
	public String urlAddr;  //报告下载地址
	public int readTimes;  //读取次数
	public String sReportType;// 报表类型
	//

	public ReportInfo() {
		super();
	}
	
	public int getID() {
	    return this.ID;
	}
	public void setID(int ID) {
	    this.ID=ID;
	}
	public String getsname() {
	    return this.sname;
	}
	public void setsname(String sname) {
	    this.sname=sname;
	}
	public String getsprjName() {
	    return this.sprjName;
	}
	public void setsprjName(String sprjName) {
	    this.sprjName=sprjName;
	}
	public String getsauthor() {
	    return this.sauthor;
	}
	public void setsauthor(String sauthor) {
	    this.sauthor=sauthor;
	}
	
	public String getscreatetime() {
	    return this.screatetime;
	}
	public void setscreatetime(String screatetime) {
	    this.screatetime=screatetime;
	}
	public String getsstarttime() {
	    return this.sstarttime;
	}
	public void setsstarttime(String sstarttime) {
	    this.sstarttime=sstarttime;
	}
	public String getsendtime() {
	    return this.sendtime;
	}
	public void setsendtime(String sendtime) {
	    this.sendtime=sendtime;
	}
	public String geturlAddr() {
	    return this.urlAddr;
	}
	public void seturlAddr(String urlAddr) {
	    this.urlAddr=urlAddr;
	}
	public int getreadTimes() {
	    return this.readTimes;
	}
	public void setreadTimes(int readTimes) {
	    this.readTimes=readTimes;
	}

	public String getsReportType() {
		return sReportType;
	}

	public void setsReportType(String sReportType) {
		this.sReportType = sReportType;
	}
	
}
