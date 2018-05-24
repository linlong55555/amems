package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * b_h_016 盘点单
 * @author xu.yong
 *
 */
public class TakeStock extends BizEntity{
    private String id;

    private String dprtcode;

    private String pddh;

    private Integer cklb;

    private String ckid;

    private String ckh;

    private String ckmc;

    private String pdbmid;

    private String pdrid;

    private Date ksrq;

    private Date jsrq;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String shbmid;

    private String shrid;

    private Date shsj;

    private Integer zt;

    private String bz;
    
    private User pdr;
    
    private User shr;
    
    private List<TakeStockScope> takeStockScopeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getPddh() {
        return pddh;
    }

    public void setPddh(String pddh) {
        this.pddh = pddh == null ? null : pddh.trim();
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

    public String getPdbmid() {
        return pdbmid;
    }

    public void setPdbmid(String pdbmid) {
        this.pdbmid = pdbmid == null ? null : pdbmid.trim();
    }

    public String getPdrid() {
        return pdrid;
    }

    public void setPdrid(String pdrid) {
        this.pdrid = pdrid == null ? null : pdrid.trim();
    }

    public Date getKsrq() {
        return ksrq;
    }

    public void setKsrq(Date ksrq) {
        this.ksrq = ksrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
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

	public User getPdr() {
		return pdr;
	}

	public void setPdr(User pdr) {
		this.pdr = pdr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public List<TakeStockScope> getTakeStockScopeList() {
		return takeStockScopeList;
	}

	public void setTakeStockScopeList(List<TakeStockScope> takeStockScopeList) {
		this.takeStockScopeList = takeStockScopeList;
	}
    
}