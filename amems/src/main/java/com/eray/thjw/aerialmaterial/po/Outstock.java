package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * b_h_013 出库单
 * 
 * @author xu.yong
 * 
 */
public class Outstock extends BizEntity {
	private String id;

	private String dprtcode;

	private Integer cklx;

	private Integer sgbs;

	private String ckdh;

	private Date cksj;

	private String fjzch;

	private Integer zt;

	private String zdbmid;

	private String zdrid;

	private Date zdsj;

	private String ckbmid;

	private String cukid;

	private String sqbmid;

	private String sqrid;

	private String jddxid;

	private String jdfzr;

	private String bz;

	private Date sqsj;

	private String xgdjid;

	private String fxDateBegin;

	private String fxDateEnd;

	private String sqr;

	private String ckr;

	private String zdri;

	private String jddxms;

	private String fxd;
	private String otheraerocadeid;
	
	private List<String>   xgdjids;
	
	private List<Piecenumber> piecenumber;

	private List<MaterialRequisitionDetail> materialRequisitionDetail;
	
	/** 虚拟字段 start */
	private Instock instock;//外飞行队查询时使用：入库单
	/** 虚拟字段 end */
	
	private User ckrUser;
	
	private Department department;
	
	

	public String getJddxms() {
		return jddxms;
	}

	public List<String> getXgdjids() {
		return xgdjids;
	}

	public void setXgdjids(List<String> xgdjids) {
		this.xgdjids = xgdjids;
	}

	public String getFxd() {
		return fxd;
	}

	public void setFxd(String fxd) {
		this.fxd = fxd;
	}

	public String getOtheraerocadeid() {
		return otheraerocadeid;
	}

	public void setOtheraerocadeid(String otheraerocadeid) {
		this.otheraerocadeid = otheraerocadeid;
	}

	public void setJddxms(String jddxms) {
		this.jddxms = jddxms;
	}

	public String getZdri() {
		return zdri;
	}

	public void setZdri(String zdri) {
		this.zdri = zdri;
	}

	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getCkr() {
		return ckr;
	}

	public void setCkr(String ckr) {
		this.ckr = ckr;
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

	public List<MaterialRequisitionDetail> getMaterialRequisitionDetail() {
		return materialRequisitionDetail;
	}

	public void setMaterialRequisitionDetail(
			List<MaterialRequisitionDetail> materialRequisitionDetail) {
		this.materialRequisitionDetail = materialRequisitionDetail;
	}

	public List<Piecenumber> getPiecenumber() {
		return piecenumber;
	}

	public void setPiecenumber(List<Piecenumber> piecenumber) {
		this.piecenumber = piecenumber;
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

	public String getCkdh() {
		return ckdh;
	}

	public void setCkdh(String ckdh) {
		this.ckdh = ckdh == null ? null : ckdh.trim();
	}

	public Date getCksj() {
		return cksj;
	}

	public void setCksj(Date cksj) {
		this.cksj = cksj;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch == null ? null : fjzch.trim();
	}

	public Integer getCklx() {
		return cklx;
	}

	public void setCklx(Integer cklx) {
		this.cklx = cklx;
	}

	public Integer getSgbs() {
		return sgbs;
	}

	public void setSgbs(Integer sgbs) {
		this.sgbs = sgbs;
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

	public String getCkbmid() {
		return ckbmid;
	}

	public void setCkbmid(String ckbmid) {
		this.ckbmid = ckbmid == null ? null : ckbmid.trim();
	}

	public String getCukid() {
		return cukid;
	}

	public void setCukid(String cukid) {
		this.cukid = cukid == null ? null : cukid.trim();
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

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}

	public String getXgdjid() {
		return xgdjid;
	}

	public void setXgdjid(String xgdjid) {
		this.xgdjid = xgdjid == null ? null : xgdjid.trim();
	}

	public Instock getInstock() {
		return instock;
	}

	public void setInstock(Instock instock) {
		this.instock = instock;
	}

	public User getCkrUser() {
		return ckrUser;
	}

	public void setCkrUser(User ckrUser) {
		this.ckrUser = ckrUser;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}