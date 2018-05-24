package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_z_00601 年度审核计划
 * @CreateTime 2018年1月4日 上午10:57:38
 * @CreateBy 林龙
 */
public class AnnualAuditPlan extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String ndjhid;

    private String dprtcode;

    private Integer nf;

    private Integer bbh;

    private Integer yf;

    private Integer zt;

    private Integer lx;

    private String shdxid;

    private String shdxbh;

    private String shdxmc;

    private String bz;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private List<AuditMembers> auditMembersList; //审核成员list
    
    public List<AuditMembers> getAuditMembersList() {
		return auditMembersList;
	}

	public void setAuditMembersList(List<AuditMembers> auditMembersList) {
		this.auditMembersList = auditMembersList;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNdjhid() {
        return ndjhid;
    }

    public void setNdjhid(String ndjhid) {
        this.ndjhid = ndjhid == null ? null : ndjhid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getNf() {
		return nf;
	}

	public void setNf(Integer nf) {
		this.nf = nf;
	}

	public Integer getBbh() {
		return bbh;
	}

	public void setBbh(Integer bbh) {
		this.bbh = bbh;
	}

	public Integer getYf() {
		return yf;
	}

	public void setYf(Integer yf) {
		this.yf = yf;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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