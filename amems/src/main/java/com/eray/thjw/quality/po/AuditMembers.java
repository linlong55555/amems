package com.eray.thjw.quality.po;

import java.util.Date;
/**
 * 
 * @Description b_z_901 审核成员
 * @CreateTime 2018年1月4日 上午10:59:04
 * @CreateBy 林龙
 */
public class AuditMembers {
	
    private String id;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String ywid;

    private Integer ywlx;

    private Integer js;

    private String cyid;

    private String cybh;

    private String cymc;

    private String bmid;

    private String bmbh;

    private String bmmc;

    private String zw;
    
    private String shcy;

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

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getYwlx() {
		return ywlx;
	}

	public void setYwlx(Integer ywlx) {
		this.ywlx = ywlx;
	}

	public Integer getJs() {
		return js;
	}

	public void setJs(Integer js) {
		this.js = js;
	}

	public String getCyid() {
        return cyid;
    }

    public void setCyid(String cyid) {
        this.cyid = cyid == null ? null : cyid.trim();
    }

    public String getCybh() {
        return cybh;
    }

    public void setCybh(String cybh) {
        this.cybh = cybh == null ? null : cybh.trim();
    }

    public String getCymc() {
        return cymc;
    }

    public void setCymc(String cymc) {
        this.cymc = cymc == null ? null : cymc.trim();
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh == null ? null : bmbh.trim();
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc == null ? null : bmmc.trim();
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw == null ? null : zw.trim();
    }

	public String getShcy() {
		return shcy;
	}

	public void setShcy(String shcy) {
		this.shcy=shcy==null?null:shcy.trim();
	}
    
    
}