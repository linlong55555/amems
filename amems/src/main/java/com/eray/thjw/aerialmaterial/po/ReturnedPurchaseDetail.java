package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/*
 * b_h_02201 退货单明细
 * 
 */
public class ReturnedPurchaseDetail extends BizEntity{
    private String id;

    private String mainid;

    private String kcllid;

    private String shdmxid;

    private String rkmxid;

    private String ckdmxid;

    private BigDecimal sl;

    private Integer zt;

    private String whrid;

    private Date whsj;
    
    private MaterialHistory materialHistory;//库存履历表
    
    private HCMainData hcMainData;

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
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

    public String getShdmxid() {
        return shdmxid;
    }

    public void setShdmxid(String shdmxid) {
        this.shdmxid = shdmxid == null ? null : shdmxid.trim();
    }

    public String getRkmxid() {
        return rkmxid;
    }

    public void setRkmxid(String rkmxid) {
        this.rkmxid = rkmxid == null ? null : rkmxid.trim();
    }

    public String getCkdmxid() {
        return ckdmxid;
    }

    public void setCkdmxid(String ckdmxid) {
        this.ckdmxid = ckdmxid == null ? null : ckdmxid.trim();
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}
    
}