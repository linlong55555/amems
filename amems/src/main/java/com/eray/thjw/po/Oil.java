package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

public class Oil extends BaseEntity{
    private String id;

    private String ypgg;

    private BigDecimal ypmd;

    private String ms;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private User whr_user;
    
    private Department bm_dprt;
    
    private Department jg_dprt;
    
    private String keyword;
    
    private String oldypgg;

    public String getOldypgg() {
		return oldypgg;
	}

	public void setOldypgg(String oldypgg) {
		this.oldypgg = oldypgg;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public User getWhr_user() {
		return whr_user;
	}

	public void setWhr_user(User whr_user) {
		this.whr_user = whr_user;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYpgg() {
        return ypgg;
    }

    public void setYpgg(String ypgg) {
        this.ypgg = ypgg == null ? null : ypgg.trim();
    }

    public BigDecimal getYpmd() {
        return ypmd;
    }

    public void setYpmd(BigDecimal ypmd) {
        this.ypmd = ypmd;
    }
    
    public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
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
}