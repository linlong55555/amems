package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

/**
 * b_h_01602 盘点范围表
 * @author xu.yong
 *
 */
public class TakeStockScope {
    private String id;

    private String mainid;

    private Integer pdlx;

    private String dxid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private HCMainData hcMainData;
    
    private Storage storage;

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

    public Integer getPdlx() {
        return pdlx;
    }

    public void setPdlx(Integer pdlx) {
        this.pdlx = pdlx;
    }

    public String getDxid() {
        return dxid;
    }

    public void setDxid(String dxid) {
        this.dxid = dxid == null ? null : dxid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
    
}