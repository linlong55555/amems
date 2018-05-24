package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.po.BaseEntity;

/**
 * 
 * @Description B_G2_997 器材/工具
 * @CreateTime 2017-8-14 下午4:02:52
 * @CreateBy 刘兵
 */
public class MaterialTool extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Integer xc;

    private Integer bjlx;

    private String bjid;

    private String jh;

    private BigDecimal sl;

    private String hhxx;
    
    private String jhly;
    
    private String bxx;
    
    private String qcdh;

    private String bz;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    /** 部件 */
    private HCMainData hcMainData;

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

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public Integer getBjlx() {
        return bjlx;
    }

    public void setBjlx(Integer bjlx) {
        this.bjlx = bjlx;
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getHhxx() {
        return hhxx;
    }

    public void setHhxx(String hhxx) {
        this.hhxx = hhxx == null ? null : hhxx.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

	public String getJhly() {
		return jhly;
	}

	public void setJhly(String jhly) {
		this.jhly = jhly;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public String getBxx() {
		return bxx;
	}

	public void setBxx(String bxx) {
		this.bxx = bxx;
	}

	public String getQcdh() {
		return qcdh;
	}

	public void setQcdh(String qcdh) {
		this.qcdh = qcdh;
	}

	@Override
	public String toString() {
		return "MaterialTool [id=" + id + ", dprtcode=" + dprtcode + ", ywlx="
				+ ywlx + ", ywid=" + ywid + ", xc=" + xc + ", bjlx=" + bjlx
				+ ", bjid=" + bjid + ", jh=" + jh + ", sl=" + sl + ", hhxx="
				+ hhxx + ", jhly=" + jhly + ", bxx=" + bxx + ", qcdh=" + qcdh
				+ ", bz=" + bz + ", zt=" + zt + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", hcMainData="
				+ hcMainData + ", pagination=" + pagination + ", getId()="
				+ getId() + ", getDprtcode()=" + getDprtcode() + ", getYwlx()="
				+ getYwlx() + ", getYwid()=" + getYwid() + ", getXc()="
				+ getXc() + ", getBjlx()=" + getBjlx() + ", getBjid()="
				+ getBjid() + ", getJh()=" + getJh() + ", getSl()=" + getSl()
				+ ", getHhxx()=" + getHhxx() + ", getBz()=" + getBz()
				+ ", getZt()=" + getZt() + ", getWhdwid()=" + getWhdwid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getJhly()=" + getJhly() + ", getHcMainData()="
				+ getHcMainData() + ", getBxx()=" + getBxx() + ", getQcdh()="
				+ getQcdh() + ", getPagination()=" + getPagination()
				+ ", getParamsMap()=" + getParamsMap() + ", getCzls()="
				+ getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}