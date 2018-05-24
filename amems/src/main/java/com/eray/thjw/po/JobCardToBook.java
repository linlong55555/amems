package com.eray.thjw.po;

import java.util.Date;

public class JobCardToBook {
	 private String id;

	    private String mainid;

	    private String xdtzsid;

	    private String whbmid;

	    private String whrid;

	    private Date whsj;

	    private Integer zt;

	    private String dprtcode;

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

	    public String getXdtzsid() {
	        return xdtzsid;
	    }

	    public void setXdtzsid(String xdtzsid) {
	        this.xdtzsid = xdtzsid == null ? null : xdtzsid.trim();
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

	    public Integer getZt() {
	        return zt;
	    }

	    public void setZt(Integer zt) {
	        this.zt = zt;
	    }

	    public String getDprtcode() {
	        return dprtcode;
	    }

	    public void setDprtcode(String dprtcode) {
	        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	    }
}
