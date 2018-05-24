package com.eray.thjw.material2.po;

import java.math.BigDecimal;

import com.eray.thjw.aerialmaterial.po.Stock;

/**
 * @Description b_h2_0200101 收货单-上架
 * @CreateTime 2018年3月12日 下午2:49:31
 * @CreateBy 韩武
 */
public class OutinReceiptShelves {
    private String id;

    private String mainid;

    private Integer cklb;

    private String ckid;

    private String ckh;

    private String ckmc;

    private String kwid;

    private String kwh;

    private BigDecimal sjsl;

    private String jldw;

    private BigDecimal kccb;

    private String biz;

    private String kcllid;
    
    private BigDecimal jz;
    
    private String jzbz;
    
    private Stock stock;

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

    public Integer getCklb() {
        return cklb;
    }

    public void setCklb(Integer cklb) {
        this.cklb = cklb;
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid == null ? null : ckid.trim();
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

    public String getKwid() {
        return kwid;
    }

    public void setKwid(String kwid) {
        this.kwid = kwid == null ? null : kwid.trim();
    }

    public String getKwh() {
        return kwh;
    }

    public void setKwh(String kwh) {
        this.kwh = kwh == null ? null : kwh.trim();
    }

    public BigDecimal getSjsl() {
        return sjsl;
    }

    public void setSjsl(BigDecimal sjsl) {
        this.sjsl = sjsl;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw == null ? null : jldw.trim();
    }

    public BigDecimal getKccb() {
        return kccb;
    }

    public void setKccb(BigDecimal kccb) {
        this.kccb = kccb;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz == null ? null : biz.trim();
    }

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
}