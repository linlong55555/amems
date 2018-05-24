package com.eray.thjw.airportensure.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class Fuelup extends BizEntity {
    private String id;

    private String fjjydbh;

    private String fjssdw;

    private String jd;

    private String fjjx;

    private String fjzch;

    private String hbh;

    private String hyddh;

    private Date jyrq;

    private String jycch;

    private String jydjbh;

    private String fyr;

    private String jykssj;

    private String jyjssj;

    private String syr;

    private String ypgg;

    private Double sjmd;

    private Double sjjysl;

    private Integer zt;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String bz;
    
    private String jdms;
    
    private String jyDateBegin; //虚拟字段: 加油开始时间
    
    private String jyDateEnd; //虚拟字段: 加油结束时间
    
    private String jyyf; //虚拟字段: 加油月份
    
    private double ypfy; //虚拟字段: 油品费用
    
    private double ypzl; //虚拟字段: 油品重量
    
    private String selectDate; //虚拟字段: 选择的日期
    
    
    public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getJyyf() {
		return jyyf;
	}

	public void setJyyf(String jyyf) {
		this.jyyf = jyyf;
	}

	public Double getYpfy() {
		return ypfy;
	}

	public void setYpfy(Double ypfy) {
		this.ypfy = ypfy;
	}

	public Double getYpzl() {
		return ypzl;
	}

	public void setYpzl(Double ypzl) {
		this.ypzl = ypzl;
	}

	public String getJyDateBegin() {
		return jyDateBegin;
	}

	public void setJyDateBegin(String jyDateBegin) {
		this.jyDateBegin = jyDateBegin;
	}

	public String getJyDateEnd() {
		return jyDateEnd;
	}

	public void setJyDateEnd(String jyDateEnd) {
		this.jyDateEnd = jyDateEnd;
	}

	public String getJdms() {
		return jdms;
	}

	public void setJdms(String jdms) {
		this.jdms = jdms;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFjjydbh() {
        return fjjydbh;
    }

    public void setFjjydbh(String fjjydbh) {
        this.fjjydbh = fjjydbh == null ? null : fjjydbh.trim();
    }

    public String getFjssdw() {
        return fjssdw;
    }

    public void setFjssdw(String fjssdw) {
        this.fjssdw = fjssdw == null ? null : fjssdw.trim();
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd == null ? null : jd.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getHbh() {
        return hbh;
    }

    public void setHbh(String hbh) {
        this.hbh = hbh == null ? null : hbh.trim();
    }

    public String getHyddh() {
        return hyddh;
    }

    public void setHyddh(String hyddh) {
        this.hyddh = hyddh == null ? null : hyddh.trim();
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
    }

    public String getJycch() {
        return jycch;
    }

    public void setJycch(String jycch) {
        this.jycch = jycch == null ? null : jycch.trim();
    }

    public String getJydjbh() {
        return jydjbh;
    }

    public void setJydjbh(String jydjbh) {
        this.jydjbh = jydjbh == null ? null : jydjbh.trim();
    }

    public String getFyr() {
        return fyr;
    }

    public void setFyr(String fyr) {
        this.fyr = fyr == null ? null : fyr.trim();
    }

    public String getJykssj() {
        return jykssj;
    }

    public void setJykssj(String jykssj) {
        this.jykssj = jykssj == null ? null : jykssj.trim();
    }

    public String getJyjssj() {
        return jyjssj;
    }

    public void setJyjssj(String jyjssj) {
        this.jyjssj = jyjssj == null ? null : jyjssj.trim();
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr == null ? null : syr.trim();
    }

    public String getYpgg() {
        return ypgg;
    }

    public void setYpgg(String ypgg) {
        this.ypgg = ypgg == null ? null : ypgg.trim();
    }

    public Double getSjmd() {
		return sjmd;
	}

	public void setSjmd(Double sjmd) {
		this.sjmd = sjmd;
	}

	public Double getSjjysl() {
		return sjjysl;
	}

	public void setSjjysl(Double sjjysl) {
		this.sjjysl = sjjysl;
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

	@Override
	public String toString() {
		return "Fuelup [id=" + id + ", fjjydbh=" + fjjydbh + ", fjssdw="
				+ fjssdw + ", jd=" + jd + ", fjjx=" + fjjx + ", fjzch=" + fjzch
				+ ", hbh=" + hbh + ", hyddh=" + hyddh + ", jyrq=" + jyrq
				+ ", jycch=" + jycch + ", jydjbh=" + jydjbh + ", fyr=" + fyr
				+ ", jykssj=" + jykssj + ", jyjssj=" + jyjssj + ", syr=" + syr
				+ ", ypgg=" + ypgg + ", sjmd=" + sjmd + ", sjjysl=" + sjjysl
				+ ", zt=" + zt + ", dprtcode=" + dprtcode + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj + ", bz=" + bz
				+ ", jdms=" + jdms + ", jyDateBegin=" + jyDateBegin
				+ ", jyDateEnd=" + jyDateEnd + ", jyyf=" + jyyf + ", ypfy="
				+ ypfy + ", ypzl=" + ypzl + "]";
	}
    
    
}