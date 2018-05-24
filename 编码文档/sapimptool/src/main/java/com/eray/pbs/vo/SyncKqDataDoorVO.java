package com.eray.pbs.vo;

import java.util.Date;

/**
 * 同步IN&OUT VO
 * 
 * @author ganqing
 * 
 */
public class SyncKqDataDoorVO {
	private long recID; // 记录号，自增长的唯一值

	private Integer empID; // 人员内部编号

	private String EmpNo; // 人员工号 No10441

	private Date kqday; // 打卡时间 2016-07-19 08:01:03

	private Integer devID; // 机号，最大不超过255

	private String devDesp; // 设备表述

	private String inout; // 进出标记

	private String isSync; // 是否同步过，每一个新进数据该值为0，可以考虑在同步后将该字段修改为1用来区分是否同步过

	public long getRecID() {
		return recID;
	}

	public void setRecID(long recID) {
		this.recID = recID;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public String getEmpNo() {
		return EmpNo;
	}

	public void setEmpNo(String empNo) {
		EmpNo = empNo;
	}

	public Date getKqday() {
		return kqday;
	}

	public void setKqday(Date kqday) {
		this.kqday = kqday;
	}

	public Integer getDevID() {
		return devID;
	}

	public void setDevID(Integer devID) {
		this.devID = devID;
	}

	public String getDevDesp() {
		return devDesp;
	}

	public void setDevDesp(String devDesp) {
		this.devDesp = devDesp;
	}

	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	@Override
	public String toString() {
		return "SyncKqDataDoorVO [recID=" + recID + ", empID=" + empID
				+ ", EmpNo=" + EmpNo + ", kqday=" + kqday + ", devID=" + devID
				+ ", devDesp=" + devDesp + ", inout=" + inout + ", isSync="
				+ isSync + "]";
	}

}
