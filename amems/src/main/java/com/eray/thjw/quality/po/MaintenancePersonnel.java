package com.eray.thjw.quality.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.PlanPerson;

/**
 * b_z_001 维修人员档案主表
 * @author xu.yong
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintenancePersonnel extends BizEntity {
	
	private String id;

    private String dprtcode;

    private String rybh;

    private Integer wbbs;

    private String wxryid;

    private String xm;

    private Integer xb;

    private String szdw;

    private Date sr;

    private String jg;

    private String zzmm;

    private String sfz;

    private String yzbm;

    private String lxdh;

    private String yxdz;

    private String dz;

    private Integer isJh;

    private String jtcy;

    private String xl;

    private Date cjgzrq;

    private Date rzrq;

    private String rzqxx;

    private String daszdw;

    private String dabh;

    private Integer zzzt;

    private Date lzrq;

    private BigDecimal cxfz;

    private String cxbz;

    private String zpdzD;

    private String zpdzX;

    private String bz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String wxrbmid;
    
    private String UserName;
    
    private String sqgwid;
    
    private String sqid;
    
    /** 教育经历 */
    private List<PersonnelToEducationExperience> educations;
    
    /** 外语水平 */
    private List<PersonnelToForeignLanguage> languages;
    
    /** 聘任职称 */
    private List<PersonnelToProfessionalTitle> titles;
    
    /** 工作履历 */
    private List<PersonnelToWorkExperience> workExperiences;
    
    /** 岗位职务 */
    private List<PersonnelToPost> posts;
    
    /** 技术履历 */
    private List<PersonnelToTechnicalExperience> technicalExperiences;
    
    /** 维修执照 */
    private List<PersonnelToAuthorizationRecord> maintenanceLicenses;
    
    /** 机型执照 */
    private List<PersonnelToAuthorizationRecord> typeLicenses;
    
    /** 维修技术等级升级记录 */
    private List<PersonnelToTechnicalGradeRecord> grades;
    
    /** 培训记录 */
    private List<PlanPerson> trainings;
    
    /** 业务考核记录 */
    private List<PersonnelToBusinessAssessment> businessAssessments;
    
    /** 学术成就 */
    private List<PersonnelToAchievement> scholarships;
    
    /** 嘉奖记录 */
    private List<PersonnelToAchievement> citationRecords;
    
    /** 事故征候情况 */
    private List<PersonnelToCreditRecord> incidentSituations;
    
    /** 负有责任的不安全事件 */
    private List<PersonnelToCreditRecord> unsafeIncidents;
    
    /** 不诚信行为 */
    private List<PersonnelToCreditRecord> dishonestBehaviors;
    
    /** 需删除附件 */
    private List<String> delAttachments;
    
    /** 岗位 */
    private List<Business> businesses;
    
    /**用户*/
    private User user;
    
    private Department jg_dprt;
    
    public String getSqid() {
		return sqid;
	}

	public void setSqid(String sqid) {
		this.sqid = sqid;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }

    public Integer getWbbs() {
        return wbbs;
    }

    public void setWbbs(Integer wbbs) {
        this.wbbs = wbbs;
    }

    public String getSzdw() {
        return szdw;
    }

    public String getSqgwid() {
		return sqgwid;
	}

	public void setSqgwid(String sqgwid) {
		this.sqgwid = sqgwid;
	}

	public void setSzdw(String szdw) {
        this.szdw = szdw == null ? null : szdw.trim();
    }

    public String getWxryid() {
        return wxryid;
    }

    public void setWxryid(String wxryid) {
        this.wxryid = wxryid == null ? null : wxryid.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public Integer getXb() {
        return xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }


    public Date getSr() {
        return sr;
    }

    public void setSr(Date sr) {
        this.sr = sr;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg == null ? null : jg.trim();
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm == null ? null : zzmm.trim();
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz == null ? null : sfz.trim();
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm == null ? null : yzbm.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public String getYxdz() {
        return yxdz;
    }

    public void setYxdz(String yxdz) {
        this.yxdz = yxdz == null ? null : yxdz.trim();
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz == null ? null : dz.trim();
    }

    public Integer getIsJh() {
        return isJh;
    }

    public void setIsJh(Integer isJh) {
        this.isJh = isJh;
    }

    public String getJtcy() {
        return jtcy;
    }

    public void setJtcy(String jtcy) {
        this.jtcy = jtcy == null ? null : jtcy.trim();
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl == null ? null : xl.trim();
    }

    public Date getCjgzrq() {
        return cjgzrq;
    }

    public void setCjgzrq(Date cjgzrq) {
        this.cjgzrq = cjgzrq;
    }

    public Date getRzrq() {
        return rzrq;
    }

    public void setRzrq(Date rzrq) {
        this.rzrq = rzrq;
    }

    public String getRzqxx() {
        return rzqxx;
    }

    public void setRzqxx(String rzqxx) {
        this.rzqxx = rzqxx == null ? null : rzqxx.trim();
    }

    public String getDaszdw() {
        return daszdw;
    }

    public void setDaszdw(String daszdw) {
        this.daszdw = daszdw == null ? null : daszdw.trim();
    }

    public String getDabh() {
        return dabh;
    }

    public void setDabh(String dabh) {
        this.dabh = dabh == null ? null : dabh.trim();
    }

    public Integer getZzzt() {
        return zzzt;
    }

    public void setZzzt(Integer zzzt) {
        this.zzzt = zzzt;
    }

    public Date getLzrq() {
        return lzrq;
    }

    public void setLzrq(Date lzrq) {
        this.lzrq = lzrq;
    }

    public BigDecimal getCxfz() {
        return cxfz;
    }

    public void setCxfz(BigDecimal cxfz) {
        this.cxfz = cxfz;
    }

    public String getCxbz() {
        return cxbz;
    }

    public void setCxbz(String cxbz) {
        this.cxbz = cxbz == null ? null : cxbz.trim();
    }

    public String getZpdzD() {
        return zpdzD;
    }

    public void setZpdzD(String zpdzD) {
        this.zpdzD = zpdzD == null ? null : zpdzD.trim();
    }

    public String getZpdzX() {
        return zpdzX;
    }

    public void setZpdzX(String zpdzX) {
        this.zpdzX = zpdzX == null ? null : zpdzX.trim();
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

	public List<PersonnelToEducationExperience> getEducations() {
		return educations;
	}

	public void setEducations(List<PersonnelToEducationExperience> educations) {
		this.educations = educations;
	}

	public List<PersonnelToForeignLanguage> getLanguages() {
		return languages;
	}

	public void setLanguages(List<PersonnelToForeignLanguage> languages) {
		this.languages = languages;
	}

	public List<PersonnelToProfessionalTitle> getTitles() {
		return titles;
	}

	public void setTitles(List<PersonnelToProfessionalTitle> titles) {
		this.titles = titles;
	}

	public List<PersonnelToWorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<PersonnelToWorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

	public List<String> getDelAttachments() {
		return delAttachments;
	}

	public void setDelAttachments(List<String> delAttachments) {
		this.delAttachments = delAttachments;
	}

	public List<PersonnelToPost> getPosts() {
		return posts;
	}

	public void setPosts(List<PersonnelToPost> posts) {
		this.posts = posts;
	}

	public List<PersonnelToTechnicalExperience> getTechnicalExperiences() {
		return technicalExperiences;
	}

	public void setTechnicalExperiences(
			List<PersonnelToTechnicalExperience> technicalExperiences) {
		this.technicalExperiences = technicalExperiences;
	}

	public List<PersonnelToAuthorizationRecord> getMaintenanceLicenses() {
		return maintenanceLicenses;
	}

	public void setMaintenanceLicenses(
			List<PersonnelToAuthorizationRecord> maintenanceLicenses) {
		this.maintenanceLicenses = maintenanceLicenses;
	}

	public List<PersonnelToAuthorizationRecord> getTypeLicenses() {
		return typeLicenses;
	}

	public void setTypeLicenses(List<PersonnelToAuthorizationRecord> typeLicenses) {
		this.typeLicenses = typeLicenses;
	}

	public List<PersonnelToTechnicalGradeRecord> getGrades() {
		return grades;
	}

	public void setGrades(List<PersonnelToTechnicalGradeRecord> grades) {
		this.grades = grades;
	}

	public List<PlanPerson> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<PlanPerson> trainings) {
		this.trainings = trainings;
	}

	public List<PersonnelToBusinessAssessment> getBusinessAssessments() {
		return businessAssessments;
	}

	public void setBusinessAssessments(
			List<PersonnelToBusinessAssessment> businessAssessments) {
		this.businessAssessments = businessAssessments;
	}

	public List<PersonnelToAchievement> getScholarships() {
		return scholarships;
	}

	public void setScholarships(List<PersonnelToAchievement> scholarships) {
		this.scholarships = scholarships;
	}

	public List<PersonnelToAchievement> getCitationRecords() {
		return citationRecords;
	}

	public void setCitationRecords(List<PersonnelToAchievement> citationRecords) {
		this.citationRecords = citationRecords;
	}

	public List<PersonnelToCreditRecord> getIncidentSituations() {
		return incidentSituations;
	}

	public void setIncidentSituations(
			List<PersonnelToCreditRecord> incidentSituations) {
		this.incidentSituations = incidentSituations;
	}

	public List<PersonnelToCreditRecord> getUnsafeIncidents() {
		return unsafeIncidents;
	}

	public void setUnsafeIncidents(List<PersonnelToCreditRecord> unsafeIncidents) {
		this.unsafeIncidents = unsafeIncidents;
	}

	public List<PersonnelToCreditRecord> getDishonestBehaviors() {
		return dishonestBehaviors;
	}

	public void setDishonestBehaviors(
			List<PersonnelToCreditRecord> dishonestBehaviors) {
		this.dishonestBehaviors = dishonestBehaviors;
	}

	public String getWxrbmid() {
		return wxrbmid;
	}

	public void setWxrbmid(String wxrbmid) {
		this.wxrbmid = wxrbmid;
	}

	public List<Business> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}

}