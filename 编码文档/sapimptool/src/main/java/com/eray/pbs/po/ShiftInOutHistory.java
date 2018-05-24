package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 记录IN&OUT 的原始数据 2016.07.22
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_shift_inout_history")
public class ShiftInOutHistory extends BaseEntity {
	private String batchNumber; // 批次号
	private String empId; // 员工编号
	private String inDate; // 上班打卡时间
	private String outDate; // 下班打卡时间
	private String markDate; // 打卡日期
	private Date sysInDate; // 系统默认的上班打卡时间
	private Date sysOutDate; // 系统默认的下班打卡时间
	private Date insertDate; //该数据入库时间
	
	
	@Column(name = "insertdate_")
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

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

	@Override
	public String toString() {
		return "ShiftInOutHistory [batchNumber=" + batchNumber + ", empId="
				+ empId + ", inDate=" + inDate + ", outDate=" + outDate
				+ ", markDate=" + markDate + ", sysInDate=" + sysInDate
				+ ", sysOutDate=" + sysOutDate + ", insertDate=" + insertDate
				+ "]";
	}
	
	
}
