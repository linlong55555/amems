package com.eray.thjw.project2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * 
 * @description B_G_015
 * 
 */
public class ShapeStructureRepair extends BizEntity {

	private String id;
	
	private String fjzch;
	
	private Integer sjlx;
	
	private String wxfaid;
	
	private String zjh;
	
	private String qxms;
	
	private String wz;
	
	private Date fxrq;
	
	private Date xlrq;
	
	private String xlyj;
	
	private Integer xlfs;
	
	private Integer is_gb;
	
	private Integer is_xlxjc;
	
	private String  lxjcjg;
	
	private Integer is_jrwxfq;
	
	private String xlqfj;
	
	private String xlhfj;
	
	private String xlyjfj;
	
	private String whbmid;
	
	private String whrid;
	
	private Date whsj;
	
	private Integer zt;
	
	private User whr;
	
	private Department whbmname;
	
	private List<Attachment> xlhAttachment;
	
	private List<Attachment> xlyjAttachment;
	
	private FixChapter fixChapter;
	
	private List<String> delAttachements;
	
	
	public String getXlyjfj() {
		return xlyjfj;
	}

	public void setXlyjfj(String xlyjfj) {
		this.xlyjfj = xlyjfj;
	}

	public List<Attachment> getXlyjAttachment() {
		return xlyjAttachment;
	}

	public void setXlyjAttachment(List<Attachment> xlyjAttachment) {
		this.xlyjAttachment = xlyjAttachment;
	}

	public List<String> getDelAttachements() {
		return delAttachements;
	}

	public void setDelAttachements(List<String> delAttachements) {
		this.delAttachements = delAttachements;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public Integer getSjlx() {
		return sjlx;
	}

	public void setSjlx(Integer sjlx) {
		this.sjlx = sjlx;
	}

	public String getWxfaid() {
		return wxfaid;
	}

	public void setWxfaid(String wxfaid) {
		this.wxfaid = wxfaid;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getQxms() {
		return qxms;
	}

	public void setQxms(String qxms) {
		this.qxms = qxms;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public Date getFxrq() {
		return fxrq;
	}

	public void setFxrq(Date fxrq) {
		this.fxrq = fxrq;
	}

	public Date getXlrq() {
		return xlrq;
	}

	public void setXlrq(Date xlrq) {
		this.xlrq = xlrq;
	}

	public String getXlyj() {
		return xlyj;
	}

	public void setXlyj(String xlyj) {
		this.xlyj = xlyj;
	}

	public Integer getXlfs() {
		return xlfs;
	}

	public void setXlfs(Integer xlfs) {
		this.xlfs = xlfs;
	}

	public Integer getIs_gb() {
		return is_gb;
	}

	public void setIs_gb(Integer is_gb) {
		this.is_gb = is_gb;
	}

	public Integer getIs_xlxjc() {
		return is_xlxjc;
	}

	public void setIs_xlxjc(Integer is_xlxjc) {
		this.is_xlxjc = is_xlxjc;
	}

	public String getLxjcjg() {
		return lxjcjg;
	}

	public void setLxjcjg(String lxjcjg) {
		this.lxjcjg = lxjcjg;
	}

	public Integer getIs_jrwxfq() {
		return is_jrwxfq;
	}

	public void setIs_jrwxfq(Integer is_jrwxfq) {
		this.is_jrwxfq = is_jrwxfq;
	}

	public String getXlqfj() {
		return xlqfj;
	}

	public void setXlqfj(String xlqfj) {
		this.xlqfj = xlqfj;
	}

	public String getXlhfj() {
		return xlhfj;
	}

	public void setXlhfj(String xlhfj) {
		this.xlhfj = xlhfj;
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

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getWhbmname() {
		return whbmname;
	}

	public void setWhbmname(Department whbmname) {
		this.whbmname = whbmname;
	}

	public List<Attachment> getXlhAttachment() {
		return xlhAttachment;
	}

	public void setXlhAttachment(List<Attachment> xlhAttachment) {
		this.xlhAttachment = xlhAttachment;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}
	
}
