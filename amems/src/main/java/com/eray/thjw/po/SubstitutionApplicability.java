package com.eray.thjw.po;

import java.util.Date;

public class SubstitutionApplicability {
    private String id;

    private String mainid;

    private Integer syxlx;

    private String sydx;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

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

    public Integer getSyxlx() {
		return syxlx;
	}

	public void setSyxlx(Integer syxlx) {
		this.syxlx = syxlx;
	}

	public String getSydx() {
        return sydx;
    }

    public void setSydx(String sydx) {
        this.sydx = sydx == null ? null : sydx.trim();
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