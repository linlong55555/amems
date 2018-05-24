package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

public class Routinginspectionrecord extends BizEntity{
    private String id;

    private String xjbh;

    private String xsrid;

    private String xsrmc;

    private Date xsrq;

    private String kssj;

    private String jssj;

    private Integer isYhxs;

    private Integer zt;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User whr_user;
    
    private Department whbm_dprt;
    
    private Department jg_dprt;
    
    //明细list
    private List<RoutinginspectionrecordDetail> detailList;
    
    //附件删除id
    private List<String> attachmentDeleteIds;
    
    
    

	public List<String> getAttachmentDeleteIds() {
		return attachmentDeleteIds;
	}

	public void setAttachmentDeleteIds(List<String> attachmentDeleteIds) {
		this.attachmentDeleteIds = attachmentDeleteIds;
	}

	public List<RoutinginspectionrecordDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<RoutinginspectionrecordDetail> detailList) {
		this.detailList = detailList;
	}

	public User getWhr_user() {
		return whr_user;
	}

	public void setWhr_user(User whr_user) {
		this.whr_user = whr_user;
	}

	public Department getWhbm_dprt() {
		return whbm_dprt;
	}

	public void setWhbm_dprt(Department whbm_dprt) {
		this.whbm_dprt = whbm_dprt;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXjbh() {
        return xjbh;
    }

    public void setXjbh(String xjbh) {
        this.xjbh = xjbh == null ? null : xjbh.trim();
    }

    public String getXsrid() {
        return xsrid;
    }

    public void setXsrid(String xsrid) {
        this.xsrid = xsrid == null ? null : xsrid.trim();
    }

    public String getXsrmc() {
        return xsrmc;
    }

    public void setXsrmc(String xsrmc) {
        this.xsrmc = xsrmc == null ? null : xsrmc.trim();
    }

    public Date getXsrq() {
        return xsrq;
    }

    public void setXsrq(Date xsrq) {
        this.xsrq = xsrq;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj == null ? null : kssj.trim();
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj == null ? null : jssj.trim();
    }

    public Integer getIsYhxs() {
		return isYhxs;
	}

	public void setIsYhxs(Integer isYhxs) {
		this.isYhxs = isYhxs;
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
		return "Routinginspectionrecord [id=" + id + ", xjbh=" + xjbh
				+ ", xsrid=" + xsrid + ", xsrmc=" + xsrmc + ", xsrq=" + xsrq
				+ ", kssj=" + kssj + ", jssj=" + jssj + ", isYhxs=" + isYhxs
				+ ", zt=" + zt + ", dprtcode=" + dprtcode + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", whr_user=" + whr_user + ", whbm_dprt=" + whbm_dprt
				+ ", jg_dprt=" + jg_dprt + "]";
	}
    
}