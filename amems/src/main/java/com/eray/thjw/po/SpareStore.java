package com.eray.thjw.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

public class SpareStore  extends BizEntity {
   
	private String id;

    private String dprtcode;

    private Short cklb;

    private String ckh;

    private String ckmc;

    private String kwh;

    private String bjh;

    private String pch;

    private String sn;

    private String zwms;

    private String ywms;

    private String jldw;

    private BigDecimal kcsl;

    private String htbhCg;

    private String htbhSx;

    private String tddh;

    private Date rksj;

    private String rkbmid;

    private String rkrid;

    private String shzh;

    private String shzwz;

    private Short zt;

    private String bz;

    private String yhbmid;

    private String yhrid;

    private Date yhsj;

    private BigDecimal htjg;

    private BigDecimal juescb;

    private BigDecimal jiescb;

    private BigDecimal gscb;
    
    private String hcsId;
    
	private List<String>   hcids;
	
	private String zwmc;
	
	private String ywmc;
	
	private String jh;
	
	private String xlh;
	
	private String wz;
	
	private String zjhms;					//章节号描述
	
	private String cjjh;
	
	
	
	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getZjhms() {
		return zjhms;
	}

	public void setZjhms(String zjhms) {
		this.zjhms = zjhms;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
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

	public String getHcsId() {
		return hcsId;
	}

	public void setHcsId(String hcsId) {
		this.hcsId = hcsId;
	}

	public List<String> getHcids() {
		return hcids;
	}

	public void setHcids(List<String> hcids) {
		this.hcids = hcids;
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

    public Short getCklb() {
        return cklb;
    }

    public void setCklb(Short cklb) {
        this.cklb = cklb;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh == null ? null : ckh.trim();
    }

    public String getCkmc() {
        return ckmc;
    }

    public void setCkmc(String ckmc) {
        this.ckmc = ckmc == null ? null : ckmc.trim();
    }

    public String getKwh() {
        return kwh;
    }

    public void setKwh(String kwh) {
        this.kwh = kwh == null ? null : kwh.trim();
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

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw == null ? null : jldw.trim();
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

    public String getHtbhCg() {
        return htbhCg;
    }

    public void setHtbhCg(String htbhCg) {
        this.htbhCg = htbhCg == null ? null : htbhCg.trim();
    }

    public String getHtbhSx() {
        return htbhSx;
    }

    public void setHtbhSx(String htbhSx) {
        this.htbhSx = htbhSx == null ? null : htbhSx.trim();
    }

    public String getTddh() {
        return tddh;
    }

    public void setTddh(String tddh) {
        this.tddh = tddh == null ? null : tddh.trim();
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public String getRkbmid() {
        return rkbmid;
    }

    public void setRkbmid(String rkbmid) {
        this.rkbmid = rkbmid == null ? null : rkbmid.trim();
    }

    public String getRkrid() {
        return rkrid;
    }

    public void setRkrid(String rkrid) {
        this.rkrid = rkrid == null ? null : rkrid.trim();
    }

    public String getShzh() {
        return shzh;
    }

    public void setShzh(String shzh) {
        this.shzh = shzh == null ? null : shzh.trim();
    }

    public String getShzwz() {
        return shzwz;
    }

    public void setShzwz(String shzwz) {
        this.shzwz = shzwz == null ? null : shzwz.trim();
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getYhbmid() {
        return yhbmid;
    }

    public void setYhbmid(String yhbmid) {
        this.yhbmid = yhbmid == null ? null : yhbmid.trim();
    }

    public String getYhrid() {
        return yhrid;
    }

    public void setYhrid(String yhrid) {
        this.yhrid = yhrid == null ? null : yhrid.trim();
    }

    public Date getYhsj() {
        return yhsj;
    }

    public void setYhsj(Date yhsj) {
        this.yhsj = yhsj;
    }

    public BigDecimal getHtjg() {
        return htjg;
    }

    public void setHtjg(BigDecimal htjg) {
        this.htjg = htjg;
    }

    public BigDecimal getJuescb() {
        return juescb;
    }

    public void setJuescb(BigDecimal juescb) {
        this.juescb = juescb;
    }

    public BigDecimal getJiescb() {
        return jiescb;
    }

    public void setJiescb(BigDecimal jiescb) {
        this.jiescb = jiescb;
    }

    public BigDecimal getGscb() {
        return gscb;
    }

    public void setGscb(BigDecimal gscb) {
        this.gscb = gscb;
    }

	@Override
	public String toString() {
		return "SpareStore [id=" + id + ", dprtcode=" + dprtcode + ", cklb="
				+ cklb + ", ckh=" + ckh + ", ckmc=" + ckmc + ", kwh=" + kwh
				+ ", bjh=" + bjh + ", pch=" + pch + ", sn=" + sn + ", zwms="
				+ zwms + ", ywms=" + ywms + ", jldw=" + jldw + ", kcsl=" + kcsl
				+ ", htbhCg=" + htbhCg + ", htbhSx=" + htbhSx + ", tddh="
				+ tddh + ", rksj=" + rksj + ", rkbmid=" + rkbmid + ", rkrid="
				+ rkrid + ", shzh=" + shzh + ", shzwz=" + shzwz + ", zt=" + zt
				+ ", bz=" + bz + ", yhbmid=" + yhbmid + ", yhrid=" + yhrid
				+ ", yhsj=" + yhsj + ", htjg=" + htjg + ", juescb=" + juescb
				+ ", jiescb=" + jiescb + ", gscb=" + gscb + ", hcsId=" + hcsId
				+ "]";
	}
    

}