package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

public class QualityReviewRecord extends BizEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String mainid;
	
	private String czrid;
	
	private Date czsj;
	
	private String czsm;
	
	private String hd;
	
	private String fjid;
	
	private String czrname;

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

	public String getCzsm() {
		return czsm;
	}

	public void setCzsm(String czsm) {
		this.czsm = czsm;
	}

	public String getHd() {
		return hd;
	}

	public void setHd(String hd) {
		this.hd = hd;
	}

	public String getFjid() {
		return fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCzrname() {
		return czrname;
	}

	public void setCzrname(String czrname) {
		this.czrname = czrname;
	}

}
