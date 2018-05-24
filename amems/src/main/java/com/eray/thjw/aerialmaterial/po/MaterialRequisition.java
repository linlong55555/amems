package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 
 * b_h_007 领用申请单
 * @author xu.yong
 *
 */
public class MaterialRequisition extends BizEntity{
    private String id;


    private String lysqdh;

    private String sqbmid;

    private String sqrid;

    private Date sqrq;

    private String jdmc;

    private String fjzch;

    private String lyyy;

    private String ckid;

    private String ckh;

    private String ckmc;

    private String bz;

    private Integer zt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String spbmid;

    private String sprid;

    private Date spsj;

    private String spyj;
    
    /** 虚拟字段 start */
    private User sqrUser;//申请人用户对象
    
    private User zdrUser;//制单人用户对象
    
    private User zdjsUser;//指定结束用户对象
    
    private String sqrname;//申请人名称
    
    private String fxDateBegin;//开始时间
    
    private String fxDateEnd;//结束时间
    /** 虚拟字段 end  */
    
    private List<MaterialRequisitionDetail> detailList;

    private String zdjsrid;
    
    private Date zdjsrq;
    
    private String zdjsyy;
    
    private User zdjsrUser;//虚拟指定结束人

	public String getSqrname() {
		return sqrname;
	}

	public void setSqrname(String sqrname) {
		this.sqrname = sqrname;
	}

	public String getFxDateBegin() {
		return fxDateBegin;
	}

	public void setFxDateBegin(String fxDateBegin) {
		this.fxDateBegin = fxDateBegin;
	}

	public String getFxDateEnd() {
		return fxDateEnd;
	}

	public void setFxDateEnd(String fxDateEnd) {
		this.fxDateEnd = fxDateEnd;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLysqdh() {
        return lysqdh;
    }

    public void setLysqdh(String lysqdh) {
        this.lysqdh = lysqdh == null ? null : lysqdh.trim();
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

    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc == null ? null : jdmc.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getLyyy() {
        return lyyy;
    }

    public void setLyyy(String lyyy) {
        this.lyyy = lyyy == null ? null : lyyy.trim();
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid == null ? null : ckid.trim();
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh == null ? null : ckh.trim();
    }

    public String getCkmc() {
        return ckmc;
    }

    public void setCkmc(String ckmc) {
        this.ckmc = ckmc == null ? null : ckmc.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public String getSpbmid() {
        return spbmid;
    }

    public void setSpbmid(String spbmid) {
        this.spbmid = spbmid == null ? null : spbmid.trim();
    }

    public String getSprid() {
        return sprid;
    }

    public void setSprid(String sprid) {
        this.sprid = sprid == null ? null : sprid.trim();
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }

	public List<MaterialRequisitionDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<MaterialRequisitionDetail> detailList) {
		this.detailList = detailList;
	}

	public User getSqrUser() {
		return sqrUser;
	}

	public void setSqrUser(User sqrUser) {
		this.sqrUser = sqrUser;
	}

	public User getZdrUser() {
		return zdrUser;
	}

	public void setZdrUser(User zdrUser) {
		this.zdrUser = zdrUser;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public User getZdjsrUser() {
		return zdjsrUser;
	}

	public void setZdjsrUser(User zdjsrUser) {
		this.zdjsrUser = zdjsrUser;
	}

	public User getZdjsUser() {
		return zdjsUser;
	}

	public void setZdjsUser(User zdjsUser) {
		this.zdjsUser = zdjsUser;
	}
	
}