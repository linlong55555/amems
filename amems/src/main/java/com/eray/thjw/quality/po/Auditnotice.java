package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 质量审核通知单   b_z_007
 * @CreateTime 2018-1-8 上午10:02:03
 * @CreateBy 孙霁
 */
public class Auditnotice extends BizEntity{
    private String id;

    private String dprtcode;

    private String jcdbh;

    private Date jhShrq;

    private Integer shlb;

    private Integer lx;

    private String shdxid;

    private String shdxbh;

    private String shdxmc;

    private String jcnr;

    private String qrrid;

    private String qrrbh;

    private String qrrmc;

    private Date qrsj;

    private Integer zt;

    private Integer ydzt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User whr;
    
    private Department jg_dprt;
    
    private List<AuditMembers> shdxList;
    
    private List<Attachment> attachmentList;
    
    private String ndshjhid;
    
	public String getNdshjhid() {
		return ndshjhid;
	}

	public void setNdshjhid(String ndshjhid) {
		this.ndshjhid = ndshjhid;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<AuditMembers> getShdxList() {
		return shdxList;
	}

	public void setShdxList(List<AuditMembers> shdxList) {
		this.shdxList = shdxList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getJcdbh() {
		return jcdbh;
	}

	public void setJcdbh(String jcdbh) {
		this.jcdbh = jcdbh;
	}

	public Date getJhShrq() {
		return jhShrq;
	}

	public void setJhShrq(Date jhShrq) {
		this.jhShrq = jhShrq;
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
		this.shdxid = shdxid;
	}

	public String getShdxbh() {
		return shdxbh;
	}

	public void setShdxbh(String shdxbh) {
		this.shdxbh = shdxbh;
	}

	public String getShdxmc() {
		return shdxmc;
	}

	public void setShdxmc(String shdxmc) {
		this.shdxmc = shdxmc;
	}

	public String getJcnr() {
		return jcnr;
	}

	public void setJcnr(String jcnr) {
		this.jcnr = jcnr;
	}

	public String getQrrid() {
		return qrrid;
	}

	public void setQrrid(String qrrid) {
		this.qrrid = qrrid;
	}

	public String getQrrbh() {
		return qrrbh;
	}

	public void setQrrbh(String qrrbh) {
		this.qrrbh = qrrbh;
	}

	public String getQrrmc() {
		return qrrmc;
	}

	public void setQrrmc(String qrrmc) {
		this.qrrmc = qrrmc;
	}

	public Date getQrsj() {
		return qrsj;
	}

	public void setQrsj(Date qrsj) {
		this.qrsj = qrsj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getYdzt() {
		return ydzt;
	}

	public void setYdzt(Integer ydzt) {
		this.ydzt = ydzt;
	}

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

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

  
}