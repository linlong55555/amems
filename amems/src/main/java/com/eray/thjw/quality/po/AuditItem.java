package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

public class AuditItem extends BizEntity{
    private String id;

    private String dprtcode;

    private String shxmdbh;

    private Date sjShrq;

    private Integer shlb;

    private Integer lx;

    private String shdxid;

    private String shdxbh;

    private String shdxmc;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String shtzdid;
    
    private User whr;
    
    private Department jg_dprt;
    
    private List<AuditMembers> shdxList;
    
    private List<Attachment> attachmentList;


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

	public List<AuditMembers> getShdxList() {
		return shdxList;
	}

	public void setShdxList(List<AuditMembers> shdxList) {
		this.shdxList = shdxList;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getShtzdid() {
		return shtzdid;
	}

	public void setShtzdid(String shtzdid) {
		this.shtzdid = shtzdid;
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

    public String getShxmdbh() {
        return shxmdbh;
    }

    public void setShxmdbh(String shxmdbh) {
        this.shxmdbh = shxmdbh == null ? null : shxmdbh.trim();
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