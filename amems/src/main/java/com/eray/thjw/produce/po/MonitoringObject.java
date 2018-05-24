package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_S2_901 监控对象
 * @CreateTime 2017-9-23 下午3:29:48
 * @CreateBy 刘兵
 */
public class MonitoringObject extends BaseEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String fJksjid;

    private String bJksjid;

    private Integer lylx;

    private String lyid;

    private String lybh;

    private String wxfaid;

    private String wxfabh;

    private Integer eoxc;

    private String fjzch;

    private String fjxlh;

    private String zjqdid;

    private String bjh;

    private String xlh;

    private Date jssj;

    private String byxjksjid;

    private Integer wz;
    
    private Integer hdwz;
    
    private Integer jsgs;
    
    /** 监控数据-上次执行数据 */
    private List<MonitoringLast> monitoringLastList;
    
    /** 监控数据-(计划)执行数据 */
    private List<MonitoringPlan> monitoringPlanList;
    
    /** 待组包的监控项目 */
    private List<MonitoringWorkpackage> monitoringWorkpackageList;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getfJksjid() {
        return fJksjid;
    }

    public void setfJksjid(String fJksjid) {
        this.fJksjid = fJksjid == null ? null : fJksjid.trim();
    }

    public String getbJksjid() {
        return bJksjid;
    }

    public void setbJksjid(String bJksjid) {
        this.bJksjid = bJksjid == null ? null : bJksjid.trim();
    }

    public Integer getLylx() {
        return lylx;
    }

    public void setLylx(Integer lylx) {
        this.lylx = lylx;
    }

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLybh() {
        return lybh;
    }

    public void setLybh(String lybh) {
        this.lybh = lybh == null ? null : lybh.trim();
    }

    public String getWxfaid() {
        return wxfaid;
    }

    public void setWxfaid(String wxfaid) {
        this.wxfaid = wxfaid == null ? null : wxfaid.trim();
    }

    public String getWxfabh() {
        return wxfabh;
    }

    public void setWxfabh(String wxfabh) {
        this.wxfabh = wxfabh == null ? null : wxfabh.trim();
    }

    public Integer getEoxc() {
        return eoxc;
    }

    public void setEoxc(Integer eoxc) {
        this.eoxc = eoxc;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getFjxlh() {
        return fjxlh;
    }

    public void setFjxlh(String fjxlh) {
        this.fjxlh = fjxlh == null ? null : fjxlh.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getByxjksjid() {
        return byxjksjid;
    }

    public void setByxjksjid(String byxjksjid) {
        this.byxjksjid = byxjksjid == null ? null : byxjksjid.trim();
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

	public List<MonitoringPlan> getMonitoringPlanList() {
		return monitoringPlanList;
	}

	public void setMonitoringPlanList(List<MonitoringPlan> monitoringPlanList) {
		this.monitoringPlanList = monitoringPlanList;
	}

	public List<MonitoringLast> getMonitoringLastList() {
		return monitoringLastList;
	}

	public void setMonitoringLastList(List<MonitoringLast> monitoringLastList) {
		this.monitoringLastList = monitoringLastList;
	}

	public List<MonitoringWorkpackage> getMonitoringWorkpackageList() {
		return monitoringWorkpackageList;
	}

	public void setMonitoringWorkpackageList(
			List<MonitoringWorkpackage> monitoringWorkpackageList) {
		this.monitoringWorkpackageList = monitoringWorkpackageList;
	}

	public Integer getHdwz() {
		return hdwz;
	}

	public void setHdwz(Integer hdwz) {
		this.hdwz = hdwz;
	}

	public Integer getJsgs() {
		return jsgs;
	}

	public void setJsgs(Integer jsgs) {
		this.jsgs = jsgs;
	}

	@Override
	public String toString() {
		return "MonitoringObject [id=" + id + ", dprtcode=" + dprtcode
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", zt=" + zt + ", fJksjid=" + fJksjid + ", bJksjid="
				+ bJksjid + ", lylx=" + lylx + ", lyid=" + lyid + ", lybh="
				+ lybh + ", wxfaid=" + wxfaid + ", wxfabh=" + wxfabh
				+ ", eoxc=" + eoxc + ", fjzch=" + fjzch + ", fjxlh=" + fjxlh
				+ ", zjqdid=" + zjqdid + ", bjh=" + bjh + ", xlh=" + xlh
				+ ", jssj=" + jssj + ", byxjksjid=" + byxjksjid + ", wz=" + wz
				+ ", hdwz=" + hdwz + ", jsgs=" + jsgs + ", monitoringLastList="
				+ monitoringLastList + ", monitoringPlanList="
				+ monitoringPlanList + ", monitoringWorkpackageList="
				+ monitoringWorkpackageList + ", pagination=" + pagination
				+ ", getId()=" + getId() + ", getDprtcode()=" + getDprtcode()
				+ ", getWhbmid()=" + getWhbmid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getZt()=" + getZt()
				+ ", getfJksjid()=" + getfJksjid() + ", getbJksjid()="
				+ getbJksjid() + ", getLylx()=" + getLylx() + ", getLyid()="
				+ getLyid() + ", getLybh()=" + getLybh() + ", getWxfaid()="
				+ getWxfaid() + ", getWxfabh()=" + getWxfabh() + ", getEoxc()="
				+ getEoxc() + ", getFjzch()=" + getFjzch() + ", getFjxlh()="
				+ getFjxlh() + ", getZjqdid()=" + getZjqdid() + ", getBjh()="
				+ getBjh() + ", getXlh()=" + getXlh() + ", getJssj()="
				+ getJssj() + ", getByxjksjid()=" + getByxjksjid()
				+ ", getWz()=" + getWz() + ", getMonitoringPlanList()="
				+ getMonitoringPlanList() + ", getMonitoringLastList()="
				+ getMonitoringLastList() + ", getMonitoringWorkpackageList()="
				+ getMonitoringWorkpackageList() + ", getHdwz()=" + getHdwz()
				+ ", getJsgs()=" + getJsgs() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}