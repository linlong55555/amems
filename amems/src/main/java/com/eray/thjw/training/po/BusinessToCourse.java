package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

public class BusinessToCourse extends BizEntity {
    private String id;

    private String gwid;

    private String kcid;

    private String jyyq;

    private String pxjg;

    private String ly;

    private String jctg;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private String kcmc;
    
    private Date whsj;
    
    private List<String> courseIds;
    
    private Course course;
    
    private List<PersonnelTrainingRecently> personnelTrainingRecentlys;

    public String getKcmc() {
		return kcmc;
	}

	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}

	public List<PersonnelTrainingRecently> getPersonnelTrainingRecentlys() {
		return personnelTrainingRecentlys;
	}

	public void setPersonnelTrainingRecentlys(
			List<PersonnelTrainingRecently> personnelTrainingRecentlys) {
		this.personnelTrainingRecentlys = personnelTrainingRecentlys;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<String> getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(List<String> courseIds) {
		this.courseIds = courseIds;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGwid() {
        return gwid;
    }

    public void setGwid(String gwid) {
        this.gwid = gwid == null ? null : gwid.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public String getJyyq() {
        return jyyq;
    }

    public void setJyyq(String jyyq) {
        this.jyyq = jyyq == null ? null : jyyq.trim();
    }

    public String getPxjg() {
        return pxjg;
    }

    public void setPxjg(String pxjg) {
        this.pxjg = pxjg == null ? null : pxjg.trim();
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly == null ? null : ly.trim();
    }

    public String getJctg() {
        return jctg;
    }

    public void setJctg(String jctg) {
        this.jctg = jctg == null ? null : jctg.trim();
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
}