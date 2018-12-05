package com.anchor.model;

public class UssResult {

	public String sDatetime;	//时间
	public double distance;	//距离
	public double temperature;	//temperature
	//
	public final static String RES_TIME ="st"; 
	public final static String RES_DIS ="dis";  

	public String getSDatetime() {
	    return this.sDatetime;
	}
	public void setsDatetime(String sDatetime) {
	    this.sDatetime=sDatetime;
	}
	public double getdistance() {
	    return this.distance;
	}
	public void setdistance(double distance) {
	    this.distance=distance;
	}
	public double gettemperaturet() {
	    return this.temperature;
	}
	public void settemperature(double temperature) {
	    this.temperature=temperature;
	}
}
