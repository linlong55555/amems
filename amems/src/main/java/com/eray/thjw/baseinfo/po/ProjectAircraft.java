package com.eray.thjw.baseinfo.po;

import java.util.Date;

/** 
 * @Description 项目用到的飞机基本信息(飞机基本数据用于145单位)
 * @CreateTime 2017-12-6 下午2:55:57
 * @CreateBy 甘清	
 */
public class ProjectAircraft {
	private String fjzch; //飞机注册号
	private String dprtcode; //组织机构码
	private String fjjx;  //飞机机型
	private String xlh; //序列号
	private String bzm; //备注名
	private String rhyzph; //润滑油脂牌号
	private String yyyph; //液压油牌号
	private String fjms; //飞机描述
	private String ipcyxxh; //IPC有效性号
	private Integer zt; //状态 0无效、1有效
	private String whbmid; //维护人部门ID
	private String whrid; //维护人ID
	private Date whsj; //维护人ID
	
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
	public String getFjjx() {
		return fjjx;
	}
	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getBzm() {
		return bzm;
	}
	public void setBzm(String bzm) {
		this.bzm = bzm;
	}
	public String getRhyzph() {
		return rhyzph;
	}
	public void setRhyzph(String rhyzph) {
		this.rhyzph = rhyzph;
	}
	public String getYyyph() {
		return yyyph;
	}
	public void setYyyph(String yyyph) {
		this.yyyph = yyyph;
	}
	public String getFjms() {
		return fjms;
	}
	public void setFjms(String fjms) {
		this.fjms = fjms;
	}
	public String getIpcyxxh() {
		return ipcyxxh;
	}
	public void setIpcyxxh(String ipcyxxh) {
		this.ipcyxxh = ipcyxxh;
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
	
	
	
}
