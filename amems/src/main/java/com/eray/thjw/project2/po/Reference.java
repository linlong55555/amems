package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description B_G2_999 相关参考文件
 * @CreateTime 2017-8-14 下午4:04:07
 * @CreateBy 刘兵
 */
public class Reference extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Integer xc;

    private String wjly;

    private String wjlx;

    private String wjbh;

    private String wjbt;

    private Date bfrq;

    private Date sxrq;

    private Date dqrq;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String wjbb;

    private String ym;

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

    public String getWjly() {
        return wjly;
    }

    public void setWjly(String wjly) {
        this.wjly = wjly == null ? null : wjly.trim();
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx == null ? null : wjlx.trim();
    }

    public String getWjbh() {
        return wjbh;
    }

    public void setWjbh(String wjbh) {
        this.wjbh = wjbh == null ? null : wjbh.trim();
    }

    public String getWjbt() {
        return wjbt;
    }

    public void setWjbt(String wjbt) {
        this.wjbt = wjbt == null ? null : wjbt.trim();
    }

    public Date getBfrq() {
        return bfrq;
    }

    public void setBfrq(Date bfrq) {
        this.bfrq = bfrq;
    }

    public Date getSxrq() {
        return sxrq;
    }

    public void setSxrq(Date sxrq) {
        this.sxrq = sxrq;
    }

    public Date getDqrq() {
        return dqrq;
    }

    public void setDqrq(Date dqrq) {
        this.dqrq = dqrq;
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

    public String getWjbb() {
        return wjbb;
    }

    public void setWjbb(String wjbb) {
        this.wjbb = wjbb == null ? null : wjbb.trim();
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym == null ? null : ym.trim();
    }

	@Override
	public String toString() {
		return "Reference [id=" + id + ", dprtcode=" + dprtcode + ", ywlx="
				+ ywlx + ", ywid=" + ywid + ", xc=" + xc + ", wjly=" + wjly
				+ ", wjlx=" + wjlx + ", wjbh=" + wjbh + ", wjbt=" + wjbt
				+ ", bfrq=" + bfrq + ", sxrq=" + sxrq + ", dqrq=" + dqrq
				+ ", zt=" + zt + ", whdwid=" + whdwid + ", whrid=" + whrid
				+ ", whsj=" + whsj + ", wjbb=" + wjbb + ", ym=" + ym
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getYwlx()="
				+ getYwlx() + ", getYwid()=" + getYwid() + ", getXc()="
				+ getXc() + ", getWjly()=" + getWjly() + ", getWjlx()="
				+ getWjlx() + ", getWjbh()=" + getWjbh() + ", getWjbt()="
				+ getWjbt() + ", getBfrq()=" + getBfrq() + ", getSxrq()="
				+ getSxrq() + ", getDqrq()=" + getDqrq() + ", getZt()="
				+ getZt() + ", getWhdwid()=" + getWhdwid() + ", getWhrid()="
				+ getWhrid() + ", getWhsj()=" + getWhsj() + ", getWjbb()="
				+ getWjbb() + ", getYm()=" + getYm() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
}