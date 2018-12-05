package com.anchor.model;

import java.sql.Date;

/**
 * 项目信息类,
 * @author www.xcanchor.com
 *
 */
public class PointInfo {

	//public final static String BIAODUAN ="标段";  
	//public final static String JIEGOUDES ="结构概述";  
	//public final static String JIANCENEIRONG ="主要监测内容";  
	public String SensorName;	//设备名
	public String StaName;	//测站名
	public String PrjName;  //项目名
	public String GroupName; //组名
	public String SensorType;  //开始时间
	public double Latitude;    //项目概略地址
	public double Longitude;  //项目概略地址
	public String StatusVal;  //项目状态值
	public String CurStatus;  //项目状态简述
	public String voltage; //项目背景
	public String location;  //测点位置
	public String imgURL;	//照片保存路径
	public String imgX;	//图片坐标
	public String imgY;	//图片坐标
	public double ref1;       //参考值1
	public double ref2;       //参考值2
	public double ref3;       //参考值3
	public double alert;       //一级预警
	public double action;       //二级预警
	public double alarm;       //三级预警
	public double north;	//北部
	public double east;	//东部
	public double up;	//上部
	public String sensorID;

	public String notifyinterval;	//通告值
	//
	
	
	
	public String getSensorName() {
	    return this.SensorName;
	}
	public String getSensorID() {
		return sensorID;
	}
	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}
	public void setSensorName(String SensorName) {
	    this.SensorName=SensorName;
	}
	public String getStaName() {
	    return this.StaName;
	}
	public void setStaName(String StaName) {
	    this.StaName=StaName;
	}
	public String getPrjName() {
	    return this.PrjName;
	}
	public void setPrjName(String PrjName) {
	    this.PrjName=PrjName;
	}
	public String getSensorType() {
	    return this.SensorType;
	}
	public void setSensorType(String SensorType) {
	    this.SensorType=SensorType;
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
	public String getCurStatus() {
	    return this.CurStatus;
	}
	public void setCurStatus(String CurStatus) {
	    this.CurStatus=CurStatus;
	}
	public String getvoltage() {
	    return this.voltage;
	}
	public void setvoltage(String voltage) {
	    this.voltage=voltage;
	}
	public double getref1() {
	    return this.ref1;
	}
	public void setref1(double ref1) {
	    this.ref1=ref1;
	}
	public double getref2() {
	    return this.ref2;
	}
	public void setref2(double ref2) {
	    this.ref2=ref2;
	}
	public double getref3() {
	    return this.ref3;
	}
	public void setref3(double ref3) {
	    this.ref3=ref3;
	}
	public String getGroupName() {
	    return this.GroupName;
	}
	public void setGroupName(String groupname) {
	    this.GroupName=groupname;
	}
	public double getalert() {
	    return this.alert;
	}
	public void setalert(double alert) {
	    this.alert=alert;
	}
	public double getaction() {
	    return this.action;
	}
	public void setaction(double action) {
	    this.action=action;
	}
	public double getnorth() {
	    return this.north;
	}
	public void setnorth(double north) {
	    this.north=north;
	}
	public double geteast() {
	    return this.east;
	}
	public void seteast(double east) {
	    this.east=east;
	}
	public double getup() {
	    return this.up;
	}
	public void setup(double up) {
	    this.up=up;
	}
	public double getalarm() {
	    return this.alarm;
	}
	public void setalarm(double alarm) {
	    this.alarm=alarm;
	}
	public String getnotifyinterval() {
	    return this.notifyinterval;
	}
	public void setnotifyinterval(String notifyinterval) {
	    this.notifyinterval=notifyinterval;
	}
	
	public String getlocation() {
	    return this.location;
	}
	public void setlocation(String location) {
	    this.location=location;
	}
	public String getimgURL() {
	    return this.imgURL;
	}
	public void setimgURL(String imgURL) {
	    this.imgURL=imgURL;
	}
	public String getimgX() {
	    return this.imgX;
	}
	public void setimgX(String imgX) {
	    this.imgX=imgX;
	}
	public String getimgY() {
	    return this.imgY;
	}
	public void setimgY(String imgY) {
	    this.imgY=imgY;
	}
	public PointInfo() {
		super();
	}
}
