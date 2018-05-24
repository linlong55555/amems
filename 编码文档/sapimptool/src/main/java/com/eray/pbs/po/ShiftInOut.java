package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 员工打卡记录信息 2016.07.22
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_shift_inout")
public class ShiftInOut extends BaseEntity {

	private String batchNumber;	//批次号
	private String empId;		//员工编号
	private String inDate;		//上班打卡时间（格式：HH:mm）
	private String outDate;		//下班打卡时间（格式：HH:mm）
	private String markDate;	//打卡日期（格式：yyyyMMdd）
	private Date sysInDate;		//系统默认的上班打卡时间
	private Date sysOutDate;	//系统默认的下班打卡时间
	private String modifyStatus;//修改状态（Y：已修改，N：未修改）
	
	@Column(name = "batchnumber_")
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	@Column(name = "empid_")
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "indate_")
	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	@Column(name = "outdate_")
	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	@Column(name = "markdate_")
	public String getMarkDate() {
		return markDate;
	}

	public void setMarkDate(String markDate) {
		this.markDate = markDate;
	}

	@Column(name = "sysindate_")
	public Date getSysInDate() {
		return sysInDate;
	}

	public void setSysInDate(Date sysInDate) {
		this.sysInDate = sysInDate;
	}

	@Column(name = "sysoutdate_")
	public Date getSysOutDate() {
		return sysOutDate;
	}

	public void setSysOutDate(Date sysOutDate) {
		this.sysOutDate = sysOutDate;
	}

	@Column(name = "modifystatus_")
	public String getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}

	@Override
	public String toString() {
		return "ShiftInOut [batchNumber=" + batchNumber + ", empId=" + empId
				+ ", inDate=" + inDate + ", outDate=" + outDate + ", markDate="
				+ markDate + ", sysInDate=" + sysInDate + ", sysOutDate="
				+ sysOutDate + ", modifyStatus=" + modifyStatus + "]";
	}

}
