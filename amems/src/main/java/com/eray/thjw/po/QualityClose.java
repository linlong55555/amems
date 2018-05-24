package com.eray.thjw.po;

import java.util.Date;
/**
 * @author liub
 * @description 质量关闭B_G_00103
 */
public class QualityClose {
    private String id;

    private String mainid;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String gbyy;

    private Integer zt;
    
    private String whrusername;
    
    private String whrrealname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
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

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

	public String getWhrusername() {
		return whrusername;
	}

	public void setWhrusername(String whrusername) {
		this.whrusername = whrusername;
	}

	public String getWhrrealname() {
		return whrrealname;
	}

	public void setWhrrealname(String whrrealname) {
		this.whrrealname = whrrealname;
	}

}