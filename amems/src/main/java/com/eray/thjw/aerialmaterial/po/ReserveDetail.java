package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * b_h_00201 提订单-航材表
 * @author xu.yong
 *
 */
public class ReserveDetail extends BizEntity{
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

    private Integer xjzt;

    private String xjdh;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private BigDecimal ycgsl;
    
    private HCMainData hcMainData;
    
    private List<String> idList;
    
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

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public BigDecimal getSqsl() {
    	/*if(sqsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(sqsl));
    	}*/
        return sqsl;
    }

    public void setSqsl(BigDecimal sqsl) {
        this.sqsl = sqsl;
    }

    public BigDecimal getShsl() {
    	if(shsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(shsl));
    	}
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

    public Integer getXjzt() {
        return xjzt;
    }

    public void setXjzt(Integer xjzt) {
        this.xjzt = xjzt;
    }

    public String getXjdh() {
        return xjdh;
    }

    public void setXjdh(String xjdh) {
        this.xjdh = xjdh == null ? null : xjdh.trim();
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

	public BigDecimal getYcgsl() {
		return ycgsl;
	}

	public void setYcgsl(BigDecimal ycgsl) {
		this.ycgsl = ycgsl;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

}