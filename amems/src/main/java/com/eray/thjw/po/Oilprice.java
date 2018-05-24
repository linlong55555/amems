package com.eray.thjw.po;

import java.util.Date;

public class Oilprice extends BizEntity{
    private String id;

    private String ypgg;

    private Double ypjg;

    private Date ksrq;

    private Date jzrq;

    private Integer zt;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String mc;
    
    private String sz;
    
    private User whr;
    
    private String ypggid;
    
    public String getYpggid() {
		return ypggid;
	}

	public void setYpggid(String ypggid) {
		this.ypggid = ypggid;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getSz() {
		return sz;
	}

	public void setSz(String sz) {
		this.sz = sz;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYpgg() {
        return ypgg;
    }

    public void setYpgg(String ypgg) {
        this.ypgg = ypgg == null ? null : ypgg.trim();
    }

	public Double getYpjg() {
		return ypjg;
	}

	public void setYpjg(Double ypjg) {
		this.ypjg = ypjg;
	}

	public Date getKsrq() {
        return ksrq;
    }

    public void setKsrq(Date ksrq) {
        this.ksrq = ksrq;
    }

    public Date getJzrq() {
        return jzrq;
    }

    public void setJzrq(Date jzrq) {
        this.jzrq = jzrq;
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

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
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