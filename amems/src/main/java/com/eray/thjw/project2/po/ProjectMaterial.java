package com.eray.thjw.project2.po;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description 维修项目-关联部件关系B_G2_01204
 * @CreateTime 2017-8-14 下午3:19:53
 * @CreateBy 刘兵
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMaterial extends BaseEntity {
    private String id;

    private String mainid;

    private String bjh;
    
    private Integer xc;

    private String cj;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    /** 部件-监控项目 */
    private List<ProjectMonitor> projectMonitors;

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

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj == null ? null : cj.trim();
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

	public List<ProjectMonitor> getProjectMonitors() {
		return projectMonitors;
	}

	public void setProjectMonitors(List<ProjectMonitor> projectMonitors) {
		this.projectMonitors = projectMonitors;
	}
	
	public Integer getXc() {
		return xc;
	}

	public void setXc(Integer xc) {
		this.xc = xc;
	}

	@Override
	public String toString() {
		return "ProjectMaterial [id=" + id + ", mainid=" + mainid + ", bjh="
				+ bjh + ", xc=" + xc + ", cj=" + cj + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", projectMonitors="
				+ projectMonitors + ", pagination=" + pagination + ", getId()="
				+ getId() + ", getMainid()=" + getMainid() + ", getBjh()="
				+ getBjh() + ", getCj()=" + getCj() + ", getWhdwid()="
				+ getWhdwid() + ", getWhrid()=" + getWhrid() + ", getWhsj()="
				+ getWhsj() + ", getProjectMonitors()=" + getProjectMonitors()
				+ ", getXc()=" + getXc() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}