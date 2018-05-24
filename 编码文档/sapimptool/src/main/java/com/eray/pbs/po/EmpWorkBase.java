package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.Global;
import com.eray.util.jpa.BaseEntity;

/**
 * 人员工单表实体
 */
@Entity
@Table(name = "pbs_empwork")
public class EmpWorkBase extends BaseEntity {
	private String rid; // 工包号
	private String wid; // 工单号
	private String toEid; // 接收人工号
	private Date bookOnDate; // BookOn时间
	private Date bookOffDate; // BookOff时间
	private String workOver; // 任务是否已经结束（指该工单是否已经Complete了） 0：未结束 1：已结束
	private String backsapstatus = Global.NO; // 标识该数据是否已经回传至SAP，no标识为回传，yes标识已经回传
												// 2017.07.31 GQ

	@Column(name = "rid_")
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Column(name = "wid_")
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	@Column(name = "toeid_")
	public String getToEid() {
		return toEid;
	}

	public void setToEid(String toEid) {
		this.toEid = toEid;
	}

	@Column(name = "bookondate_")
	public Date getBookOnDate() {
		return bookOnDate;
	}

	public void setBookOnDate(Date bookOnDate) {
		this.bookOnDate = bookOnDate;
	}

	@Column(name = "bookoffdate_")
	public Date getBookOffDate() {
		return bookOffDate;
	}

	public void setBookOffDate(Date bookOffDate) {
		this.bookOffDate = bookOffDate;
	}

	@Column(name = "workover_")
	public String getWorkOver() {
		return workOver;
	}

	public void setWorkOver(String workOver) {
		this.workOver = workOver;
	}

	@Column(name = "backsapstatus_")
	public String getBacksapstatus() {
		return backsapstatus;
	}

	public void setBacksapstatus(String backsapstatus) {
		this.backsapstatus = backsapstatus;
	}

	@Override
	public String toString() {
		return "EmpWorkBase [rid=" + rid + ", wid=" + wid + ", toEid=" + toEid
				+ ", bookOnDate=" + bookOnDate + ", bookOffDate=" + bookOffDate
				+ ", workOver=" + workOver + ", backsapstatus=" + backsapstatus
				+ "]";
	}
	
	

}
