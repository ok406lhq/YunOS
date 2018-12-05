package com.anchor.model;

import java.util.Date;

public class DismeterRecord {

    private int ID;
    private int nSensorID;
    private double dRealLen;
    private double dIniLen;
    private Date dtCreateDT;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getnSensorID() {
        return nSensorID;
    }

    public void setnSensorID(int nSensorID) {
        this.nSensorID = nSensorID;
    }

    public double getdRealLen() {
        return dRealLen;
    }

    public void setdRealLen(double dRealLen) {
        this.dRealLen = dRealLen;
    }

    public double getdIniLen() {
        return dIniLen;
    }

    public void setdIniLen(double dIniLen) {
        this.dIniLen = dIniLen;
    }

    public Date getDtCreateDT() {
        return dtCreateDT;
    }

    public void setDtCreateDT(Date dtCreateDT) {
        this.dtCreateDT = dtCreateDT;
    }
}
