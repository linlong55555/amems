package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class OverseasListing  extends BizEntity{
	
    private String id;

    private Integer jdlx;

    private String wpdxid;

    private String bjid;

    private String bjh;

    private String pch;

    private String sn;

    private BigDecimal kcsl;

    private BigDecimal ghsl;

    private Date wpsj;

    private Integer zt;

    private String bz;

    private Date sxqx;

    private Date spqx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getJdlx() {
		return jdlx;
	}

	public void setJdlx(Integer jdlx) {
		this.jdlx = jdlx;
	}

	public String getWpdxid() {
        return wpdxid;
    }

    public void setWpdxid(String wpdxid) {
        this.wpdxid = wpdxid == null ? null : wpdxid.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public BigDecimal getKcsl() {
    	if(kcsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(kcsl));
    	}
        return kcsl;
    }

    public void setKcsl(BigDecimal kcsl) {
        this.kcsl = kcsl;
    }

    public BigDecimal getGhsl() {
    	if(ghsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(ghsl));
    	}
        return ghsl;
    }

    public void setGhsl(BigDecimal ghsl) {
        this.ghsl = ghsl;
    }

    public Date getWpsj() {
        return wpsj;
    }

    public void setWpsj(Date wpsj) {
        this.wpsj = wpsj;
    }



    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getSxqx() {
        return sxqx;
    }

    public void setSxqx(Date sxqx) {
        this.sxqx = sxqx;
    }

    public Date getSpqx() {
        return spqx;
    }

    public void setSpqx(Date spqx) {
        this.spqx = spqx;
    }
}