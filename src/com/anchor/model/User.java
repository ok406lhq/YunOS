package com.anchor.model;



public class User {
	public Double id;
	public String UserID;	
	public String UserName;
	public String Password;
	public String mobile;
	public String eTelNum;
	public String email;
	public String role;
	public int loginnum;
	public String loginIp;
	public String lastlogindata;
	public String adddate;
	public String department;
	public String position;
	public String address;
	public String defPrj;
	public String avatar;
	public int totalnum;
///////////////////////////////////////////////////
	public int warnID;
	public String userName;
	public String prjName;
	public String Wtext;
	public String warningtype;
	public String warningLevel;
	public String Interval;
	public String msgInterval;
	public String project;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public User() {
		super();
	}
	
	
	//ID, sUserName, sPassWord, sPartMent, sTel, sEMail, dtCreateAt, sPosition,sAddress
	public User(String UserID, String userName, String password, String sPartMent, String sTel,String sEMail, 
			String dtCreateAt, String position,String sAddress) {
		super();
		this.UserID = UserID;
		this.UserName = userName;
		this.Password = password;
		this.department = sPartMent;
		this.mobile = sTel;
	    this.email = sEMail;
		this.adddate = dtCreateAt;
		this.position = position;
		this.address = sAddress;
		
	
	}
	
	
	public void settotalnum(int totalnum){
		this.totalnum=totalnum;
		
	}
	
	public int gettotalnum(){
		return this.totalnum;
	}
	
	public void setid(Double id){
		this.id=id;
		
	}
	
	public Double getid(){
		return this.id;
	}
	
	public void setUserID(String string){
		this.UserID=string;
		
	}
	
	public String getUserID(){
		return this.UserID;
	}
	public void setUserName(String UserName){
		this.UserName=UserName;
		
	}
	public String getUserName(){
		return this.UserName;
	}
	public void setPassword(String Password){
		this.Password=Password;
		
	}
	public String getPassword(){
		return this.Password;
	}
	
	public void setmobile(String mobile){
		this.mobile=mobile;
		
	}
	public String getmobile(){
		return this.mobile;
	}
	
	public void seteTelNum(String eTelNum){
		this.eTelNum=eTelNum;
		
	}
	public String geteTelNum(){
		return this.eTelNum;
	}
	public void setemail(String email){
		this.email=email;
		
	}
	public String getemail(){
		return this.email;
	}
	
	public void setrole(String role){
		this.role=role;
		
	}
	public String getrole(){
		return this.role;
	}
	
	public void setloginIp(String loginIp){
		this.loginIp=loginIp;
		
	}
	public String getloginIp(){
		return this.loginIp;
	}
	
	public void setlastlogindata(String lastlogindata){
		this.lastlogindata=lastlogindata;
		
	}
	public String getlastlogindata(){
		return this.lastlogindata;
	}
	
	public void setloginnum(int loginnum){
		this.loginnum=loginnum;
		
	}
	public int getloginnum(){
		return this.loginnum;
	}
	
	public void setadddate(String adddate){
		this.adddate=adddate;
		
	}
	public String getadddate(){
		return this.adddate;
	}
	
	public void setdepartment(String department){
		this.department=department;
		
	}
	public String getdepartment(){
		return this.department;
	}
	public void setposition(String position){
		this.position=position;
		
	}
	public String getposition(){
		return this.position;
	}
	
	public void setaddress(String address){
		this.address=address;
		
	}
	public String getaddress(){
		return this.address;
	}
	
	public void setdefPrj(String defPrj){
		this.defPrj=defPrj;
		
	}
	public String getdefPrj(){
		return this.defPrj;
	}
	
	public void setavatar(String avatar){
		this.avatar=avatar;
		
	}
	public String getavatar(){
		return this.avatar;
	}
///////////////////////////////////////////////////
	public void setuserName(String userName){
		this.userName=userName;
		
	}
	public String getuserName(){
		return this.userName;
	}
	
	public void setprjName(String prjName){
		this.prjName=prjName;
		
	}
	public String getprjName(){
		return this.prjName;
	}
	
	public void setWtext(String Wtext){
		this.Wtext=Wtext;
		
	}
	public String getWtext(){
		return this.Wtext;
	}
	public void setwarningtype(String warningtype){
		this.warningtype=warningtype;
		
	}
	public String getwarningtype(){
		return this.warningtype;
	}
	public void setwarningLevel(String warningLevel){
		this.warningLevel=warningLevel;
		
	}
	public String getwarningLevel(){
		return this.warningLevel;
	}
	public void setInterval(String Interval){
		this.Interval=Interval;
		
	}
	public String getInterval(){
		return this.Interval;
	}
	
	
	public void setwarnID(int warnID){
		this.warnID=warnID;
		
	}
	public int getwarnID(){
		return this.warnID;
	}
	public void setmsgInterval(String msgInterval) {
		// TODO Auto-generated method stub
		this.msgInterval=msgInterval;
	}
	public String getmsgInterval(){
		return this.msgInterval;
	}
	
	
}
