package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_g2_00100 技术评估单-附加信息
 * @CreateTime 2017年8月24日 下午10:09:35
 * @CreateBy 林龙
 */
public class TechnicalAttached extends BizEntity {
	private String id;

	private String mainid;

	private String wclx;

	private String dj;

	private String jjcd;

	private String sjgz;

	private String wjzy;

	private String syfwYwj;

	private Integer syx;

	private String fsyyy;

	private Short sylb;

	private String syfwBdw;

	private String gbtj;

	private Integer isCfjc;

	private Integer isZzcs;

	private Integer isMfhc;

	private Integer isZbhc;

	private Integer isTsgj;

	private Integer isYxzlph;

	private String ywjnr;

	private String bj;

	private String wjgxjx;

	private String xgwjzxztdc;

	private String gcpgjl;

	private String jlfjid;
	
	private String fjzch;

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid == null ? null : mainid.trim();
	}

	public String getWclx() {
		return wclx;
	}

	public void setWclx(String wclx) {
		this.wclx = wclx == null ? null : wclx.trim();
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj == null ? null : dj.trim();
	}

	public String getJjcd() {
		return jjcd;
	}

	public void setJjcd(String jjcd) {
		this.jjcd = jjcd == null ? null : jjcd.trim();
	}

	public String getSjgz() {
		return sjgz;
	}

	public void setSjgz(String sjgz) {
		this.sjgz = sjgz == null ? null : sjgz.trim();
	}

	public String getWjzy() {
		return wjzy;
	}

	public void setWjzy(String wjzy) {
		this.wjzy = wjzy == null ? null : wjzy.trim();
	}

	public String getSyfwYwj() {
		return syfwYwj;
	}

	public void setSyfwYwj(String syfwYwj) {
		this.syfwYwj = syfwYwj == null ? null : syfwYwj.trim();
	}

	public String getFsyyy() {
		return fsyyy;
	}

	public void setFsyyy(String fsyyy) {
		this.fsyyy = fsyyy == null ? null : fsyyy.trim();
	}

	public Short getSylb() {
		return sylb;
	}

	public void setSylb(Short sylb) {
		this.sylb = sylb;
	}

	public String getSyfwBdw() {
		return syfwBdw;
	}

	public void setSyfwBdw(String syfwBdw) {
		this.syfwBdw = syfwBdw == null ? null : syfwBdw.trim();
	}

	public String getGbtj() {
		return gbtj;
	}

	public void setGbtj(String gbtj) {
		this.gbtj = gbtj == null ? null : gbtj.trim();
	}

	public String getYwjnr() {
		return ywjnr;
	}

	public Integer getSyx() {
		return syx;
	}

	public void setSyx(Integer syx) {
		this.syx = syx;
	}

	public Integer getIsCfjc() {
		return isCfjc;
	}

	public void setIsCfjc(Integer isCfjc) {
		this.isCfjc = isCfjc;
	}

	public Integer getIsZzcs() {
		return isZzcs;
	}

	public void setIsZzcs(Integer isZzcs) {
		this.isZzcs = isZzcs;
	}

	public Integer getIsMfhc() {
		return isMfhc;
	}

	public void setIsMfhc(Integer isMfhc) {
		this.isMfhc = isMfhc;
	}

	public Integer getIsZbhc() {
		return isZbhc;
	}

	public void setIsZbhc(Integer isZbhc) {
		this.isZbhc = isZbhc;
	}

	public Integer getIsTsgj() {
		return isTsgj;
	}

	public void setIsTsgj(Integer isTsgj) {
		this.isTsgj = isTsgj;
	}

	public Integer getIsYxzlph() {
		return isYxzlph;
	}

	public void setIsYxzlph(Integer isYxzlph) {
		this.isYxzlph = isYxzlph;
	}

	public void setYwjnr(String ywjnr) {
		this.ywjnr = ywjnr == null ? null : ywjnr.trim();
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj == null ? null : bj.trim();
	}

	public String getWjgxjx() {
		return wjgxjx;
	}

	public void setWjgxjx(String wjgxjx) {
		this.wjgxjx = wjgxjx == null ? null : wjgxjx.trim();
	}

	public String getXgwjzxztdc() {
		return xgwjzxztdc;
	}

	public void setXgwjzxztdc(String xgwjzxztdc) {
		this.xgwjzxztdc = xgwjzxztdc == null ? null : xgwjzxztdc.trim();
	}

	public String getGcpgjl() {
		return gcpgjl;
	}

	public void setGcpgjl(String gcpgjl) {
		this.gcpgjl = gcpgjl == null ? null : gcpgjl.trim();
	}

	public String getJlfjid() {
		return jlfjid;
	}

	public void setJlfjid(String jlfjid) {
		this.jlfjid = jlfjid == null ? null : jlfjid.trim();
	}
}