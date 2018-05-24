package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * @Description b_z_011 审核问题单
 * @CreateTime 2018年1月8日 下午1:45:32
 * @CreateBy 韩武
 */
public class AuditDiscovery extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String shwtdbh;

    private Date sjShrq;

    private Integer shlb;

    private Integer lx;

    private String shdxid;

    private String shdxbh;

    private String shdxmc;

    private String zrrbmid;

    private String zrrid;

    private String zrrbh;

    private String zrrmc;

    private String jcnr;

    private Integer wtlx;

    private Date yqfkrq;

    private String zgjy;
    
    private String gbsm;

    private String gbrbmid;

    private String gbrid;

    private Date gbsj;
    
    /** 发现问题通知单详情 */
    private List<AuditDiscoveryDetail> details;
    /** 维护人*/
    private User whr;
    /** 组织机构*/
    private Department department;
    
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

    public String getShwtdbh() {
        return shwtdbh;
    }

    public void setShwtdbh(String shwtdbh) {
        this.shwtdbh = shwtdbh == null ? null : shwtdbh.trim();
    }

    public Date getSjShrq() {
        return sjShrq;
    }

    public void setSjShrq(Date sjShrq) {
        this.sjShrq = sjShrq;
    }

    public Integer getShlb() {
        return shlb;
    }

    public void setShlb(Integer shlb) {
        this.shlb = shlb;
    }

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public String getShdxid() {
        return shdxid;
    }

    public void setShdxid(String shdxid) {
        this.shdxid = shdxid == null ? null : shdxid.trim();
    }

    public String getShdxbh() {
        return shdxbh;
    }

    public void setShdxbh(String shdxbh) {
        this.shdxbh = shdxbh == null ? null : shdxbh.trim();
    }

    public String getShdxmc() {
        return shdxmc;
    }

    public void setShdxmc(String shdxmc) {
        this.shdxmc = shdxmc == null ? null : shdxmc.trim();
    }

    public String getZrrbmid() {
        return zrrbmid;
    }

    public void setZrrbmid(String zrrbmid) {
        this.zrrbmid = zrrbmid == null ? null : zrrbmid.trim();
    }

    public String getZrrid() {
        return zrrid;
    }

    public void setZrrid(String zrrid) {
        this.zrrid = zrrid == null ? null : zrrid.trim();
    }

    public String getZrrbh() {
        return zrrbh;
    }

    public void setZrrbh(String zrrbh) {
        this.zrrbh = zrrbh == null ? null : zrrbh.trim();
    }

    public String getZrrmc() {
        return zrrmc;
    }

    public void setZrrmc(String zrrmc) {
        this.zrrmc = zrrmc == null ? null : zrrmc.trim();
    }

    public String getJcnr() {
        return jcnr;
    }

    public void setJcnr(String jcnr) {
        this.jcnr = jcnr == null ? null : jcnr.trim();
    }

    public Integer getWtlx() {
        return wtlx;
    }

    public void setWtlx(Integer wtlx) {
        this.wtlx = wtlx;
    }

    public Date getYqfkrq() {
        return yqfkrq;
    }

    public void setYqfkrq(Date yqfkrq) {
        this.yqfkrq = yqfkrq;
    }

    public String getZgjy() {
        return zgjy;
    }

    public void setZgjy(String zgjy) {
        this.zgjy = zgjy == null ? null : zgjy.trim();
    }

	public List<AuditDiscoveryDetail> getDetails() {
		return details;
	}

	public void setDetails(List<AuditDiscoveryDetail> details) {
		this.details = details;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getGbsm() {
		return gbsm;
	}

	public void setGbsm(String gbsm) {
		this.gbsm = gbsm;
	}

	public String getGbrbmid() {
		return gbrbmid;
	}

	public void setGbrbmid(String gbrbmid) {
		this.gbrbmid = gbrbmid;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public Date getGbsj() {
		return gbsj;
	}

	public void setGbsj(Date gbsj) {
		this.gbsj = gbsj;
	}
	
}