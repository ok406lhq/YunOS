package com.anchor.model;

public class CMResult {
/*	public final static String RES_TIME ="时间"; 
	public final static String NORTH_DIS ="北向位移";  
	public final static String EAST_DIS ="东向位移";  
	public final static String VERT_DIS ="垂直向位移";  
	public final static String SIGMA ="Sigma";  
*/
	/*
	public final static String RES_TIME ="sDateTime"; 
	public final static String NORTH_DIS ="dNorth";  
	public final static String EAST_DIS ="dEast";  
	public final static String VERT_DIS ="dUP";  
	public final static String SIGMA ="Sigma";  
	*/
	public final static String RES_TIME ="st"; 
	public final static String OFFSET ="of";  
	public final static String TEMPERATURE ="tm"; 
	
	public String sDatetime;	//时间
	public double offset;	//位移
	public double temperature;	//温度

	public String getSDatetime() {
	    return this.sDatetime;
	}
	public void setsDatetime(String sDatetime) {
	    this.sDatetime=sDatetime;
	}
	public double getdoffset() {
	    return this.offset;
	}
	public void setoffset(double offset) {
	    this.offset=offset;
	}
	public double gettemperature() {
	    return this.temperature;
	}
	public void settemperature(double temperature) {
	    this.temperature=temperature;
	}
}
