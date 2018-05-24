package com.eray.thjw.quality.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_z_014 质量审核报告
 * @CreateTime 2018年1月8日 上午10:58:27
 * @CreateBy liudeng
 */
public class QualityAuditReport extends BizEntity{
private String id;
private String dprtcode;
private Integer zt;
private String whbmid;
private Date whsj;
private String whrid;
private String shrbmid;
private String shrid;
private Date shsj;
private String shyij;
private String sprbmid;
private String sprid;
private Date spsj;
private String spyj;
private String gbsm;
private String gbrbmid;
private String gbrid;
private Date gbsj;
private String zlshbgbh;
private String shmd;
private String shfw;
private String shyj;
private String shgy;
private String shjl;
private Integer shlb;
private Integer lx;
private String shdxid;
private String shdxbh;
private String shdxmc;
private Date shrq;
private User user;
private AuditMembers auditPerson;
private DistributeDepartment distributeDepartment;

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
public Date getWhsj() {
	return whsj;
}
public void setWhsj(Date whsj) {
	this.whsj = whsj;
}
public String getShrbmid() {
	return shrbmid;
}
public void setShrbmid(String shrbmid) {
	this.shrbmid = shrbmid;
}
public String getShrid() {
	return shrid;
}
public void setShrid(String shrid) {
	this.shrid = shrid;
}
public Date getShsj() {
	return shsj;
}
public void setShsj(Date shsj) {
	this.shsj = shsj;
}
public String getShyij() {
	return shyij;
}
public void setShyij(String shyij) {
	this.shyij = shyij;
}
public String getSprbmid() {
	return sprbmid;
}
public void setSprbmid(String sprbmid) {
	this.sprbmid = sprbmid;
}
public String getSprid() {
	return sprid;
}
public void setSprid(String sprid) {
	this.sprid = sprid;
}
public Date getSpsj() {
	return spsj;
}
public void setSpsj(Date spsj) {
	this.spsj = spsj;
}
public String getSpyj() {
	return spyj;
}
public void setSpyj(String spyj) {
	this.spyj = spyj;
}
public String getGbsm() {
	return gbsm;
}
public void setGbsm(String gbsm) {
	this.gbsm = gbsm;
}
public String getGbrbmid() {
	return gbrbmid;
}
public void setGbrbmid(String gbrbmid) {
	this.gbrbmid = gbrbmid;
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
public String getZlshbgbh() {
	return zlshbgbh;
}
public void setZlshbgbh(String zlshbgbh) {
	this.zlshbgbh = zlshbgbh;
}
public String getShmd() {
	return shmd;
}
public void setShmd(String shmd) {
	this.shmd = shmd;
}
public String getShfw() {
	return shfw;
}
public void setShfw(String shfw) {
	this.shfw = shfw;
}
public String getShyj() {
	return shyj;
}
public void setShyj(String shyj) {
	this.shyj = shyj;
}
public String getShgy() {
	return shgy;
}
public void setShgy(String shgy) {
	this.shgy = shgy;
}
public String getShjl() {
	return shjl;
}
public void setShjl(String shjl) {
	this.shjl = shjl;
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
public Date getShrq() {
	return shrq;
}
public void setShrq(Date shrq) {
	this.shrq = shrq;
}
public String getWhrid() {
	return whrid;
}
public void setWhrid(String whrid) {
	this.whrid = whrid;
}
public String getShdxmc() {
	return shdxmc;
}
public void setShdxmc(String shdxmc) {
	this.shdxmc = shdxmc;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

public AuditMembers getAuditPerson() {
	return auditPerson;
}
public void setAuditPerson(AuditMembers auditPerson) {
	this.auditPerson = auditPerson;
}


public DistributeDepartment getDistributeDepartment() {
	return distributeDepartment;
}
public void setDistributeDepartment(DistributeDepartment distributeDepartment) {
	this.distributeDepartment = distributeDepartment;
}
@Override
public String toString() {
	return "QualityAuditReport [id=" + id + ", dprtcode=" + dprtcode + ", zt="
			+ zt + ", whbmid=" + whbmid + ", whsj=" + whsj + ", shrbmid="
			+ shrbmid + ", shrid=" + shrid + ", shsj=" + shsj + ", shyij="
			+ shyij + ", sprbmid=" + sprbmid + ", sprid=" + sprid + ", spsj="
			+ spsj + ", spyj=" + spyj + ", gbsm=" + gbsm + ", gbrbmid="
			+ gbrbmid + ", gbrid=" + gbrid + ", gbsj=" + gbsj + ", zlshbgbh="
			+ zlshbgbh + ", shmd=" + shmd + ", shfw=" + shfw + ", shyj=" + shyj
			+ ", shgy=" + shgy + ", shjl=" + shjl + ", shlb=" + shlb + ", lx="
			+ lx + ", shdxid=" + shdxid + ", shdxbh=" + shdxbh + ", shrq="
			+ shrq + "]";
}



}
