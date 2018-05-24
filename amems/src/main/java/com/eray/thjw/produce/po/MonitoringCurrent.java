package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceProject;

/**
 * @Description B_S2_902 当前监控数据
 * @CreateTime 2017-9-23 下午3:29:48
 * @CreateBy 刘兵
 */
public class MonitoringCurrent extends BaseEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

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
    
    /** 用户 */
    private User zdr;
    
    /** 维修项目 */
    private MaintenanceProject maintenanceProject;
    
    /** EO */
    private EngineeringOrder engineeringOrder;
    
    /** 附件 */
    private Attachment attachment;

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

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public MaintenanceProject getMaintenanceProject() {
		return maintenanceProject;
	}

	public void setMaintenanceProject(MaintenanceProject maintenanceProject) {
		this.maintenanceProject = maintenanceProject;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public EngineeringOrder getEngineeringOrder() {
		return engineeringOrder;
	}

	public void setEngineeringOrder(EngineeringOrder engineeringOrder) {
		this.engineeringOrder = engineeringOrder;
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
		return "MonitoringCurrent [id=" + id + ", dprtcode=" + dprtcode
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", lylx=" + lylx + ", lyid=" + lyid + ", lybh=" + lybh
				+ ", wxfaid=" + wxfaid + ", wxfabh=" + wxfabh + ", eoxc="
				+ eoxc + ", fjzch=" + fjzch + ", fjxlh=" + fjxlh + ", zjqdid="
				+ zjqdid + ", bjh=" + bjh + ", xlh=" + xlh + ", jssj=" + jssj
				+ ", byxjksjid=" + byxjksjid + ", wz=" + wz + ", hdwz=" + hdwz
				+ ", jsgs=" + jsgs + ", zdr=" + zdr + ", maintenanceProject="
				+ maintenanceProject + ", engineeringOrder=" + engineeringOrder
				+ ", attachment=" + attachment + ", pagination=" + pagination
				+ ", getId()=" + getId() + ", getDprtcode()=" + getDprtcode()
				+ ", getWhbmid()=" + getWhbmid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getLylx()=" + getLylx()
				+ ", getLyid()=" + getLyid() + ", getLybh()=" + getLybh()
				+ ", getWxfaid()=" + getWxfaid() + ", getWxfabh()="
				+ getWxfabh() + ", getEoxc()=" + getEoxc() + ", getFjzch()="
				+ getFjzch() + ", getFjxlh()=" + getFjxlh() + ", getZjqdid()="
				+ getZjqdid() + ", getBjh()=" + getBjh() + ", getXlh()="
				+ getXlh() + ", getJssj()=" + getJssj() + ", getByxjksjid()="
				+ getByxjksjid() + ", getWz()=" + getWz() + ", getZdr()="
				+ getZdr() + ", getMaintenanceProject()="
				+ getMaintenanceProject() + ", getAttachment()="
				+ getAttachment() + ", getEngineeringOrder()="
				+ getEngineeringOrder() + ", getHdwz()=" + getHdwz()
				+ ", getJsgs()=" + getJsgs() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


}