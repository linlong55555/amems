package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * b_h_008 入库单
 * @author xu.yong
 *
 */
public class Instock extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer rklx;

    private String rkdh;

    private String rkbmid;

    private String rukid;

    private Date rksj;

    private Integer zt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String fjzch;

    private String sqbmid;

    private String sqrid;

    private String jddxid;

    private String jdfzr;

    private String bz;

    private Date sqsj2;

    private String xgdjid;
    
    /** 虚拟字段 start */
    private User sqrUser;//申请人User对象
    
    private User rkrUser;//入库人User对象
    
    private Secondment secondment;//借调对象
    
    private Department department; //组织机构对象
    
    private List<InstockDetail> instockDetailList;//入库航材
    
    private User zdrUser;
    /** 虚拟字段 end */

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

    public Integer getRklx() {
        return rklx;
    }

    public void setRklx(Integer rklx) {
        this.rklx = rklx;
    }

    public String getRkdh() {
        return rkdh;
    }

    public void setRkdh(String rkdh) {
        this.rkdh = rkdh == null ? null : rkdh.trim();
    }

    public String getRkbmid() {
        return rkbmid;
    }

    public void setRkbmid(String rkbmid) {
        this.rkbmid = rkbmid == null ? null : rkbmid.trim();
    }

    public String getRukid() {
        return rukid;
    }

    public void setRukid(String rukid) {
        this.rukid = rukid == null ? null : rukid.trim();
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getSqbmid() {
        return sqbmid;
    }

    public void setSqbmid(String sqbmid) {
        this.sqbmid = sqbmid == null ? null : sqbmid.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public String getJddxid() {
        return jddxid;
    }

    public void setJddxid(String jddxid) {
        this.jddxid = jddxid == null ? null : jddxid.trim();
    }

    public String getJdfzr() {
        return jdfzr;
    }

    public void setJdfzr(String jdfzr) {
        this.jdfzr = jdfzr == null ? null : jdfzr.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getSqsj2() {
        return sqsj2;
    }

    public void setSqsj2(Date sqsj2) {
        this.sqsj2 = sqsj2;
    }

    public String getXgdjid() {
        return xgdjid;
    }

    public void setXgdjid(String xgdjid) {
        this.xgdjid = xgdjid == null ? null : xgdjid.trim();
    }

	public User getSqrUser() {
		return sqrUser;
	}

	public void setSqrUser(User sqrUser) {
		this.sqrUser = sqrUser;
	}

	public User getRkrUser() {
		return rkrUser;
	}

	public void setRkrUser(User rkrUser) {
		this.rkrUser = rkrUser;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Secondment getSecondment() {
		return secondment;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	public List<InstockDetail> getInstockDetailList() {
		return instockDetailList;
	}

	public void setInstockDetailList(List<InstockDetail> instockDetailList) {
		this.instockDetailList = instockDetailList;
	}

	public User getZdrUser() {
		return zdrUser;
	}

	public void setZdrUser(User zdrUser) {
		this.zdrUser = zdrUser;
	}
	
}