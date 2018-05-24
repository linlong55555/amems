package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_s_019 飞机缺陷表
 * @CreateTime 2017年9月15日 上午9:54:15
 * @CreateBy 林龙
 */
@SuppressWarnings("serial")
public class PlaneFaultMonitoring extends BizEntity{
    
	private String id;
	
	private String fjzch;
	
	private String gzxx;
	
	private String bz;
	
	private Integer gbzt;
	
	private String whdwid;
	
	private String whrid;
	
	private Date whsj;
	
	private String gbrid;
	
	private Date gbsj;
	
	private String gbyy;
	
	private String gbrname;
	
	private String zdrname;
	
	private Integer gzcs;
	
	private Date zjfsrq;
	
	private String zjh;

	
	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getGzxx() {
		return gzxx;
	}

	public void setGzxx(String gzxx) {
		this.gzxx = gzxx;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getGbzt() {
		return gbzt;
	}

	public void setGbzt(Integer gbzt) {
		this.gbzt = gbzt;
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

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public Date getGbsj() {
		return gbsj;
	}

	public void setGbsj(Date gbsj) {
		this.gbsj = gbsj;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy;
	}

	public String getGbrname() {
		return gbrname;
	}

	public void setGbrname(String gbrname) {
		this.gbrname = gbrname;
	}

	public String getZdrname() {
		return zdrname;
	}

	public void setZdrname(String zdrname) {
		this.zdrname = zdrname;
	}

	public Integer getGzcs() {
		return gzcs;
	}

	public void setGzcs(Integer gzcs) {
		this.gzcs = gzcs;
	}

	public Date getZjfsrq() {
		return zjfsrq;
	}

	public void setZjfsrq(Date zjfsrq) {
		this.zjfsrq = zjfsrq;
	}
	
}