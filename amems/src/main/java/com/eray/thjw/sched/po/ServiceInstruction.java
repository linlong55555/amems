package com.eray.thjw.sched.po;

import java.util.Date;

public class ServiceInstruction {
    private String id;

    private String dprtcode;

    private String zllx;

    private String ywdx;

    private Date ywrq;

    private String zxpc;

    private Short zt;

    private String zddwid;

    private String zdrid;

    private Date zdsj;
    
    private String bz;

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

    public String getZllx() {
        return zllx;
    }

    public void setZllx(String zllx) {
        this.zllx = zllx == null ? null : zllx.trim();
    }

    public String getYwdx() {
        return ywdx;
    }

    public void setYwdx(String ywdx) {
        this.ywdx = ywdx == null ? null : ywdx.trim();
    }

    public Date getYwrq() {
        return ywrq;
    }

    public void setYwrq(Date ywrq) {
        this.ywrq = ywrq;
    }

    public String getZxpc() {
        return zxpc;
    }

    public void setZxpc(String zxpc) {
        this.zxpc = zxpc == null ? null : zxpc.trim();
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
}