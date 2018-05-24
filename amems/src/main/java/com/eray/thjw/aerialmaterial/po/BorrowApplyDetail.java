package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

/**
 * b_h_01801 借入申请单-航材表
 * @author xu.yong
 *
 */
public class BorrowApplyDetail {
    private String id;

    private String mainid;

    private String bjid;

    private String bjh;

    private BigDecimal sqsl;

    private BigDecimal shsl;

    private BigDecimal sl;

    private String yt;

    private Integer zt;

    private String bz;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private String sn;
    
    
    /** 虚拟字段 start */
    private HCMainData hcMainData;
    
    private String state;//标识是否是其他飞行队库存
    /** 虚拟字段 end */

    
    public String getId() {
        return id;
    }

    public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public BigDecimal getSqsl() {
        if(sqsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(sqsl));
    	}
        return sqsl;
    }

    public void setSqsl(BigDecimal sqsl) {
        this.sqsl = sqsl;
    }

    public BigDecimal getShsl() {
        return shsl;
    }

    public void setShsl(BigDecimal shsl) {
        this.shsl = shsl;
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

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt == null ? null : yt.trim();
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

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}
    
}