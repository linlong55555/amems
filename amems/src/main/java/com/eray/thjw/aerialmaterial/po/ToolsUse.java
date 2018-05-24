package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * b_w_002 工具/设备使用记录
 * @author xu.yong
 *
 */
@SuppressWarnings("serial")
public class ToolsUse extends BizEntity{
	
    private String id;

    private String jldh;

    private String kcid;

    private String bjid;

    private String bjh;

    private String bjxlh;

    private String xh;

    private String gg;

    private String jyZrrid;

    private String jyZrrmc;

    private Date jySj;

    private BigDecimal jySl;

    private String jyBz;

    private String jcZrrid;

    private Date jcSj;

    private BigDecimal jcSl;

    private String jcBz;

    private String ghZrrid;

    private String ghZrrmc;

    private Date ghSj;

    private BigDecimal ghSl;

    private String ghBz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    //扩展字段
    private User jczrr;
    
    private String zwms;
    private String ywms;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJldh() {
        return jldh;
    }

    public void setJldh(String jldh) {
        this.jldh = jldh == null ? null : jldh.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
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

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getJyZrrid() {
        return jyZrrid;
    }

    public void setJyZrrid(String jyZrrid) {
        this.jyZrrid = jyZrrid == null ? null : jyZrrid.trim();
    }

    public String getJyZrrmc() {
        return jyZrrmc;
    }

    public void setJyZrrmc(String jyZrrmc) {
        this.jyZrrmc = jyZrrmc == null ? null : jyZrrmc.trim();
    }

    public Date getJySj() {
        return jySj;
    }

    public void setJySj(Date jySj) {
        this.jySj = jySj;
    }

    public BigDecimal getJySl() {
        return jySl;
    }

    public void setJySl(BigDecimal jySl) {
        this.jySl = jySl;
    }

    public String getJyBz() {
        return jyBz;
    }

    public void setJyBz(String jyBz) {
        this.jyBz = jyBz == null ? null : jyBz.trim();
    }

    public String getJcZrrid() {
        return jcZrrid;
    }

    public void setJcZrrid(String jcZrrid) {
        this.jcZrrid = jcZrrid == null ? null : jcZrrid.trim();
    }

    public Date getJcSj() {
        return jcSj;
    }

    public void setJcSj(Date jcSj) {
        this.jcSj = jcSj;
    }

    public BigDecimal getJcSl() {
        return jcSl;
    }

    public void setJcSl(BigDecimal jcSl) {
        this.jcSl = jcSl;
    }

    public String getJcBz() {
        return jcBz;
    }

    public void setJcBz(String jcBz) {
        this.jcBz = jcBz == null ? null : jcBz.trim();
    }

    public String getGhZrrid() {
        return ghZrrid;
    }

    public void setGhZrrid(String ghZrrid) {
        this.ghZrrid = ghZrrid == null ? null : ghZrrid.trim();
    }

    public String getGhZrrmc() {
        return ghZrrmc;
    }

    public void setGhZrrmc(String ghZrrmc) {
        this.ghZrrmc = ghZrrmc == null ? null : ghZrrmc.trim();
    }

    public Date getGhSj() {
        return ghSj;
    }

    public void setGhSj(Date ghSj) {
        this.ghSj = ghSj;
    }

    public BigDecimal getGhSl() {
        return ghSl;
    }

    public void setGhSl(BigDecimal ghSl) {
        this.ghSl = ghSl;
    }

    public String getGhBz() {
        return ghBz;
    }

    public void setGhBz(String ghBz) {
        this.ghBz = ghBz == null ? null : ghBz.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	public User getJczrr() {
		return jczrr;
	}

	public void setJczrr(User jczrr) {
		this.jczrr = jczrr;
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
}