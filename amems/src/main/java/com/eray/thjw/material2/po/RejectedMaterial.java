package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description  对应表b_h_026
 * @CreateTime 2018-3-2 下午2:23:58
 * @CreateBy 孙霁
 */
public class RejectedMaterial extends BizEntity{
    private String id;

    private String dprtcode;

    private String whrbmid;

    private String whrid;

    private Date whsj;

    private Date tlrq;

    private String bjid;

    private String bjh;

    private String bjmc;

    private String pch;

    private String xlh;

    private BigDecimal tlsl;

    private String dw;

    private Integer sfky;

    private Integer bjly;

    private String fjzch;

    private String sm;

    private Integer zt;

    private String wckcid;

    private Integer wllb;
    
    private User tlr;
    
    private User shr;
    
    private List<ReceivingRelationship> receivingRelationshiplist; //b_h2_0200101 收货单-上架List集合
    
    
    public List<ReceivingRelationship> getReceivingRelationshiplist() {
		return receivingRelationshiplist;
	}

	public void setReceivingRelationshiplist(
			List<ReceivingRelationship> receivingRelationshiplist) {
		this.receivingRelationshiplist = receivingRelationshiplist;
	}

	public User getTlr() {
		return tlr;
	}

	public void setTlr(User tlr) {
		this.tlr = tlr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
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

    public String getWhrbmid() {
        return whrbmid;
    }

    public void setWhrbmid(String whrbmid) {
        this.whrbmid = whrbmid == null ? null : whrbmid.trim();
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

    public Date getTlrq() {
        return tlrq;
    }

    public void setTlrq(Date tlrq) {
        this.tlrq = tlrq;
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

    public String getBjmc() {
        return bjmc;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc == null ? null : bjmc.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public BigDecimal getTlsl() {
        return tlsl;
    }

    public void setTlsl(BigDecimal tlsl) {
        this.tlsl = tlsl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public Integer getSfky() {
        return sfky;
    }

    public void setSfky(Integer sfky) {
        this.sfky = sfky;
    }

    public Integer getBjly() {
        return bjly;
    }

    public void setBjly(Integer bjly) {
        this.bjly = bjly;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getWckcid() {
        return wckcid;
    }

    public void setWckcid(String wckcid) {
        this.wckcid = wckcid == null ? null : wckcid.trim();
    }

    public Integer getWllb() {
        return wllb;
    }

    public void setWllb(Integer wllb) {
        this.wllb = wllb;
    }
}