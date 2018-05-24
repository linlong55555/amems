package com.eray.thjw.quality.po;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BizEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelToPost extends BizEntity{
    private String id;

    private String mainid;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String gwzw;

    private String gzfw;

    private String dwbm;

    private Date ksrq;

    private Date jsrq;

    private String bz;
    
    private String rybh;
    
    public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
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

    public String getGwzw() {
        return gwzw;
    }

    public void setGwzw(String gwzw) {
        this.gwzw = gwzw == null ? null : gwzw.trim();
    }

    public String getGzfw() {
        return gzfw;
    }

    public void setGzfw(String gzfw) {
        this.gzfw = gzfw == null ? null : gzfw.trim();
    }

    public String getDwbm() {
        return dwbm;
    }

    public void setDwbm(String dwbm) {
        this.dwbm = dwbm == null ? null : dwbm.trim();
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

	public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}