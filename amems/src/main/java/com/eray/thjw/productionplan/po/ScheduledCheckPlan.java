package com.eray.thjw.productionplan.po;

import java.util.Date;

/**
 * b_s_008 定检件监控计划表
 * @author zhuchao
 *
 */
public class ScheduledCheckPlan {
    private String id;

    private String zjqdid;

    private String djxmid;

    private String djxmbh;

    private String jkflbh;

    private String jklbh;

    private String jkjhz;

    private String jksjz;

    private Short dw;

    private String fxjldh;

    private Date fxjltbsj;

    private String djrwdh;

    private Short isCyjs;

    private String sctjid;

    private String xctjid;

    private String zdrid;

    private Date zdsj;

    private Short zt;

    private String dprtcode;
    
    private String fxjldid;
    
    private Date fxrq;
    
    private Integer csbj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getDjxmid() {
        return djxmid;
    }

    public void setDjxmid(String djxmid) {
        this.djxmid = djxmid == null ? null : djxmid.trim();
    }

    public String getDjxmbh() {
        return djxmbh;
    }

    public void setDjxmbh(String djxmbh) {
        this.djxmbh = djxmbh == null ? null : djxmbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkjhz() {
        return jkjhz;
    }

    public void setJkjhz(String jkjhz) {
        this.jkjhz = jkjhz == null ? null : jkjhz.trim();
    }

    public String getJksjz() {
        return jksjz;
    }

    public void setJksjz(String jksjz) {
        this.jksjz = jksjz == null ? null : jksjz.trim();
    }

    public Short getDw() {
        return dw;
    }

    public void setDw(Short dw) {
        this.dw = dw;
    }

    public String getFxjldh() {
        return fxjldh;
    }

    public void setFxjldh(String fxjldh) {
        this.fxjldh = fxjldh == null ? null : fxjldh.trim();
    }

    public Date getFxjltbsj() {
        return fxjltbsj;
    }

    public void setFxjltbsj(Date fxjltbsj) {
        this.fxjltbsj = fxjltbsj;
    }

    public String getDjrwdh() {
        return djrwdh;
    }

    public void setDjrwdh(String djrwdh) {
        this.djrwdh = djrwdh == null ? null : djrwdh.trim();
    }

    public Short getIsCyjs() {
        return isCyjs;
    }

    public void setIsCyjs(Short isCyjs) {
        this.isCyjs = isCyjs;
    }

    public String getSctjid() {
        return sctjid;
    }

    public void setSctjid(String sctjid) {
        this.sctjid = sctjid == null ? null : sctjid.trim();
    }

    public String getXctjid() {
        return xctjid;
    }

    public void setXctjid(String xctjid) {
        this.xctjid = xctjid == null ? null : xctjid.trim();
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

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public Date getFxrq() {
		return fxrq;
	}

	public void setFxrq(Date fxrq) {
		this.fxrq = fxrq;
	}

	public Integer getCsbj() {
		return csbj;
	}

	public void setCsbj(Integer csbj) {
		this.csbj = csbj;
	}
}