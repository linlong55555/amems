package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;


@SuppressWarnings("serial")
public class PlaneLoadInfo extends BizEntity{
    
	private String id;
	
	private String mainid;
	
	private Date zxrq;
	
	private String fjzch;
	
	private String zjqdid;
	
	private String bjid;
	
	private String bjmc;
	
	private String sxm;
	
	private BigDecimal zdz;
	
	private String bz;
	
	private Integer zt;
	
	private String zddwid;
	
	private String zdrid;
	
	private Date zdsj;
	
	private User zdr;
	
	private Department zd_department;

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

	public Date getZxrq() {
		return zxrq;
	}

	public void setZxrq(Date zxrq) {
		this.zxrq = zxrq;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getSxm() {
		return sxm;
	}

	public void setSxm(String sxm) {
		this.sxm = sxm;
	}

	public BigDecimal getZdz() {
		return zdz;
	}

	public void setZdz(BigDecimal zdz) {
		this.zdz = zdz;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getZddwid() {
		return zddwid;
	}

	public void setZddwid(String zddwid) {
		this.zddwid = zddwid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public Department getZd_department() {
		return zd_department;
	}

	public void setZd_department(Department zd_department) {
		this.zd_department = zd_department;
	}
	
}