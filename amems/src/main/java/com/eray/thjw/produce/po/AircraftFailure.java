package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/** 
 * @Description 飞机故障履历-虚拟实体
 * @CreateTime 2018年5月22日 上午11:12:18
 * @CreateBy 韩武	
 */
public class AircraftFailure extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String fjzch;
	
	private String dprtcode;
	
	private Date fxrq;
	
	private String gzbg;
	
	private String gzxx;
	
	private String clcs;
	
	private String gzz;
	
	private String zrr;
	
	private String sjZd;

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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Date getFxrq() {
		return fxrq;
	}

	public void setFxrq(Date fxrq) {
		this.fxrq = fxrq;
	}

	public String getGzbg() {
		return gzbg;
	}

	public void setGzbg(String gzbg) {
		this.gzbg = gzbg;
	}

	public String getGzxx() {
		return gzxx;
	}

	public void setGzxx(String gzxx) {
		this.gzxx = gzxx;
	}

	public String getClcs() {
		return clcs;
	}

	public void setClcs(String clcs) {
		this.clcs = clcs;
	}

	public String getGzz() {
		return gzz;
	}

	public void setGzz(String gzz) {
		this.gzz = gzz;
	}

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}

	public String getSjZd() {
		return sjZd;
	}

	public void setSjZd(String sjZd) {
		this.sjZd = sjZd;
	}
}
