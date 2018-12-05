package com.anchor.model;

public class TiltmeterResult {
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
	public final static String X_DIS ="x";  
	public final static String Y_DIS ="y";  
	
	public String sDatetime;	//时间
	
	public String st;
	public double x;
	public double y;

	public String getSDatetime() {
	    return this.sDatetime;
	}
	public void setsDatetime(String sDatetime) {
	    this.sDatetime=sDatetime;
	}

	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	
}
