package com.eray.thjw.po;

import java.util.Date;
//岗位要求日志  b_p_00101_rec
public class WorkRequireRec extends BizEntity{
private String id;
private String mainid;
private Integer xc;
private String gwyq;
private Integer zt;
private String whbmid;
private String whrid;
private Date whsj;
private Integer rec_xglx;
private String rec_czrid;
private Date rec_czsj;
private String rec_ip;
private String rec_czsm;
private String rec_czls;
private String rec_zbid;
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
public Integer getXc() {
	return xc;
}
public void setXc(Integer xc) {
	this.xc = xc;
}
public String getGwyq() {
	return gwyq;
}
public void setGwyq(String gwyq) {
	this.gwyq = gwyq;
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
public Integer getRec_xglx() {
	return rec_xglx;
}
public void setRec_xglx(Integer rec_xglx) {
	this.rec_xglx = rec_xglx;
}
public String getRec_czrid() {
	return rec_czrid;
}
public void setRec_czrid(String rec_czrid) {
	this.rec_czrid = rec_czrid;
}
public Date getRec_czsj() {
	return rec_czsj;
}
public void setRec_czsj(Date rec_czsj) {
	this.rec_czsj = rec_czsj;
}
public String getRec_ip() {
	return rec_ip;
}
public void setRec_ip(String rec_ip) {
	this.rec_ip = rec_ip;
}
public String getRec_czsm() {
	return rec_czsm;
}
public void setRec_czsm(String rec_czsm) {
	this.rec_czsm = rec_czsm;
}
public String getRec_czls() {
	return rec_czls;
}
public void setRec_czls(String rec_czls) {
	this.rec_czls = rec_czls;
}
public String getRec_zbid() {
	return rec_zbid;
}
public void setRec_zbid(String rec_zbid) {
	this.rec_zbid = rec_zbid;
}
@Override
public String toString() {
	return "WorkRequireRec [id=" + id + ", mainid=" + mainid + ", xc=" + xc
			+ ", gwyq=" + gwyq + ", zt=" + zt + ", whbmid=" + whbmid
			+ ", whrid=" + whrid + ", whsj=" + whsj + ", rec_xglx=" + rec_xglx
			+ ", rec_czrid=" + rec_czrid + ", rec_czsj=" + rec_czsj
			+ ", rec_ip=" + rec_ip + ", rec_czsm=" + rec_czsm + ", rec_czls="
			+ rec_czls + ", rec_zbid=" + rec_zbid + "]";
}




}
