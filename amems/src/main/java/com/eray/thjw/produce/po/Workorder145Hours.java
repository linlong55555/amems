package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;

public class Workorder145Hours {
    private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String gxbh;

    private String gzzid;

    private BigDecimal jhgs;

    private String gxms;

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

    public String getGxbh() {
        return gxbh;
    }

    public void setGxbh(String gxbh) {
        this.gxbh = gxbh == null ? null : gxbh.trim();
    }

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public BigDecimal getJhgs() {
        return jhgs;
    }

    public void setJhgs(BigDecimal jhgs) {
        this.jhgs = jhgs;
    }

    public String getGxms() {
        return gxms;
    }

    public void setGxms(String gxms) {
        this.gxms = gxms == null ? null : gxms.trim();
    }
}