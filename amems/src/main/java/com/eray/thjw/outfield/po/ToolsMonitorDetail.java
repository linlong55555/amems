package com.eray.thjw.outfield.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * b_w_00101 计量工具监控明细表
 * @author xu.yong
 *
 */
public class ToolsMonitorDetail extends BizEntity{

	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String bjxlh;

    private String bjh;

    private String zwms;

    private String ywms;

    private String gg;

    private String xh;

    private Integer bjbs;

    private String bz;

    private Date jyScrq;

    private Date jyXcrq;

    private Integer jyZq;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User whr;
    
    private ToolsMonitor toolsMonitor;

    public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public ToolsMonitor getToolsMonitor() {
		return toolsMonitor;
	}

	public void setToolsMonitor(ToolsMonitor toolsMonitor) {
		this.toolsMonitor = toolsMonitor;
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

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

    public Integer getBjbs() {
        return bjbs;
    }

    public void setBjbs(Integer bjbs) {
        this.bjbs = bjbs;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getJyScrq() {
        return jyScrq;
    }

    public void setJyScrq(Date jyScrq) {
        this.jyScrq = jyScrq;
    }

    public Date getJyXcrq() {
        return jyXcrq;
    }

    public void setJyXcrq(Date jyXcrq) {
        this.jyXcrq = jyXcrq;
    }

    public Integer getJyZq() {
        return jyZq;
    }

    public void setJyZq(Integer jyZq) {
        this.jyZq = jyZq;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
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
}