package com.eray.thjw.productionplan.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.FixChapter;
/**
 * @author liub
 * @description 故障总结B_S_015
 * @develop date 2017.01.03
 */
public class MaintenanceFailureSummary extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer kjfw;

    private String gjc;

    private String gzms;

    private String jyzj;

    private String fjjx;

    private String fjzch;

    private String jd;

    private String zjh;

    private String zy;

    private String bjh;

    private Integer zt;

    private String zddwid;

    private String zdrid;

    private Date zdsj;
    
    private String summaryDetailJson; 
    
    private String attachmentsJson; 
    
    private String jgslstr; 
    
    private List<String> detailIdList;
    
    private List<MaintenanceFailureSummaryDetail> summaryDetailList;
    
    private FixChapter fixChapter;

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

    public Integer getKjfw() {
        return kjfw;
    }

    public void setKjfw(Integer kjfw) {
        this.kjfw = kjfw;
    }

    public String getGjc() {
        return gjc;
    }

    public void setGjc(String gjc) {
        this.gjc = gjc == null ? null : gjc.trim();
    }

    public String getGzms() {
        return gzms;
    }

    public void setGzms(String gzms) {
        this.gzms = gzms == null ? null : gzms.trim();
    }

    public String getJyzj() {
        return jyzj;
    }

    public void setJyzj(String jyzj) {
        this.jyzj = jyzj == null ? null : jyzj.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd == null ? null : jd.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

	public List<String> getDetailIdList() {
		return detailIdList;
	}

	public void setDetailIdList(List<String> detailIdList) {
		this.detailIdList = detailIdList;
	}

	public List<MaintenanceFailureSummaryDetail> getSummaryDetailList() {
		return summaryDetailList;
	}

	public void setSummaryDetailList(
			List<MaintenanceFailureSummaryDetail> summaryDetailList) {
		this.summaryDetailList = summaryDetailList;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public String getSummaryDetailJson() {
		return summaryDetailJson;
	}

	public void setSummaryDetailJson(String summaryDetailJson) {
		this.summaryDetailJson = summaryDetailJson;
	}

	public String getAttachmentsJson() {
		return attachmentsJson;
	}

	public void setAttachmentsJson(String attachmentsJson) {
		this.attachmentsJson = attachmentsJson;
	}

	public String getJgslstr() {
		return jgslstr;
	}

	public void setJgslstr(String jgslstr) {
		this.jgslstr = jgslstr;
	}
    
}