package com.eray.thjw.po;


public class TechnicalUpload extends BaseEntity{

	private String id;

	private String mainid;

	private String yswjm;

	private String wbwjm;

	private String nbwjm;

	private String sm;

	private String cflj;

	private Integer yxzt;

	private String czsj;

	private String czbmid;

	private String czrid;

	private String dprtCode;
	
	private String wjzd;
	
	private String hzm;
	
	private Double wjdx;
	
	private String technicalfileId;
	
	
	

	public String getTechnicalfileId() {
		return technicalfileId;
	}

	public void setTechnicalfileId(String technicalfileId) {
		this.technicalfileId = technicalfileId;
	}

	public Double getWjdx() {
		return wjdx;
	}

	public void setWjdx(Double wjdx) {
		this.wjdx = wjdx;
	}

	public String getHzm() {
		return hzm;
	}

	public void setHzm(String hzm) {
		this.hzm = hzm;
	}

	public String getWjzd() {
		wjzd = (cflj==null?"":cflj).concat("\\").concat(nbwjm==null?"":nbwjm);
		return wjzd;
	}

	public void setWjzd(String wjzd) {
		this.wjzd = wjzd;
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

	public String getYswjm() {
		return yswjm;
	}

	public void setYswjm(String yswjm) {
		this.yswjm = yswjm;
	}

	public String getWbwjm() {
		return wbwjm;
	}

	public void setWbwjm(String wbwjm) {
		this.wbwjm = wbwjm;
	}

	public String getNbwjm() {
		return nbwjm;
	}

	public void setNbwjm(String nbwjm) {
		this.nbwjm = nbwjm;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getCflj() {
		return cflj;
	}

	public void setCflj(String cflj) {
		this.cflj = cflj;
	}


	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
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

	public String getDprtCode() {
		return dprtCode;
	}

	public void setDprtCode(String dprtCode) {
		this.dprtCode = dprtCode;
	}

}