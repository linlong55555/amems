package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_s_01901 飞机缺陷明细表
 * @CreateTime 2017年10月17日 下午5:43:43
 * @CreateBy 胡才秋
 */
@SuppressWarnings("serial")
public class PlaneFaultMonitoringInfo extends BizEntity{
  
	private String id;
	
	private String mainid;
	
	private String fjzch;
	
	private String fxjldid;
	
	private String hbh;
	
	private Date hbrq;
	
	private String zlh;
	
	private String pgsl;
	
	private String cljg;
	
	private String cxjxx;
	
	private String zsjxx;
	
	private String bz;
	
	private Integer zt;
	
	private String whdwid;
	
	private String whrid;
	
	private Date whsj;
	
	private String gdid;
	
	private List<Attachment> attachmentList;
	
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public String getHbh() {
		return hbh;
	}

	public void setHbh(String hbh) {
		this.hbh = hbh;
	}

	public Date getHbrq() {
		return hbrq;
	}

	public void setHbrq(Date hbrq) {
		this.hbrq = hbrq;
	}

	public String getZlh() {
		return zlh;
	}

	public void setZlh(String zlh) {
		this.zlh = zlh;
	}

	public String getPgsl() {
		return pgsl;
	}

	public void setPgsl(String pgsl) {
		this.pgsl = pgsl;
	}

	public String getCljg() {
		return cljg;
	}

	public void setCljg(String cljg) {
		this.cljg = cljg;
	}

	public String getCxjxx() {
		return cxjxx;
	}

	public void setCxjxx(String cxjxx) {
		this.cxjxx = cxjxx;
	}

	public String getZsjxx() {
		return zsjxx;
	}

	public void setZsjxx(String zsjxx) {
		this.zsjxx = zsjxx;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
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

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

}