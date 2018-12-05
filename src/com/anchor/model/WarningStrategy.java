/**
 * 
 */
package com.anchor.model;

/** 
 * 项目名称：BDSIMP
 * 类描述：测点预警值
 * 作者：张庭伟     时间：2018年6月26日下午3:46:28
 * 编辑：                时间：
 * 备注：
 * @version
 */
public class WarningStrategy {
	
	private int ID;
  	private int nProjectID;
  	private int nSensorID;
  	private String sSensorType;
  	private String dTopLevelValue;
  	private String dMediumLevelValue;
  	private String dLowLevelValue;
  	private int iWarningInterval;
  	private String dtCreateDT;
    private String dtModifyDT;
    private int nModifyBy;
    
	/**
	 * 
	 */
	public WarningStrategy() {
		super();
	}

	/**
	 * @param iD
	 * @param nProjectID
	 * @param nSensorID
	 * @param sSensorType
	 * @param dTopLevelValue
	 * @param dMediumLevelValue
	 * @param dLowLevelValue
	 * @param iWarningInterval
	 * @param dtCreateDT
	 * @param dtModifyDT
	 * @param nModifyBy
	 */
	public WarningStrategy(int iD, int nProjectID, int nSensorID, String sSensorType, String dTopLevelValue,
			String dMediumLevelValue, String dLowLevelValue, int iWarningInterval, String dtCreateDT, String dtModifyDT,
			int nModifyBy) {
		super();
		ID = iD;
		this.nProjectID = nProjectID;
		this.nSensorID = nSensorID;
		this.sSensorType = sSensorType;
		this.dTopLevelValue = dTopLevelValue;
		this.dMediumLevelValue = dMediumLevelValue;
		this.dLowLevelValue = dLowLevelValue;
		this.iWarningInterval = iWarningInterval;
		this.dtCreateDT = dtCreateDT;
		this.dtModifyDT = dtModifyDT;
		this.nModifyBy = nModifyBy;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getnProjectID() {
		return nProjectID;
	}

	public void setnProjectID(int nProjectID) {
		this.nProjectID = nProjectID;
	}

	public int getnSensorID() {
		return nSensorID;
	}

	public void setnSensorID(int nSensorID) {
		this.nSensorID = nSensorID;
	}

	public String getsSensorType() {
		return sSensorType;
	}

	public void setsSensorType(String sSensorType) {
		this.sSensorType = sSensorType;
	}

	public String getdTopLevelValue() {
		return dTopLevelValue;
	}

	public void setdTopLevelValue(String dTopLevelValue) {
		this.dTopLevelValue = dTopLevelValue;
	}

	public String getdMediumLevelValue() {
		return dMediumLevelValue;
	}

	public void setdMediumLevelValue(String dMediumLevelValue) {
		this.dMediumLevelValue = dMediumLevelValue;
	}

	public String getdLowLevelValue() {
		return dLowLevelValue;
	}

	public void setdLowLevelValue(String dLowLevelValue) {
		this.dLowLevelValue = dLowLevelValue;
	}

	public int getiWarningInterval() {
		return iWarningInterval;
	}

	public void setiWarningInterval(int iWarningInterval) {
		this.iWarningInterval = iWarningInterval;
	}

	public String getDtCreateDT() {
		return dtCreateDT;
	}

	public void setDtCreateDT(String dtCreateDT) {
		this.dtCreateDT = dtCreateDT;
	}

	public String getDtModifyDT() {
		return dtModifyDT;
	}

	public void setDtModifyDT(String dtModifyDT) {
		this.dtModifyDT = dtModifyDT;
	}

	public int getnModifyBy() {
		return nModifyBy;
	}

	public void setnModifyBy(int nModifyBy) {
		this.nModifyBy = nModifyBy;
	}
	  
	  
}
