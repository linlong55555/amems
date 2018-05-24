package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;

import enu.aerialmaterial.ScrapStatusEnum;
/*
 * 
 * b_h_00901 已报废部件清单
 */
public class HasScrappedList extends BizEntity{

	private String id;

    private String mainid;

    private String kcllid;
    
    private String ckid;
    
    private String kwid;
    
    private String kcid;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;
    
    private BigDecimal bfsl;
    
    private BigDecimal kcsl;
    
    private BigDecimal kysl;
    
    private ScrapList scrapList;
    
    private MaterialHistory materialHistory;
    
    private HCMainData hCMainData;
    
    //导出虚拟字段
    private Integer hclx;
    
    private String spsjBegin;
    
    private String spsjEnd;
    
    private String bfck;
    
    private String bfkw;
    
    private String ysck;
    
    private String yskw;
    
    private String pch;
    
    private String sn;
    
    private String kccb;
    
    private Department jg_dprt;
    
    private String sjkc;
    
    private String ztText;

	public String getZtText() {
		return ztText;
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public String getSpsjBegin() {
		return spsjBegin;
	}

	public void setSpsjBegin(String spsjBegin) {
		this.spsjBegin = spsjBegin;
	}

	public String getSpsjEnd() {
		return spsjEnd;
	}

	public void setSpsjEnd(String spsjEnd) {
		this.spsjEnd = spsjEnd;
	}

	public ScrapList getScrapList() {
		return scrapList;
	}

	public void setScrapList(ScrapList scrapList) {
		this.scrapList = scrapList;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public HCMainData gethCMainData() {
		return hCMainData;
	}

	public void sethCMainData(HCMainData hCMainData) {
		this.hCMainData = hCMainData;
	}

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

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
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

	public String getCkid() {
		return ckid;
	}

	public void setCkid(String ckid) {
		this.ckid = ckid;
	}

	public String getKwid() {
		return kwid;
	}

	public void setKwid(String kwid) {
		this.kwid = kwid;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public BigDecimal getBfsl() {
		return bfsl;
	}

	public void setBfsl(BigDecimal bfsl) {
		this.bfsl = bfsl;
	}

	public BigDecimal getKcsl() {
		return kcsl;
	}

	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}

	public BigDecimal getKysl() {
		return kysl;
	}

	public void setKysl(BigDecimal kysl) {
		this.kysl = kysl;
	}

	public String getBfck() {
		return bfck;
	}

	public void setBfck(String bfck) {
		this.bfck = bfck;
	}

	public String getBfkw() {
		return bfkw;
	}

	public void setBfkw(String bfkw) {
		this.bfkw = bfkw;
	}

	public String getYsck() {
		return ysck;
	}

	public void setYsck(String ysck) {
		this.ysck = ysck;
	}

	public String getYskw() {
		return yskw;
	}

	public void setYskw(String yskw) {
		this.yskw = yskw;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getKccb() {
		return kccb;
	}

	public void setKccb(String kccb) {
		this.kccb = kccb;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getSjkc() {
		return sjkc;
	}

	public void setSjkc(String sjkc) {
		this.sjkc = sjkc;
	}

	
}