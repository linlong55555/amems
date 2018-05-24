package com.eray.thjw.training.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * @author liub
 * @description 培训计划B_P_002
 */
public class TrainingPlan extends BizEntity {
    /**
	 * S
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private Integer jhlx;

    private String pxlb;

    private String kcid;

    private Integer fxbs;

    private String ks;

    private String pxxs;

    private String ksxs;

    private String zrr;

    private String pxjg;

    private Integer xt;

    private String fjjx;

    private String zy;

    private Date jhKsrq;

    private String jhKssj;

    private Date jhJsrq;

    private String jhJssj;

    private Date sjKsrq;

    private String sjKssj;

    private Date sjJsrq;

    private String sjJssj;

    private String kcdd;

    private String kcnr;

    private String jsid;

    private String jsxm;

    private String pxdx;

    private BigDecimal pxys;

    private String pxysBz;

    private Integer jsBz;

    private Integer isJcff;

    private String sjks;

    private Integer ycrs;

    private Integer scrs;

    private String bz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;
    
    private String state;//标识
    
    private List<String> fjidlist;  //储存课程下面的附件
    /**
     * 课程
     */
    private Course course;
    
    /**
     * 用户
     */
    private User jsr;
    
    /**
     * 培训计划-培训课程人员表
     */
    private List<PlanPerson> planPersonList;
    
    private List<Map<String,String>> bt;
    
    private List<Map<String,String>> logo;
    
	public List<String> getFjidlist() {
		return fjidlist;
	}

	public void setFjidlist(List<String> fjidlist) {
		this.fjidlist = fjidlist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Map<String, String>> getBt() {
		return bt;
	}

	public void setBt(List<Map<String, String>> bt) {
		this.bt = bt;
	}

	public List<Map<String, String>> getLogo() {
		return logo;
	}

	public void setLogo(List<Map<String, String>> logo) {
		this.logo = logo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

    public Integer getJhlx() {
        return jhlx;
    }

    public void setJhlx(Integer jhlx) {
        this.jhlx = jhlx;
    }

    public String getPxlb() {
        return pxlb;
    }

    public void setPxlb(String pxlb) {
        this.pxlb = pxlb == null ? null : pxlb.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public Integer getFxbs() {
        return fxbs;
    }

    public void setFxbs(Integer fxbs) {
        this.fxbs = fxbs;
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

    public String getZrr() {
        return zrr;
    }

    public void setZrr(String zrr) {
        this.zrr = zrr == null ? null : zrr.trim();
    }

    public String getPxjg() {
        return pxjg;
    }

    public void setPxjg(String pxjg) {
        this.pxjg = pxjg == null ? null : pxjg.trim();
    }

    public Integer getXt() {
        return xt;
    }

    public void setXt(Integer xt) {
        this.xt = xt;
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public Date getJhKsrq() {
        return jhKsrq;
    }

    public void setJhKsrq(Date jhKsrq) {
        this.jhKsrq = jhKsrq;
    }

    public String getJhKssj() {
        return jhKssj;
    }

    public void setJhKssj(String jhKssj) {
        this.jhKssj = jhKssj == null ? null : jhKssj.trim();
    }

    public Date getJhJsrq() {
        return jhJsrq;
    }

    public void setJhJsrq(Date jhJsrq) {
        this.jhJsrq = jhJsrq;
    }

    public String getJhJssj() {
        return jhJssj;
    }

    public void setJhJssj(String jhJssj) {
        this.jhJssj = jhJssj == null ? null : jhJssj.trim();
    }

    public Date getSjKsrq() {
        return sjKsrq;
    }

    public void setSjKsrq(Date sjKsrq) {
        this.sjKsrq = sjKsrq;
    }

    public String getSjKssj() {
        return sjKssj;
    }

    public void setSjKssj(String sjKssj) {
        this.sjKssj = sjKssj == null ? null : sjKssj.trim();
    }

    public Date getSjJsrq() {
        return sjJsrq;
    }

    public void setSjJsrq(Date sjJsrq) {
        this.sjJsrq = sjJsrq;
    }

    public String getSjJssj() {
        return sjJssj;
    }

    public void setSjJssj(String sjJssj) {
        this.sjJssj = sjJssj == null ? null : sjJssj.trim();
    }

    public String getKcdd() {
        return kcdd;
    }

    public void setKcdd(String kcdd) {
        this.kcdd = kcdd == null ? null : kcdd.trim();
    }

    public String getKcnr() {
        return kcnr;
    }

    public void setKcnr(String kcnr) {
        this.kcnr = kcnr == null ? null : kcnr.trim();
    }

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid == null ? null : jsid.trim();
    }

    public String getJsxm() {
        return jsxm;
    }

    public void setJsxm(String jsxm) {
        this.jsxm = jsxm == null ? null : jsxm.trim();
    }

    public String getPxdx() {
        return pxdx;
    }

    public void setPxdx(String pxdx) {
        this.pxdx = pxdx == null ? null : pxdx.trim();
    }

    public BigDecimal getPxys() {
        return pxys;
    }

    public void setPxys(BigDecimal pxys) {
        this.pxys = pxys;
    }

    public String getPxysBz() {
        return pxysBz;
    }

    public void setPxysBz(String pxysBz) {
        this.pxysBz = pxysBz == null ? null : pxysBz.trim();
    }

    public Integer getJsBz() {
        return jsBz;
    }

    public void setJsBz(Integer jsBz) {
        this.jsBz = jsBz;
    }

    public Integer getIsJcff() {
        return isJcff;
    }

    public void setIsJcff(Integer isJcff) {
        this.isJcff = isJcff;
    }

    public String getSjks() {
        return sjks;
    }

    public void setSjks(String sjks) {
        this.sjks = sjks == null ? null : sjks.trim();
    }

    public Integer getYcrs() {
        return ycrs;
    }

    public void setYcrs(Integer ycrs) {
        this.ycrs = ycrs;
    }

    public Integer getScrs() {
        return scrs;
    }

    public void setScrs(Integer scrs) {
        this.scrs = scrs;
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

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
    }

    public Date getZdjsrq() {
        return zdjsrq;
    }

    public void setZdjsrq(Date zdjsrq) {
        this.zdjsrq = zdjsrq;
    }

    public String getZdjsyy() {
        return zdjsyy;
    }

    public void setZdjsyy(String zdjsyy) {
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<PlanPerson> getPlanPersonList() {
		return planPersonList;
	}

	public void setPlanPersonList(List<PlanPerson> planPersonList) {
		this.planPersonList = planPersonList;
	}

	public User getJsr() {
		return jsr;
	}

	public void setJsr(User jsr) {
		this.jsr = jsr;
	}
    
}