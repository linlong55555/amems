package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BizEntity;

/**
 * @author liub
 * @description 课程管理B_P_003
 */
public class Course extends BizEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String kcbh;

    private String kcmc;

    private String kclb;

    private String kcnr;

    private String ks;

    private String pxxs;

    private String ksxs;

    private Integer isFx;

    private String fxks;

    private Integer zqz;

    private Integer zqdw;

    private String pxmb;

    private String bz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date whsj;
    
    private String fxpxxs;
    
    private String fxksxs;
    
    private String fjjx;
    
    private String jyyq;
    
    private String pxjg;
    
    private String ly;
    
    private String jctg;
    
    private BusinessToCourse businessToCourse;
    
    private List<PlanPerson> planPersons;
    
    private List<BusinessToCourse> businessToCourseList;
    

    public List<BusinessToCourse> getBusinessToCourseList() {
		return businessToCourseList;
	}

	public void setBusinessToCourseList(List<BusinessToCourse> businessToCourseList) {
		this.businessToCourseList = businessToCourseList;
	}

	public List<PlanPerson> getPlanPersons() {
		return planPersons;
	}

	public void setPlanPersons(List<PlanPerson> planPersons) {
		this.planPersons = planPersons;
	}

	public BusinessToCourse getBusinessToCourse() {
		return businessToCourse;
	}

	public void setBusinessToCourse(BusinessToCourse businessToCourse) {
		this.businessToCourse = businessToCourse;
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

    public String getKcbh() {
        return kcbh;
    }

    public void setKcbh(String kcbh) {
        this.kcbh = kcbh == null ? null : kcbh.trim();
    }

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc == null ? null : kcmc.trim();
    }

    public String getKclb() {
        return kclb;
    }

    public void setKclb(String kclb) {
        this.kclb = kclb == null ? null : kclb.trim();
    }

    public String getKcnr() {
        return kcnr;
    }

    public void setKcnr(String kcnr) {
        this.kcnr = kcnr == null ? null : kcnr.trim();
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks == null ? null : ks.trim();
    }

    public String getPxxs() {
        return pxxs;
    }

    public void setPxxs(String pxxs) {
        this.pxxs = pxxs == null ? null : pxxs.trim();
    }

    public String getKsxs() {
        return ksxs;
    }

    public void setKsxs(String ksxs) {
        this.ksxs = ksxs == null ? null : ksxs.trim();
    }

    public Integer getIsFx() {
        return isFx;
    }

    public void setIsFx(Integer isFx) {
        this.isFx = isFx;
    }

    public String getFxks() {
        return fxks;
    }

    public void setFxks(String fxks) {
        this.fxks = fxks == null ? null : fxks.trim();
    }

    public Integer getZqz() {
        return zqz;
    }

    public void setZqz(Integer zqz) {
        this.zqz = zqz;
    }

    public Integer getZqdw() {
        return zqdw;
    }

    public void setZqdw(Integer zqdw) {
        this.zqdw = zqdw;
    }

    public String getPxmb() {
        return pxmb;
    }

    public void setPxmb(String pxmb) {
        this.pxmb = pxmb == null ? null : pxmb.trim();
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

	public String getFxpxxs() {
		return fxpxxs;
	}

	public void setFxpxxs(String fxpxxs) {
		this.fxpxxs = fxpxxs;
	}

	public String getFxksxs() {
		return fxksxs;
	}

	public void setFxksxs(String fxksxs) {
		this.fxksxs = fxksxs;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getJyyq() {
		return jyyq;
	}

	public void setJyyq(String jyyq) {
		this.jyyq = jyyq;
	}

	public String getPxjg() {
		return pxjg;
	}

	public void setPxjg(String pxjg) {
		this.pxjg = pxjg;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getJctg() {
		return jctg;
	}

	public void setJctg(String jctg) {
		this.jctg = jctg;
	}
    
}