package com.eray.thjw.po;

import java.util.Date;

//对应表b_p_00101,岗位要求
public class WorkRequire extends BizEntity{
private String id;
private String mainid;
private Integer xc;
private String gwyq;
private Integer zt;
private String whrid;
private String whbmid;
private Date whsj;
private String gwbh;


public String getGwbh() {
	return gwbh;
}
public void setGwbh(String gwbh) {
	this.gwbh = gwbh;
}
public String getWhrid() {
	return whrid;
}
public void setWhrid(String whrid) {
	this.whrid = whrid;
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
public Date getWhsj() {
	return whsj;
}
public void setWhsj(Date whsj) {
	this.whsj = whsj;
}

@Override
public String toString() {
	return "WorkRequire [id=" + id + ", mainid=" + mainid + ", xc=" + xc
			+ ", gwyq=" + gwyq + ", zt=" + zt + ", whbmid=" + whbmid
			+ ", whsj=" + whsj + "]";
}



}
