package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * b_g_010 EO工单
 * @author zhuchao
 *
 */
public class EoBill {
    private String id;

    private String gdjcid;

    private String gdbh;

    private String gczlid;

    private String gczlzxdxid;

    private String zy;

    private String zy2;

    private BigDecimal jhGs;

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

    private Date zfrq;

    private String zfyy;

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

    public String getGczlid() {
        return gczlid;
    }

    public void setGczlid(String gczlid) {
        this.gczlid = gczlid == null ? null : gczlid.trim();
    }

    public String getGczlzxdxid() {
        return gczlzxdxid;
    }

    public void setGczlzxdxid(String gczlzxdxid) {
        this.gczlzxdxid = gczlzxdxid == null ? null : gczlzxdxid.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getZy2() {
        return zy2;
    }

    public void setZy2(String zy2) {
        this.zy2 = zy2 == null ? null : zy2.trim();
    }

    public BigDecimal getJhGs() {
        return jhGs;
    }

    public void setJhGs(BigDecimal jhGs) {
        this.jhGs = jhGs;
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

    public Date getZfrq() {
        return zfrq;
    }

    public void setZfrq(Date zfrq) {
        this.zfrq = zfrq;
    }

    public String getZfyy() {
        return zfyy;
    }

    public void setZfyy(String zfyy) {
        this.zfyy = zfyy == null ? null : zfyy.trim();
    }

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}
}