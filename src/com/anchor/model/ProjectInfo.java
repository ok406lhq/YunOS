package com.anchor.model;

import java.sql.Date;

//import com.sun.org.apache.xpath.internal.functions.Function;

/**
 * 项目信息类,
 * @author www.xcanchor.com
 *
 */
public class ProjectInfo {
/*
	public final static String PRJNAME ="项目名";  
	public final static String PRJTYPE ="项目类型";  
	public final static String PRJADDR ="项目地址";  
	public final static String STARTTIME ="开始时间";  
	public final static String ENDTIME ="结束时间"; 
	public final static String BIAODUAN ="标段";  
	public final static String JIEGOUDES ="结构概述";  
	public final static String JIANCENEIRONG ="主要监测内容";  
	public final static String LATITUDE ="经度坐标";  
	public final static String LONGITUDE ="纬度坐标";  
	public final static String STATUSVAL ="状态";  
	public final static String STATUSDES ="状态描述";  
*/	//
	public final static String PRJNAME ="prjName";  
	public final static String PRJALAIS ="prjAlais";  
	public final static String PRJTYPE ="prjType";  
	public final static String PRJADDR ="prjAddr";  
	public final static String STARTTIME ="startTime";  
	public final static String ENDTIME ="endTime"; 
	public final static String BIAODUAN ="BiaoDuan";  
	public final static String JIEGOUDES ="JieGouDes";  
	public final static String JIANCENEIRONG ="JianceNeirong";  
	public final static String LATITUDE ="Latitude";  
	public final static String LONGITUDE ="Longitude";  
	public final static String STATUSVAL ="statusVal";  
	public final static String STATUSDES ="StatusDes";  
	public final static String LXR1 ="lxr1";  	//联系人1
	public final static String LXR2 ="lxr2";  //联系人2
	public final static String ADDRCITY="sAddrCity";//市
	public final static String ADDRREGIONS="sAddrRegions";//区县
	public final static String SURLADDRESS="sURLAddress";//最新报表
	
	public String PrjID;	//项目ID
	public String PrjName;	//项目名称
	public String PrjAlais;	//项目别名
	public String PrjType;  //项目类型
	public String StartTime;  //开始时间
	public String EndTime;    //结束时间
	public double Latitude;   //项目概略坐标
	public double Longitude;  //项目概略坐标
	public String StatusVal;  //项目状态值
	public String StatusDes;  //项目状态简述
	public String Description; //项目背景
	public String Bgimg;       //项目背景图片-兼容考虑
	public String renzhengma;
	public String property;		//项目其他属性，要键值查询  
	public String PrjAddr;  //项目地址
	public String Lxr1;
	public String Lxr2;
	public String addrCity;//市
	public String addrRegions;//区县
	public String urlAddress;//最新报表

	
	public String getPrjID() {
	    return this.PrjID;
	}
	public String getLxr1() {
		return Lxr1;
	}
	public void setLxr1(String lxr1) {
		Lxr1 = lxr1;
	}
	public String getLxr2() {
		return Lxr2;
	}
	public void setLxr2(String lxr2) {
		Lxr2 = lxr2;
	}
	public void setPrjID(String PrjID) {
	    this.PrjID=PrjID;
	}
	public String getPrjName() {
	    return this.PrjName;
	}
	public void setPrjName(String PrjName) {
	    this.PrjName=PrjName;
	}
	public String getPrjAlais() {
	    return this.PrjAlais;
	}
	public void setPrjAlais(String PrjAlais) {
	    this.PrjAlais=PrjAlais;
	}
	public String getPrjType() {
	    return this.PrjType;
	}
	public void setPrjType(String PrjType) {
	    this.PrjType=PrjType;
	}
	public String getStartTime() {
	    return this.StartTime;
	}
	public void setStartTime(String StartTime) {
	    this.StartTime=StartTime;
	}
	
	public String getEndTime() {
	    return this.EndTime;
	}
	public void setEndTime(String EndTime) {
	    this.EndTime=EndTime;
	}
	public double getLatitude() {
	    return this.Latitude;
	}
	public void setLatitude(double Latitude) {
	    this.Latitude=Latitude;
	}
	public double getLongitude() {
	    return this.Longitude;
	}
	public void setLongitude(double Longitude) {
	    this.Longitude=Longitude;
	}
	public String getStatusVal() {
	    return this.StatusVal;
	}
	public void setStatusVal(String StatusVal) {
	    this.StatusVal=StatusVal;
	}
	public String getStatusDes() {
	    return this.StatusDes;
	}
	public void setStatusDes(String StatusDes) {
	    this.StatusDes=StatusDes;
	}
	public String getDescription() {
	    return this.Description;
	}
	public void setDescription(String Description) {
	    this.Description=Description;
	}
	public String getPrjAddr() {
	    return this.PrjAddr;
	}
	public void setPrjAddr(String PrjAddr) {
	    this.PrjAddr=PrjAddr;
	}
	public String getBgimg() {
	    return this.Bgimg;
	}
	public void setBgimg(String Bgimg) {
	    this.Bgimg=Bgimg;
	}
	public String getrenzhengma() {
	    return this.PrjName;
	}
	public void setrenzhengma(String renzhengma) {
	    this.renzhengma=renzhengma;
	}
	
	public String getproperty(String itemName) {
		if (this.property == null)
			return itemName;
		String[] aa = this.property.split("\\|"); 
		for (int i = 0 ; i <aa.length ; i++ ) { 
			String [] bb = aa[i].split(":");
			if (bb.length != 2)
				continue;
			if (bb[0].equals(itemName))
				return bb[1];
		}
	    return itemName;
	}
	public String getpropertyVal(){
		  return this.property;
	}
	public void setproperty(String property) {
	    this.property=property;
	}
	public void setpropertyVal(String itemName, String property) {
	    this.property=property;
	}
	public ProjectInfo() {
		super();
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getAddrRegions() {
		return addrRegions;
	}
	public void setAddrRegions(String addrRegions) {
		this.addrRegions = addrRegions;
	}
	public String getUrlAddress() {
		return urlAddress;
	}
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	
	
	
}
