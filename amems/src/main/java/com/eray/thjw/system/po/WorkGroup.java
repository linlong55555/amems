package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

public class WorkGroup extends BizEntity{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String gzzdm;
	
	private String gzzmc;
	
//	private String dprtCode;
	
	private String remark;
	
	private String whbmid;
	
	private String whrid;
	
	private Date whsj;
	
	private Integer zt;
	
	
	private Integer mrbs;
	
	
	public Integer getMrbs() {
		return mrbs;
	}

	public void setMrbs(Integer mrbs) {
		this.mrbs = mrbs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private User whr;
	
	private Department wh_department;
	
	private String keyword;
	
	private String name;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGzzdm() {
		return gzzdm;
	}

	public void setGzzdm(String gzzdm) {
		this.gzzdm = gzzdm;
	}

	public String getGzzmc() {
		return gzzmc;
	}

	public void setGzzmc(String gzzmc) {
		this.gzzmc = gzzmc;
	}

//	public String getDprtCode() {
//		return dprtCode;
//	}
//
//	public void setDprtCode(String dprtCode) {
//		this.dprtCode = dprtCode;
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
