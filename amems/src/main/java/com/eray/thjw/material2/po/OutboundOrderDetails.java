package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_h2_02301 出库单-明细
 * @CreateTime 2018年3月15日 下午3:09:51
 * @CreateBy 林龙
 */
public class OutboundOrderDetails  extends BizEntity{
    private String id;

    private String mainid;

    private String kcllid;

    private String wckcllid;
    
    //虚拟字段
    private BigDecimal cksl;
    
    private String kcid;
    
    private Integer hclx;
    private Integer gljb;
    private String bjh;
    private BigDecimal djsl;
    private BigDecimal kcsl;
    private String ckh;
    private String kwh;
    private String sn;
    private String pch;
    private String gg;
    private String xingh;
    private BigDecimal kccb;
    private String biz;
    private BigDecimal jz;
    private String jzbz;
    private String 	jldw;

	public Integer getGljb() {
		return gljb;
	}

	public void setGljb(Integer gljb) {
		this.gljb = gljb;
	}

	public BigDecimal getJz() {
		return jz;
	}

	public void setJz(BigDecimal jz) {
		this.jz = jz;
	}

	public String getJzbz() {
		return jzbz;
	}

	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public BigDecimal getDjsl() {
		return djsl;
	}

	public void setDjsl(BigDecimal djsl) {
		this.djsl = djsl;
	}

	public BigDecimal getKcsl() {
		return kcsl;
	}

	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getXingh() {
		return xingh;
	}

	public void setXingh(String xingh) {
		this.xingh = xingh;
	}

	public BigDecimal getKccb() {
		return kccb;
	}

	public void setKccb(BigDecimal kccb) {
		this.kccb = kccb;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}


	public BigDecimal getCksl() {
		return cksl;
	}

	public void setCksl(BigDecimal cksl) {
		this.cksl = cksl;
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

    public String getWckcllid() {
        return wckcllid;
    }

    public void setWckcllid(String wckcllid) {
        this.wckcllid = wckcllid == null ? null : wckcllid.trim();
    }
}