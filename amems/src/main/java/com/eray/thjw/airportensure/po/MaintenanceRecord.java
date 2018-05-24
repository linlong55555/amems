package com.eray.thjw.airportensure.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

public class MaintenanceRecord extends BizEntity {
	private static final long serialVersionUID = 1L;

	private String id; // id

	private String wxdbh; // 维修单编号

	private Date wxsj; // 维修时间

	private String wxryid; // 维修人员id

	private String wxrymc; // 维修人员名称

	private String wxdx; // 维修对象

	private Integer wxfs; // 维修方式

	private String wxnr; // 维修内容

	private Integer zt; // 状态

	private String dprtcode; // 机构代码

	private String whbmid; // 维护部门id

	private Department wh_department;// 维护部门

	private String whrid; // 维护人id

	private Date whsj; // 维护时间

	private Date searchBeginDate; // 查询开始时间

	private Date searchEndDate; // 查询结束时间

	private User whr;// 维护人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWxdbh() {
		return wxdbh;
	}

	public void setWxdbh(String wxdbh) {
		this.wxdbh = wxdbh;
	}

	public Date getWxsj() {
		return wxsj;
	}

	public void setWxsj(Date wxsj) {
		this.wxsj = wxsj;
	}

	public String getWxryid() {
		return wxryid;
	}

	public void setWxryid(String wxryid) {
		this.wxryid = wxryid;
	}

	public String getWxrymc() {
		return wxrymc;
	}

	public void setWxrymc(String wxrymc) {
		this.wxrymc = wxrymc;
	}

	public String getWxdx() {
		return wxdx;
	}

	public void setWxdx(String wxdx) {
		this.wxdx = wxdx;
	}

	public Integer getWxfs() {
		return wxfs;
	}

	public void setWxfs(Integer wxfs) {
		this.wxfs = wxfs;
	}

	public String getWxnr() {
		return wxnr;
	}

	public void setWxnr(String wxnr) {
		this.wxnr = wxnr;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getSearchBeginDate() {
		return searchBeginDate;
	}

	public void setSearchBeginDate(Date searchBeginDate) {
		this.searchBeginDate = searchBeginDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getWh_department() {
		return wh_department;
	}

	public void setWh_department(Department wh_department) {
		this.wh_department = wh_department;
	}

}
