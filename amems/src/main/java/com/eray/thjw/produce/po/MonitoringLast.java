package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_S2_903 监控数据-上次执行数据
 * @CreateTime 2017-9-23 下午3:29:48
 * @CreateBy 刘兵
 */
public class MonitoringLast extends BaseEntity {
    private String id;

    private String jksjid;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String jklbh;

    private String jkflbh;

    private String scjhz;

    private String scsjz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJksjid() {
        return jksjid;
    }

    public void setJksjid(String jksjid) {
        this.jksjid = jksjid == null ? null : jksjid.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getScjhz() {
        return scjhz;
    }

    public void setScjhz(String scjhz) {
        this.scjhz = scjhz == null ? null : scjhz.trim();
    }

    public String getScsjz() {
        return scsjz;
    }

    public void setScsjz(String scsjz) {
        this.scsjz = scsjz == null ? null : scsjz.trim();
    }

	@Override
	public String toString() {
		return "MonitoringLast [id=" + id + ", jksjid=" + jksjid + ", whdwid="
				+ whdwid + ", whrid=" + whrid + ", whsj=" + whsj + ", zt=" + zt
				+ ", jklbh=" + jklbh + ", jkflbh=" + jkflbh + ", scjhz="
				+ scjhz + ", scsjz=" + scsjz + ", pagination=" + pagination
				+ ", getId()=" + getId() + ", getJksjid()=" + getJksjid()
				+ ", getWhdwid()=" + getWhdwid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getZt()=" + getZt()
				+ ", getJklbh()=" + getJklbh() + ", getJkflbh()=" + getJkflbh()
				+ ", getScjhz()=" + getScjhz() + ", getScsjz()=" + getScsjz()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
}