package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import com.eray.thjw.po.BaseEntity;

/**
 * b_h_015 移库单
 * @author xu.yong
 *
 */
@SuppressWarnings("serial")
public class Transfer extends BaseEntity {
    private String id;

    private String dprtcode;

    private String ykdh;

    private String kcllidYs;

    private String bjid;

    private String bjh;

    private String pch;

    private String sn;

    private String zwms;

    private String ywms;

    private Integer ysCklb;

    private String ysCkid;

    private String ysCkh;

    private String ysCkmc;

    private String ysKwid;

    private String ysKwh;

    private BigDecimal ysSl;

    private Integer mbCklb;

    private String mbCkid;

    private String mbCkh;

    private String mbCkmc;

    private String mbKwid;

    private String mbKwh;

    private BigDecimal mbSl;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String ykbmid;

    private String ykrid;

    private Date ykrq;

    private String kcllidMb;

    private String ykyy;

    private String zt;
    
    private String username;
    
    private String realname;
    
    private String dprtname;
    
    private String displayname;
    
    

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
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

    public String getYkdh() {
        return ykdh;
    }

    public void setYkdh(String ykdh) {
        this.ykdh = ykdh == null ? null : ykdh.trim();
    }

    public String getKcllidYs() {
        return kcllidYs;
    }

    public void setKcllidYs(String kcllidYs) {
        this.kcllidYs = kcllidYs == null ? null : kcllidYs.trim();
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

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public Integer getYsCklb() {
        return ysCklb;
    }

    public void setYsCklb(Integer ysCklb) {
        this.ysCklb = ysCklb;
    }

    public String getYsCkid() {
        return ysCkid;
    }

    public void setYsCkid(String ysCkid) {
        this.ysCkid = ysCkid == null ? null : ysCkid.trim();
    }

    public String getYsCkh() {
        return ysCkh;
    }

    public void setYsCkh(String ysCkh) {
        this.ysCkh = ysCkh == null ? null : ysCkh.trim();
    }

    public String getYsCkmc() {
        return ysCkmc;
    }

    public void setYsCkmc(String ysCkmc) {
        this.ysCkmc = ysCkmc == null ? null : ysCkmc.trim();
    }

    public String getYsKwid() {
        return ysKwid;
    }

    public void setYsKwid(String ysKwid) {
        this.ysKwid = ysKwid == null ? null : ysKwid.trim();
    }

    public String getYsKwh() {
        return ysKwh;
    }

    public void setYsKwh(String ysKwh) {
        this.ysKwh = ysKwh == null ? null : ysKwh.trim();
    }

    public BigDecimal getYsSl() {
        return ysSl;
    }

    public void setYsSl(BigDecimal ysSl) {
        this.ysSl = ysSl;
    }

    public Integer getMbCklb() {
        return mbCklb;
    }

    public void setMbCklb(Integer mbCklb) {
        this.mbCklb = mbCklb;
    }

    public String getMbCkid() {
        return mbCkid;
    }

    public void setMbCkid(String mbCkid) {
        this.mbCkid = mbCkid == null ? null : mbCkid.trim();
    }

    public String getMbCkh() {
        return mbCkh;
    }

    public void setMbCkh(String mbCkh) {
        this.mbCkh = mbCkh == null ? null : mbCkh.trim();
    }

    public String getMbCkmc() {
        return mbCkmc;
    }

    public void setMbCkmc(String mbCkmc) {
        this.mbCkmc = mbCkmc == null ? null : mbCkmc.trim();
    }

    public String getMbKwid() {
        return mbKwid;
    }

    public void setMbKwid(String mbKwid) {
        this.mbKwid = mbKwid == null ? null : mbKwid.trim();
    }

    public String getMbKwh() {
        return mbKwh;
    }

    public void setMbKwh(String mbKwh) {
        this.mbKwh = mbKwh == null ? null : mbKwh.trim();
    }

    public BigDecimal getMbSl() {
        return mbSl;
    }

    public void setMbSl(BigDecimal mbSl) {
        this.mbSl = mbSl;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
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

    public String getYkbmid() {
        return ykbmid;
    }

    public void setYkbmid(String ykbmid) {
        this.ykbmid = ykbmid == null ? null : ykbmid.trim();
    }

    public String getYkrid() {
        return ykrid;
    }

    public void setYkrid(String ykrid) {
        this.ykrid = ykrid == null ? null : ykrid.trim();
    }

    public Date getYkrq() {
        return ykrq;
    }

    public void setYkrq(Date ykrq) {
        this.ykrq = ykrq;
    }

    public String getKcllidMb() {
        return kcllidMb;
    }

    public void setKcllidMb(String kcllidMb) {
        this.kcllidMb = kcllidMb == null ? null : kcllidMb.trim();
    }

    public String getYkyy() {
        return ykyy;
    }

    public void setYkyy(String ykyy) {
        this.ykyy = ykyy == null ? null : ykyy.trim();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }
}