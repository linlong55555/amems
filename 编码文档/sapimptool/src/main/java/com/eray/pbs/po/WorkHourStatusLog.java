package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 记录修改工单WorkHourStatus的标识为log（很难定位到标识为出错的地方,故加强log进行标识）
 * @author ganqing
 *
 */
@Entity
@Table(name = "pbs_workhourstatus_log")
public class WorkHourStatusLog extends BaseEntity {
	
	private String wid;  //工单编号
	
	private String rid; //工包编号
	
	private Date addDate = new Date(); //插入时间
	
	private String method; //调用的方法
	
	private String system = "app"; //系统类别
	
	private String workInfo; //完整的工单信息
	
	private String remark; //备注信息
	

	public WorkHourStatusLog() {
	}

	public WorkHourStatusLog(String wid, String rid, String method,String workInfo) {
		this.wid = wid;
		this.rid = rid;
		this.method = method;
		this.workInfo = workInfo;
	}

	@Column(name="wid_")
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	@Column(name="rid_")
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Column(name="adddate_")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	@Column(name="method_")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name="system_")
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Column(name="workinfom_")
	public String getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(String workInfo) {
		this.workInfo = workInfo;
	}

	@Column(name="remark_")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
