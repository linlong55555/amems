package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;

public class QualityReview extends BizEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String dh;
	
	private Date zgxq;
	
	private String zgzt;
	
	private String zrbmid;
	
	private Integer zt;
	
	private String xfrid;
	
	private Date xfsj;
	
	private String xfyy;
	
	private String xffjid;
	
	private String fkrid;
	
	private Date fksj;
	
	private String zgsm;
	
	private String zgfjid;
	
	private String shrid;
	
	private String shsm;
	
	private Date shsj;
	
	private String shfjid;
	
	private String gbrid;
	
	private String gbyy;
	
	private Date gbsj;
	
	private String gbfjid;
	
	private Date whsj;
	
	private String whbmid;
	
	private String whrid;
	
	private String xfrname;
	
	private String fkrname;
	
	private String shrname;
	
	private String gbrname;
	
	private String whrname;
	
	private String zrbmname;
	
	private String whbmname;
	
	private List<Attachment> xfattachments;
	
	private List<Attachment> zgattachments;
	
	private List<Attachment> shattachments;
	
	private List<Attachment> gbattachments;
	
	private List<QualityReviewRecord> qualityReviewRecordList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public Date getZgxq() {
		return zgxq;
	}

	public void setZgxq(Date zgxq) {
		this.zgxq = zgxq;
	}

	public String getZgzt() {
		return zgzt;
	}

	public void setZgzt(String zgzt) {
		this.zgzt = zgzt;
	}

	public String getZrbmid() {
		return zrbmid;
	}

	public void setZrbmid(String zrbmid) {
		this.zrbmid = zrbmid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getXfrid() {
		return xfrid;
	}

	public void setXfrid(String xfrid) {
		this.xfrid = xfrid;
	}

	public Date getXfsj() {
		return xfsj;
	}

	public void setXfsj(Date xfsj) {
		this.xfsj = xfsj;
	}

	public String getXfyy() {
		return xfyy;
	}

	public void setXfyy(String xfyy) {
		this.xfyy = xfyy;
	}

	public String getXffjid() {
		return xffjid;
	}

	public void setXffjid(String xffjid) {
		this.xffjid = xffjid;
	}

	public String getFkrid() {
		return fkrid;
	}

	public void setFkrid(String fkrid) {
		this.fkrid = fkrid;
	}

	public Date getFksj() {
		return fksj;
	}

	public void setFksj(Date fksj) {
		this.fksj = fksj;
	}

	public String getZgsm() {
		return zgsm;
	}

	public void setZgsm(String zgsm) {
		this.zgsm = zgsm;
	}

	public String getZgfjid() {
		return zgfjid;
	}

	public void setZgfjid(String zgfjid) {
		this.zgfjid = zgfjid;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getShsm() {
		return shsm;
	}

	public void setShsm(String shsm) {
		this.shsm = shsm;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public String getShfjid() {
		return shfjid;
	}

	public void setShfjid(String shfjid) {
		this.shfjid = shfjid;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy;
	}

	public Date getGbsj() {
		return gbsj;
	}

	public void setGbsj(Date gbsj) {
		this.gbsj = gbsj;
	}

	public String getGbfjid() {
		return gbfjid;
	}

	public void setGbfjid(String gbfjid) {
		this.gbfjid = gbfjid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
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

	public String getXfrname() {
		return xfrname;
	}

	public void setXfrname(String xfrname) {
		this.xfrname = xfrname;
	}

	public String getFkrname() {
		return fkrname;
	}

	public void setFkrname(String fkrname) {
		this.fkrname = fkrname;
	}

	public String getShrname() {
		return shrname;
	}

	public void setShrname(String shrname) {
		this.shrname = shrname;
	}

	public String getGbrname() {
		return gbrname;
	}

	public void setGbrname(String gbrname) {
		this.gbrname = gbrname;
	}

	public String getWhrname() {
		return whrname;
	}

	public void setWhrname(String whrname) {
		this.whrname = whrname;
	}

	public String getZrbmname() {
		return zrbmname;
	}

	public void setZrbmname(String zrbmname) {
		this.zrbmname = zrbmname;
	}

	public String getWhbmname() {
		return whbmname;
	}

	public void setWhbmname(String whbmname) {
		this.whbmname = whbmname;
	}

	public List<QualityReviewRecord> getQualityReviewRecordList() {
		return qualityReviewRecordList;
	}

	public void setQualityReviewRecordList(List<QualityReviewRecord> qualityReviewRecordList) {
		this.qualityReviewRecordList = qualityReviewRecordList;
	}

	public List<Attachment> getXfattachments() {
		return xfattachments;
	}

	public void setXfattachments(List<Attachment> xfattachments) {
		this.xfattachments = xfattachments;
	}

	public List<Attachment> getZgattachments() {
		return zgattachments;
	}

	public void setZgattachments(List<Attachment> zgattachments) {
		this.zgattachments = zgattachments;
	}

	public List<Attachment> getShattachments() {
		return shattachments;
	}

	public void setShattachments(List<Attachment> shattachments) {
		this.shattachments = shattachments;
	}

	public List<Attachment> getGbattachments() {
		return gbattachments;
	}

	public void setGbattachments(List<Attachment> gbattachments) {
		this.gbattachments = gbattachments;
	}
	
}
