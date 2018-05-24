package com.eray.thjw.po;

import com.eray.thjw.flightdata.po.Attachment;

/**
 * 文件夹
 * @author ll
 *
 */
public class FileCatalog  extends  BizEntity {
	private String id;

	private String mkdm;

	private String mlmc;

	private String fmlid;

	private String sm;

	private Integer yxzt;

	private String czsj;

	private String czbmid;

	private String czrid;
	
	private String dprtcode;
	
    /** 文档压缩包附件 */
    private Attachment docZipAttachment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getMkdm() {
		return mkdm;
	}

	public void setMkdm(String mkdm) {
		this.mkdm = mkdm == null ? null : mkdm.trim();
	}

	public String getMlmc() {
		return mlmc;
	}

	public void setMlmc(String mlmc) {
		this.mlmc = mlmc == null ? null : mlmc.trim();
	}

	public String getFmlid() {
		return fmlid;
	}

	public void setFmlid(String fmlid) {
		this.fmlid = fmlid == null ? null : fmlid.trim();
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm == null ? null : sm.trim();
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
		this.czbmid = czbmid == null ? null : czbmid.trim();
	}

	public String getCzrid() {
		return czrid;
	}

	public void setCzrid(String czrid) {
		this.czrid = czrid == null ? null : czrid.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Attachment getDocZipAttachment() {
		return docZipAttachment;
	}

	public void setDocZipAttachment(Attachment docZipAttachment) {
		this.docZipAttachment = docZipAttachment;
	}
}