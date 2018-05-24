package com.eray.thjw.flightdata.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class MountSubcomponent extends BizEntity{
    private String id;

    private String mainid;

    private String zbjZjqdid;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String dprtcode;

    private Integer tbbs;
    
    private String fxjldid;
    
    private String jh;
    
    private String xlh;
    
    private String zwmc;
    
    private String ywmc;
    
    private String zjh_show;

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

    public String getZbjZjqdid() {
        return zbjZjqdid;
    }

    public void setZbjZjqdid(String zbjZjqdid) {
        this.zbjZjqdid = zbjZjqdid == null ? null : zbjZjqdid.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getZjh_show() {
		return zjh_show;
	}

	public void setZjh_show(String zjh_show) {
		this.zjh_show = zjh_show;
	}
	
}