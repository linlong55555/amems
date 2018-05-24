package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

public class Substitution extends BizEntity{
    private String id;

    private String dprtcode;

    private String bjh;

    private String ms;

    private String tdjh;

    private String tdjms;

    private String jxbs;

    private String gkbs;

    private Integer knxbs;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User whr_user;
    
    private Department jg_dprt;
    
    private Department bm_dprt;
    
    private List<String> gkbsList;
    
    private List<String> jxbsList;
    
    private List<SubstitutionApplicability> applicabilityList;
    
    public List<SubstitutionApplicability> getApplicabilityList() {
		return applicabilityList;
	}

	public void setApplicabilityList(
			List<SubstitutionApplicability> applicabilityList) {
		this.applicabilityList = applicabilityList;
	}

	public List<String> getGkbsList() {
		return gkbsList;
	}

	public void setGkbsList(List<String> gkbsList) {
		this.gkbsList = gkbsList;
	}

	public List<String> getJxbsList() {
		return jxbsList;
	}

	public void setJxbsList(List<String> jxbsList) {
		this.jxbsList = jxbsList;
	}

	public User getWhr_user() {
		return whr_user;
	}

	public void setWhr_user(User whr_user) {
		this.whr_user = whr_user;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

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

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms == null ? null : ms.trim();
    }

    public String getTdjh() {
        return tdjh;
    }

    public void setTdjh(String tdjh) {
        this.tdjh = tdjh == null ? null : tdjh.trim();
    }

    public String getTdjms() {
        return tdjms;
    }

    public void setTdjms(String tdjms) {
        this.tdjms = tdjms == null ? null : tdjms.trim();
    }

    public String getJxbs() {
        return jxbs;
    }

    public void setJxbs(String jxbs) {
        this.jxbs = jxbs == null ? null : jxbs.trim();
    }

    public String getGkbs() {
        return gkbs;
    }

    public void setGkbs(String gkbs) {
        this.gkbs = gkbs == null ? null : gkbs.trim();
    }

    public Integer getKnxbs() {
        return knxbs;
    }

    public void setKnxbs(Integer knxbs) {
        this.knxbs = knxbs;
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