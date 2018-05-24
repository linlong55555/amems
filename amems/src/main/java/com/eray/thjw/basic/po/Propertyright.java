package com.eray.thjw.basic.po;

import java.util.Date;

import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

public class Propertyright extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String cqbh;

    private String fjzch;

    private String gsid;

    private String gsmc;

    private String bz;
    
    private Customer customer;
    
    private User whr;
    
    private Department jg_dprt;

    private Department bm_dprt;
    
    public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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


    public String getCqbh() {
        return cqbh;
    }

    public void setCqbh(String cqbh) {
        this.cqbh = cqbh == null ? null : cqbh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getGsid() {
        return gsid;
    }

    public void setGsid(String gsid) {
        this.gsid = gsid == null ? null : gsid.trim();
    }

    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc == null ? null : gsmc.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}