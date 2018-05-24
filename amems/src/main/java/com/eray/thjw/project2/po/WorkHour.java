package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * 
 * @Description B_G2_993 工种工时
 * @CreateTime 2017-8-14 下午4:02:52
 * @CreateBy 刘兵
 */
public class WorkHour extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Integer xc;

    private String rw;
    
    private String jdid;

    private String jdbm;

    private String gzzid;

    private String gzzbh;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private String ztcsj;

    private String bz;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

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

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw == null ? null : rw.trim();
    }

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public String getGzzbh() {
        return gzzbh;
    }

    public void setGzzbh(String gzzbh) {
        this.gzzbh = gzzbh == null ? null : gzzbh.trim();
    }

    public BigDecimal getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public String getZtcsj() {
        return ztcsj;
    }

    public void setZtcsj(String ztcsj) {
        this.ztcsj = ztcsj == null ? null : ztcsj.trim();
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

	public String getJdid() {
		return jdid;
	}

	public void setJdid(String jdid) {
		this.jdid = jdid;
	}

	public String getJdbm() {
		return jdbm;
	}

	public void setJdbm(String jdbm) {
		this.jdbm = jdbm;
	}

	@Override
	public String toString() {
		return "WorkHour [id=" + id + ", dprtcode=" + dprtcode + ", ywlx="
				+ ywlx + ", ywid=" + ywid + ", xc=" + xc + ", rw=" + rw
				+ ", jdid=" + jdid + ", jdbm=" + jdbm + ", gzzid=" + gzzid
				+ ", gzzbh=" + gzzbh + ", jhgsRs=" + jhgsRs + ", jhgsXss="
				+ jhgsXss + ", ztcsj=" + ztcsj + ", bz=" + bz + ", zt=" + zt
				+ ", whdwid=" + whdwid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getYwlx()="
				+ getYwlx() + ", getYwid()=" + getYwid() + ", getXc()="
				+ getXc() + ", getRw()=" + getRw() + ", getGzzid()="
				+ getGzzid() + ", getGzzbh()=" + getGzzbh() + ", getJhgsRs()="
				+ getJhgsRs() + ", getJhgsXss()=" + getJhgsXss()
				+ ", getZtcsj()=" + getZtcsj() + ", getBz()=" + getBz()
				+ ", getZt()=" + getZt() + ", getWhdwid()=" + getWhdwid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getJdid()=" + getJdid() + ", getJdbm()=" + getJdbm()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}