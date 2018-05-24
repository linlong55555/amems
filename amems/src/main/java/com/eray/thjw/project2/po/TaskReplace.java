package com.eray.thjw.project2.po;

import java.util.ArrayList;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

public class TaskReplace extends BaseEntity{
//维护方案id
private String wxfaid;
//机构代码
private String dprtcode;
//机型
private String jx;

//维护项目id
private String wxxmid;
//任务号
private String rwh;
//维修项目类型(1:一般项目 2:时空项目 3:时寿项目 4:定检包)
private Integer wxxmlx;
//工卡id
private String gkid;
//工卡编号
private String gkbh;
//维修项目版本
private String bb;
//参考号
private String ckh;
//参考文件
private String ckwj;
//计划工时
private String jhgs;
private String jhgsRs;
private String jhgsXss;
//任务描述
private String rwms;
//章节号
private String zjh;

/** 关联机型 */
private List<ProjectModel> projectModelList=new ArrayList<ProjectModel>();

//章节号中文描述
private String zjh_zwms;
//章节号英文描述
private String zjh_ywms;
//维修项目大类编号
private String dlbh;
//维修项目大类中文描述
private String dlbh_zwms;
//维修项目大类英文描述
private String dlbh_ywms;
//适用性
private String syx;
//飞机注册号
private String fjzch;
//Eo编号
private String eobh;


public List<ProjectModel> getProjectModelList() {
	return projectModelList;
}
public void setProjectModelList(List<ProjectModel> projectModelList) {
	this.projectModelList = projectModelList;
}
public String getFjzch() {
	return fjzch;
}
public void setFjzch(String fjzch) {
	this.fjzch = fjzch;
}

public String getJhgs() {
	return jhgs;
}
public void setJhgs(String jhgs) {
	this.jhgs = jhgs;
}
public String getJhgsRs() {
	return jhgsRs;
}
public void setJhgsRs(String jhgsRs) {
	this.jhgsRs = jhgsRs;
}

public String getBb() {
	return bb;
}
public void setBb(String bb) {
	this.bb = bb;
}
public String getJhgsXss() {
	return jhgsXss;
}
public void setJhgsXss(String jhgsXss) {
	this.jhgsXss = jhgsXss;
}
public String getWxfaid() {
	return wxfaid;
}
public void setWxfaid(String wxfaid) {
	this.wxfaid = wxfaid;
}
public String getEobh() {
	return eobh;
}
public void setEobh(String eobh) {
	this.eobh = eobh;
}

public String getDprtcode() {
	return dprtcode;
}
public void setDprtcode(String dprtcode) {
	this.dprtcode = dprtcode;
}
public String getJx() {
	return jx;
}
public void setJx(String jx) {
	this.jx = jx;
}
public String getWxxmid() {
	return wxxmid;
}
public void setWxxmid(String wxxmid) {
	this.wxxmid = wxxmid;
}
public String getRwh() {
	return rwh;
}
public void setRwh(String rwh) {
	this.rwh = rwh;
}
public Integer getWxxmlx() {
	return wxxmlx;
}
public void setWxxmlx(Integer wxxmlx) {
	this.wxxmlx = wxxmlx;
}
public String getGkid() {
	return gkid;
}
public void setGkid(String gkid) {
	this.gkid = gkid;
}
public String getGkbh() {
	return gkbh;
}
public void setGkbh(String gkbh) {
	this.gkbh = gkbh;
}
public String getCkh() {
	return ckh;
}
public void setCkh(String ckh) {
	this.ckh = ckh;
}
public String getRwms() {
	return rwms;
}
public void setRwms(String rwms) {
	this.rwms = rwms;
}
public String getZjh() {
	return zjh;
}
public String getCkwj() {
	return ckwj;
}
public void setCkwj(String ckwj) {
	this.ckwj = ckwj;
}
public void setZjh(String zjh) {
	this.zjh = zjh;
}
public String getZjh_zwms() {
	return zjh_zwms;
}
public void setZjh_zwms(String zjh_zwms) {
	this.zjh_zwms = zjh_zwms;
}
public String getZjh_ywms() {
	return zjh_ywms;
}
public void setZjh_ywms(String zjh_ywms) {
	this.zjh_ywms = zjh_ywms;
}
public String getDlbh() {
	return dlbh;
}
public void setDlbh(String dlbh) {
	this.dlbh = dlbh;
}
public String getDlbh_zwms() {
	return dlbh_zwms;
}
public void setDlbh_zwms(String dlbh_zwms) {
	this.dlbh_zwms = dlbh_zwms;
}
public String getDlbh_ywms() {
	return dlbh_ywms;
}
public void setDlbh_ywms(String dlbh_ywms) {
	this.dlbh_ywms = dlbh_ywms;
}
public String getSyx() {
	return syx;
}
public void setSyx(String syx) {
	this.syx = syx;
}

}
