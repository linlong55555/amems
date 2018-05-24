package com.eray.thjw.flightdata.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class MountSpecialCondition extends BizEntity{
    private String id;

    private String mainid;

    private String tsfxpzid;

    private BigDecimal xsz;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

    private Integer tbbs;
    
    private String fxjldid;

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

    public String getTsfxpzid() {
        return tsfxpzid;
    }

    public void setTsfxpzid(String tsfxpzid) {
        this.tsfxpzid = tsfxpzid == null ? null : tsfxpzid.trim();
    }

    public BigDecimal getXsz() {
        return xsz;
    }

    public void setXsz(BigDecimal xsz) {
        this.xsz = xsz;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
    }

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}
}