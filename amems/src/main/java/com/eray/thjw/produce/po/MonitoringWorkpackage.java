package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
/**
 * 
 * @Description b_s2_009待组包的监控项目
 * @CreateTime 2017年9月27日 下午5:23:23
 * @CreateBy 岳彬彬
 */
public class MonitoringWorkpackage extends BaseEntity{
    private String id;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String fjzch;

    private String jksjgdid;

    private String djbjksjid;

    private Integer lx;

    private String gbid;

    private Integer xsbs;

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

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getJksjgdid() {
        return jksjgdid;
    }

    public void setJksjgdid(String jksjgdid) {
        this.jksjgdid = jksjgdid == null ? null : jksjgdid.trim();
    }

    public String getDjbjksjid() {
        return djbjksjid;
    }

    public void setDjbjksjid(String djbjksjid) {
        this.djbjksjid = djbjksjid == null ? null : djbjksjid.trim();
    }

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public String getGbid() {
        return gbid;
    }

    public void setGbid(String gbid) {
        this.gbid = gbid == null ? null : gbid.trim();
    }

    public Integer getXsbs() {
        return xsbs;
    }

    public void setXsbs(Integer xsbs) {
        this.xsbs = xsbs;
    }
}