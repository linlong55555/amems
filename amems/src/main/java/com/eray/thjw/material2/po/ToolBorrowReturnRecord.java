package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_h_030 工具借用/归还记录
 * @CreateTime 2018年3月28日 上午11:22:00
 * @CreateBy 韩武
 */
public class ToolBorrowReturnRecord extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String kcid;

    private String bjid;

    private String bjh;

    private String bjxlh;

    private Integer jllx;

    private String jyZrrid;

    private String jyZrrmc;

    private Date jySj;

    private BigDecimal jySl;

    private String jyBz;

    private Integer cqjybs;

    private String dprtcode;

    private String czbmid;

    private String czrid;

    private Date czsj;
    
    private String ywms;
    
    private String zwms;
    
    private String pch;
    
    private String xingh;
    
    private String gg;
    
    private String ckh;
    
    private String ckmc;
    
    private String kwh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public Integer getJllx() {
        return jllx;
    }

    public void setJllx(Integer jllx) {
        this.jllx = jllx;
    }

    public String getJyZrrid() {
        return jyZrrid;
    }

    public void setJyZrrid(String jyZrrid) {
        this.jyZrrid = jyZrrid == null ? null : jyZrrid.trim();
    }

    public String getJyZrrmc() {
        return jyZrrmc;
    }

    public void setJyZrrmc(String jyZrrmc) {
        this.jyZrrmc = jyZrrmc == null ? null : jyZrrmc.trim();
    }

    public Date getJySj() {
        return jySj;
    }

    public void setJySj(Date jySj) {
        this.jySj = jySj;
    }

    public BigDecimal getJySl() {
        return jySl;
    }

    public void setJySl(BigDecimal jySl) {
        this.jySl = jySl;
    }

    public String getJyBz() {
        return jyBz;
    }

    public void setJyBz(String jyBz) {
        this.jyBz = jyBz == null ? null : jyBz.trim();
    }

    public Integer getCqjybs() {
        return cqjybs;
    }

    public void setCqjybs(Integer cqjybs) {
        this.cqjybs = cqjybs;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getCzbmid() {
        return czbmid;
    }

    public void setCzbmid(String czbmid) {
        this.czbmid = czbmid == null ? null : czbmid.trim();
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms;
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getXingh() {
		return xingh;
	}

	public void setXingh(String xingh) {
		this.xingh = xingh;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh;
	}

	public String getCkmc() {
		return ckmc;
	}

	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
	}
}