package com.anchor.model;


public class SystemLog {
	public String id;
	public String userID;	//userid
	public String username;  //用户名
	public String active;  //行为动作
	public String descript; //日志描叙
	public String adddate; //日子日期
	public String getid(){
		return this.id;
	}
	
	public void setid(String id){
		this.id=id;
	}
	public String getuserID(){
		return this.userID;
	}
	
	public void setuserID(String userID){
		this.userID=userID;
	}
	
	public String getusername(){
		return this.username;
	}
	
	public void setusername(String username){
		this.username=username;
	}
	
	public String getactive(){
		return this.active;
	}
	
	public void setactive(String active){
		this.active=active;
	}
	
	public String getdescript(){
		return this.descript;
	}
	
	public void setdescript(String descript){
		this.descript=descript;
	}
	public String getadddate(){
		return this.adddate;
	}
	
	public void setadddate(String adddate){
		this.adddate=adddate;
	}
	
	public SystemLog() {
		super();
	}
}
