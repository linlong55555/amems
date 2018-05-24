package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * @Description b_s2_010 145工包信息
 * @CreateTime 2017-10-23 下午7:24:33
 * @CreateBy 雷伟
 */
public class Workpackage145 extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String xfrdwid;

    private String xfrid;

    private Date xfsj;

    private String gbrid;

    private Date gbrq;

    private String gbyy;

    private String zdbmid;

    private String zdrid;

    private Date zdrq;

    private Integer zt;

    private Integer wgbs;

    private String xmid;

    private String gbbh;

    private String gbmc;

    private String fjjx;

    private String fjzch;

    private String fjxlh;

    private String wxlx;

    private Integer wbbs;

    private String xfdwid;

    private String xfdw;

    private String zxdwid;

    private String zxdw;

    private Date jhKsrq;

    private Date jhJsrq;

    private Date sjKsrq;

    private Date sjJsrq;

    private String sjZxdwid;

    private String sjZxdw;

    private String sjGzz;

    private String sjJcz;

    private String sjZd; 
    
    private String gzyq;
    /** 组织机构*/
    private Department department;
    /** 维护人*/
    private User whr;
    /** 关闭人*/
    private User gbr;
    /** 项目*/
    private Project project;
    
    private String xmbh; //项目编号
    
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

    public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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

    public String getXfrdwid() {
        return xfrdwid;
    }

    public void setXfrdwid(String xfrdwid) {
        this.xfrdwid = xfrdwid == null ? null : xfrdwid.trim();
    }

    public String getXfrid() {
        return xfrid;
    }

    public void setXfrid(String xfrid) {
        this.xfrid = xfrid == null ? null : xfrid.trim();
    }

    public Date getXfsj() {
        return xfsj;
    }

    public void setXfsj(Date xfsj) {
        this.xfsj = xfsj;
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbrq() {
        return gbrq;
    }

    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
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

    public Date getZdrq() {
        return zdrq;
    }

    public void setZdrq(Date zdrq) {
        this.zdrq = zdrq;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getWgbs() {
        return wgbs;
    }

    public void setWgbs(Integer wgbs) {
        this.wgbs = wgbs;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid == null ? null : xmid.trim();
    }

    public String getGbbh() {
        return gbbh;
    }

    public void setGbbh(String gbbh) {
        this.gbbh = gbbh == null ? null : gbbh.trim();
    }

    public String getGbmc() {
        return gbmc;
    }

    public void setGbmc(String gbmc) {
        this.gbmc = gbmc == null ? null : gbmc.trim();
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

    public String getFjxlh() {
        return fjxlh;
    }

    public void setFjxlh(String fjxlh) {
        this.fjxlh = fjxlh == null ? null : fjxlh.trim();
    }

    public String getWxlx() {
        return wxlx;
    }

    public void setWxlx(String wxlx) {
        this.wxlx = wxlx == null ? null : wxlx.trim();
    }

    public Integer getWbbs() {
        return wbbs;
    }

    public void setWbbs(Integer wbbs) {
        this.wbbs = wbbs;
    }

    public String getXfdwid() {
        return xfdwid;
    }

    public void setXfdwid(String xfdwid) {
        this.xfdwid = xfdwid == null ? null : xfdwid.trim();
    }

    public String getXfdw() {
        return xfdw;
    }

    public void setXfdw(String xfdw) {
        this.xfdw = xfdw == null ? null : xfdw.trim();
    }

    public String getZxdwid() {
        return zxdwid;
    }

    public void setZxdwid(String zxdwid) {
        this.zxdwid = zxdwid == null ? null : zxdwid.trim();
    }

    public String getZxdw() {
        return zxdw;
    }

    public void setZxdw(String zxdw) {
        this.zxdw = zxdw == null ? null : zxdw.trim();
    }

    public Date getJhKsrq() {
        return jhKsrq;
    }

    public void setJhKsrq(Date jhKsrq) {
        this.jhKsrq = jhKsrq;
    }

    public Date getJhJsrq() {
        return jhJsrq;
    }

    public void setJhJsrq(Date jhJsrq) {
        this.jhJsrq = jhJsrq;
    }

    public Date getSjKsrq() {
        return sjKsrq;
    }

    public void setSjKsrq(Date sjKsrq) {
        this.sjKsrq = sjKsrq;
    }

    public Date getSjJsrq() {
        return sjJsrq;
    }

    public void setSjJsrq(Date sjJsrq) {
        this.sjJsrq = sjJsrq;
    }

    public String getSjZxdwid() {
        return sjZxdwid;
    }

    public void setSjZxdwid(String sjZxdwid) {
        this.sjZxdwid = sjZxdwid == null ? null : sjZxdwid.trim();
    }

    public String getSjZxdw() {
        return sjZxdw;
    }

    public void setSjZxdw(String sjZxdw) {
        this.sjZxdw = sjZxdw == null ? null : sjZxdw.trim();
    }

    public String getSjGzz() {
        return sjGzz;
    }

    public void setSjGzz(String sjGzz) {
        this.sjGzz = sjGzz == null ? null : sjGzz.trim();
    }

    public String getSjJcz() {
        return sjJcz;
    }

    public void setSjJcz(String sjJcz) {
        this.sjJcz = sjJcz == null ? null : sjJcz.trim();
    }

    public String getSjZd() {
        return sjZd;
    }

    public void setSjZd(String sjZd) {
        this.sjZd = sjZd == null ? null : sjZd.trim();
    }

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public User getGbr() {
		return gbr;
	}

	public void setGbr(User gbr) {
		this.gbr = gbr;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getGzyq() {
		return gzyq;
	}

	public void setGzyq(String gzyq) {
		this.gzyq = gzyq;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	    
}