package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.system.po.WorkGroup;

/**
 * 
 * @Description B_G2_013 工卡
 * @CreateTime 2017-8-14 下午4:02:52
 * @CreateBy 刘兵
 */
public class WorkCard extends BaseEntity {
    private String id;

    private String dprtcode;

    private String gkh;

    private BigDecimal bb;

    private String jx;

    private Integer isBj;

    private String zjh;

    private String wxxmbh;

    private String rwdh;

    private String kzh;

    private String cmph;

    private String gzdh;

    private String gkfjid;

    private String gzbt;

    private String gzlx;
    
    private String gklx;

    private String zy;

    private String gzzid;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private String yjwj;

    private String tbsysm;

    private String gzgs;

    private String gznrfjid;

    private String bz;

    private Integer zt;

    private Integer zxbs;

    private String fBbid;

    private String bBbid;

    private String whbmid;

    private String whrid;

    private Date whsj;

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
    
    private Integer worktype;
    
    /** 用户 */
    private User zdr;
    
    /** 审核人 */
    private User shr;
    
    /** 批准人 */
    private User pfr;
    
    /** 维修项目 */
    private MaintenanceProject maintenanceProject;
    
    /** 章节号 */
    private FixChapter fixChapter;
    
    /** 工作组 */
    private WorkGroup workGroup;
    
    /** 接近/盖板 */
    private List<CoverPlate> coverPlateList;
    
    /** 工种工时 */
    private List<WorkHour> workHourList;
    
    /** 下达指令来源 **/
    private List<Instructionsource> instructionsourceList;
    
    /** 技术评估单 **/
    private List<Technical> technicalList;
    
    /** 相关参考文件 */
    private List<Reference> referenceList;
    
    /** 器材/工具 */
    private List<MaterialTool> materialToolList;
    
    /** 工作内容 */
    private List<WorkContent> workContentList;
    
    /** 工卡-关联工卡 */
    private List<WorkCard2Related> workCard2RelatedList;
    
    /** 工卡-适用单位 */
    private List<ApplicableUnit> applicableUnitList;
    
    /** 工作内容附件 */
    private Attachment workContentAttachment;
    
    /** 工卡附件 */
    private Attachment workCardAttachment;
    
    /** 其它附件 */
    private List<Attachment> attachmentList;

    public Integer getWorktype() {
		return worktype;
	}

	public void setWorktype(Integer worktype) {
		this.worktype = worktype;
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

    public String getGkh() {
        return gkh;
    }

    public void setGkh(String gkh) {
        this.gkh = gkh == null ? null : gkh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public Integer getIsBj() {
        return isBj;
    }

    public void setIsBj(Integer isBj) {
        this.isBj = isBj;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getWxxmbh() {
        return wxxmbh;
    }

    public void setWxxmbh(String wxxmbh) {
        this.wxxmbh = wxxmbh == null ? null : wxxmbh.trim();
    }

    public String getRwdh() {
        return rwdh;
    }

    public void setRwdh(String rwdh) {
        this.rwdh = rwdh == null ? null : rwdh.trim();
    }

    public String getKzh() {
        return kzh;
    }

    public void setKzh(String kzh) {
        this.kzh = kzh == null ? null : kzh.trim();
    }

    public String getCmph() {
        return cmph;
    }

    public void setCmph(String cmph) {
        this.cmph = cmph == null ? null : cmph.trim();
    }

    public String getGzdh() {
        return gzdh;
    }

    public void setGzdh(String gzdh) {
        this.gzdh = gzdh == null ? null : gzdh.trim();
    }

    public String getGkfjid() {
        return gkfjid;
    }

    public void setGkfjid(String gkfjid) {
        this.gkfjid = gkfjid == null ? null : gkfjid.trim();
    }

    public String getGzbt() {
        return gzbt;
    }

    public void setGzbt(String gzbt) {
        this.gzbt = gzbt == null ? null : gzbt.trim();
    }

    public String getGzlx() {
        return gzlx;
    }

    public void setGzlx(String gzlx) {
        this.gzlx = gzlx == null ? null : gzlx.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public BigDecimal getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public String getYjwj() {
        return yjwj;
    }

    public void setYjwj(String yjwj) {
        this.yjwj = yjwj == null ? null : yjwj.trim();
    }

    public String getTbsysm() {
        return tbsysm;
    }

    public void setTbsysm(String tbsysm) {
        this.tbsysm = tbsysm == null ? null : tbsysm.trim();
    }

    public String getGzgs() {
        return gzgs;
    }

    public void setGzgs(String gzgs) {
        this.gzgs = gzgs == null ? null : gzgs.trim();
    }

    public String getGznrfjid() {
        return gznrfjid;
    }

    public void setGznrfjid(String gznrfjid) {
        this.gznrfjid = gznrfjid == null ? null : gznrfjid.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getZxbs() {
        return zxbs;
    }

    public void setZxbs(Integer zxbs) {
        this.zxbs = zxbs;
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

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
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

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public User getPfr() {
		return pfr;
	}

	public void setPfr(User pfr) {
		this.pfr = pfr;
	}

	public MaintenanceProject getMaintenanceProject() {
		return maintenanceProject;
	}

	public void setMaintenanceProject(MaintenanceProject maintenanceProject) {
		this.maintenanceProject = maintenanceProject;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public List<CoverPlate> getCoverPlateList() {
		return coverPlateList;
	}

	public void setCoverPlateList(List<CoverPlate> coverPlateList) {
		this.coverPlateList = coverPlateList;
	}

	public List<WorkHour> getWorkHourList() {
		return workHourList;
	}

	public void setWorkHourList(List<WorkHour> workHourList) {
		this.workHourList = workHourList;
	}

	public String getGklx() {
		return gklx;
	}

	public void setGklx(String gklx) {
		this.gklx = gklx;
	}

	public WorkGroup getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(WorkGroup workGroup) {
		this.workGroup = workGroup;
	}

	public List<Instructionsource> getInstructionsourceList() {
		return instructionsourceList;
	}

	public void setInstructionsourceList(
			List<Instructionsource> instructionsourceList) {
		this.instructionsourceList = instructionsourceList;
	}

	public List<Technical> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<Technical> technicalList) {
		this.technicalList = technicalList;
	}

	public List<Reference> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	public List<MaterialTool> getMaterialToolList() {
		return materialToolList;
	}

	public void setMaterialToolList(List<MaterialTool> materialToolList) {
		this.materialToolList = materialToolList;
	}

	public List<WorkContent> getWorkContentList() {
		return workContentList;
	}

	public void setWorkContentList(List<WorkContent> workContentList) {
		this.workContentList = workContentList;
	}

	public List<WorkCard2Related> getWorkCard2RelatedList() {
		return workCard2RelatedList;
	}

	public void setWorkCard2RelatedList(List<WorkCard2Related> workCard2RelatedList) {
		this.workCard2RelatedList = workCard2RelatedList;
	}

	public Attachment getWorkContentAttachment() {
		return workContentAttachment;
	}

	public void setWorkContentAttachment(Attachment workContentAttachment) {
		this.workContentAttachment = workContentAttachment;
	}

	public Attachment getWorkCardAttachment() {
		return workCardAttachment;
	}

	public void setWorkCardAttachment(Attachment workCardAttachment) {
		this.workCardAttachment = workCardAttachment;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<ApplicableUnit> getApplicableUnitList() {
		return applicableUnitList;
	}

	public void setApplicableUnitList(List<ApplicableUnit> applicableUnitList) {
		this.applicableUnitList = applicableUnitList;
	}

	@Override
	public String toString() {
		return "WorkCard [id=" + id + ", dprtcode=" + dprtcode + ", gkh=" + gkh
				+ ", bb=" + bb + ", jx=" + jx + ", isBj=" + isBj + ", zjh="
				+ zjh + ", wxxmbh=" + wxxmbh + ", rwdh=" + rwdh + ", kzh="
				+ kzh + ", cmph=" + cmph + ", gzdh=" + gzdh + ", gkfjid="
				+ gkfjid + ", gzbt=" + gzbt + ", gzlx=" + gzlx + ", gklx="
				+ gklx + ", zy=" + zy + ", gzzid=" + gzzid + ", jhgsRs="
				+ jhgsRs + ", jhgsXss=" + jhgsXss + ", yjwj=" + yjwj
				+ ", tbsysm=" + tbsysm + ", gzgs=" + gzgs + ", gznrfjid="
				+ gznrfjid + ", bz=" + bz + ", zt=" + zt + ", zxbs=" + zxbs
				+ ", fBbid=" + fBbid + ", bBbid=" + bBbid + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj + ", shbmid="
				+ shbmid + ", shrid=" + shrid + ", shsj=" + shsj + ", shyj="
				+ shyj + ", pfbmid=" + pfbmid + ", pfrid=" + pfrid + ", pfsj="
				+ pfsj + ", pfyj=" + pfyj + ", gbrid=" + gbrid + ", gbrq="
				+ gbrq + ", gbyy=" + gbyy + ", pfjl=" + pfjl + ", shjl=" + shjl
				+ ", zdr=" + zdr + ", shr=" + shr + ", pfr=" + pfr
				+ ", maintenanceProject=" + maintenanceProject
				+ ", fixChapter=" + fixChapter + ", workGroup=" + workGroup
				+ ", coverPlateList=" + coverPlateList + ", workHourList="
				+ workHourList + ", instructionsourceList="
				+ instructionsourceList + ", technicalList=" + technicalList
				+ ", referenceList=" + referenceList + ", materialToolList="
				+ materialToolList + ", workContentList=" + workContentList
				+ ", workCard2RelatedList=" + workCard2RelatedList
				+ ", applicableUnitList=" + applicableUnitList
				+ ", workContentAttachment=" + workContentAttachment
				+ ", workCardAttachment=" + workCardAttachment
				+ ", attachmentList=" + attachmentList + ", pagination="
				+ pagination + ", getId()=" + getId() + ", getDprtcode()="
				+ getDprtcode() + ", getGkh()=" + getGkh() + ", getBb()="
				+ getBb() + ", getJx()=" + getJx() + ", getIsBj()=" + getIsBj()
				+ ", getZjh()=" + getZjh() + ", getWxxmbh()=" + getWxxmbh()
				+ ", getRwdh()=" + getRwdh() + ", getKzh()=" + getKzh()
				+ ", getCmph()=" + getCmph() + ", getGzdh()=" + getGzdh()
				+ ", getGkfjid()=" + getGkfjid() + ", getGzbt()=" + getGzbt()
				+ ", getGzlx()=" + getGzlx() + ", getZy()=" + getZy()
				+ ", getGzzid()=" + getGzzid() + ", getJhgsRs()=" + getJhgsRs()
				+ ", getJhgsXss()=" + getJhgsXss() + ", getYjwj()=" + getYjwj()
				+ ", getTbsysm()=" + getTbsysm() + ", getGzgs()=" + getGzgs()
				+ ", getGznrfjid()=" + getGznrfjid() + ", getBz()=" + getBz()
				+ ", getZt()=" + getZt() + ", getZxbs()=" + getZxbs()
				+ ", getfBbid()=" + getfBbid() + ", getbBbid()=" + getbBbid()
				+ ", getWhbmid()=" + getWhbmid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getShbmid()=" + getShbmid()
				+ ", getShrid()=" + getShrid() + ", getShsj()=" + getShsj()
				+ ", getShyj()=" + getShyj() + ", getPfbmid()=" + getPfbmid()
				+ ", getPfrid()=" + getPfrid() + ", getPfsj()=" + getPfsj()
				+ ", getPfyj()=" + getPfyj() + ", getGbrid()=" + getGbrid()
				+ ", getGbrq()=" + getGbrq() + ", getGbyy()=" + getGbyy()
				+ ", getPfjl()=" + getPfjl() + ", getShjl()=" + getShjl()
				+ ", getZdr()=" + getZdr() + ", getShr()=" + getShr()
				+ ", getPfr()=" + getPfr() + ", getMaintenanceProject()="
				+ getMaintenanceProject() + ", getFixChapter()="
				+ getFixChapter() + ", getCoverPlateList()="
				+ getCoverPlateList() + ", getWorkHourList()="
				+ getWorkHourList() + ", getGklx()=" + getGklx()
				+ ", getWorkGroup()=" + getWorkGroup()
				+ ", getInstructionsourceList()=" + getInstructionsourceList()
				+ ", getTechnicalList()=" + getTechnicalList()
				+ ", getReferenceList()=" + getReferenceList()
				+ ", getMaterialToolList()=" + getMaterialToolList()
				+ ", getWorkContentList()=" + getWorkContentList()
				+ ", getWorkCard2RelatedList()=" + getWorkCard2RelatedList()
				+ ", getWorkContentAttachment()=" + getWorkContentAttachment()
				+ ", getWorkCardAttachment()=" + getWorkCardAttachment()
				+ ", getAttachmentList()=" + getAttachmentList()
				+ ", getApplicableUnitList()=" + getApplicableUnitList()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}



}