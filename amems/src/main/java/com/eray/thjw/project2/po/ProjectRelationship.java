package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description 维修项目-关联维修项目关系B_G2_01202
 * @CreateTime 2017-8-14 下午4:17:43
 * @CreateBy 刘兵
 */
public class ProjectRelationship extends BaseEntity {
    private String id;

    private String mainid;

    private String jx;

    private String wxxmbh;

    private Integer zt;

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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getWxxmbh() {
        return wxxmbh;
    }

    public void setWxxmbh(String wxxmbh) {
        this.wxxmbh = wxxmbh == null ? null : wxxmbh.trim();
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

	@Override
	public String toString() {
		return "ProjectRelationship [id=" + id + ", mainid=" + mainid + ", jx="
				+ jx + ", wxxmbh=" + wxxmbh + ", zt=" + zt + ", whdwid="
				+ whdwid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getMainid()=" + getMainid() + ", getJx()=" + getJx()
				+ ", getWxxmbh()=" + getWxxmbh() + ", getZt()=" + getZt()
				+ ", getWhdwid()=" + getWhdwid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}