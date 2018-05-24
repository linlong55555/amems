package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class SysLog extends BizEntity{
	
    private String id;

    private String dprtcode;

    private String czrid;

    private String czip;

    private String czmc;

    private String czurl;

    private String czsj;

    private Integer rzlx;

    private String ycnr;

    private String username;
    
    private Date casj;

    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public String getCzip() {
        return czip;
    }

    public void setCzip(String czip) {
        this.czip = czip == null ? null : czip.trim();
    }

    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc == null ? null : czmc.trim();
    }

    public String getCzurl() {
        return czurl;
    }

    public void setCzurl(String czurl) {
        this.czurl = czurl == null ? null : czurl.trim();
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj == null ? null : czsj.trim();
    }

    public Integer getRzlx() {
        return rzlx;
    }

    public void setRzlx(Integer rzlx) {
        this.rzlx = rzlx;
    }

    public String getYcnr() {
        return ycnr;
    }

    public void setYcnr(String ycnr) {
        this.ycnr = ycnr == null ? null : ycnr.trim();
    }

    public Date getCasj() {
        return casj;
    }

    public void setCasj(Date casj) {
        this.casj = casj;
    }
}