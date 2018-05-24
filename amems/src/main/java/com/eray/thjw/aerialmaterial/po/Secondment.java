package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_014 借调对象表
 * @author xu.yong
 *
 */
@SuppressWarnings("serial")
public class Secondment extends BizEntity{
    private String id;

    private String dprtcode;

    private String jddxbh;

    private Integer jddxlx;

    private String jddxms;

    private String bz;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private String username;
    
    private String realname;
    
    private String dprtname;
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

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

    public String getJddxbh() {
        return jddxbh;
    }

    public void setJddxbh(String jddxbh) {
        this.jddxbh = jddxbh == null ? null : jddxbh.trim();
    }

    public Integer getJddxlx() {
        return jddxlx;
    }

    public void setJddxlx(Integer jddxlx) {
        this.jddxlx = jddxlx;
    }

    public String getJddxms() {
        return jddxms;
    }

    public void setJddxms(String jddxms) {
        this.jddxms = jddxms == null ? null : jddxms.trim();
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
}