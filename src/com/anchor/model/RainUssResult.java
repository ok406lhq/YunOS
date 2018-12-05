package com.anchor.model;

public class RainUssResult {
/*	public final static String RES_TIME ="时间"; 
	public final static String NORTH_DIS ="北向位移";  
	public final static String EAST_DIS ="东向位移";  
	public final static String VERT_DIS ="垂直向位移";  
	public final static String SIGMA ="Sigma";  
*/
	public final static String RES_TIME ="sDateTime"; 
	public final static String NORTH_DIS ="dNorth";  
	public final static String EAST_DIS ="dEast";  
	public final static String VERT_DIS ="dUP";  
	public final static String SIGMA ="Sigma";  
	
	public String sDatetime;	//时间
	public double dNorth;	//北向位移
	public double dEast;	//东向位移
	public double dUP;	//垂直向位移
	public double sigma;	//sigma

	public String getSDatetime() {
	    return this.sDatetime;
	}
	public void setsDatetime(String sDatetime) {
	    this.sDatetime=sDatetime;
	}
	public double getdNorth() {
	    return this.dNorth;
	}
	public void setdNorth(double dNorth) {
	    this.dNorth=dNorth;
	}
	public double getdEast() {
	    return this.dEast;
	}
	public void setdEast(double dEast) {
	    this.dEast=dEast;
	}
	public double getdUP() {
	    return this.dUP;
	}
	public void setdUP(double dUP) {
	    this.dUP=dUP;
	}
	public double getsigma() {
	    return this.sigma;
	}
	public void setsigma(double sigma) {
	    this.sigma=sigma;
	}
}
