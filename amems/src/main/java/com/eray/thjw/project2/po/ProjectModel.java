package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description 维修项目对应飞机关系表B_G2_01203
 * @CreateTime 2017-8-14 下午4:16:55
 * @CreateBy 刘兵
 */
public class ProjectModel extends BaseEntity {
    private String id;

    private String mainid;

    private String fjzch;
    
    private Integer xc;

    private String whdwid;

    private String whrid;

    private Date whsj;

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

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
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

	public Integer getXc() {
		return xc;
	}

	public void setXc(Integer xc) {
		this.xc = xc;
	}

	@Override
	public String toString() {
		return "ProjectModel [id=" + id + ", mainid=" + mainid + ", fjzch="
				+ fjzch + ", xc=" + xc + ", whdwid=" + whdwid + ", whrid="
				+ whrid + ", whsj=" + whsj + ", pagination=" + pagination
				+ ", getId()=" + getId() + ", getMainid()=" + getMainid()
				+ ", getFjzch()=" + getFjzch() + ", getWhdwid()=" + getWhdwid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getXc()=" + getXc() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

    
}