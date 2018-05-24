package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00602 航段信息
 * @CreateTime 2017年10月24日 下午4:58:36
 * @CreateBy 韩武
 */
public class FlightSheetLeg extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer hd;

    private String hdms;

    private String hz;

    private String jcrid;

    private String jcr;

    private String bz;
    
    private String dprtcode;
    
    private String fjzch;
    
    /** 完成工作 */
    private List<FlightSheetFinishedWork> finishedWorks;
    
    /** 防冰液信息 */
    private List<FlightSheetAntiIce> antiIces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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

    public Integer getHd() {
        return hd;
    }

    public void setHd(Integer hd) {
        this.hd = hd;
    }

    public String getHdms() {
        return hdms;
    }

    public void setHdms(String hdms) {
        this.hdms = hdms == null ? null : hdms.trim();
    }

    public String getHz() {
        return hz;
    }

    public void setHz(String hz) {
        this.hz = hz == null ? null : hz.trim();
    }

    public String getJcrid() {
        return jcrid;
    }

    public void setJcrid(String jcrid) {
        this.jcrid = jcrid == null ? null : jcrid.trim();
    }

    public String getJcr() {
        return jcr;
    }

    public void setJcr(String jcr) {
        this.jcr = jcr == null ? null : jcr.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

	public List<FlightSheetFinishedWork> getFinishedWorks() {
		return finishedWorks;
	}

	public void setFinishedWorks(List<FlightSheetFinishedWork> finishedWorks) {
		this.finishedWorks = finishedWorks;
	}

	public List<FlightSheetAntiIce> getAntiIces() {
		return antiIces;
	}

	public void setAntiIces(List<FlightSheetAntiIce> antiIces) {
		this.antiIces = antiIces;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
}