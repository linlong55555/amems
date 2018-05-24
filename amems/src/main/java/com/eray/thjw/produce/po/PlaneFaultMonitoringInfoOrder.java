package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description 飞机缺陷涉及工单表飞机缺陷涉及工单表  b_s_0190101
 * @CreateTime 2017年10月17日 下午5:42:24
 * @CreateBy 胡才秋
 */
@SuppressWarnings("serial")
public class PlaneFaultMonitoringInfoOrder extends BizEntity{
  
	private String id;
	
	private String mainid;
	
	private String gdid;
	
	private Integer zt;
	
	private String whdwid;
	
	private String whrid;
	
	private Date whsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

}