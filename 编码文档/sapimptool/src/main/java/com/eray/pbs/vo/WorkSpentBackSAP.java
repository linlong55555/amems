package com.eray.pbs.vo;

/**
 * 单张工单回传至SAP
 * 
 * @author ganqing
 * 
 */
public class WorkSpentBackSAP {
	private String wid; // 工单编号
	private String operNum; // 工序编号
	private String reservedField;// 保留字段,用户不清楚该字段的实际内容，保留为空
	private String workCenter; // 工作组
	private String workHour; // 工时
	private String hourUnit;// 工时单位H
	private String bookOnDate;// book Date 26012016 ddMMyyyy
	private String bookOnTime;// book time 0842   HHmm
	private String bookOffDate; // book off Date
	private String bookOffTime; // book off time
	private String employeeid; // 员工编号
	private String activityType; // 对应SAP的Activity Type
	private String lastUnknownWord; //最后一个未知的字段

	public String getReservedField() {
		return reservedField;
	}

	public void setReservedField(String reservedField) {
		this.reservedField = reservedField;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getOperNum() {
		return operNum;
	}

	public void setOperNum(String operNum) {
		this.operNum = operNum;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getWorkHour() {
		return workHour;
	}

	public void setWorkHour(String workHour) {
		this.workHour = workHour;
	}

	public String getHourUnit() {
		return hourUnit;
	}

	public void setHourUnit(String hourUnit) {
		this.hourUnit = hourUnit;
	}

	public String getBookOnDate() {
		return bookOnDate;
	}

	public void setBookOnDate(String bookOnDate) {
		this.bookOnDate = bookOnDate;
	}

	public String getBookOnTime() {
		return bookOnTime;
	}

	public void setBookOnTime(String bookOnTime) {
		this.bookOnTime = bookOnTime;
	}

	public String getBookOffDate() {
		return bookOffDate;
	}

	public void setBookOffDate(String bookOffDate) {
		this.bookOffDate = bookOffDate;
	}

	public String getBookOffTime() {
		return bookOffTime;
	}

	public void setBookOffTime(String bookOffTime) {
		this.bookOffTime = bookOffTime;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getLastUnknownWord() {
		return lastUnknownWord;
	}

	public void setLastUnknownWord(String lastUnknownWord) {
		this.lastUnknownWord = lastUnknownWord;
	}

	@Override
	public String toString() {
		return "WorkSpentBackSAP [wid=" + wid + ", operNum=" + operNum
				+ ", reservedField=" + reservedField + ", workCenter="
				+ workCenter + ", workHour=" + workHour + ", hourUnit="
				+ hourUnit + ", bookOnDate=" + bookOnDate + ", bookOnTime="
				+ bookOnTime + ", bookOffDate=" + bookOffDate
				+ ", bookOffTime=" + bookOffTime + ", employeeid=" + employeeid
				+ ", activityType=" + activityType + ", lastUnknownWord="
				+ lastUnknownWord + "]";
	}
	
	

}
