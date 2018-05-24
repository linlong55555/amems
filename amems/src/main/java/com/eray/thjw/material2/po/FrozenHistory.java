package com.eray.thjw.material2.po;

import java.util.Date;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.po.BizEntity;

/**
 * b_h_023
 * 库存冻结履历
 */
public class FrozenHistory extends BizEntity{

private String	id;

private Integer kclx;

private String kcid;

private Integer ywlx;

private String ywid;

private Integer djsl;

private String ywbh;

private Date czsj;

private Stock stock;


public String getYwbh() {
	return ywbh;
}

public void setYwbh(String ywbh) {
	this.ywbh = ywbh;
}

public Date getCzsj() {
	return czsj;
}

public void setCzsj(Date czsj) {
	this.czsj = czsj;
}

public Stock getStock() {
	return stock;
}

public void setStock(Stock stock) {
	this.stock = stock;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Integer getKclx() {
	return kclx;
}

public void setKclx(Integer kclx) {
	this.kclx = kclx;
}

public String getKcid() {
	return kcid;
}

public void setKcid(String kcid) {
	this.kcid = kcid;
}

public Integer getYwlx() {
	return ywlx;
}

public void setYwlx(Integer ywlx) {
	this.ywlx = ywlx;
}

public String getYwid() {
	return ywid;
}

public void setYwid(String ywid) {
	this.ywid = ywid;
}

public Integer getDjsl() {
	return djsl;
}

public void setDjsl(Integer djsl) {
	this.djsl = djsl;
}




	
}
