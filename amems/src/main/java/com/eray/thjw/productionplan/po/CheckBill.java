package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * b_g_016 定检工单
 * @author zhuchao
 *
 */
public class CheckBill {
    private String id;

    private String gdjcid;

    private String gdbh;

    private String zy;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private String xfgdyy;

    private String bz;

    private String zddwid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;

    private String dprtcode;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;
    
    private String jkbz;
    
    private String djrwdid;
    
    private String djgkid;
    

    public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getDjrwdid() {
		return djrwdid;
	}

	public void setDjrwdid(String djrwdid) {
		this.djrwdid = djrwdid;
	}

	public String getDjgkid() {
		return djgkid;
	}

	public void setDjgkid(String djgkid) {
		this.djgkid = djgkid;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGdjcid() {
        return gdjcid;
    }

    public void setGdjcid(String gdjcid) {
        this.gdjcid = gdjcid == null ? null : gdjcid.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
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

    public String getXfgdyy() {
        return xfgdyy;
    }

    public void setXfgdyy(String xfgdyy) {
        this.xfgdyy = xfgdyy == null ? null : xfgdyy.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
    }

    public Date getZdjsrq() {
        return zdjsrq;
    }

    public void setZdjsrq(Date zdjsrq) {
        this.zdjsrq = zdjsrq;
    }

    public String getZdjsyy() {
        return zdjsyy;
    }

    public void setZdjsyy(String zdjsyy) {
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }
}