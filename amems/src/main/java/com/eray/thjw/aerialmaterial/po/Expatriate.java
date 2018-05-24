package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_012 外派清单
 * 
 * @author xu.yong
 * 
 */
public class Expatriate extends BizEntity {
	private String id;

	private String dprtcode;

	private Short jdlx;

	private String wpdxid;

	private String bjid;

	private String bjh;

	private String pch;

	private String sn;

	private BigDecimal kcsl;// 借用数量

	private BigDecimal ghsl;// 已归还数量

	private Date wpsj;

	private Integer zt;

	private String bz;

	private Date sxqx;

	private Date spqx;

	private String gljb;

	private String hclx;

	private String zwms;

	private String ywms;

	private String cjjh;

	private String jldw;


	/** 虚拟字段 start */

	private BigDecimal dghsl;// 待归还数量 kcsl-ghsl

	private Secondment secondment;// 外派对象

	private HCMainData hcMainData;// 航材主数据
	
	private String jddxms; //借调对象描述。

	private String jddxlx; //借调對象類型
	/** 虚拟字段 end */

	public String getId() {
		return id;
	}

	public String getJddxlx() {
		return jddxlx;
	}

	public void setJddxlx(String jddxlx) {
		this.jddxlx = jddxlx;
	}


	public String getJddxms() {
		return jddxms;
	}

	public void setJddxms(String jddxms) {
		this.jddxms = jddxms;
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getGljb() {
		return gljb;
	}

	public void setGljb(String gljb) {
		this.gljb = gljb;
	}

	public String getHclx() {
		return hclx;
	}

	public void setHclx(String hclx) {
		this.hclx = hclx;
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

	public Short getJdlx() {
		return jdlx;
	}

	public void setJdlx(Short jdlx) {
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

	public BigDecimal getDghsl() {
		if(dghsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(dghsl));
    	}
        return dghsl;
	}

	public void setDghsl(BigDecimal dghsl) {
		this.dghsl = dghsl;
	}

	public Secondment getSecondment() {
		return secondment;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

}