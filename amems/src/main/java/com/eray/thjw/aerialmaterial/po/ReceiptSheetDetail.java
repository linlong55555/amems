package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

/**
 * b_h_02001 收货单明细
 * @author xu.yong
 *
 */
public class ReceiptSheetDetail {
    private String id;

    private String mainid;

    private String bjid;

    private String pch;

    private String sn;

    private String kwid;

    private BigDecimal sl;

    private BigDecimal thsl;

    private String kcllid;

    private Integer zt;

    private String xgdjid;

    private String whrid;

    private Date whsj;
    
    private String yjid;
    
    private Integer isZj;
    
    private HCMainData hcMainData;
    
    private HCMainData yjMainData;
    
    private String ckid;

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

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
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

    public String getKwid() {
        return kwid;
    }

    public void setKwid(String kwid) {
        this.kwid = kwid == null ? null : kwid.trim();
    }

    public BigDecimal getSl() {
    	if(sl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(sl));
    	}
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getThsl() {
    	if(thsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(thsl));
    	}
        return thsl;
    }

    public void setThsl(BigDecimal thsl) {
        this.thsl = thsl;
    }


    public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid;
	}

	public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getXgdjid() {
        return xgdjid;
    }

    public void setXgdjid(String xgdjid) {
        this.xgdjid = xgdjid == null ? null : xgdjid.trim();
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

	public String getYjid() {
		return yjid;
	}

	public void setYjid(String yjid) {
		this.yjid = yjid;
	}

	public Integer getIsZj() {
		return isZj;
	}

	public void setIsZj(Integer isZj) {
		this.isZj = isZj;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public String getCkid() {
		return ckid;
	}

	public void setCkid(String ckid) {
		this.ckid = ckid;
	}

	public HCMainData getYjMainData() {
		return yjMainData;
	}

	public void setYjMainData(HCMainData yjMainData) {
		this.yjMainData = yjMainData;
	}
    
}