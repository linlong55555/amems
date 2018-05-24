package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_01601 盘点明细表
 * @author xu.yong
 *
 */
public class TakeStockDetail extends BizEntity {
    private String id;

    private String mainid;

    private String kcllid;

    private BigDecimal kcsl;

    private BigDecimal cksl;

    private String bz;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private Stock stock;
    
    private HCMainData hcMainData;
    
    private MaterialHistory materialHistory;
    
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

    public BigDecimal getCksl() {
    	if(cksl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(cksl));
    	}
        return cksl;
    }

    public void setCksl(BigDecimal cksl) {
        this.cksl = cksl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}
    
}