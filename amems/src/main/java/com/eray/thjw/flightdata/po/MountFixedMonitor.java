package com.eray.thjw.flightdata.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

public class MountFixedMonitor extends BizEntity{
    private String id;

    private String mainid;

    private String fjzch;

    private Integer bjlx;

    private String jh;

    private String xlh;

    private String djbh;

    private String zwms;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

    private Integer tbbs;
    
    private String nbsbm;
    
    private String bb;
    
    private String fxjldid;
    
    private List<MountMonitorItem> monitorItemList;	// 装上件-监控项信息
    
    private String wxfabh;	//维修方案编号

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

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public Integer getBjlx() {
        return bjlx;
    }

    public void setBjlx(Integer bjlx) {
        this.bjlx = bjlx;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh == null ? null : djbh.trim();
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
    }

	public List<MountMonitorItem> getMonitorItemList() {
		return monitorItemList;
	}

	public void setMonitorItemList(List<MountMonitorItem> monitorItemList) {
		this.monitorItemList = monitorItemList;
	}

	public String getNbsbm() {
		return nbsbm;
	}

	public void setNbsbm(String nbsbm) {
		this.nbsbm = nbsbm;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public String getWxfabh() {
		return wxfabh;
	}

	public void setWxfabh(String wxfabh) {
		this.wxfabh = wxfabh;
	}
    
}