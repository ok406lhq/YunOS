package com.anchor.model;


public class MoniteSite {
	
	public String nSiteID;
	public String sSendCommand;	
	public String nSendMinuteInterval; 
	public String dtSendDateTime;  
	public String dtLoginDateTime; 
	public String sLastAction; 
	public String dtLastDateTime; 
	public String sProjectName; 
	public String sProjectAbbr; 
	public String sDtuName;
	public String cnSLastAction;

	public String getCnSLastAction() {
		if ("Heart".equals(sLastAction)) {
			cnSLastAction = "心跳包";
		} else if ("DisConnect".equals(sLastAction)) {
			cnSLastAction = "离线";
		} else if ("Register".equals(sLastAction)) {
			cnSLastAction = "注册";
		} else {
			cnSLastAction = "数据";
		}
		return cnSLastAction;
	}

	
	
	public String getsProjectAbbr() {
		return sProjectAbbr;
	}



	public void setsProjectAbbr(String sProjectAbbr) {
		this.sProjectAbbr = sProjectAbbr;
	}



	public void setCnSLastAction(String cnSLastAction) {
		this.cnSLastAction = cnSLastAction;
	}

	public String getnSiteID(){
		return this.nSiteID;
	}
	
	public void setnSiteID(String nSiteID){
		this.nSiteID=nSiteID;
	}
	
	
	public String getsSendCommand(){
		return this.sSendCommand;
	}
	
	public void setsSendCommand(String sSendCommand){
		this.sSendCommand=sSendCommand;
	}
	
	public String getnSendMinuteInterval(){
		return this.nSendMinuteInterval;
	}
	
	public void setnSendMinuteInterval(String nSendMinuteInterval){
		this.nSendMinuteInterval=nSendMinuteInterval;
	}
	
	public String getdtSendDateTime(){
		return this.dtSendDateTime;
	}
	
	public void setdtSendDateTime(String dtSendDateTime){
		this.dtSendDateTime=dtSendDateTime;
	}
	
	public String getdtLoginDateTime(){
		return this.dtLoginDateTime;
	}
	
	public void setdtLoginDateTime(String dtLoginDateTime){
		this.dtLoginDateTime=dtLoginDateTime;
	}
	
	
	
	public String getsLastAction(){
		return this.sLastAction;
	}
	
	public void setsLastAction(String sLastAction){
		this.sLastAction=sLastAction;
	}
	
	public String getdtLastDateTime(){
		return this.dtLastDateTime;
	}
	
	public void setdtLastDateTime(String dtLastDateTime){
		this.dtLastDateTime=dtLastDateTime;
	}
	
	
	//monitesite.setdtLastDateTime(objItem.getString("sProjectName"));
	//monitesite.setdtLastDateTime(objItem.getString("sDtuName"));
	public String getsProjectName(){
		return this.sProjectName;
	}
	
	public void setsProjectName(String sProjectName){
		this.sProjectName=sProjectName;
	}
	
	public String getsDtuName(){
		return this.sDtuName;
	}
	
	public void setsDtuName(String sDtuName){
		this.sDtuName=sDtuName;
	}
	
	
	
	
	
	public MoniteSite() {
		super();
	}
}
