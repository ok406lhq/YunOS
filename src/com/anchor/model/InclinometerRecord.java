package com.anchor.model;

import java.util.Date;

public class InclinometerRecord {

    private String ID;
    private int nSensorID;
    private double ddisx;
    private double ddisy;
    private double dtemp;
    private Date dtDateTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getnSensorID() {
        return nSensorID;
    }

    public void setnSensorID(int nSensorID) {
        this.nSensorID = nSensorID;
    }

    public double getDdisx() {
        return ddisx;
    }

    public void setDdisx(double ddisx) {
        this.ddisx = ddisx;
    }

    public double getDdisy() {
        return ddisy;
    }

    public void setDdisy(double ddisy) {
        this.ddisy = ddisy;
    }

    public double getDtemp() {
        return dtemp;
    }

    public void setDtemp(double dtemp) {
        this.dtemp = dtemp;
    }

    public Date getDtDateTime() {
        return dtDateTime;
    }

    public void setDtDateTime(Date dtDateTime) {
        this.dtDateTime = dtDateTime;
    }
}
