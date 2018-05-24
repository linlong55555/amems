package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * 
 * @Description b_g2_001 技术评估单
 * @CreateTime 2017年8月24日 下午10:09:57
 * @CreateBy 林龙
 */
public class Technical extends BizEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String dprtcode;

	private String pgdh;

	private BigDecimal bb;

	private Integer lylx;

	private String lysm;

	private String jswjid;

	private String glpgdid;

	private String jx;

	private Date pgqx;

	private String zjh;

	private String pgdzt;

	private String sxyq;

	private String bz;

	private Integer zxbs;

	private String fBbid;

	private String bBbid;

	private Integer zt;

	private String pgbmid;

	private String pgrid;

	private Date pgsj;

	private String shbmid;

	private String shrid;

	private Date shsj;

	private String shyj;

	private String pfbmid;

	private String pfrid;

	private Date pfsj;

	private String pfyj;

	private String gbrid;

	private Date gbrq;

	private String gbyy;

	private Integer pfjl;

	private Integer shjl;
	
	private Date yjbfzlsj;
	
	private String yjbfzl;
	
	private TechnicalAttached technicalAttached; //b_g2_00100 技术评估单-附加信息
	
	private Airworthiness airworthiness; //b_g2_000 适航性资料
	
	private List<GiveInstruction> giveInstructionList; //b_g2_00101 技术评估单-下达指令List集合
	
	private List<Reference> referenceList; //B_G2_999 相关参考文件List集合
	
	private List<Attachment> attachmentList; //d_011 附件表List
	
	private List<DistributionDepartment> distributionDepartmentList; //b_g2_998 分发部门或用户List
	
	private FixChapter fixChapter; //d_005 ATA章节
	
	private User pgr;//评估人
	
	private User shr;//审核人
	
	private User pzr;//批准人
	
	private User gbr;//关闭人
	
	private Department jg_dprt;//组织机构
	
    private Attachment assessmentAttachment;//评估单结论附件
    
    private Attachment airworthinessAttachment;//适航性资料附件
    
    /**b_g2_00102 技术评估单-适用性 **/
    private List<TEApplicability> syxszList;
    
    public List<TEApplicability> getSyxszList() {
		return syxszList;
	}

	public void setSyxszList(List<TEApplicability> syxszList) {
		this.syxszList = syxszList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getYjbfzl() {
		return yjbfzl;
	}

	public void setYjbfzl(String yjbfzl) {
		this.yjbfzl = yjbfzl;
	}

	public Attachment getAirworthinessAttachment() {
		return airworthinessAttachment;
	}

	public void setAirworthinessAttachment(Attachment airworthinessAttachment) {
		this.airworthinessAttachment = airworthinessAttachment;
	}

	private String deleteUploadId;//删除附件id
    
	public String getDeleteUploadId() {
		return deleteUploadId;
	}

	public void setDeleteUploadId(String deleteUploadId) {
		this.deleteUploadId = deleteUploadId;
	}

	public Date getYjbfzlsj() {
		return yjbfzlsj;
	}

	public void setYjbfzlsj(Date yjbfzlsj) {
		this.yjbfzlsj = yjbfzlsj;
	}

	public Attachment getAssessmentAttachment() {
		return assessmentAttachment;
	}

	public void setAssessmentAttachment(Attachment assessmentAttachment) {
		this.assessmentAttachment = assessmentAttachment;
	}

	public User getGbr() {
		return gbr;
	}

	public void setGbr(User gbr) {
		this.gbr = gbr;
	}

	public List<DistributionDepartment> getDistributionDepartmentList() {
		return distributionDepartmentList;
	}

	public void setDistributionDepartmentList(
			List<DistributionDepartment> distributionDepartmentList) {
		this.distributionDepartmentList = distributionDepartmentList;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<GiveInstruction> getGiveInstructionList() {
		return giveInstructionList;
	}

	public void setGiveInstructionList(List<GiveInstruction> giveInstructionList) {
		this.giveInstructionList = giveInstructionList;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public List<Reference> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	public User getPgr() {
		return pgr;
	}

	public void setPgr(User pgr) {
		this.pgr = pgr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public User getPzr() {
		return pzr;
	}

	public void setPzr(User pzr) {
		this.pzr = pzr;
	}

	public Airworthiness getAirworthiness() {
		return airworthiness;
	}

	public void setAirworthiness(Airworthiness airworthiness) {
		this.airworthiness = airworthiness;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public TechnicalAttached getTechnicalAttached() {
		return technicalAttached;
	}

	public void setTechnicalAttached(TechnicalAttached technicalAttached) {
		this.technicalAttached = technicalAttached;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh == null ? null : pgdh.trim();
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public String getLysm() {
		return lysm;
	}

	public void setLysm(String lysm) {
		this.lysm = lysm == null ? null : lysm.trim();
	}

	public String getJswjid() {
		return jswjid;
	}

	public void setJswjid(String jswjid) {
		this.jswjid = jswjid == null ? null : jswjid.trim();
	}

	public String getGlpgdid() {
		return glpgdid;
	}

	public void setGlpgdid(String glpgdid) {
		this.glpgdid = glpgdid == null ? null : glpgdid.trim();
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx == null ? null : jx.trim();
	}

	public Date getPgqx() {
		return pgqx;
	}

	public void setPgqx(Date pgqx) {
		this.pgqx = pgqx;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh == null ? null : zjh.trim();
	}

	public String getPgdzt() {
		return pgdzt;
	}

	public void setPgdzt(String pgdzt) {
		this.pgdzt = pgdzt == null ? null : pgdzt.trim();
	}

	public String getSxyq() {
		return sxyq;
	}

	public void setSxyq(String sxyq) {
		this.sxyq = sxyq == null ? null : sxyq.trim();
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public String getfBbid() {
		return fBbid;
	}

	public void setfBbid(String fBbid) {
		this.fBbid = fBbid == null ? null : fBbid.trim();
	}

	public String getbBbid() {
		return bBbid;
	}

	public void setbBbid(String bBbid) {
		this.bBbid = bBbid == null ? null : bBbid.trim();
	}

	public String getPgbmid() {
		return pgbmid;
	}

	public void setPgbmid(String pgbmid) {
		this.pgbmid = pgbmid == null ? null : pgbmid.trim();
	}

	public String getPgrid() {
		return pgrid;
	}

	public void setPgrid(String pgrid) {
		this.pgrid = pgrid == null ? null : pgrid.trim();
	}

	public Date getPgsj() {
		return pgsj;
	}

	public void setPgsj(Date pgsj) {
		this.pgsj = pgsj;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid == null ? null : shbmid.trim();
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid == null ? null : shrid.trim();
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj == null ? null : shyj.trim();
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid == null ? null : pfbmid.trim();
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid == null ? null : pfrid.trim();
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj == null ? null : pfyj.trim();
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid == null ? null : gbrid.trim();
	}

	public Date getGbrq() {
		return gbrq;
	}

	public void setGbrq(Date gbrq) {
		this.gbrq = gbrq;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy == null ? null : gbyy.trim();
	}

	public Integer getLylx() {
		return lylx;
	}

	public void setLylx(Integer lylx) {
		this.lylx = lylx;
	}

	public Integer getZxbs() {
		return zxbs;
	}

	public void setZxbs(Integer zxbs) {
		this.zxbs = zxbs;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getPfjl() {
		return pfjl;
	}

	public void setPfjl(Integer pfjl) {
		this.pfjl = pfjl;
	}

	public Integer getShjl() {
		return shjl;
	}

	public void setShjl(Integer shjl) {
		this.shjl = shjl;
	}

}