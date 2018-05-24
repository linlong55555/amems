package com.eray.thjw.po;

import java.util.Date;

public class DeptInfo extends BizEntity{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String deptType;
	
	private String remark;
	
	private String lxr1;
	
	private String lxdh1;
	
	private String fax1;
	
	private String email1;
	
	private String lxr2;
	
	private String lxdh2;
	
	private String fax2;
	
	private String email2;
	
	private String dz;
	
	private Date yxqks;
	
	private Date yxqjs;
	
	private Integer zcfj_max;
	
	private Integer zczh_max;
	
	private String whbmid;
	
	private String whrid;
	
	private Date whsj;
	
	private Integer zt;
	//虚拟字段
	private Integer pxh;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLxr1() {
		return lxr1;
	}

	public void setLxr1(String lxr1) {
		this.lxr1 = lxr1;
	}

	public String getLxdh1() {
		return lxdh1;
	}

	public void setLxdh1(String lxdh1) {
		this.lxdh1 = lxdh1;
	}

	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getLxr2() {
		return lxr2;
	}

	public void setLxr2(String lxr2) {
		this.lxr2 = lxr2;
	}

	public String getLxdh2() {
		return lxdh2;
	}

	public void setLxdh2(String lxdh2) {
		this.lxdh2 = lxdh2;
	}

	public String getFax2() {
		return fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public Date getYxqks() {
		return yxqks;
	}

	public void setYxqks(Date yxqks) {
		this.yxqks = yxqks;
	}

	public Date getYxqjs() {
		return yxqjs;
	}

	public void setYxqjs(Date yxqjs) {
		this.yxqjs = yxqjs;
	}

	public Integer getZcfj_max() {
		return zcfj_max;
	}

	public void setZcfj_max(Integer zcfj_max) {
		this.zcfj_max = zcfj_max;
	}

	public Integer getZczh_max() {
		return zczh_max;
	}

	public void setZczh_max(Integer zczh_max) {
		this.zczh_max = zczh_max;
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

	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}

}
