package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.Department;

/**
 * b_g2_998分发部门或用户
 * 
 * @author yuebinbin
 *
 */
public class DistributionDepartment {
	private String id;

	private String dprtcode;

	private Integer ywlx;

	private String ywid;

	private Integer lx;

	private String dxid;

	private Integer isJs;

	private Integer zt;

	private String whdwid;

	private String whrid;

	private Date whsj;
	/** 部门 **/
	private Department department;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public Integer getYwlx() {
		return ywlx;
	}

	public void setYwlx(Integer ywlx) {
		this.ywlx = ywlx;
	}

	public String getYwid() {
		return ywid;
	}

	public void setYwid(String ywid) {
		this.ywid = ywid == null ? null : ywid.trim();
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getDxid() {
		return dxid;
	}

	public void setDxid(String dxid) {
		this.dxid = dxid == null ? null : dxid.trim();
	}

	public Integer getIsJs() {
		return isJs;
	}

	public void setIsJs(Integer isJs) {
		this.isJs = isJs;
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
		this.whdwid = whdwid == null ? null : whdwid.trim();
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid == null ? null : whrid.trim();
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}