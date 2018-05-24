package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;


/**
 * author  刘邓
 * 工具借还记录
 *  b_h_030
 */
public class ToolBorrowRecord extends BizEntity{

private String id;

private String kcid;

private String bjid;

private String bjh;

private String bjxlh;

private Integer jllx;

private String jy_zrrid;

private String jy_zrrmc;

private Date jy_sj;

private BigDecimal jy_sl;

private String jy_bz;

private Integer cqjybs;

private String dprtcode;

private String czbmid;

private String czrid;

private Date czsj;

private Department jg_dprt;





public Department getJg_dprt() {
	return jg_dprt;
}

public void setJg_dprt(Department jg_dprt) {
	this.jg_dprt = jg_dprt;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getKcid() {
	return kcid;
}

public void setKcid(String kcid) {
	this.kcid = kcid;
}

public String getBjid() {
	return bjid;
}

public void setBjid(String bjid) {
	this.bjid = bjid;
}

public String getBjh() {
	return bjh;
}

public void setBjh(String bjh) {
	this.bjh = bjh;
}

public String getBjxlh() {
	return bjxlh;
}

public void setBjxlh(String bjxlh) {
	this.bjxlh = bjxlh;
}

public Integer getJllx() {
	return jllx;
}

public void setJllx(Integer jllx) {
	this.jllx = jllx;
}

public String getJy_zrrid() {
	return jy_zrrid;
}

public void setJy_zrrid(String jy_zrrid) {
	this.jy_zrrid = jy_zrrid;
}

public String getJy_zrrmc() {
	return jy_zrrmc;
}

public void setJy_zrrmc(String jy_zrrmc) {
	this.jy_zrrmc = jy_zrrmc;
}

public Date getJy_sj() {
	return jy_sj;
}

public void setJy_sj(Date jy_sj) {
	this.jy_sj = jy_sj;
}

public BigDecimal getJy_sl() {
	return jy_sl;
}

public void setJy_sl(BigDecimal jy_sl) {
	this.jy_sl = jy_sl;
}

public String getJy_bz() {
	return jy_bz;
}

public void setJy_bz(String jy_bz) {
	this.jy_bz = jy_bz;
}

public Integer getCqjybs() {
	return cqjybs;
}

public void setCqjybs(Integer cqjybs) {
	this.cqjybs = cqjybs;
}

public String getDprtcode() {
	return dprtcode;
}

public void setDprtcode(String dprtcode) {
	this.dprtcode = dprtcode;
}

public String getCzbmid() {
	return czbmid;
}

public void setCzbmid(String czbmid) {
	this.czbmid = czbmid;
}

public String getCzrid() {
	return czrid;
}

public void setCzrid(String czrid) {
	this.czrid = czrid;
}

public Date getCzsj() {
	return czsj;
}

public void setCzsj(Date czsj) {
	this.czsj = czsj;
}




}
