package com.anchor.model;

import java.util.List;

public class IPIItem {
/*	
	public final static String DIS_X1 ="X向位移1";  
	public final static String DIS_Y1 ="Y向位移1";  
*/
	public final static String DIS_X1 ="X1";  
	public final static String DIS_Y1 ="Y1";  
	
	double  m_disX;
	double  m_disY;
	double  m_depth;

	public double getdisX() {
	    return this.m_disX;
	}
	public void setdisX(double disX) {
	    this.m_disX=disX;
	}
	public double getdisY() {
	    return this.m_disY;
	}
	public void setdisY(double disY) {
	    this.m_disY=disY;
	}
	public double getDepth() {
	    return this.m_depth;
	}
	public void setDepth(double depth) {
	    this.m_depth=depth;
	}
}
