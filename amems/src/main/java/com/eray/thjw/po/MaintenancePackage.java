package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 文件
 * @author Admin_PC
 *
 */
public class MaintenancePackage extends BizEntity {
	private String id;

	private String mainid;

	private String yswjm;

	private String wbwjm;

	private String nbwjm;

	private BigDecimal wjdx;

	private String hzm;

	private String sm;

	private String cflj;

	private Integer yxzt;

	private Date czsj;

	private String czbmid;

	private String czrid;

	private String wjdxs;

	private String ids;
	
	/** 所属文件夹 */
	private FileCatalog folder;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getWjdxs() {
		return wjdxs;
	}

	public void setWjdxs(String wjdxs) {
		this.wjdxs = wjdxs;
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

	public String getYswjm() {
		return yswjm;
	}

	public void setYswjm(String yswjm) {
		this.yswjm = yswjm == null ? null : yswjm.trim();
	}

	public String getWbwjm() {
		return wbwjm;
	}

	public void setWbwjm(String wbwjm) {
		this.wbwjm = wbwjm == null ? null : wbwjm.trim();
	}

	public String getNbwjm() {
		return nbwjm;
	}

	public void setNbwjm(String nbwjm) {
		this.nbwjm = nbwjm == null ? null : nbwjm.trim();
	}

	public BigDecimal getWjdx() {
		return wjdx;
	}

	public void setWjdx(BigDecimal wjdx) {
		this.wjdx = wjdx;
	}

	public String getHzm() {
		return hzm;
	}

	public void setHzm(String hzm) {
		this.hzm = hzm == null ? null : hzm.trim();
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm == null ? null : sm.trim();
	}

	public String getCflj() {
		return cflj;
	}

	public void setCflj(String cflj) {
		this.cflj = cflj == null ? null : cflj.trim();
	}

	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}

	public String getCzbmid() {
		return czbmid;
	}

	public void setCzbmid(String czbmid) {
		this.czbmid = czbmid == null ? null : czbmid.trim();
	}

	public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid == null ? null : czrid.trim();
	}

	public FileCatalog getFolder() {
		return folder;
	}

	public void setFolder(FileCatalog folder) {
		this.folder = folder;
	}
}