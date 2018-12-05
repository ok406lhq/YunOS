package com.anchor.model;

import java.util.ArrayList;
import java.util.List;

public class IPIResult {
/*	public final static String RES_TIME ="时间"; 
	public final static String DIS_X1 ="X向位移1";  
	public final static String DIS_Y1 ="Y向位移1";  
*/
	public final static String RES_TIME ="st"; 
	public final static String DIS_X1 ="X_";  
	public final static String DIS_Y1 ="Y_";  
	
	public String sDatetime;	//时间
	public String sensorname;
	public double sensorY;
	public double disX;
	public double disY;
	
	public List<IPIItem> IpiItems = new ArrayList<IPIItem>();		//数据
	
	public String getSDatetime() {
	    return this.sDatetime;
	}
	public void setsDatetime(String sDatetime) {
	    this.sDatetime=sDatetime;
	}
	public String getsensorname() {
	    return this.sensorname;
	}
	public void setsensorname(String sensorname) {
	    this.sensorname=sensorname;
	}
	public double getsensorY() {
	    return this.sensorY;
	}
	public void setsensorY(double sensorY) {
	    this.sensorY=sensorY;
	}
	public IPIItem FindItemByDepth(double depth){
		for( int i = 0 ; i < IpiItems.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
			IPIItem ipi = IpiItems.get(i);
			if (ipi.getDepth() == depth)
					return ipi;
		}
		return null;
	}
	public double getdisX() {
		return disX;
	}
	public void setdisX(double disX) {
		this.disX = disX;
	}
	public double getdisY() {
		return disY;
	}
	public void setdisY(double disY) {
		this.disY = disY;
	}
	
}
