package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 从第三方同步数据(最原始数据) 2016.07.21
 * @author ganqing
 *
 */

@Entity
@Table(name = "pbs_shift_inout_base")
public class SyncKqDataDoor extends BaseEntity {

	private long recID; //记录号，自增长的唯一值
	
	private String empID; //人员工号
	
	private long empNo; //人员内部编号
	
	private Date shiftdate;   //打卡时间
	
	private Integer devID; //机号，最大不超过255
	
	private String devDesp; //设备表述
	
	private String type;  //进出标记
	
	private String handleStatus; //标记该数据是否已经处理
	
	private Date handleDate; //处理的日期
	

	@Column(name = "recid_")
	public long getRecID() {
		return recID;
	}

	public void setRecID(long recID) {
		this.recID = recID;
	}

	@Column(name = "empid_")
	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	@Column(name = "empno_")
	public long getEmpNo() {
		return empNo;
	}

	public void setEmpNo(long empNo) {
		this.empNo = empNo;
	}

	@Column(name = "shiftdate_")
	public Date getShiftdate() {
		return shiftdate;
	}

	public void setShiftdate(Date shiftdate) {
		this.shiftdate = shiftdate;
	}

	@Column(name = "devid_")
	public Integer getDevID() {
		return devID;
	}

	public void setDevID(Integer devID) {
		this.devID = devID;
	}

	@Column(name = "devdesp_")
	public String getDevDesp() {
		return devDesp;
	}

	public void setDevDesp(String devDesp) {
		this.devDesp = devDesp;
	}

	@Column(name = "type_")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "handlestatus_")
	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	@Column(name = "handledate_")
	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	@Override
	public String toString() {
		return "SyncKqDataDoor [recID=" + recID + ", empID=" + empID
				+ ", empNo=" + empNo + ", shiftdate=" + shiftdate + ", devID="
				+ devID + ", type=" + type
				+ ", handleStatus=" + handleStatus + ", handleDate="
				+ handleDate + "]";
	}
	
	
}
